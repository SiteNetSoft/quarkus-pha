#!/bin/bash
set -euo pipefail

# Export a static snapshot of the demo site for GitHub Pages.
# Usage: bash scripts/static-site.sh            (BASE_PATH defaults to /quarkus-pha/)
#        BASE_PATH=/other/ bash scripts/static-site.sh

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
OUT="$PROJECT_ROOT/build/static-site"
BASE_PATH="${BASE_PATH:-/quarkus-pha/}"

rm -rf "$OUT"

if curl -sf http://localhost:9090/ >/dev/null 2>&1; then
  echo "ERROR: something is already responding on http://localhost:9090/." >&2
  echo "       Refusing to proceed: the static-site snapshot would be taken from" >&2
  echo "       an unknown/dev server instead of the packaged showcase we control" >&2
  echo "       (this repo's known false-green trap). Stop whatever is running on" >&2
  echo "       port 9090 and re-run." >&2
  exit 1
fi

echo "==> Starting packaged showcase (e2e.sh start)..."
bash "$SCRIPT_DIR/e2e.sh" start
trap 'bash "$SCRIPT_DIR/e2e.sh" stop' EXIT

echo "==> Exporting static site (base path: $BASE_PATH)..."
python3 "$SCRIPT_DIR/static-site-crawl.py" \
  --base-url http://localhost:9090 \
  --base-path "$BASE_PATH" \
  --out "$OUT"

PREVIEW="$PROJECT_ROOT/build/static-site-preview"
mkdir -p "$PREVIEW$(dirname "${BASE_PATH%/}")"
ln -sfn "$OUT" "$PREVIEW${BASE_PATH%/}"

echo "==> Done: $OUT"
echo "    Preview: python3 -m http.server -d $PREVIEW 8000"
echo "    then browse http://localhost:8000${BASE_PATH}"
