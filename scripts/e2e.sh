#!/bin/bash
set -euo pipefail

# Usage:
#   bash scripts/e2e.sh          # build, start, test, stop
#   bash scripts/e2e.sh start    # build and start Quarkus server
#   bash scripts/e2e.sh stop     # stop Quarkus server

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
E2E_DIR="$PROJECT_ROOT/integration-tests/e2e"
QUARKUS_APP="$PROJECT_ROOT/integration-tests/build/quarkus-app"

QUARKUS_CONTAINER="quarkus-pha-e2e"
QUARKUS_IMAGE="registry.access.redhat.com/ubi9/openjdk-25-runtime:1.24"
PLAYWRIGHT_IMAGE="mcr.microsoft.com/playwright:v1.59.1-noble"

cmd_start() {
  if curl -sf http://localhost:9090 > /dev/null 2>&1; then
    echo "==> Quarkus already running on :9090"
    return
  fi

  echo "==> Building Quarkus application..."
  JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-25-openjdk-amd64}" \
    "$PROJECT_ROOT/gradlew" -p "$PROJECT_ROOT" :quarkus-pha-integration-tests:quarkusBuild

  # Remove stale container from a previous failed run
  podman rm -f "$QUARKUS_CONTAINER" 2>/dev/null || true

  echo "==> Starting Quarkus in Podman container..."
  podman run --rm -d \
    --name "$QUARKUS_CONTAINER" \
    --network=host \
    -e JAVA_APP_JAR=/deployments/quarkus-run.jar \
    -v "$QUARKUS_APP:/deployments:Z" \
    "$QUARKUS_IMAGE"

  echo "==> Waiting for Quarkus to start..."
  for i in $(seq 1 30); do
    if curl -sf http://localhost:9090 > /dev/null 2>&1; then
      echo "==> Quarkus is ready"
      return
    fi
    if [ "$i" -eq 30 ]; then
      echo "==> ERROR: Quarkus did not start within 30 seconds"
      echo "==> Container logs:"
      podman logs "$QUARKUS_CONTAINER" 2>&1 || true
      exit 1
    fi
    sleep 1
  done
}

cmd_stop() {
  echo "==> Stopping Quarkus container..."
  podman stop "$QUARKUS_CONTAINER" 2>/dev/null || true
}

cmd_test() {
  QUARKUS_STARTED=""

  cleanup() {
    if [ -n "$QUARKUS_STARTED" ]; then
      cmd_stop
    fi
  }
  trap cleanup EXIT

  # Reports live in a gitignored .reports/ directory at the repo root
  REPORTS_DIR="$PROJECT_ROOT/.reports"
  mkdir -p "$REPORTS_DIR"

  # CSS validation doesn't need Quarkus running, so kick it off immediately
  echo "==> Launching CSS validation (background)..."
  CSS_VALIDATE_LOG="$REPORTS_DIR/validate-css.log"
  bash "$SCRIPT_DIR/validate-css.sh" > "$CSS_VALIDATE_LOG" 2>&1 &
  CSS_VALIDATE_PID=$!

  if ! curl -sf http://localhost:9090 > /dev/null 2>&1; then
    cmd_start
    QUARKUS_STARTED=1
  else
    echo "==> Quarkus already running on :9090"
  fi

  echo "==> Running HTML validation (background) and Playwright tests (foreground) in parallel..."

  # Create output directories on host so reports are preserved
  mkdir -p "$E2E_DIR/test-results" "$E2E_DIR/playwright-report"

  # HTML validation in the background; needs Quarkus
  HTML_VALIDATE_LOG="$REPORTS_DIR/validate.log"
  bash "$SCRIPT_DIR/validate.sh" > "$HTML_VALIDATE_LOG" 2>&1 &
  HTML_VALIDATE_PID=$!

  set +e
  podman run --rm \
    --network=host \
    --ipc=host \
    -e CI="${CI:-}" \
    -v "$E2E_DIR:/work:Z" \
    -w /work \
    "$PLAYWRIGHT_IMAGE" \
    bash -c '
      set -e
      npm install --no-audit --no-fund 2>&1
      npx playwright test
    '
  PLAYWRIGHT_EXIT=$?

  echo ""
  echo "==> Waiting for HTML validation to finish..."
  wait "$HTML_VALIDATE_PID"
  HTML_VALIDATE_EXIT=$?

  echo "==> Waiting for CSS validation to finish..."
  wait "$CSS_VALIDATE_PID"
  CSS_VALIDATE_EXIT=$?
  set -e

  echo ""
  echo "--- HTML validation log ($HTML_VALIDATE_LOG) ---"
  cat "$HTML_VALIDATE_LOG"
  echo "--- end HTML validation log ---"
  echo ""
  echo "--- CSS validation log ($CSS_VALIDATE_LOG) ---"
  cat "$CSS_VALIDATE_LOG"
  echo "--- end CSS validation log ---"

  if [ "$PLAYWRIGHT_EXIT" -ne 0 ] || [ "$HTML_VALIDATE_EXIT" -ne 0 ] || [ "$CSS_VALIDATE_EXIT" -ne 0 ]; then
    EXIT_CODE=1
  else
    EXIT_CODE=0
  fi

  echo ""
  if [ $EXIT_CODE -ne 0 ]; then
    echo "==> E2E tests failed (Playwright=$PLAYWRIGHT_EXIT, html=$HTML_VALIDATE_EXIT, css=$CSS_VALIDATE_EXIT)"
    echo "    HTML report:        $E2E_DIR/playwright-report/index.html"
    echo "    HTML validation log: $HTML_VALIDATE_LOG"
    echo "    CSS validation log:  $CSS_VALIDATE_LOG"
  else
    echo "==> All E2E tests + HTML/CSS validation passed"
  fi

  exit $EXIT_CODE
}

case "${1:-}" in
  start) cmd_start ;;
  stop)  cmd_stop  ;;
  *)     cmd_test  ;;
esac
