#!/usr/bin/env python3
"""Build a claude.ai/design gallery bundle from the live dev server.

Captures one preview page per implemented component and assembles a
self-contained static bundle suitable for uploading to a claude.ai/design
design-system project (one card per component, grouped by template category).
The upload itself is done by Claude Code's DesignSync tool; this script only
produces the bundle directory plus an upload manifest.

Per component (id -> href parsed from HelloResource.java's IMPLEMENTED map):
  * Fetch the demo page and follow the FIRST standalone-example link
    (href="{href}/{slug}") — the minimal example-standalone wrapper.
  * Components without a standalone wrapper (HTMX server-driven patterns,
    some heavy custom components) fall back to capturing the demo page.
  * Rewrites: strip ?v=... cache busters; /web/... -> ../../web/... (pages
    land at components/{id}/index.html, depth 2); prepend the
    <!-- @dsCard group="..." --> marker the Design System pane indexes.
    Group = runtime template category dir, title-cased; "Composed" when the
    component has no runtime template.

Assets: whole web/css, web/js, and every vendor dir EXCEPT
  * icons/  — resolved server-side into inline SVG; browsers never fetch it
  * monaco/ — only the demo pages' Qute-source toggle uses it; omitting it
    just disables that toggle in fallback captures
plus a final scan pass that copies any page-referenced web/ file still
missing, and fails loudly on references that don't exist in the source tree.

Usage:
  python3 scripts/design-sync-bundle.py <out-dir> [--server http://localhost:9090]

The dev server must be running (see MEMORY: port 9090). Output:
  <out-dir>/components/{id}/index.html   one card page per component
  <out-dir>/web/...                      shared assets
  <out-dir>/manifest.json                sorted path list for DesignSync
                                         write_files (path == localPath),
                                         chunk it at <=256 entries per call
"""

import argparse
import json
import os
import re
import shutil
import sys
import urllib.request

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
WEB = os.path.join(ROOT, "runtime/src/main/resources/META-INF/resources/web")
TEMPLATES = os.path.join(ROOT, "runtime/src/main/resources/templates/components")
HELLO = os.path.join(
    ROOT, "integration-tests/src/main/java/org/sitenetsoft/quarkus/pha/it/HelloResource.java"
)
SKIP_VENDOR = {"icons", "monaco"}


def fetch(server, path):
    try:
        with urllib.request.urlopen(server + path, timeout=30) as r:
            return r.read().decode("utf-8"), r.status
    except urllib.error.HTTPError as e:
        return "", e.code


def implemented_components():
    src = open(HELLO).read()
    return dict(re.findall(r'Map\.entry\("([a-z0-9-]+)",\s*"(/[^"]+)"\)', src[: src.index("LABELS")]))


def category_groups():
    groups = {}
    for d in sorted(os.listdir(TEMPLATES)):
        cat_dir = os.path.join(TEMPLATES, d)
        if not os.path.isdir(cat_dir):
            continue
        for f in os.listdir(cat_dir):
            if f.endswith(".html"):
                groups[f[:-5]] = d.replace("-", " ").title()
    return groups


def rewrite(page):
    page = re.sub(r"\?v=[A-Za-z0-9._-]+", "", page)
    return re.sub(r'(href|src)="/web/', r'\1="../../web/', page)


def main():
    ap = argparse.ArgumentParser(description=__doc__.splitlines()[0])
    ap.add_argument("out")
    ap.add_argument("--server", default="http://localhost:9090")
    args = ap.parse_args()

    impl = implemented_components()
    groups = category_groups()
    captured, fallbacks, failed = [], [], []

    for cid, href in sorted(impl.items()):
        demo, code = fetch(args.server, href)
        if code != 200:
            failed.append((cid, f"demo {code}"))
            continue
        m = re.search(r'href="(%s/[a-z0-9-]+)"' % re.escape(href), demo)
        if m:
            page, code = fetch(args.server, m.group(1))
            if code != 200:
                failed.append((cid, f"standalone {code}"))
                continue
        else:
            page = demo
            fallbacks.append(cid)
        out_dir = os.path.join(args.out, "components", cid)
        os.makedirs(out_dir, exist_ok=True)
        with open(os.path.join(out_dir, "index.html"), "w") as f:
            f.write(f'<!-- @dsCard group="{groups.get(cid, "Composed")}" -->\n' + rewrite(page))
        captured.append(cid)

    for sub in ("css", "js"):
        dst = os.path.join(args.out, "web", sub)
        if os.path.isdir(dst):
            shutil.rmtree(dst)
        shutil.copytree(os.path.join(WEB, sub), dst)
    for d in sorted(os.listdir(os.path.join(WEB, "vendor"))):
        if d in SKIP_VENDOR or not os.path.isdir(os.path.join(WEB, "vendor", d)):
            continue
        dst = os.path.join(args.out, "web/vendor", d)
        if os.path.isdir(dst):
            shutil.rmtree(dst)
        shutil.copytree(os.path.join(WEB, "vendor", d), dst)
    # pages reference only the minified bundle; drop the readable copy (939KB)
    os.remove(os.path.join(args.out, "web/css/patternfly-docs-bundle.css"))

    unresolved = []
    for cid in captured:
        page = open(os.path.join(args.out, "components", cid, "index.html")).read()
        for ref in re.findall(r'(?:href|src)="\.\./\.\./(web/[^"#?]+)', page):
            dst = os.path.join(args.out, ref)
            if os.path.exists(dst) or "/monaco/" in ref:
                continue
            src_f = os.path.join(WEB, ref[len("web/"):])
            if os.path.exists(src_f):
                os.makedirs(os.path.dirname(dst), exist_ok=True)
                shutil.copy(src_f, dst)
            else:
                unresolved.append((cid, ref))

    paths = sorted(
        os.path.relpath(os.path.join(r, f), args.out)
        for r, _, fs in os.walk(args.out)
        for f in fs
        if f != "manifest.json"
    )
    with open(os.path.join(args.out, "manifest.json"), "w") as f:
        json.dump([{"path": p, "localPath": p} for p in paths], f, indent=1)

    print(f"captured {len(captured)} components ({len(fallbacks)} demo-page fallbacks: {fallbacks})")
    print(f"bundle: {len(paths)} files -> {args.out}")
    if failed:
        print(f"FAILED pages: {failed}", file=sys.stderr)
    if unresolved:
        print(f"UNRESOLVED asset refs: {unresolved}", file=sys.stderr)
    return 1 if (failed or unresolved) else 0


if __name__ == "__main__":
    sys.exit(main())
