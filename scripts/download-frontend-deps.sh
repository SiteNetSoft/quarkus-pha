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

    # Monaco Editor (AMD loader bundle — no build step needed at runtime)
    echo "  Monaco Editor..."
    mkdir -p /output/monaco
    cp -r node_modules/monaco-editor/min/vs /output/monaco/

    # Video.js (HTML5 video player)
    echo "  Video.js..."
    mkdir -p /output/videojs
    cp node_modules/video.js/dist/video.min.js /output/videojs/
    cp node_modules/video.js/dist/video-js.min.css /output/videojs/
    cp -r node_modules/video.js/dist/font /output/videojs/

    # Cytoscape.js (graph visualization for the topology bucket)
    echo "  Cytoscape.js..."
    mkdir -p /output/cytoscape
    cp node_modules/cytoscape/dist/cytoscape.min.js /output/cytoscape/

    # Quill (rich text editor)
    echo "  Quill..."
    mkdir -p /output/quill
    cp node_modules/quill/dist/quill.js /output/quill/
    cp node_modules/quill/dist/quill.snow.css /output/quill/
    cp node_modules/quill/dist/quill.bubble.css /output/quill/
    cp node_modules/quill/dist/quill.core.css /output/quill/

    # Icons — raw SVGs from Font Awesome Free + PatternFly v6 source
    # FA Free ships per-icon SVGs directly; pficon SVGs are not on npm so we
    # generate them from the PF v6 source repo (pficons.mjs definitions).
    echo "  Icons (FA Free solid/regular/brands)..."
    mkdir -p /output/icons/fa-solid /output/icons/fa-regular /output/icons/fa-brands
    cp node_modules/@fortawesome/fontawesome-free/svgs/solid/*.svg   /output/icons/fa-solid/
    cp node_modules/@fortawesome/fontawesome-free/svgs/regular/*.svg /output/icons/fa-regular/
    cp node_modules/@fortawesome/fontawesome-free/svgs/brands/*.svg  /output/icons/fa-brands/
    cp node_modules/@fortawesome/fontawesome-free/LICENSE.txt        /output/icons/fa-LICENSE.txt 2>/dev/null || true

    echo "  Icons (PatternFly v6.4.0 pficons from GitHub source)..."
    apk add --no-cache curl tar >/dev/null 2>&1
    mkdir -p /output/icons/pficon /tmp/pf
    cd /tmp/pf
    curl -fsSL https://github.com/patternfly/patternfly/archive/refs/tags/v6.4.0.tar.gz \
      | tar -xz --strip-components=1 \
          patternfly-6.4.0/src/icons/definitions/pficons.mjs \
          patternfly-6.4.0/LICENSE.txt
    cp LICENSE.txt /output/icons/pficon-LICENSE.txt 2>/dev/null || true
    cat > /tmp/pf/build-pficons.mjs <<EOF
import { pfIcons } from "/tmp/pf/src/icons/definitions/pficons.mjs";
import { writeFileSync } from "fs";

let count = 0;
for (const [name, def] of Object.entries(pfIcons)) {
  const svg = \`<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 \${def.width} \${def.height}" role="img"><path d="\${def.svgPathData}"/></svg>\`;
  writeFileSync(\`/output/icons/pficon/\${name}.svg\`, svg);
  count++;
}
console.log(\`    wrote \${count} pficon SVGs\`);
EOF
    node /tmp/pf/build-pficons.mjs
    cd /work

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
    node -e "
      const pkg = require(\"/work/node_modules/monaco-editor/package.json\");
      console.log(\"  Monaco:      \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/video.js/package.json\");
      console.log(\"  Video.js:    \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/quill/package.json\");
      console.log(\"  Quill:       \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/@fortawesome/fontawesome-free/package.json\");
      console.log(\"  FA Free:     \" + pkg.version);
    "
    node -e "
      const pkg = require(\"/work/node_modules/cytoscape/package.json\");
      console.log(\"  Cytoscape:   \" + pkg.version);
    "
    echo "  pficon:      patternfly/patternfly@v6.4.0 (GitHub source)"
  '

echo ""
echo "==> Vendor files are at: $VENDOR_DIR"
echo ""
ls -la "$VENDOR_DIR"
