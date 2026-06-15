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
PLAYWRIGHT_IMAGE="mcr.microsoft.com/playwright:v1.61.0-noble"

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

  # Reference / inventory checks (vendor refs, deps drift, unused JS) — pure
  # filesystem inspection, no Quarkus needed
  echo "==> Launching reference checks (background)..."
  REFS_LOG="$REPORTS_DIR/check-references.log"
  bash "$SCRIPT_DIR/check-references.sh" > "$REFS_LOG" 2>&1 &
  REFS_PID=$!

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

  # Server-side smoke tests (@QuarkusTest + RestAssured). Launched after
  # cmd_start so the Gradle daemon used by quarkusBuild is free. The test
  # spawns its own Quarkus instance on a separate port — no conflict with
  # the :9090 instance Playwright/HTML-validate use.
  SERVER_TEST_LOG="$REPORTS_DIR/server-tests.log"
  (
    cd "$PROJECT_ROOT"
    JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-25-openjdk-amd64}" \
      ./gradlew --no-daemon \
        :quarkus-pha-integration-tests:test
  ) > "$SERVER_TEST_LOG" 2>&1 &
  SERVER_TEST_PID=$!

  # JS console-error reports written by tests/console-errors.spec.js
  JS_REPORT_DIR="$REPORTS_DIR/js-validation"
  rm -rf "$JS_REPORT_DIR"
  mkdir -p "$JS_REPORT_DIR"

  # axe-core a11y reports written by tests/a11y.spec.js
  A11Y_REPORT_DIR="$REPORTS_DIR/a11y"
  rm -rf "$A11Y_REPORT_DIR"
  mkdir -p "$A11Y_REPORT_DIR"

  # HTMX swap-target contract reports written by tests/htmx-targets.spec.js
  HTMX_TARGETS_REPORT_DIR="$REPORTS_DIR/htmx-targets"
  rm -rf "$HTMX_TARGETS_REPORT_DIR"
  mkdir -p "$HTMX_TARGETS_REPORT_DIR"

  # Keyboard navigation reports written by tests/keyboard-nav.spec.js
  KEYBOARD_NAV_REPORT_DIR="$REPORTS_DIR/keyboard-nav"
  rm -rf "$KEYBOARD_NAV_REPORT_DIR"
  mkdir -p "$KEYBOARD_NAV_REPORT_DIR"

  PLAYWRIGHT_LOG="$REPORTS_DIR/playwright.log"

  set +e
  podman run --rm \
    --network=host \
    --ipc=host \
    -e CI="${CI:-}" \
    -e JS_REPORT_DIR=/reports/js \
    -e A11Y_REPORT_DIR=/reports/a11y \
    -e HTMX_TARGETS_REPORT_DIR=/reports/htmx-targets \
    -e KEYBOARD_NAV_REPORT_DIR=/reports/keyboard-nav \
    -v "$E2E_DIR:/work:Z" \
    -v "$JS_REPORT_DIR:/reports/js:Z" \
    -v "$A11Y_REPORT_DIR:/reports/a11y:Z" \
    -v "$HTMX_TARGETS_REPORT_DIR:/reports/htmx-targets:Z" \
    -v "$KEYBOARD_NAV_REPORT_DIR:/reports/keyboard-nav:Z" \
    -w /work \
    "$PLAYWRIGHT_IMAGE" \
    bash -c '
      set -e
      npm install --no-audit --no-fund 2>&1
      npx playwright test
    ' 2>&1 | tee "$PLAYWRIGHT_LOG"
  PLAYWRIGHT_EXIT=${PIPESTATUS[0]}

  # Build summary.txt for JS console-errors from per-page report files
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

  # Build summary.txt for a11y from per-page JSON files (path, blocking count)
  {
    for f in "$A11Y_REPORT_DIR"/*.json; do
      [ -e "$f" ] || continue
      # Each file has top-level "path" and "blocking" fields
      p=$(awk -F'"' '/"path":/ {print $4; exit}' "$f")
      n=$(awk -F'[:, ]+' '/"blocking":/ {gsub(/[^0-9]/,"",$3); print $3; exit}' "$f")
      n=${n:-0}
      if [ "$n" = "0" ]; then
        printf 'PASS\t%s\t0\n' "$p"
      else
        printf 'FAIL\t%s\t%s\n' "$p" "$n"
      fi
    done | sort -k2
  } > "$A11Y_REPORT_DIR/summary.txt"

  # Build summary.txt for htmx-targets from per-page JSON files (path, broken count)
  {
    for f in "$HTMX_TARGETS_REPORT_DIR"/*.json; do
      [ -e "$f" ] || continue
      p=$(awk -F'"' '/"path":/ {print $4; exit}' "$f")
      # Each file has a "broken" array; count its entries by matching "trigger":
      n=$(awk '/"broken":[[:space:]]*\[/,/\]/{ if ($0 ~ /"trigger":/) c++ } END {print c+0}' "$f")
      if [ "${n:-0}" = "0" ]; then
        printf 'PASS\t%s\t0\n' "$p"
      else
        printf 'FAIL\t%s\t%s\n' "$p" "$n"
      fi
    done | sort -k2
  } > "$HTMX_TARGETS_REPORT_DIR/summary.txt"

  # Build summary.txt for keyboard-nav (each file has top-level "status")
  {
    for f in "$KEYBOARD_NAV_REPORT_DIR"/*.json; do
      [ -e "$f" ] || continue
      p=$(awk -F'"' '/"path":/ {print $4; exit}' "$f")
      s=$(awk -F'"' '/"status":/ {print $4; exit}' "$f")
      printf '%s\t%s\t-\n' "${s:-FAIL}" "$p"
    done | sort -k2
  } > "$KEYBOARD_NAV_REPORT_DIR/summary.txt"

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

  echo "==> Waiting for reference checks to finish..."
  wait "$REFS_PID"
  REFS_EXIT=$?

  echo "==> Waiting for server-side smoke tests to finish..."
  wait "$SERVER_TEST_PID"
  SERVER_TEST_EXIT=$?

  # Pull JUnit XML totals; fall back to gradle log scrape if XML missing.
  SERVER_TEST_DIR="$REPORTS_DIR/server-tests"
  rm -rf "$SERVER_TEST_DIR"
  mkdir -p "$SERVER_TEST_DIR"
  cp -f "$PROJECT_ROOT/integration-tests/build/test-results/test/"*.xml \
        "$SERVER_TEST_DIR/" 2>/dev/null || true

  ST_TOTAL=0; ST_FAIL=0; ST_ERR=0
  for xml in "$SERVER_TEST_DIR"/*.xml; do
    [ -f "$xml" ] || continue
    t=$(awk -F'"' '/<testsuite[[:space:]]/ {for(i=1;i<NF;i++)if($i~/tests=$/){print $(i+1);exit}}' "$xml")
    f=$(awk -F'"' '/<testsuite[[:space:]]/ {for(i=1;i<NF;i++)if($i~/failures=$/){print $(i+1);exit}}' "$xml")
    e=$(awk -F'"' '/<testsuite[[:space:]]/ {for(i=1;i<NF;i++)if($i~/errors=$/){print $(i+1);exit}}' "$xml")
    ST_TOTAL=$((ST_TOTAL + ${t:-0}))
    ST_FAIL=$((ST_FAIL + ${f:-0}))
    ST_ERR=$((ST_ERR + ${e:-0}))
  done

  {
    if [ "$ST_FAIL" = "0" ] && [ "$ST_ERR" = "0" ]; then
      printf 'PASS\tserver-tests\t%s tests, 0 failures\n' "$ST_TOTAL"
    else
      printf 'FAIL\tserver-tests\t%s tests, %s failures, %s errors\n' "$ST_TOTAL" "$ST_FAIL" "$ST_ERR"
    fi
  } > "$SERVER_TEST_DIR/summary.txt"
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
  echo ""
  echo "--- Reference checks log ($REFS_LOG) ---"
  cat "$REFS_LOG"
  echo "--- end Reference checks log ---"
  echo ""
  echo "--- Server-side smoke tests log ($SERVER_TEST_LOG) ---"
  tail -40 "$SERVER_TEST_LOG"
  echo "--- end Server-side smoke tests log ---"

  if [ "$PLAYWRIGHT_EXIT" -ne 0 ] || [ "$HTML_VALIDATE_EXIT" -ne 0 ] || [ "$CSS_VALIDATE_EXIT" -ne 0 ] || [ "$JS_TYPECHECK_EXIT" -ne 0 ] || [ "$REFS_EXIT" -ne 0 ] || [ "$SERVER_TEST_EXIT" -ne 0 ]; then
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
    local html_pass html_fail css_pass css_fail jsv_pass jsv_fail jst_pass jst_fail a11y_pass a11y_fail
    html_pass=$(count_status "$REPORTS_DIR/html-validation/summary.txt" "PASS")
    html_fail=$(count_status "$REPORTS_DIR/html-validation/summary.txt" "FAIL")
    css_pass=$(count_status "$REPORTS_DIR/css-validation/summary.txt" "PASS")
    css_fail=$(count_status "$REPORTS_DIR/css-validation/summary.txt" "FAIL")
    jsv_pass=$(count_status "$JS_REPORT_DIR/summary.txt" "PASS")
    jsv_fail=$(count_status "$JS_REPORT_DIR/summary.txt" "FAIL")
    jst_pass=$(count_status "$REPORTS_DIR/js-typecheck/summary.txt" "PASS")
    jst_fail=$(count_status "$REPORTS_DIR/js-typecheck/summary.txt" "FAIL")
    a11y_pass=$(count_status "$A11Y_REPORT_DIR/summary.txt" "PASS")
    a11y_fail=$(count_status "$A11Y_REPORT_DIR/summary.txt" "FAIL")
    local hxt_pass hxt_fail
    hxt_pass=$(count_status "$HTMX_TARGETS_REPORT_DIR/summary.txt" "PASS")
    hxt_fail=$(count_status "$HTMX_TARGETS_REPORT_DIR/summary.txt" "FAIL")
    local kbd_pass kbd_fail kbd_skip
    kbd_pass=$(count_status "$KEYBOARD_NAV_REPORT_DIR/summary.txt" "PASS")
    kbd_fail=$(count_status "$KEYBOARD_NAV_REPORT_DIR/summary.txt" "FAIL")
    kbd_skip=$(count_status "$KEYBOARD_NAV_REPORT_DIR/summary.txt" "SKIP")

    # Reference check has its own summary.txt with three sub-check rows
    local refs_summary
    if [ -f "$REPORTS_DIR/references/summary.txt" ]; then
      refs_summary=$(awk '/^(vendor-refs|deps-vs-vendor|unused-js)/ {printf "%s=%s ", $1, $2}' "$REPORTS_DIR/references/summary.txt")
    else
      refs_summary="(no report)"
    fi

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
      printf '%-18s  %-6s  %s\n' 'Server-side tests' "$(status_label "$SERVER_TEST_EXIT")"   "${ST_TOTAL} tests, ${ST_FAIL} failures, ${ST_ERR} errors"
      printf '%-18s  %-6s  %s\n' 'Playwright'        "$(status_label "$PLAYWRIGHT_EXIT")"     "${pw_pass} passed, ${pw_fail} failed"
      printf '%-18s  %-6s  %s\n' 'HTML validation'   "$(status_label "$HTML_VALIDATE_EXIT")"  "${html_pass} PASS, ${html_fail} FAIL"
      printf '%-18s  %-6s  %s\n' 'CSS validation'    "$(status_label "$CSS_VALIDATE_EXIT")"   "${css_pass} PASS, ${css_fail} FAIL"
      printf '%-18s  %-6s  %s\n' 'JS console'        "$(status_label "$PLAYWRIGHT_EXIT")"     "${jsv_pass} PASS, ${jsv_fail} FAIL  (rolls into Playwright)"
      printf '%-18s  %-6s  %s\n' 'JS type-check'     "$(status_label "$JS_TYPECHECK_EXIT")"   "${jst_pass} PASS, ${jst_fail} FAIL"
      printf '%-18s  %-6s  %s\n' 'a11y (axe)'        "$(status_label "$PLAYWRIGHT_EXIT")"     "${a11y_pass} PASS, ${a11y_fail} FAIL  (rolls into Playwright)"
      printf '%-18s  %-6s  %s\n' 'HTMX targets'      "$(status_label "$PLAYWRIGHT_EXIT")"     "${hxt_pass} PASS, ${hxt_fail} FAIL  (rolls into Playwright)"
      printf '%-18s  %-6s  %s\n' 'Keyboard nav'      "$(status_label "$PLAYWRIGHT_EXIT")"     "${kbd_pass} PASS, ${kbd_fail} FAIL, ${kbd_skip} SKIP  (rolls into Playwright)"
      printf '%-18s  %-6s  %s\n' 'Reference checks'  "$(status_label "$REFS_EXIT")"           "$refs_summary"
      printf '\n'
      printf 'Per-job reports:\n'
      printf '  Server-side tests: %s\n' "$SERVER_TEST_DIR/"
      printf '  HTML:              %s\n' "$REPORTS_DIR/html-validation/"
      printf '  CSS:               %s\n' "$REPORTS_DIR/css-validation/"
      printf '  JS console:        %s\n' "$JS_REPORT_DIR/"
      printf '  JS type-check:     %s\n' "$REPORTS_DIR/js-typecheck/"
      printf '  a11y (axe):        %s\n' "$A11Y_REPORT_DIR/"
      printf '  HTMX targets:      %s\n' "$HTMX_TARGETS_REPORT_DIR/"
      printf '  Keyboard nav:      %s\n' "$KEYBOARD_NAV_REPORT_DIR/"
      printf '  Reference checks:  %s\n' "$REPORTS_DIR/references/"
      printf '  Playwright:        %s\n' "$E2E_DIR/playwright-report/index.html"
      printf '\n'
      printf 'Overall: %s\n' "$([ "$EXIT_CODE" -eq 0 ] && printf 'PASS' || printf 'FAIL')"
    } > "$summary_file"
  }
  write_summary

  echo ""
  if [ $EXIT_CODE -ne 0 ]; then
    echo "==> E2E tests failed (Playwright=$PLAYWRIGHT_EXIT, server-tests=$SERVER_TEST_EXIT, html=$HTML_VALIDATE_EXIT, css=$CSS_VALIDATE_EXIT, jsTypecheck=$JS_TYPECHECK_EXIT, refs=$REFS_EXIT)"
    echo "    HTML report:           $E2E_DIR/playwright-report/index.html"
    echo "    HTML validation log:   $HTML_VALIDATE_LOG"
    echo "    CSS validation log:    $CSS_VALIDATE_LOG"
    echo "    JS console reports:    $JS_REPORT_DIR/"
    echo "    JS type-check log:     $JS_TYPECHECK_LOG"
    echo "    JS type-check reports: $REPORTS_DIR/js-typecheck/"
    echo "    a11y reports:          $A11Y_REPORT_DIR/"
    echo "    HTMX targets reports:  $HTMX_TARGETS_REPORT_DIR/"
    echo "    Keyboard nav reports:  $KEYBOARD_NAV_REPORT_DIR/"
    echo "    Reference checks log:  $REFS_LOG"
    echo "    Reference checks dir:  $REPORTS_DIR/references/"
    echo "    Server-tests log:      $SERVER_TEST_LOG"
    echo "    Server-tests dir:      $SERVER_TEST_DIR/"
    echo "    Top-level summary:     $REPORTS_DIR/summary.txt"
  else
    echo "==> All server-tests + E2E + validation/typecheck/a11y/refs passed"
    echo "    Top-level summary:     $REPORTS_DIR/summary.txt"
  fi

  exit $EXIT_CODE
}

case "${1:-}" in
  start) cmd_start ;;
  stop)  cmd_stop  ;;
  *)     cmd_test  ;;
esac
