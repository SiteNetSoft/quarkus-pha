#!/bin/bash
set -euo pipefail

# Download frontend dependencies using Podman + Node.js container
# Keeps the host environment clean — no npm/node_modules on the host

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
WEB_DIR="$PROJECT_ROOT/runtime/src/main/resources/META-INF/resources/web"
VENDOR_DIR="$WEB_DIR/vendor"
IT_MEDIA_DIR="$PROJECT_ROOT/integration-tests/src/main/resources/META-INF/resources/web/vendor/media"
mkdir -p "$IT_MEDIA_DIR"

echo "==> Creating vendor output directory..."
mkdir -p "$VENDOR_DIR"

echo "==> Running Podman container to install and copy frontend deps..."
podman run --rm \
  -v "$WEB_DIR/package.json:/work/package.json:ro,Z" \
  -v "$VENDOR_DIR:/output:Z" \
  -v "$IT_MEDIA_DIR:/output-it-media:Z" \
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
    # Ship minified CSS: patternfly.min.css comes from the package; the package
    # has no patternfly-addons.min.css, so minify it ourselves with esbuild.
    cp node_modules/@patternfly/patternfly/patternfly.min.css /output/patternfly/
    npx --yes esbuild node_modules/@patternfly/patternfly/patternfly-addons.css \
        --minify --outfile=/output/patternfly/patternfly-addons.min.css
    cp -r node_modules/@patternfly/patternfly/assets/fonts /output/patternfly/assets/
    cp -r node_modules/@patternfly/patternfly/assets/pficon /output/patternfly/assets/
    # Theme background images: the glass contrast theme sets
    # url("./assets/images/{PF,Felt}-Bkg-Generic-{Light,Dark}.svg") on <html>
    # — without these the glass surfaces are translucent over nothing and the
    # theme looks inert. Only these four are referenced by the CSS; the rest
    # of the package images dir is 8.8M of docs/demo art we do not ship.
    # (No apostrophes in this heredoc — it is a single-quoted sh -c script.)
    mkdir -p /output/patternfly/assets/images
    cp node_modules/@patternfly/patternfly/assets/images/*Bkg-Generic-*.svg /output/patternfly/assets/images/

    # Alpine.js
    echo "  Alpine.js..."
    mkdir -p /output/alpine
    cp node_modules/alpinejs/dist/cdn.min.js /output/alpine/alpine.min.js

    # HTMX
    echo "  HTMX..."
    mkdir -p /output/htmx /output/htmx/ext
    cp node_modules/htmx.org/dist/htmx.min.js /output/htmx/
    cp node_modules/htmx-ext-sse/sse.js /output/htmx/ext/sse.js

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
    # v6 is ESM-only: main module + shared/worker chunks it loads by relative URL
    cp node_modules/maplibre-gl/dist/maplibre-gl.mjs /output/maplibre/
    cp node_modules/maplibre-gl/dist/maplibre-gl-shared.mjs /output/maplibre/
    cp node_modules/maplibre-gl/dist/maplibre-gl-worker.mjs /output/maplibre/
    cp node_modules/maplibre-gl/dist/maplibre-gl.css /output/maplibre/
    rm -f /output/maplibre/maplibre-gl.js

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

    # Sample media for the video-player demo — GENERATED, never fetched.
    # The demo used to source clips from commondatastorage.googleapis.com, which
    # now returns 403. Synthetic clips keep the demo self-contained: no CDN to rot,
    # no third-party traffic when someone views the page, works offline.
    # Generated into the integration-tests demo app (not the runtime vendor tree)
    # so the clips never ship inside the extension JAR; the served URL stays
    # /web/vendor/media/.
    echo "  Sample media (ffmpeg)..."
    apk add --no-cache ffmpeg >/dev/null 2>&1
    ffmpeg -y -loglevel error \
      -f lavfi -i testsrc2=duration=10:size=640x360:rate=30 \
      -f lavfi -i sine=frequency=440:duration=10 \
      -c:v libx264 -preset veryfast -pix_fmt yuv420p -movflags +faststart \
      -c:a aac -b:a 64k -shortest /output-it-media/sample-pattern.mp4
    ffmpeg -y -loglevel error \
      -f lavfi -i smptebars=duration=10:size=640x360:rate=30 \
      -f lavfi -i sine=frequency=523:duration=10 \
      -c:v libx264 -preset veryfast -pix_fmt yuv420p -movflags +faststart \
      -c:a aac -b:a 64k -shortest /output-it-media/sample-bars.mp4
    ffmpeg -y -loglevel error \
      -f lavfi -i testsrc=duration=10:size=640x360:rate=30 \
      -f lavfi -i sine=frequency=349:duration=10 \
      -c:v libx264 -preset veryfast -pix_fmt yuv420p -movflags +faststart \
      -c:a aac -b:a 64k -shortest /output-it-media/sample-counter.mp4
    ffmpeg -y -loglevel error -i /output-it-media/sample-pattern.mp4 \
      -vframes 1 -q:v 3 /output-it-media/sample-pattern.jpg

    # Cytoscape.js (graph visualization for the topology bucket)
    echo "  Cytoscape.js..."
    mkdir -p /output/cytoscape
    cp node_modules/cytoscape/dist/cytoscape.min.js /output/cytoscape/

    # PatternFly extensions — CSS-only vendor (React components ignored).
    # The two @patternfly/react-* packages in web/package.json exist ONLY as
    # sources for these stylesheets; no React code is vendored or served.
    # See CONTRIBUTING.md ("A note on the @patternfly/react-* packages").
    echo "  PatternFly extensions (CSS)..."
    mkdir -p /output/patternfly-extensions
    cp node_modules/@patternfly/react-user-feedback/dist/esm/Feedback/Feedback.css \
       /output/patternfly-extensions/user-feedback.css

    # Catalog view ships SCSS, not CSS. Compile it locally with Dart Sass.
    node -e "
      const sass = require(\"sass\");
      const r = sass.compile(
        \"node_modules/@patternfly/react-catalog-view-extension/dist/sass/_react-catalog-view-extension.scss\",
        { loadPaths: [\"node_modules/@patternfly/react-catalog-view-extension/dist/sass\"], style: \"compressed\", silenceDeprecations: [\"import\"] }
      );
      require(\"fs\").writeFileSync(\"/output/patternfly-extensions/catalog-view.css\", r.css);
    "

    # Quill (rich text editor)
    echo "  Quill..."
    mkdir -p /output/quill
    cp node_modules/quill/dist/quill.js /output/quill/
    cp node_modules/quill/dist/quill.snow.css /output/quill/
    cp node_modules/quill/dist/quill.bubble.css /output/quill/
    cp node_modules/quill/dist/quill.core.css /output/quill/

    # Icons — raw SVGs from Font Awesome Free + PatternFly v6
    # FA Free ships per-icon SVGs directly; pficon SVGs are generated from the
    # pficons.mjs definitions shipped in the @patternfly/patternfly npm package
    # (icons/pficons.mjs). Do NOT fetch these from a GitHub tag archive — the
    # v6.6.0 tag was deleted upstream and tag URLs are mutable.
    echo "  Icons (FA Free solid/regular/brands)..."
    mkdir -p /output/icons/fa-solid /output/icons/fa-regular /output/icons/fa-brands
    cp node_modules/@fortawesome/fontawesome-free/svgs/solid/*.svg   /output/icons/fa-solid/
    cp node_modules/@fortawesome/fontawesome-free/svgs/regular/*.svg /output/icons/fa-regular/
    cp node_modules/@fortawesome/fontawesome-free/svgs/brands/*.svg  /output/icons/fa-brands/
    cp node_modules/@fortawesome/fontawesome-free/LICENSE.txt        /output/icons/fa-LICENSE.txt 2>/dev/null || true

    echo "  Icons (PatternFly pficons from the npm package)..."
    mkdir -p /output/icons/pficon /tmp/pf
    PF_VERSION=$(node -p "require(\"/work/node_modules/@patternfly/patternfly/package.json\").version")
    printf "pficon SVGs generated from @patternfly/patternfly %s (icons/pficons.mjs), MIT licensed.\nhttps://github.com/patternfly/patternfly/blob/main/LICENSE.txt\n" "$PF_VERSION" \
      > /output/icons/pficon-LICENSE.txt
    cat > /tmp/pf/build-pficons.mjs <<EOF
import { pfIcons } from "/work/node_modules/@patternfly/patternfly/icons/pficons.mjs";
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
    echo "  pficon:      @patternfly/patternfly ${PF_VERSION} (npm icons/pficons.mjs)"
  '

echo ""
echo "==> Vendor files are at: $VENDOR_DIR"
echo ""
ls -la "$VENDOR_DIR"
