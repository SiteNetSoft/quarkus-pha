#!/usr/bin/env python3
"""Intake a fresh PatternFly docs-site CSS export into patternfly-docs-bundle.css.

Steps (from the documented regen procedure):
  1. Self-concatenation check — the 6.5.2 export was the file duplicated
     byte-for-byte; reject if the top half equals the bottom half.
  2. Remap @font-face src URLs: the export emits hashed root-relative URLs
     like url(/dfe0e172.woff2) that don't exist here. Map by font-family +
     font-style (hash-independent) to the vendored font paths.
  3. Dedupe against the vendor PatternFly CSS (docs-bundle-dedupe.py, run as
     a subprocess): harvest class co-occurrence from the project templates,
     then drop every bundle declaration the vendor files already provide,
     under that script's cascade-group safety rules. Skippable with
     --skip-dedupe for debugging an intake in isolation.
  4. Write the result over runtime/.../web/css/patternfly-docs-bundle.css,
     then minify it (esbuild via Podman — nothing node touches the host) into
     patternfly-docs-bundle.min.css, which is what layouts/_head.html serves.
     Both files are committed: the readable source stays diffable for the
     docs-CSS comparison workflow; the .min is the shipped artifact.
  5. Verify no unmapped root-hash font URLs remain.

The dedupe is NOT self-verifying: after every intake, run the snapshot sweep
(integration-tests/e2e/_snapsweep.mjs, old vs new bundle bytes against the
live dev server) before committing. Cascade-order regressions are silent.

Usage: python3 docs-bundle-intake.py [--skip-dedupe] <export.css> <repo-root>
"""
import glob
import os
import re
import subprocess
import sys
import tempfile

FONT_MAP = {
    ("Red Hat Text", "normal"): "/web/vendor/patternfly/assets/fonts/RedHatText/RedHatTextVF.woff2",
    ("Red Hat Text", "italic"): "/web/vendor/patternfly/assets/fonts/RedHatText/RedHatTextVF-Italic.woff2",
    ("Red Hat Display", "normal"): "/web/vendor/patternfly/assets/fonts/RedHatDisplay/RedHatDisplayVF.woff2",
    ("Red Hat Display", "italic"): "/web/vendor/patternfly/assets/fonts/RedHatDisplay/RedHatDisplayVF-Italic.woff2",
    ("Red Hat Mono", "normal"): "/web/vendor/patternfly/assets/fonts/RedHatMono/RedHatMonoVF.woff2",
    ("Red Hat Mono", "italic"): "/web/vendor/patternfly/assets/fonts/RedHatMono/RedHatMonoVF-Italic.woff2",
    ("Font Awesome 5 Free", "normal"): "/web/vendor/patternfly/assets/fonts/webfonts/fa-solid-900.woff2",
    ("Font Awesome 6 Free", "normal"): "/web/vendor/patternfly/assets/fonts/webfonts/fa-solid-900.woff2",
    ("pf-v6-pficon", "normal"): "/web/vendor/patternfly/assets/pficon/pf-v6-pficon.woff2",
}

HASH_URL_RE = re.compile(r"url\(/[0-9a-f]{8}\.woff2?\)")


def harvest_cooccur(repo_root):
    """Class-sets that co-occur on one element in the project templates.

    Same harvest as the snippet in docs-bundle-dedupe.py's docstring: rules
    whose selectors share no simple selector can still fight over one element
    when the markup stacks their classes, so the dedupe needs these sets to
    union the affected cascade groups.
    """
    sets = set()
    for pattern in ("runtime/src/main/resources/templates/**/*.html",
                    "integration-tests/src/main/resources/templates/**/*.html"):
        for f in glob.glob(os.path.join(repo_root, pattern), recursive=True):
            for m in re.finditer(r'class="([^"]*)"', open(f, encoding="utf-8").read()):
                toks = [t for t in m.group(1).split() if re.fullmatch(r"[A-Za-z][A-Za-z0-9_-]*", t)]
                if len(toks) > 1:
                    sets.add(" ".join(sorted(set(toks))))
    return sorted(sets)


def dedupe(text, repo_root):
    vendor_dir = f"{repo_root}/runtime/src/main/resources/META-INF/resources/web/vendor/patternfly"
    vendor = [f"{vendor_dir}/patternfly.min.css", f"{vendor_dir}/patternfly-addons.min.css"]
    for v in vendor:
        if not os.path.exists(v):
            sys.exit(f"FATAL: vendor CSS missing: {v} (run download-frontend-deps.sh first)")
    dedupe_script = os.path.join(os.path.dirname(os.path.abspath(__file__)), "docs-bundle-dedupe.py")
    with tempfile.TemporaryDirectory() as tmp:
        cooccur = os.path.join(tmp, "cooccur.txt")
        with open(cooccur, "w", encoding="utf-8") as f:
            f.write("\n".join(harvest_cooccur(repo_root)))
        bundle_in = os.path.join(tmp, "bundle.css")
        with open(bundle_in, "w", encoding="utf-8") as f:
            f.write(text)
        deduped_out = os.path.join(tmp, "deduped.css")
        subprocess.run(
            [sys.executable, dedupe_script, "--cooccur", cooccur, bundle_in, deduped_out] + vendor,
            check=True,
        )
        return open(deduped_out, encoding="utf-8").read()


def main(export_path, repo_root, skip_dedupe=False):
    text = open(export_path, encoding="utf-8").read()
    lines = text.splitlines()
    n = len(lines)
    print(f"export: {n} lines")

    # 1. self-concatenation check
    if n % 2 == 0 and lines[: n // 2] == lines[n // 2 :]:
        sys.exit("FATAL: export is the file concatenated to itself (top half == bottom half). "
                 "Deduplicate first: head -n {} <file>".format(n // 2))

    # 2. remap @font-face blocks
    remapped, unmatched = 0, []
    def fix_block(m):
        nonlocal remapped
        block = m.group(0)
        fam = re.search(r'font-family:\s*"([^"]+)"', block)
        style = re.search(r"font-style:\s*(\w+)", block)
        key = (fam.group(1) if fam else "?", style.group(1) if style else "normal")
        target = FONT_MAP.get(key)
        if target is None:
            unmatched.append(key)
            return block
        new_block, nsub = HASH_URL_RE.subn(f"url({target})", block)
        if nsub:
            remapped += nsub
        return new_block

    text = re.sub(r"@font-face\s*\{[^}]*\}", fix_block, text)
    print(f"remapped {remapped} font URLs")
    if unmatched:
        sys.exit(f"FATAL: @font-face families with no mapping: {unmatched}")

    # 3. dedupe against vendor PatternFly CSS
    if skip_dedupe:
        print("dedupe: SKIPPED (--skip-dedupe)")
    else:
        before = len(text)
        text = dedupe(text, repo_root)
        print(f"dedupe: {before} -> {len(text)} bytes")

    # 4. write
    css_dir = f"{repo_root}/runtime/src/main/resources/META-INF/resources/web/css"
    out = f"{css_dir}/patternfly-docs-bundle.css"
    with open(out, "w", encoding="utf-8") as f:
        f.write(text if text.endswith("\n") else text + "\n")
    print(f"wrote {out}")

    # 4b. minify (esbuild in a Podman container; node never runs on the host)
    subprocess.run(
        ["podman", "run", "--rm", "-v", f"{css_dir}:/css:Z",
         "docker.io/library/node:22-alpine", "sh", "-c",
         "npx --yes esbuild /css/patternfly-docs-bundle.css --minify"
         " --outfile=/css/patternfly-docs-bundle.min.css"],
        check=True,
    )
    print(f"wrote {css_dir}/patternfly-docs-bundle.min.css")

    # 5. verify
    leftovers = HASH_URL_RE.findall(text)
    if leftovers:
        sys.exit(f"FATAL: unmapped hashed font URLs remain: {sorted(set(leftovers))}")
    print("verify: no hashed root font URLs remain — OK")

    print("\nNEXT (mandatory): snapshot-verify render-neutrality with"
          " integration-tests/e2e/_snapsweep.mjs (old vs new bundle bytes"
          " against the live dev server) before committing.")


if __name__ == "__main__":
    argv = sys.argv[1:]
    skip = "--skip-dedupe" in argv
    argv = [a for a in argv if a != "--skip-dedupe"]
    if len(argv) != 2:
        sys.exit(__doc__)
    main(argv[0], argv[1], skip_dedupe=skip)
