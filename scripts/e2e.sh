#!/bin/bash
set -euo pipefail

# Run Playwright E2E tests inside a Podman container
# Starts Quarkus if not already running on :8080

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
E2E_DIR="$PROJECT_ROOT/integration-tests/e2e"
QUARKUS_PID=""

PLAYWRIGHT_IMAGE="mcr.microsoft.com/playwright:v1.50.1-noble"

cleanup() {
  if [ -n "$QUARKUS_PID" ]; then
    echo "==> Stopping Quarkus (PID $QUARKUS_PID)..."
    kill "$QUARKUS_PID" 2>/dev/null || true
    wait "$QUARKUS_PID" 2>/dev/null || true
  fi
}
trap cleanup EXIT

# Check if Quarkus is already running on :8080
if curl -sf http://localhost:8080 > /dev/null 2>&1; then
  echo "==> Quarkus already running on :8080"
else
  echo "==> Building Quarkus application..."
  JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-25-openjdk-amd64}" \
    "$PROJECT_ROOT/gradlew" -p "$PROJECT_ROOT" :integration-tests:quarkusBuild

  echo "==> Starting Quarkus..."
  JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-25-openjdk-amd64}" \
    java -jar "$PROJECT_ROOT/integration-tests/build/quarkus-app/quarkus-run.jar" &
  QUARKUS_PID=$!

  echo "==> Waiting for Quarkus to start..."
  for i in $(seq 1 30); do
    if curl -sf http://localhost:8080 > /dev/null 2>&1; then
      echo "==> Quarkus is ready"
      break
    fi
    if [ "$i" -eq 30 ]; then
      echo "==> ERROR: Quarkus did not start within 30 seconds"
      exit 1
    fi
    sleep 1
  done
fi

echo "==> Running Playwright tests in Podman container..."

# Create output directories on host so reports are preserved
mkdir -p "$E2E_DIR/test-results" "$E2E_DIR/playwright-report"

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

EXIT_CODE=$?

echo ""
if [ $EXIT_CODE -ne 0 ]; then
  echo "==> E2E tests failed (exit code $EXIT_CODE)"
  echo "    HTML report: $E2E_DIR/playwright-report/index.html"
else
  echo "==> All E2E tests passed"
fi

exit $EXIT_CODE
