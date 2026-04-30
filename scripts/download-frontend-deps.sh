#!/bin/bash
set -euo pipefail

# Download frontend dependencies using Podman + Node.js container
# Keeps the host environment clean — no npm/node_modules on the host

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
WEB_DIR="$PROJECT_ROOT/runtime/src/main/resources/META-INF/resources/web"
VENDOR_DIR="$WEB_DIR/vendor"

echo "==> Creating vendor output directory..."
mkdir -p "$VENDOR_DIR"

echo "==> Running Podman container to install and copy frontend deps..."
podman run --rm \
  -v "$WEB_DIR/package.json:/work/package.json:ro,Z" \
  -v "$VENDOR_DIR:/output:Z" \
  docker.io/library/node:22-alpine \
  sh -c '
    set -e

    cd /work
    echo "--- npm install ---"
    npm install --no-audit --no-fund 2>&1

    echo ""
    echo "--- Copying dist files to /output ---"

    # PatternFly (CSS + assets only)
    echo "  PatternFly..."
    mkdir -p /output/patternfly/assets
    cp node_modules/@patternfly/patternfly/patternfly.css /output/patternfly/
    cp node_modules/@patternfly/patternfly/patternfly-addons.css /output/patternfly/
    cp -r node_modules/@patternfly/patternfly/assets/fonts /output/patternfly/assets/
    cp -r node_modules/@patternfly/patternfly/assets/pficon /output/patternfly/assets/

    # Alpine.js
    echo "  Alpine.js..."
    mkdir -p /output/alpine
    cp node_modules/alpinejs/dist/cdn.min.js /output/alpine/alpine.min.js

    # HTMX
    echo "  HTMX..."
    mkdir -p /output/htmx
    cp node_modules/htmx.org/dist/htmx.min.js /output/htmx/

    # ECharts
    echo "  ECharts..."
    mkdir -p /output/echarts
    cp node_modules/echarts/dist/echarts.min.js /output/echarts/

    # D3.js
    echo "  D3.js..."
    mkdir -p /output/d3
    cp node_modules/d3/dist/d3.min.js /output/d3/

    # MapLibre GL JS
    echo "  MapLibre GL..."
    mkdir -p /output/maplibre
    cp node_modules/maplibre-gl/dist/maplibre-gl.js /output/maplibre/
    cp node_modules/maplibre-gl/dist/maplibre-gl.css /output/maplibre/

    echo ""
    echo "--- Done! Vendor files written to /output ---"
    echo ""
    echo "Versions installed:"
    node -e "
      const pkg = require(\"/work/node_modules/@patternfly/patternfly/package.json\");
      console.log(\"  PatternFly:  \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/alpinejs/package.json\");
      console.log(\"  Alpine.js:   \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/htmx.org/package.json\");
      console.log(\"  HTMX:        \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/echarts/package.json\");
      console.log(\"  ECharts:     \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/d3/package.json\");
      console.log(\"  D3.js:       \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/maplibre-gl/package.json\");
      console.log(\"  MapLibre GL: \" + pkg.version);
    "
  '

echo ""
echo "==> Vendor files are at: $VENDOR_DIR"
echo ""
ls -la "$VENDOR_DIR"
