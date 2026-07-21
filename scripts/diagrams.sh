#!/bin/bash
set -euo pipefail

# Re-render the README architecture diagrams (docs/diagrams/*.puml -> .svg).
# Each diagram is rendered twice: a light version (<name>.svg) and a dark
# version (<name>-dark.svg, via PlantUML's -darkmode) — the README embeds
# both through <picture> prefers-color-scheme sources so GitHub picks the
# right one per theme.
# PlantUML runs on the host JRE (any Java >= 11 works; this is independent of
# the Gradle/Java-25 toolchain). Override the jar location with PLANTUML_JAR.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
PLANTUML_JAR="${PLANTUML_JAR:-$HOME/.local/plantuml/plantuml.jar}"
DIAGRAM_DIR="$PROJECT_ROOT/docs/diagrams"

if [ ! -f "$PLANTUML_JAR" ]; then
  echo "ERROR: PlantUML jar not found at $PLANTUML_JAR" >&2
  echo "Install it there or set PLANTUML_JAR=/path/to/plantuml.jar" >&2
  exit 1
fi

echo "==> Rendering docs/diagrams/*.puml -> SVG (light)..."
java -jar "$PLANTUML_JAR" -tsvg "$DIAGRAM_DIR"/*.puml

echo "==> Rendering docs/diagrams/*.puml -> SVG (dark)..."
DARK_TMP="$(mktemp -d)"
trap 'rm -rf "$DARK_TMP"' EXIT
java -jar "$PLANTUML_JAR" -darkmode -tsvg -o "$DARK_TMP" "$DIAGRAM_DIR"/*.puml
for f in "$DARK_TMP"/*.svg; do
  base="$(basename "$f" .svg)"
  mv "$f" "$DIAGRAM_DIR/$base-dark.svg"
done

echo "==> Done:"
ls -1 "$DIAGRAM_DIR"/*.svg
