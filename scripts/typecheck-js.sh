#!/bin/bash
set -euo pipefail

# Type-check the project's vanilla JS using `tsc --checkJs --noEmit`.
# This is purely static analysis: no .ts files, no source changes, no
# build output — tsc just reads .js as JS and reports type/semantic
# issues that ESLint cannot see (wrong arg counts, undefined property
# access on inferred-shape objects, etc.).
#
# tsconfig.json + ambient globals.d.ts are generated inside the
# container so the repo stays free of build artifacts and IDE plugins
# don't suddenly start type-checking the JS files in the editor.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
JS_DIR="$PROJECT_ROOT/runtime/src/main/resources/META-INF/resources/web/js"

NODE_IMAGE="docker.io/library/node:22-alpine"
TSC_VERSION="5.6.3"

REPORT_DIR="${REPORT_DIR:-$PROJECT_ROOT/.reports/js-typecheck}"
rm -rf "$REPORT_DIR"
mkdir -p "$REPORT_DIR"

if [ ! -d "$JS_DIR" ] || [ -z "$(find "$JS_DIR" -name '*.js' -not -path '*/vendor/*' 2>/dev/null)" ]; then
  echo "==> No JS files to type-check"
  echo "PASS - no JS files" > "$REPORT_DIR/summary.txt"
  exit 0
fi

echo "==> Type-checking JS in $JS_DIR (Podman + Node + tsc $TSC_VERSION)"

set +e
podman run --rm \
  -v "$JS_DIR:/work/js:ro,Z" \
  -v "$REPORT_DIR:/reports:Z" \
  -e "TSC_VERSION=$TSC_VERSION" \
  "$NODE_IMAGE" \
  sh -c '
    set -e
    cd /work

    cat > tsconfig.json <<EOF
{
  "compilerOptions": {
    "allowJs": true,
    "checkJs": true,
    "noEmit": true,
    "target": "es2022",
    "module": "esnext",
    "moduleResolution": "bundler",
    "strict": false,
    "noImplicitAny": false,
    "skipLibCheck": true,
    "lib": ["es2022", "dom"],
    "types": []
  },
  "include": ["js/**/*.js", "globals.d.ts"]
}
EOF

    cat > globals.d.ts <<EOF
// Runtime globals available before project JS executes.
// Vendored libraries (Alpine, HTMX, ECharts, D3, MapLibre, Quill, Cytoscape,
// Video.js, Monaco) load via <script> tags; phaAlpine and PHA are
// project-defined helpers attached to window.
declare const Alpine: any;
declare const htmx: any;
declare const echarts: any;
declare const d3: any;
declare const maplibregl: any;
declare const phaAlpine: any;
declare const Quill: any;
declare const cytoscape: any;
declare const videojs: any;

interface Window {
  Alpine?: any;
  htmx?: any;
  PHA?: any;
  maplibregl?: any;
  phaAlpine?: any;
  // Monaco loads its AMD loader onto window.require + window.monaco at runtime.
  require?: any;
  monaco?: any;
}
EOF

    npm install --silent --no-audit --no-fund "typescript@${TSC_VERSION}" >/dev/null 2>&1

    npx tsc -p tsconfig.json > /reports/output.txt 2>&1
    rc=$?
    exit $rc
  '
TSC_EXIT=$?
set -e

# tsc emits errors as:
#   js/utils/focus-trap.js(42,18): error TS2339: Property 'foo' ...
errors=$(grep -cE "^js/.+\([0-9]+,[0-9]+\): error TS[0-9]+:" "$REPORT_DIR/output.txt" || true)
errors=${errors:-0}

# Per-file summary: PASS/FAIL line per JS file
{
  while IFS= read -r f; do
    rel="${f#$JS_DIR/}"
    container_path="js/$rel"
    n=$(grep -cE "^${container_path}\([0-9]+,[0-9]+\): error TS[0-9]+:" "$REPORT_DIR/output.txt" 2>/dev/null || true)
    n=${n:-0}
    if [ "$n" = "0" ]; then
      printf "PASS\t%s\t0\n" "$rel"
    else
      printf "FAIL\t%s\t%s\n" "$rel" "$n"
    fi
  done < <(find "$JS_DIR" -type f -name '*.js' -not -path '*/vendor/*' | sort)
} > "$REPORT_DIR/summary.txt"

echo ""
echo "==> Reports written to: $REPORT_DIR"
if [ "$TSC_EXIT" -eq 0 ]; then
  echo "==> No type errors"
  exit 0
else
  total_files=$(wc -l < "$REPORT_DIR/summary.txt")
  failed_files=$(grep -c '^FAIL' "$REPORT_DIR/summary.txt" || true)
  echo "==> Type-check failed: ${errors} errors across ${failed_files:-0} of ${total_files} files"
  echo "==> Full output: $REPORT_DIR/output.txt"
  exit 1
fi
