#!/bin/bash
set -euo pipefail

# Run ESLint, Stylelint, and HTMLHint inside a Podman container
# Keeps the host environment clean — no npm/node_modules on the host

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
WEB_DIR="$PROJECT_ROOT/runtime/src/main/resources/META-INF/resources/web"
TEMPLATE_DIR="$PROJECT_ROOT/runtime/src/main/resources/templates"

IMAGE="docker.io/library/node:22-alpine"

# Build volume mounts dynamically
MOUNTS=()

# Always mount config files + package.json
MOUNTS+=("-v" "$PROJECT_ROOT/package.json:/work/package.json:ro,Z")
MOUNTS+=("-v" "$PROJECT_ROOT/eslint.config.js:/work/eslint.config.js:ro,Z")
MOUNTS+=("-v" "$PROJECT_ROOT/.stylelintrc.json:/work/.stylelintrc.json:ro,Z")
MOUNTS+=("-v" "$PROJECT_ROOT/.htmlhintrc:/work/.htmlhintrc:ro,Z")

# Mount JS source dir if it exists
JS_DIR="$WEB_DIR/js"
HAS_JS=false
if [ -d "$JS_DIR" ] && [ -n "$(find "$JS_DIR" -name '*.js' -not -path '*/vendor/*' 2>/dev/null)" ]; then
  MOUNTS+=("-v" "$JS_DIR:/work/runtime/src/main/resources/META-INF/resources/web/js:ro,Z")
  HAS_JS=true
fi

# Mount CSS source dir if it exists
CSS_DIR="$WEB_DIR/css"
HAS_CSS=false
if [ -d "$CSS_DIR" ] && [ -n "$(find "$CSS_DIR" -name '*.css' 2>/dev/null)" ]; then
  MOUNTS+=("-v" "$CSS_DIR:/work/runtime/src/main/resources/META-INF/resources/web/css:ro,Z")
  HAS_CSS=true
fi

# Mount templates dir if it exists
HAS_HTML=false
if [ -d "$TEMPLATE_DIR" ] && [ -n "$(find "$TEMPLATE_DIR" -name '*.html' 2>/dev/null)" ]; then
  MOUNTS+=("-v" "$TEMPLATE_DIR:/work/runtime/src/main/resources/templates:ro,Z")
  HAS_HTML=true
fi

echo "==> Running linters in Podman container..."
echo "    JS files:   $HAS_JS"
echo "    CSS files:  $HAS_CSS"
echo "    HTML files: $HAS_HTML"
echo ""

podman run --rm \
  "${MOUNTS[@]}" \
  "$IMAGE" \
  sh -c "
    set -e
    cd /work
    npm install --no-audit --no-fund 2>&1

    EXIT_CODE=0

    # ESLint
    if [ '$HAS_JS' = 'true' ]; then
      echo ''
      echo '--- ESLint ---'
      npx eslint . || EXIT_CODE=\$?
    else
      echo ''
      echo '--- ESLint: no JS files found, skipping ---'
    fi

    # Stylelint
    if [ '$HAS_CSS' = 'true' ]; then
      echo ''
      echo '--- Stylelint ---'
      npx stylelint 'runtime/src/main/resources/META-INF/resources/web/css/**/*.css' || EXIT_CODE=\$?
    else
      echo ''
      echo '--- Stylelint: no CSS files found, skipping ---'
    fi

    # HTMLHint
    if [ '$HAS_HTML' = 'true' ]; then
      echo ''
      echo '--- HTMLHint ---'
      npx htmlhint 'runtime/src/main/resources/templates/**/*.html' || EXIT_CODE=\$?
    else
      echo ''
      echo '--- HTMLHint: no HTML files found, skipping ---'
    fi

    echo ''
    if [ \$EXIT_CODE -ne 0 ]; then
      echo '==> Linting failed (exit code '\$EXIT_CODE')'
    else
      echo '==> All linters passed'
    fi
    exit \$EXIT_CODE
  "