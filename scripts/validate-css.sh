#!/bin/bash
set -euo pipefail

# Validate project-owned CSS files against the CSS spec using csstree-validator
# (built on csstree, kept current with MDN's syntax data — much fresher than
# the W3C/Jigsaw validator).
#
# This complements stylelint (linter) the way vnu complements htmlhint:
# stylelint catches style/best-practice issues, this catches actual spec
# violations like unknown properties or invalid values.
#
# Runs Node + csstree-validator inside Podman (no host npm/node_modules).
#
# Usage:
#   bash scripts/validate-css.sh                  # validate all project CSS
#   bash scripts/validate-css.sh path/file.css    # validate a single file

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
CSS_DIR="$PROJECT_ROOT/runtime/src/main/resources/META-INF/resources/web/css"

NODE_IMAGE="docker.io/library/node:22-alpine"
CSSV_VERSION="4.0.1"

REPORT_DIR="${REPORT_DIR:-$PROJECT_ROOT/.reports/css-validation}"
mkdir -p "$REPORT_DIR"
SUMMARY_FILE="$REPORT_DIR/summary.txt"
: > "$SUMMARY_FILE"

# Build the list of files to validate. patternfly-docs-bundle.css is the
# hand-edited PF docs-site export — csstree flags ~45 spec-strict warnings on
# PF's experimental properties (-moz-column-break-inside, padding-block-start:
# auto, grid-template-columns: fit-content max-content, etc.) that we cannot
# fix upstream. Skip it the same way we skip vendor/.
if [ -n "${1:-}" ]; then
  FILES_HOST=("$(realpath "$1")")
else
  mapfile -t FILES_HOST < <(find "$CSS_DIR" -type f -name '*.css' -not -path '*/vendor/*' -not -name 'patternfly-docs-bundle.css' | sort)
fi

if [ "${#FILES_HOST[@]}" -eq 0 ]; then
  echo "==> No CSS files to validate"
  exit 0
fi

echo "==> ${#FILES_HOST[@]} CSS files to validate"

# Translate host paths to container paths (mounted under /css)
FILES_CONTAINER=()
for f in "${FILES_HOST[@]}"; do
  rel="${f#$CSS_DIR/}"
  FILES_CONTAINER+=("/css/$rel")
done

# Run all validations in a single container invocation:
#   - install csstree-validator once
#   - emit a per-file JSON report and a TSV summary line
#   - never exit non-zero from inside (we want to write all reports first;
#     final pass/fail is decided by the host script after parsing summary.txt)
podman run --rm \
  -v "$CSS_DIR:/css:ro,Z" \
  -v "$REPORT_DIR:/reports:Z" \
  -e "FILES_CONTAINER=${FILES_CONTAINER[*]}" \
  -e "CSSV_VERSION=$CSSV_VERSION" \
  "$NODE_IMAGE" \
  sh -c '
    set -e
    cd /tmp
    npm init -y >/dev/null 2>&1
    npm install --silent --no-audit --no-fund "csstree-validator@${CSSV_VERSION}" >/dev/null 2>&1

    : > /reports/summary.txt

    for file in $FILES_CONTAINER; do
      rel="${file#/css/}"
      slug=$(printf "%s" "$rel" | sed "s|/|__|g")
      report="/reports/${slug}.json"
      raw="/tmp/${slug}.raw"

      # csstree-validator exits 0 on clean, 1 on errors. Write output
      # straight to a file — bash $() command substitution truncates this
      # tool''s output around 8 KiB, losing trailing errors.
      set +e
      npx csstree-validator --reporter json "$file" > "$raw" 2>&1
      rc=$?
      set -e

      # Count error objects: every entry has a "line": field
      errors=$(grep -cE "^[[:space:]]+\"line\":" "$raw" 2>/dev/null || true)
      errors=${errors:-0}

      if [ "$rc" = "0" ] && [ "$errors" = "0" ]; then
        echo "  PASS  $rel"
        printf "PASS\t%s\t0\n" "$rel" >> /reports/summary.txt
      else
        echo "  FAIL  $rel ($errors errors)"
        # Per-file report is the raw csstree-validator JSON output, unmodified.
        # Metadata (path, error count) lives in summary.txt.
        cp "$raw" "$report"
        printf "FAIL\t%s\t%s\n" "$rel" "$errors" >> /reports/summary.txt
      fi
    done
  '

# Tally pass/fail from the summary the container wrote
FAILED=$(grep -c "^FAIL" "$SUMMARY_FILE" 2>/dev/null || true)
FAILED=${FAILED:-0}
TOTAL=${#FILES_HOST[@]}

echo ""
echo "==> Reports written to: $REPORT_DIR"
if [ "$FAILED" -ne 0 ]; then
  echo "==> CSS validation failed on ${FAILED} of ${TOTAL} files"
  exit 1
else
  echo "==> All ${TOTAL} CSS files passed validation"
fi
