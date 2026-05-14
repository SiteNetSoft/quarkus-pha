#!/bin/bash
set -euo pipefail

# Run Collabora Online (LibreOffice in the browser) in Podman for the
# document-editor showcase. Dev-only — points at the running Quarkus app
# on host port 9090 as the WOPI host.
#
# Usage:
#   scripts/run-collabora.sh           # start (foreground)
#   scripts/run-collabora.sh stop      # stop and remove the container
#
# After it starts, open http://localhost:9090/components/document-editor
# Browser must trust the iframe at http://localhost:9980 (HTTP, no TLS).

CONTAINER_NAME="quarkus-pha-collabora"
IMAGE="docker.io/collabora/code:latest"
PORT="${COLLABORA_PORT:-9980}"
# The WOPI host must be reachable from inside the container.
# Podman's host.containers.internal points at the host's loopback.
ALIAS_GROUP1="${COLLABORA_ALIAS_GROUP1:-http://host.containers.internal:9090}"

if [[ "${1:-}" == "stop" ]]; then
  echo "==> Stopping $CONTAINER_NAME..."
  podman rm -f "$CONTAINER_NAME" >/dev/null 2>&1 || true
  echo "Stopped."
  exit 0
fi

command -v podman >/dev/null 2>&1 || {
  echo "podman not found on PATH" >&2
  exit 1
}

if podman container exists "$CONTAINER_NAME"; then
  echo "==> Removing previous $CONTAINER_NAME container..."
  podman rm -f "$CONTAINER_NAME" >/dev/null
fi

echo "==> Pulling $IMAGE (this may take a while on first run)..."
podman pull "$IMAGE"

echo "==> Starting Collabora Online on http://localhost:$PORT"
echo "    WOPI alias: $ALIAS_GROUP1"
echo "    Stop with: $0 stop"
echo ""

exec podman run --rm \
  --name "$CONTAINER_NAME" \
  -p "$PORT:9980" \
  --add-host=host.containers.internal:host-gateway \
  -e "aliasgroup1=$ALIAS_GROUP1" \
  -e "extra_params=--o:ssl.enable=false --o:ssl.termination=false --o:net.frame_ancestors=http://localhost:9090" \
  -e "username=admin" \
  -e "password=admin" \
  "$IMAGE"
