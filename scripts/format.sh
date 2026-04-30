#!/bin/bash
set -euo pipefail

# Run Prettier inside a Podman container to format HTML, CSS, JS, and JSON
# Keeps the host environment clean — no npm/node_modules on the host
#
# Usage:
#   bash scripts/format.sh                # format all default targets
#   bash scripts/format.sh --check        # check only, fail if changes needed
#   bash scripts/format.sh path/to/file   # format a specific file or directory
#
# Default targets:
#   - runtime/src/main/resources/templates/**/*.html
#   - runtime/src/main/resources/META-INF/resources/web/{css,js}/**/*.{css,js}
#   - integration-tests/src/main/resources/templates/**/*.html
#
# Vendor (runtime/.../web/vendor/) and node_modules/build/ are skipped via .prettierignore.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

IMAGE="docker.io/library/node:22-alpine"
PRETTIER_VERSION="3"
PRINT_WIDTH="120"

MODE="write"
TARGETS=()

for arg in "$@"; do
  case "$arg" in
    --check) MODE="check" ;;
    --help|-h)
      sed -n '3,15p' "$0"
      exit 0
      ;;
    *) TARGETS+=("$arg") ;;
  esac
done

if [ "${#TARGETS[@]}" -eq 0 ]; then
  TARGETS=(
    "runtime/src/main/resources/templates"
    "runtime/src/main/resources/META-INF/resources/web/css"
    "runtime/src/main/resources/META-INF/resources/web/js"
    "integration-tests/src/main/resources/templates"
  )
fi

# Validate that all targets exist before mounting
EXISTING_TARGETS=()
for t in "${TARGETS[@]}"; do
  abs="$PROJECT_ROOT/$t"
  # Allow absolute paths as-is
  [ -e "$t" ] && abs="$(cd "$(dirname "$t")" && pwd)/$(basename "$t")"
  if [ -e "$abs" ]; then
    EXISTING_TARGETS+=("$abs")
  else
    echo "==> Skipping missing target: $t"
  fi
done

if [ "${#EXISTING_TARGETS[@]}" -eq 0 ]; then
  echo "==> No valid targets to format"
  exit 1
fi

# Compute paths relative to PROJECT_ROOT for the in-container invocation
REL_TARGETS=()
for abs in "${EXISTING_TARGETS[@]}"; do
  REL_TARGETS+=("${abs#"$PROJECT_ROOT/"}")
done

PRETTIER_FLAG="--write"
[ "$MODE" = "check" ] && PRETTIER_FLAG="--check"

echo "==> Running Prettier ($MODE) in Podman container..."
echo "    Targets:"
for t in "${REL_TARGETS[@]}"; do echo "      - $t"; done
echo ""

podman run --rm \
  -v "$PROJECT_ROOT:/work:Z" \
  -w /work \
  "$IMAGE" \
  sh -c "
    set -e
    npm install --silent --no-audit --no-fund --no-save prettier@${PRETTIER_VERSION} >/dev/null 2>&1
    npx prettier ${PRETTIER_FLAG} --print-width ${PRINT_WIDTH} ${REL_TARGETS[*]}
    EXIT_CODE=\$?
    rm -rf node_modules
    exit \$EXIT_CODE
  "
