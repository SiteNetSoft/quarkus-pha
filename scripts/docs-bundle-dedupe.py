#!/usr/bin/env python3
"""Dedupe patternfly-docs-bundle.css against the vendor PatternFly CSS.

The docs bundle (a hand-edited patternfly.org docs-site export) embeds a large
copy of core PatternFly rules that the page already gets from
vendor/patternfly/patternfly.min.css + patternfly-addons.min.css, both of which
load BEFORE the bundle. This tool removes a bundle declaration only when the
vendor CSS contains an exact duplicate — same at-rule context, same selector,
same property, same value (after conservative whitespace/leading-zero
normalization). Everything else — docs-site .ws-* styles, hand edits, value or
selector overrides — is kept verbatim.

Safety rules:
  * !important declarations are never dropped.
  * Whole at-rules with non-declaration bodies (@keyframes, @font-face) are
    dropped only when the entire normalized block is identical in vendor.
  * Cascade groups are all-or-nothing. All bundle declarations sharing a
    selector subject (rightmost compound) and property family (shorthand
    prefix, e.g. border/border-style; gap/column-gap) form one cascade group,
    across media queries. Dropping only part of such a group reorders which
    declaration wins (base vs media override, shorthand vs longhand), so a
    group is dropped only when EVERY member is a vendor duplicate; otherwise
    the whole group is kept verbatim.

Usage:
  python3 scripts/docs-bundle-dedupe.py [--cooccur <classes.txt>] \\
      <bundle.css> <out.css> <vendor.css>...

docs-bundle-intake.py runs this automatically on every intake (harvesting
the co-occurrence sets itself); this CLI remains for standalone runs.

--cooccur: file with one space-separated class-set per line (the class=""
attributes found in the project's templates). Rules whose selectors share no
simple selector can still fight over one element when the markup stacks their
classes (e.g. class="catalog-tile-pf-icon pf-v6-c-icon fas fa-bolt"); the
co-occurrence sets union those cascade groups so they stay all-or-nothing.
Harvest it from the templates with:

  python3 - <<'PY'
  import re, glob
  sets = set()
  for f in glob.glob('runtime/src/main/resources/templates/**/*.html', recursive=True) \
         + glob.glob('integration-tests/src/main/resources/templates/**/*.html', recursive=True):
      for m in re.finditer(r'class="([^"]*)"', open(f).read()):
          toks = [t for t in m.group(1).split() if re.fullmatch(r'[A-Za-z][A-Za-z0-9_-]*', t)]
          if len(toks) > 1:
              sets.add(' '.join(sorted(set(toks))))
  open('cooccur.txt', 'w').write('\n'.join(sorted(sets)))
  PY

After any regen, verify render-neutrality with the snapshot harness
(integration-tests/e2e/_snapsweep.mjs) against the live dev server before
replacing the bundle — see the docs-bundle memory notes for the procedure.
"""
import re
import sys


def strip_comments(css):
    out = []
    i, n = 0, len(css)
    while i < n:
        c = css[i]
        if c == '/' and i + 1 < n and css[i + 1] == '*':
            j = css.find('*/', i + 2)
            i = n if j < 0 else j + 2
        elif c in '"\'':
            j = i + 1
            while j < n and css[j] != c:
                j += 2 if css[j] == '\\' else 1
            out.append(css[i:min(j + 1, n)])
            i = j + 1
        else:
            out.append(c)
            i += 1
    return ''.join(out)


def split_top(css):
    """Split css text into top-level statements: (kind, header, body).

    kind is 'rule' (header { body }) or 'stmt' (@import ...;)."""
    items = []
    i, n = 0, len(css)
    start = i
    depth = 0
    header_end = -1
    while i < n:
        c = css[i]
        if c in '"\'':
            j = i + 1
            while j < n and css[j] != c:
                j += 2 if css[j] == '\\' else 1
            i = j + 1
            continue
        if c == '{':
            if depth == 0:
                header_end = i
            depth += 1
        elif c == '}':
            depth -= 1
            if depth == 0:
                items.append(('rule', css[start:header_end].strip(),
                              css[header_end + 1:i]))
                start = i + 1
                header_end = -1
        elif c == ';' and depth == 0:
            stmt = css[start:i].strip()
            if stmt:
                items.append(('stmt', stmt, None))
            start = i + 1
        i += 1
    tail = css[start:].strip()
    if tail:
        items.append(('stmt', tail, None))
    return items


WS = re.compile(r'\s+')
LEADZERO = re.compile(r'(?<![0-9.])\.([0-9])')
# Minifiers rewrite CSS2-era pseudo-elements to single-colon form.
DOUBLE_COLON = re.compile(r'::(before|after|first-line|first-letter)\b')


def norm(s):
    s = WS.sub(' ', s.strip())
    s = LEADZERO.sub(r'0.\1', s)
    s = re.sub(r'\s*([:,(){}>+~])\s*', r'\1', s)
    return s


def split_sel_list(s):
    """Split a selector list on top-level commas only (not inside :is(...))."""
    parts, buf, depth = [], [], 0
    for c in s:
        if c == '(':
            depth += 1
        elif c == ')':
            depth -= 1
        if c == ',' and depth == 0:
            parts.append(''.join(buf))
            buf = []
        else:
            buf.append(c)
    parts.append(''.join(buf))
    return [p for p in (x.strip() for x in parts) if p]


def norm_sel(s):
    """Normalize a selector for cross-minifier matching: single-colon pseudo
    elements and order-insensitive selector lists (minifiers sort them)."""
    s = DOUBLE_COLON.sub(r':\1', norm(s))
    return ','.join(sorted(split_sel_list(s)))


# Properties whose shorthand is not a hyphen-prefix of its longhands.
FAMILY_MAP = {
    'gap': 'gap', 'row-gap': 'gap', 'column-gap': 'gap',
    'top': 'inset', 'right': 'inset', 'bottom': 'inset', 'left': 'inset',
    'inset': 'inset',
}
PREFIX = re.compile(r'^-(?:webkit|moz|ms|o)-')


def prop_family(prop):
    p = PREFIX.sub('', prop.lower())
    if p in FAMILY_MAP:
        return FAMILY_MAP[p]
    if p.startswith('inset-'):
        return 'inset'
    return p.split('-', 1)[0] if not p.startswith('--') else p


SIMPLE_SEL = re.compile(r'[.#][A-Za-z0-9_-]+|(?:^|(?<=[(,\s>+~]))[a-zA-Z][a-zA-Z0-9-]*')
# Pseudo-classes that name an element rather than a state — valid group keys.
# State/structural pseudos (:hover, :first-child, …) are ignored: they would
# union unrelated components into one giant cascade group.
IDENTITY_PSEUDO = re.compile(r':{1,2}(?:root|host|backdrop|scope)\b')


def subjects(sel):
    """Simple selectors (classes/ids/types/pseudos) of the rightmost compound
    of each member of a selector list. Compounds that share ANY simple
    selector can match the same element (.pf-v6-c-button.pf-m-plain vs
    .pf-m-favorited on one button), so each simple selector is its own group
    key. Members that are functional pseudo-classes expand to their
    arguments' subjects, so :is(.a, .b h1) contributes the subjects of both
    .a and .b h1. A universal subject ('.body + *') falls back to the whole
    member's simple selectors — the left-hand side scopes what it can fight
    with; if nothing at all is found, '*' marks the declaration undroppable."""
    out = set()
    for member in split_sel_list(DOUBLE_COLON.sub(r':\1', norm(sel))):
        compound = split_combinators(member)
        m = re.fullmatch(r':(?:where|is|not)\((.*)\)', compound)
        if m:
            out |= subjects(m.group(1))
            continue
        toks = set(SIMPLE_SEL.findall(compound)) | set(IDENTITY_PSEUDO.findall(compound))
        if not toks:
            toks = set(SIMPLE_SEL.findall(member)) | set(IDENTITY_PSEUDO.findall(member))
        out |= toks or {'*'}
    return out


def split_combinators(member):
    """Rightmost compound of a single selector (top-level combinators only)."""
    parts, buf, depth = [], [], 0
    for c in member:
        if c == '(':
            depth += 1
        elif c == ')':
            depth -= 1
        if c in ' >+~' and depth == 0:
            if buf:
                parts.append(''.join(buf))
                buf = []
        else:
            buf.append(c)
    if buf:
        parts.append(''.join(buf))
    return parts[-1] if parts else ''


def parse_decls(body):
    """Split a rule body into (prop, value, important) declarations."""
    decls = []
    for part in split_decl_list(body):
        if ':' not in part:
            continue
        prop, val = part.split(':', 1)
        prop, val = prop.strip(), val.strip()
        important = False
        m = re.search(r'!\s*important\s*$', val, re.I)
        if m:
            important = True
            val = val[:m.start()].rstrip()
        decls.append((prop, val, important))
    return decls


def split_decl_list(body):
    parts, buf, i, n = [], [], 0, len(body)
    depth = 0
    while i < n:
        c = body[i]
        if c in '"\'':
            j = i + 1
            while j < n and body[j] != c:
                j += 2 if body[j] == '\\' else 1
            buf.append(body[i:min(j + 1, n)])
            i = j + 1
            continue
        if c == '(':
            depth += 1
        elif c == ')':
            depth -= 1
        if c == ';' and depth == 0:
            parts.append(''.join(buf).strip())
            buf = []
        else:
            buf.append(c)
        i += 1
    last = ''.join(buf).strip()
    if last:
        parts.append(last)
    return [p for p in parts if p]


def walk(css, ctx, on_decl_rule, on_atomic):
    """Walk statements; recurse into conditional groups (@media/@supports/@layer)."""
    for kind, header, body in split_top(css):
        if kind == 'stmt':
            continue
        if header.startswith('@'):
            name = header.split('(')[0].split()[0].lower()
            if name in ('@media', '@supports', '@layer', '@container'):
                walk(body, ctx + (norm(header),), on_decl_rule, on_atomic)
            else:
                on_atomic(ctx, header, body)  # @keyframes, @font-face, ...
        else:
            on_decl_rule(ctx, header, body)


def main():
    args = sys.argv[1:]
    cooccur_sets = []
    if args[0] == '--cooccur':
        for line in open(args[1]):
            toks = ['.' + t for t in line.split()]
            if len(toks) > 1:
                cooccur_sets.append(toks)
        args = args[2:]
    bundle_path, out_path = args[0], args[1]
    vendor_paths = args[2:]

    # Union-find over group keys, seeded by markup class co-occurrence: for
    # each class-set and property family, all (class, family) keys are one
    # cascade group.
    parent = {}
    def find(k):
        parent.setdefault(k, k)
        while parent[k] != k:
            parent[k] = parent[parent[k]]
            k = parent[k]
        return k
    def union(a, b):
        ra, rb = find(a), find(b)
        if ra != rb:
            parent[ra] = rb
    fams_by_class = {}

    vendor_decls = set()   # (ctx, sel, prop, value, important)
    vendor_atomics = set() # (ctx, norm(header{body}))

    def v_rule(ctx, sel, body):
        nsel = norm_sel(sel)
        for prop, val, imp in parse_decls(body):
            vendor_decls.add((ctx, nsel, norm(prop), norm(val), imp))

    def v_atomic(ctx, header, body):
        vendor_atomics.add((ctx, norm(header) + '{' + norm(body) + '}'))

    for vp in vendor_paths:
        walk(strip_comments(open(vp).read()), (), v_rule, v_atomic)

    bundle_css = strip_comments(open(bundle_path).read())

    # Pass 1: build cascade groups. Group key = (selector subject, property
    # family), ignoring media context — a media override and its base rule
    # belong to the same group. A declaration is droppable only if every
    # group it touches is clean; and any KEPT declaration dirties all its
    # groups (it stays in the file, so partners it can shadow or be shadowed
    # by must stay too). Iterate to a fixpoint so dirt propagates across
    # rules whose subject sets overlap without being equal.
    all_decls = []
    def scan_rule(ctx, sel, body):
        nsel = norm_sel(sel)
        subs = subjects(sel)
        for prop, val, imp in parse_decls(body):
            dup = (ctx, nsel, norm(prop), norm(val), imp) in vendor_decls
            if '*' in subs:
                dup = False  # undroppable, and must dirty its groups
            fam = prop_family(prop)
            groups = frozenset((s, fam) for s in subs)
            all_decls.append((groups, dup, imp))
            for s in subs:
                fams_by_class.setdefault(s, set()).add(fam)
    walk(bundle_css, (), scan_rule, lambda *a: None)

    for cset in cooccur_sets:
        present = [c for c in cset if c in fams_by_class]
        fams = set()
        for c in present:
            fams |= fams_by_class[c]
        for fam in fams:
            keyed = [(c, fam) for c in present if fam in fams_by_class[c]]
            for k in keyed[1:]:
                union(keyed[0], k)

    canon = lambda groups: frozenset(find(g) for g in groups)
    all_decls = [(canon(groups), dup, imp) for groups, dup, imp in all_decls]

    dirty_groups = set()
    changed = True
    while changed:
        changed = False
        for groups, dup, imp in all_decls:
            kept = imp or not dup or any(g in dirty_groups for g in groups)
            if kept:
                for g in groups:
                    if g not in dirty_groups:
                        dirty_groups.add(g)
                        changed = True

    stats = {'decl_kept': 0, 'decl_dropped': 0, 'atomic_dropped': 0,
             'atomic_kept': 0, 'rules_emptied': 0}

    def rebuild(css, ctx):
        out = []
        for kind, header, body in split_top(css):
            if kind == 'stmt':
                out.append(header + ';\n')
                continue
            if header.startswith('@'):
                name = header.split('(')[0].split()[0].lower()
                if name in ('@media', '@supports', '@layer', '@container'):
                    inner = rebuild(body, ctx + (norm(header),))
                    if inner.strip():
                        out.append(header + ' {\n' + inner + '}\n')
                    continue
                key = (ctx, norm(header) + '{' + norm(body) + '}')
                if key in vendor_atomics:
                    stats['atomic_dropped'] += 1
                else:
                    stats['atomic_kept'] += 1
                    out.append(header + ' {' + body + '}\n')
                continue
            nsel = norm_sel(header)
            subs = subjects(header)
            kept = []
            for prop, val, imp in parse_decls(body):
                nprop, nval = norm(prop), norm(val)
                dup = (ctx, nsel, nprop, nval, imp) in vendor_decls
                fam = prop_family(prop)
                dirty = '*' in subs or any(
                    find((s, fam)) in dirty_groups for s in subs)
                if dup and not imp and not dirty:
                    stats['decl_dropped'] += 1
                else:
                    stats['decl_kept'] += 1
                    kept.append('  %s: %s%s;' % (prop, val,
                                ' !important' if imp else ''))
            if kept:
                out.append(header + ' {\n' + '\n'.join(kept) + '\n}\n')
            else:
                stats['rules_emptied'] += 1
        return ''.join(out)

    result = rebuild(bundle_css, ())
    with open(out_path, 'w') as f:
        f.write(result)
    print('vendor decls: %d, vendor atomic blocks: %d'
          % (len(vendor_decls), len(vendor_atomics)))
    for k, v in stats.items():
        print('%s: %d' % (k, v))


if __name__ == '__main__':
    main()
