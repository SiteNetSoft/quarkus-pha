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

  # JS type-check (tsc --checkJs) is also Quarkus-independent
  echo "==> Launching JS type-check (background)..."
  JS_TYPECHECK_LOG="$REPORTS_DIR/typecheck-js.log"
  bash "$SCRIPT_DIR/typecheck-js.sh" > "$JS_TYPECHECK_LOG" 2>&1 &
  JS_TYPECHECK_PID=$!

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

  # JS console-error reports written by tests/console-errors.spec.js
  JS_REPORT_DIR="$REPORTS_DIR/js-validation"
  rm -rf "$JS_REPORT_DIR"
  mkdir -p "$JS_REPORT_DIR"

  PLAYWRIGHT_LOG="$REPORTS_DIR/playwright.log"

  set +e
  podman run --rm \
    --network=host \
    --ipc=host \
    -e CI="${CI:-}" \
    -e JS_REPORT_DIR=/reports \
    -v "$E2E_DIR:/work:Z" \
    -v "$JS_REPORT_DIR:/reports:Z" \
    -w /work \
    "$PLAYWRIGHT_IMAGE" \
    bash -c '
      set -e
      npm install --no-audit --no-fund 2>&1
      npx playwright test
    ' 2>&1 | tee "$PLAYWRIGHT_LOG"
  PLAYWRIGHT_EXIT=${PIPESTATUS[0]}

  # Build summary.txt from the per-page report files
  {
    for f in "$JS_REPORT_DIR"/*.txt; do
      [ -e "$f" ] || continue
      [ "$(basename "$f")" = "summary.txt" ] && continue
      p=$(awk -F": " '/^Path: /{print $2; exit}' "$f")
      s=$(awk -F": " '/^Status: /{print $2; exit}' "$f")
      n=$(awk -F": " '/^Errors: /{print $2; exit}' "$f")
      printf '%s\t%s\t%s\n' "$s" "$p" "$n"
    done | sort -k2
  } > "$JS_REPORT_DIR/summary.txt"

  echo ""
  echo "==> Waiting for HTML validation to finish..."
  wait "$HTML_VALIDATE_PID"
  HTML_VALIDATE_EXIT=$?

  echo "==> Waiting for CSS validation to finish..."
  wait "$CSS_VALIDATE_PID"
  CSS_VALIDATE_EXIT=$?

  echo "==> Waiting for JS type-check to finish..."
  wait "$JS_TYPECHECK_PID"
  JS_TYPECHECK_EXIT=$?
  set -e

  echo ""
  echo "--- HTML validation log ($HTML_VALIDATE_LOG) ---"
  cat "$HTML_VALIDATE_LOG"
  echo "--- end HTML validation log ---"
  echo ""
  echo "--- CSS validation log ($CSS_VALIDATE_LOG) ---"
  cat "$CSS_VALIDATE_LOG"
  echo "--- end CSS validation log ---"
  echo ""
  echo "--- JS type-check log ($JS_TYPECHECK_LOG) ---"
  cat "$JS_TYPECHECK_LOG"
  echo "--- end JS type-check log ---"

  if [ "$PLAYWRIGHT_EXIT" -ne 0 ] || [ "$HTML_VALIDATE_EXIT" -ne 0 ] || [ "$CSS_VALIDATE_EXIT" -ne 0 ] || [ "$JS_TYPECHECK_EXIT" -ne 0 ]; then
    EXIT_CODE=1
  else
    EXIT_CODE=0
  fi

  # Top-level overview summary across all four pipelines
  write_summary() {
    local summary_file="$REPORTS_DIR/summary.txt"

    # Per-job PASS/FAIL counts pulled from each sub-summary.txt.
    # awk gives a clean single-line count whether or not anything matches.
    count_status() {
      local file="$1" tag="$2"
      [ -f "$file" ] || { printf '0'; return; }
      awk -v t="$tag" 'index($0, t) == 1 {n++} END {print n+0}' "$file"
    }
    local html_pass html_fail css_pass css_fail jsv_pass jsv_fail jst_pass jst_fail
    html_pass=$(count_status "$REPORTS_DIR/html-validation/summary.txt" "PASS")
    html_fail=$(count_status "$REPORTS_DIR/html-validation/summary.txt" "FAIL")
    css_pass=$(count_status "$REPORTS_DIR/css-validation/summary.txt" "PASS")
    css_fail=$(count_status "$REPORTS_DIR/css-validation/summary.txt" "FAIL")
    jsv_pass=$(count_status "$JS_REPORT_DIR/summary.txt" "PASS")
    jsv_fail=$(count_status "$JS_REPORT_DIR/summary.txt" "FAIL")
    jst_pass=$(count_status "$REPORTS_DIR/js-typecheck/summary.txt" "PASS")
    jst_fail=$(count_status "$REPORTS_DIR/js-typecheck/summary.txt" "FAIL")

    # Playwright stats: parsed from its run log (last "X passed" / "X failed" lines)
    local pw_pass pw_fail
    pw_pass=$(grep -oE '[0-9]+ passed' "$PLAYWRIGHT_LOG" 2>/dev/null | tail -1 | grep -oE '[0-9]+' || true)
    pw_fail=$(grep -oE '[0-9]+ failed' "$PLAYWRIGHT_LOG" 2>/dev/null | tail -1 | grep -oE '[0-9]+' || true)
    pw_pass=${pw_pass:-0}
    pw_fail=${pw_fail:-0}

    status_label() {
      [ "$1" -eq 0 ] && printf 'PASS' || printf 'FAIL'
    }

    {
      printf 'quarkus-pha E2E run summary\n'
      printf 'Run at: %s\n' "$(date -u +%Y-%m-%dT%H:%M:%SZ)"
      printf '\n'
      printf '%-16s  %-6s  %s\n' 'Job' 'Status' 'Detail'
      printf '%-16s  %-6s  %s\n' '----------------' '------' '-----------------------------------------------'
      printf '%-16s  %-6s  %s\n' 'Playwright'      "$(status_label "$PLAYWRIGHT_EXIT")"     "${pw_pass} passed, ${pw_fail} failed"
      printf '%-16s  %-6s  %s\n' 'HTML validation' "$(status_label "$HTML_VALIDATE_EXIT")"  "${html_pass} PASS, ${html_fail} FAIL"
      printf '%-16s  %-6s  %s\n' 'CSS validation'  "$(status_label "$CSS_VALIDATE_EXIT")"   "${css_pass} PASS, ${css_fail} FAIL"
      printf '%-16s  %-6s  %s\n' 'JS console'      "$(status_label "$PLAYWRIGHT_EXIT")"     "${jsv_pass} PASS, ${jsv_fail} FAIL  (rolls into Playwright)"
      printf '%-16s  %-6s  %s\n' 'JS type-check'   "$(status_label "$JS_TYPECHECK_EXIT")"   "${jst_pass} PASS, ${jst_fail} FAIL"
      printf '\n'
      printf 'Per-job reports:\n'
      printf '  HTML:           %s\n' "$REPORTS_DIR/html-validation/"
      printf '  CSS:            %s\n' "$REPORTS_DIR/css-validation/"
      printf '  JS console:     %s\n' "$JS_REPORT_DIR/"
      printf '  JS type-check:  %s\n' "$REPORTS_DIR/js-typecheck/"
      printf '  Playwright:     %s\n' "$E2E_DIR/playwright-report/index.html"
      printf '\n'
      printf 'Overall: %s\n' "$([ "$EXIT_CODE" -eq 0 ] && printf 'PASS' || printf 'FAIL')"
    } > "$summary_file"
  }
  write_summary

  echo ""
  if [ $EXIT_CODE -ne 0 ]; then
    echo "==> E2E tests failed (Playwright=$PLAYWRIGHT_EXIT, html=$HTML_VALIDATE_EXIT, css=$CSS_VALIDATE_EXIT, jsTypecheck=$JS_TYPECHECK_EXIT)"
    echo "    HTML report:           $E2E_DIR/playwright-report/index.html"
    echo "    HTML validation log:   $HTML_VALIDATE_LOG"
    echo "    CSS validation log:    $CSS_VALIDATE_LOG"
    echo "    JS console reports:    $JS_REPORT_DIR/"
    echo "    JS type-check log:     $JS_TYPECHECK_LOG"
    echo "    JS type-check reports: $REPORTS_DIR/js-typecheck/"
    echo "    Top-level summary:     $REPORTS_DIR/summary.txt"
  else
    echo "==> All E2E tests + HTML/CSS validation + JS type-check passed"
    echo "    Top-level summary:     $REPORTS_DIR/summary.txt"
  fi

  exit $EXIT_CODE
}

case "${1:-}" in
  start) cmd_start ;;
  stop)  cmd_stop  ;;
  *)     cmd_test  ;;
esac
