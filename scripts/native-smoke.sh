#!/usr/bin/env bash
# Smoke-checks a built native binary of the integration-tests app.
#
# Build first (CI does; locally the --cpus=8 podman throttle is MANDATORY, see TESTING.md):
#   ./gradlew :quarkus-pha-integration-tests:quarkusBuild \
#     -Dquarkus.native.enabled=true -Dquarkus.package.jar.enabled=false \
#     -Dquarkus.native.container-build=true
#
# Beyond page 200s, this exercises the two things native builds break first:
#  - Jackson's reflective builder/creator access (the JSON view-model contract)
#  - PhaTemplateLocator's runtime classpath reads (the pha: template-URI scheme)
# via POST /api/pha/render — the VDP-flow preview endpoint.
set -euo pipefail

BASE="http://localhost:9090"
BINARY=$(ls integration-tests/build/*-runner 2>/dev/null | head -1)
if [ -z "$BINARY" ]; then
  echo "==> ERROR: no native runner under integration-tests/build/ — build it first"
  exit 1
fi

echo "==> Starting native binary: $BINARY"
"$BINARY" &
NATIVE_PID=$!
trap 'kill $NATIVE_PID 2>/dev/null || true' EXIT

for i in $(seq 1 30); do
  curl -sf -o /dev/null "$BASE/" && break
  [ "$i" -eq 30 ] && { echo "==> ERROR: native app did not answer on :9090"; exit 1; }
  sleep 1
done
echo "==> Native app is up"

fail=0
check() {
  local desc="$1"; shift
  if "$@" > /dev/null 2>&1; then
    echo "    PASS $desc"
  else
    echo "    FAIL $desc"
    fail=1
  fi
}

# Page smokes (asset names are .min.css post-bundling — by design)
check "landing page"          curl -sf "$BASE/"
check "components grid"       curl -sf "$BASE/components"
check "table demo"            curl -sf "$BASE/components/table"
check "docs tab (Qute ref)"   curl -sf "$BASE/components/backdrop/docs/qute"
check "source-java endpoint"  curl -sf "$BASE/components/table/source-java/basic"

# The JSON view-model contract + pha: URI, end to end
RENDER=$(curl -sf -X POST -H "Content-Type: application/json" \
  --data '{"id": "mn-native", "items": [{"text": "Edit"}, {"text": "Delete", "danger": true}]}' \
  "$BASE/api/pha/render?template=pha:components/navigation/menu&model=menu" || true)
if [ "${RENDER#*pf-m-danger}" != "$RENDER" ] && [ "${RENDER#*mn-native}" != "$RENDER" ]; then
  echo "    PASS pha: URI + JSON payload renders (native Jackson + locator)"
else
  echo "    FAIL pha: URI + JSON payload renders — got: ${RENDER:0:200}"
  fail=1
fi

# Traversal must not resolve
CODE=$(curl -s -o /dev/null -w "%{http_code}" -X POST -H "Content-Type: application/json" \
  --data '{}' "$BASE/api/pha/render?template=pha:../secrets&model=menu")
if [ "$CODE" = "400" ]; then
  echo "    PASS traversal URI rejected ($CODE)"
else
  echo "    FAIL traversal URI rejected — got $CODE"
  fail=1
fi

if [ "$fail" -ne 0 ]; then
  echo "==> Native smoke FAILED"
  exit 1
fi
echo "==> Native smoke passed"
