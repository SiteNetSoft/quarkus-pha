#!/bin/bash
set -euo pipefail

# Reference / inventory checks tailored to this project's vendored-frontend
# architecture. Off-the-shelf tools (knip, depcheck, ts-prune) don't fit
# because the project has no ES module imports — everything works through
# <script>-tag globals and a manual vendor copy step.
#
# Three sub-checks:
#   1. vendor-refs:    every <script src=...> / <link href=...> in templates
#                      resolves to an existing file under web/.
#   2. deps-vs-vendor: every dep in runtime web/package.json is copied by
#                      download-frontend-deps.sh, and every copied package
#                      has a matching dep entry.
#   3. unused-js:      every .js file under web/js/ is referenced by at
#                      least one template (or another JS file).
#
# Reports land under .reports/references/.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
WEB_DIR="$PROJECT_ROOT/runtime/src/main/resources/META-INF/resources/web"
TEMPLATE_DIR="$PROJECT_ROOT/runtime/src/main/resources/templates"
WEB_PACKAGE_JSON="$WEB_DIR/package.json"
DOWNLOAD_SCRIPT="$SCRIPT_DIR/download-frontend-deps.sh"

REPORT_DIR="${REPORT_DIR:-$PROJECT_ROOT/.reports/references}"
rm -rf "$REPORT_DIR"
mkdir -p "$REPORT_DIR"

VENDOR_REFS_REPORT="$REPORT_DIR/vendor-refs.txt"
DEPS_VS_VENDOR_REPORT="$REPORT_DIR/deps-vs-vendor.txt"
UNUSED_JS_REPORT="$REPORT_DIR/unused-js.txt"
SUMMARY="$REPORT_DIR/summary.txt"

VENDOR_REFS_FAIL=0
DEPS_VS_VENDOR_FAIL=0
UNUSED_JS_FAIL=0

# ───────────────────────────────────────────────────────────────────────────
# Sub-check 1: vendor-refs
# Every "/web/..." URL in a <script src=...> or <link href=...> attribute
# must resolve to an existing file under runtime/.../web/.
# ───────────────────────────────────────────────────────────────────────────

echo "==> Sub-check 1: vendor-refs"
{
  printf '# vendor-refs — broken /web/* references in templates\n\n'

  missing_count=0
  while IFS= read -r ref; do
    # ref looks like  /web/vendor/alpine/alpine.min.js  (no query string here)
    rel="${ref#/web/}"
    file="$WEB_DIR/$rel"
    if [ ! -f "$file" ]; then
      missing_count=$((missing_count + 1))
      printf '  MISSING %s\n' "$ref"
    fi
  done < <(
    grep -rhoE '(src|href)="(/web/[^"]+)"' "$TEMPLATE_DIR" 2>/dev/null \
      | sed -E 's/^(src|href)="//; s/"$//' \
      | sort -u
  )

  printf '\n%s missing reference(s)\n' "$missing_count"
  if [ "$missing_count" -ne 0 ]; then
    VENDOR_REFS_FAIL=1
  fi
} > "$VENDOR_REFS_REPORT"
cat "$VENDOR_REFS_REPORT" | tail -2

# ───────────────────────────────────────────────────────────────────────────
# Sub-check 2: deps-vs-vendor
# Every package in web/package.json "dependencies" must be cp'd into vendor
# by download-frontend-deps.sh, and every package cp'd by that script must
# appear in dependencies.
# ───────────────────────────────────────────────────────────────────────────

echo "==> Sub-check 2: deps-vs-vendor"
{
  printf '# deps-vs-vendor — drift between web/package.json and download-frontend-deps.sh\n\n'

  # Pull bare package names from "dependencies" (handles @scope/name)
  deps=$(awk '
    /"dependencies"[[:space:]]*:[[:space:]]*\{/ {inside=1; next}
    inside && /^[[:space:]]*\}/ {inside=0}
    inside {
      if (match($0, /"([^"]+)"[[:space:]]*:/, m)) print m[1]
    }
  ' "$WEB_PACKAGE_JSON" | sort -u)

  # Pull copied package names from the cp commands (node_modules/<pkg>/...)
  copied=$(grep -oE 'node_modules/(@[^/]+/)?[^/[:space:]]+' "$DOWNLOAD_SCRIPT" \
    | sed 's|node_modules/||' \
    | sort -u)

  printf 'Declared in web/package.json:\n'
  printf '  %s\n' $deps
  printf '\nCopied by download-frontend-deps.sh:\n'
  printf '  %s\n' $copied
  printf '\n'

  declared_only=$(comm -23 <(printf '%s\n' "$deps") <(printf '%s\n' "$copied"))
  copied_only=$(comm -13 <(printf '%s\n' "$deps") <(printf '%s\n' "$copied"))

  drift=0
  if [ -n "$declared_only" ]; then
    drift=$((drift + $(printf '%s\n' "$declared_only" | wc -l)))
    printf 'DECLARED but NOT copied (the script ignores these):\n'
    printf '  %s\n' $declared_only
    printf '\n'
  fi
  if [ -n "$copied_only" ]; then
    drift=$((drift + $(printf '%s\n' "$copied_only" | wc -l)))
    printf 'COPIED but NOT declared (drift in download script):\n'
    printf '  %s\n' $copied_only
    printf '\n'
  fi

  printf '%s drift item(s)\n' "$drift"
  if [ "$drift" -ne 0 ]; then
    DEPS_VS_VENDOR_FAIL=1
  fi
} > "$DEPS_VS_VENDOR_REPORT"
cat "$DEPS_VS_VENDOR_REPORT" | tail -2

# ───────────────────────────────────────────────────────────────────────────
# Sub-check 3: unused-js
# Every .js file under web/js/ (excluding vendor) must be referenced by at
# least one template, or by another non-vendor JS file (helper imports).
# ───────────────────────────────────────────────────────────────────────────

echo "==> Sub-check 3: unused-js"
{
  printf '# unused-js — project JS files not referenced anywhere\n\n'

  unused_count=0
  while IFS= read -r file; do
    rel="${file#$WEB_DIR/}"
    web_path="/web/$rel"
    basename_only=$(basename "$file")

    # Search templates AND project JS (excluding the file itself + vendor) for
    # either the full /web/... path or the bare filename.
    if grep -rIlF \
         --include='*.html' --include='*.js' \
         --exclude-dir=vendor --exclude-dir=node_modules \
         -e "$web_path" -e "$basename_only" \
         "$TEMPLATE_DIR" "$WEB_DIR/js" 2>/dev/null \
       | grep -vF "$file" \
       | head -1 > /dev/null; then
      :
    else
      unused_count=$((unused_count + 1))
      printf '  UNUSED %s\n' "$rel"
    fi
  done < <(find "$WEB_DIR/js" -type f -name '*.js' -not -path '*/vendor/*' | sort)

  printf '\n%s unused JS file(s)\n' "$unused_count"
  if [ "$unused_count" -ne 0 ]; then
    UNUSED_JS_FAIL=1
  fi
} > "$UNUSED_JS_REPORT"
cat "$UNUSED_JS_REPORT" | tail -2

# ───────────────────────────────────────────────────────────────────────────
# Summary
# ───────────────────────────────────────────────────────────────────────────

label_for() { [ "$1" -eq 0 ] && printf 'PASS' || printf 'FAIL'; }

{
  printf 'Reference checks summary\n\n'
  printf '%-18s  %s\n' 'vendor-refs'    "$(label_for "$VENDOR_REFS_FAIL")"
  printf '%-18s  %s\n' 'deps-vs-vendor' "$(label_for "$DEPS_VS_VENDOR_FAIL")"
  printf '%-18s  %s\n' 'unused-js'      "$(label_for "$UNUSED_JS_FAIL")"
} > "$SUMMARY"

echo ""
echo "==> Reports written to: $REPORT_DIR"
cat "$SUMMARY"

if [ "$VENDOR_REFS_FAIL" -ne 0 ] || [ "$DEPS_VS_VENDOR_FAIL" -ne 0 ] || [ "$UNUSED_JS_FAIL" -ne 0 ]; then
  exit 1
fi
exit 0
