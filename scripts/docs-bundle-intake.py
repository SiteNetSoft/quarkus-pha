#!/usr/bin/env python3
"""Intake a fresh PatternFly docs-site CSS export into patternfly-docs-bundle.css.

Steps (from the documented regen procedure):
  1. Self-concatenation check — the 6.5.2 export was the file duplicated
     byte-for-byte; reject if the top half equals the bottom half.
  2. Remap @font-face src URLs: the export emits hashed root-relative URLs
     like url(/dfe0e172.woff2) that don't exist here. Map by font-family +
     font-style (hash-independent) to the vendored font paths.
  3. Write the result over runtime/.../web/css/patternfly-docs-bundle.css,
     then minify it (esbuild via Podman — nothing node touches the host) into
     patternfly-docs-bundle.min.css, which is what layouts/_head.html serves.
     Both files are committed: the readable source stays diffable for the
     docs-CSS comparison workflow; the .min is the shipped artifact.
  4. Verify no unmapped root-hash font URLs remain.

Usage: python3 docs-bundle-intake.py <export.css> <repo-root>
"""
import re
import subprocess
import sys

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


def main(export_path, repo_root):
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

    # 3. write
    css_dir = f"{repo_root}/runtime/src/main/resources/META-INF/resources/web/css"
    out = f"{css_dir}/patternfly-docs-bundle.css"
    with open(out, "w", encoding="utf-8") as f:
        f.write(text if text.endswith("\n") else text + "\n")
    print(f"wrote {out}")

    # 3b. minify (esbuild in a Podman container; node never runs on the host)
    subprocess.run(
        ["podman", "run", "--rm", "-v", f"{css_dir}:/css:Z",
         "docker.io/library/node:22-alpine", "sh", "-c",
         "npx --yes esbuild /css/patternfly-docs-bundle.css --minify"
         " --outfile=/css/patternfly-docs-bundle.min.css"],
        check=True,
    )
    print(f"wrote {css_dir}/patternfly-docs-bundle.min.css")

    # 4. verify
    leftovers = HASH_URL_RE.findall(text)
    if leftovers:
        sys.exit(f"FATAL: unmapped hashed font URLs remain: {sorted(set(leftovers))}")
    print("verify: no hashed root font URLs remain — OK")


if __name__ == "__main__":
    if len(sys.argv) != 3:
        sys.exit(__doc__)
    main(sys.argv[1], sys.argv[2])
