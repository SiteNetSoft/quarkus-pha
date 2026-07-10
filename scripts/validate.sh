#!/bin/bash
set -euo pipefail

# Validate rendered HTML from the running integration-tests app against the
# W3C/WHATWG HTML5 spec using the Nu Html Checker (vnu).
#
# Assumes the Quarkus app is already running on http://localhost:9090
# (start it with `bash scripts/e2e.sh start`).
#
# Usage:
#   bash scripts/validate.sh           # validate all discovered URLs
#   bash scripts/validate.sh /path     # validate a single path

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

QUARKUS_URL="http://localhost:9090"
VNU_CONTAINER="quarkus-pha-vnu"
# Pinned by digest (the image publishes no version tags) so validation results
# can't drift without a repo change. To bump: podman pull ghcr.io/validator/validator:latest
# && podman inspect ghcr.io/validator/validator:latest --format '{{.Digest}}'
VNU_IMAGE="ghcr.io/validator/validator@sha256:9381e2d1dd3f09b3bfb01b2e0392f89c220ea00aed2fd79e11347c24f2ae7bfc"
VNU_PORT="8888"
VNU_URL="http://localhost:${VNU_PORT}"

# Reports go to a gitignored directory so nothing accidentally lands in commits
REPORT_DIR="${REPORT_DIR:-$PROJECT_ROOT/.reports/html-validation}"
mkdir -p "$REPORT_DIR"
SUMMARY_FILE="$REPORT_DIR/summary.txt"
: > "$SUMMARY_FILE"

if ! curl -sf "$QUARKUS_URL" > /dev/null 2>&1; then
  echo "==> ERROR: Quarkus is not running on :9090"
  echo "    Start it with: bash scripts/e2e.sh start"
  exit 1
fi

start_vnu() {
  if curl -sf "$VNU_URL" > /dev/null 2>&1; then
    echo "==> vnu already running on :${VNU_PORT}"
    return
  fi

  podman rm -f "$VNU_CONTAINER" 2>/dev/null || true

  echo "==> Starting vnu validator in Podman..."
  podman run --rm -d \
    --name "$VNU_CONTAINER" \
    --network=host \
    "$VNU_IMAGE" > /dev/null

  echo "==> Waiting for vnu to start..."
  for i in $(seq 1 30); do
    if curl -sf "$VNU_URL" > /dev/null 2>&1; then
      echo "==> vnu is ready"
      return
    fi
    if [ "$i" -eq 30 ]; then
      echo "==> ERROR: vnu did not start within 30 seconds"
      podman logs "$VNU_CONTAINER" 2>&1 || true
      exit 1
    fi
    sleep 1
  done
}

stop_vnu() {
  podman stop "$VNU_CONTAINER" 2>/dev/null || true
}

discover_paths() {
  # Scrape homepage for component / extension links + add known demo pages
  local html
  html=$(curl -fsS "$QUARKUS_URL/")

  {
    echo "/"
    echo "$html" | grep -oE 'href="(/components|/extensions)/[a-z0-9/-]+"' \
      | sed -E 's/^href="(.+)"$/\1/' \
      | sort -u
    echo "/demos/dashboard"
    echo "/demos/data-management"
    echo "/demos/settings"
    echo "/demos/landing"
  }
}

# Alpine.js and HTMX attribute prefixes are intentionally non-spec-compliant
# (they don't use the `data-` prefix). vnu flags every one as invalid, which
# would mask real bugs in the noise — so we strip these from the report.
# Patterns match vnu's `gnu`-format error lines, which use Unicode smart quotes.
ALPINE_HTMX_ATTR_RE='Attribute .(x-[a-z0-9.:-]+|hx-[a-z0-9-]+|sse-[a-z0-9-]+|@[a-zA-Z0-9.@-]+|:[a-zA-Z0-9:-]+)'

# The Collabora document-editor iframe intentionally combines allow-scripts
# with allow-same-origin: Collabora's WOPI client needs both to function.
# vnu treats this as an error ("not recommended ... breaks sandboxing") but
# the iframe origin is fully trusted by us, so the combination is by design.
COLLABORA_SANDBOX_RE='Setting both .allow-scripts. and .allow-same-origin. is not recommended'

path_to_filename() {
  # /components/about-modal -> components__about-modal
  # /                       -> index
  printf '%s' "$1" | sed -e 's|^/$|index|' -e 's|^/||' -e 's|/|__|g'
}

validate_path() {
  local path="$1"
  local url="${QUARKUS_URL}${path}"
  local response
  local filtered
  local errors
  local slug
  slug=$(path_to_filename "$path")

  # POST the rendered HTML to vnu, ask for one-error-per-line gnu output
  response=$(curl -fsS "$url" \
    | curl -fsS -X POST \
        -H "Content-Type: text/html; charset=utf-8" \
        --data-binary @- \
        "${VNU_URL}/?out=gnu&level=error")

  # Drop Alpine/HTMX framework-attribute errors and the intentional
  # Collabora iframe sandbox warning (both are intentional non-spec choices)
  filtered=$(printf '%s' "$response" \
    | grep -vE "$ALPINE_HTMX_ATTR_RE" \
    | grep -vE "$COLLABORA_SANDBOX_RE" || true)

  if [ -z "$filtered" ]; then
    printf '  PASS  %s\n' "$path"
    printf 'PASS\t%s\t0\n' "$path" >> "$SUMMARY_FILE"
    return 0
  fi

  errors=$(printf '%s\n' "$filtered" | grep -c '.' || true)
  printf '  FAIL  %s (%s errors)\n' "$path" "$errors"
  printf '%s\n' "$filtered" | sed 's/^/        /'

  # Per-path report is the raw filtered vnu output. Metadata (path, error count)
  # lives in summary.txt; URL is recoverable from the filename slug.
  printf '%s\n' "$filtered" > "$REPORT_DIR/${slug}.txt"

  printf 'FAIL\t%s\t%s\n' "$path" "$errors" >> "$SUMMARY_FILE"
  return 1
}

# Single-path mode
if [ -n "${1:-}" ]; then
  start_vnu
  trap stop_vnu EXIT
  validate_path "$1"
  exit $?
fi

# Full-run mode
start_vnu
trap stop_vnu EXIT

echo "==> Discovering paths from $QUARKUS_URL/ ..."
mapfile -t PATHS < <(discover_paths)
echo "==> ${#PATHS[@]} paths to validate"
echo ""

FAILED=0
for path in "${PATHS[@]}"; do
  if ! validate_path "$path"; then
    FAILED=$((FAILED + 1))
  fi
done

echo ""
echo "==> Reports written to: $REPORT_DIR"
if [ "$FAILED" -ne 0 ]; then
  echo "==> HTML validation failed on ${FAILED} of ${#PATHS[@]} paths"
  exit 1
else
  echo "==> All ${#PATHS[@]} paths passed HTML validation"
fi
