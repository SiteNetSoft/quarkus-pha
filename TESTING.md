# Testing

This project ships a multi-layer test pipeline that runs in parallel under
`scripts/e2e.sh`. Each layer catches a different class of bug, and every
layer writes structured reports to `.reports/` (gitignored) so failures can
be triaged without re-running the suite.

## Quick start

```bash
bash scripts/e2e.sh         # full pipeline: build, start, test, stop
bash scripts/e2e.sh start   # just build + start the integration-tests app
bash scripts/e2e.sh stop    # tear down the running app
```

Everything runs in Podman containers — no host Node, JDK runtime, or vnu/css
JARs required. Only the host needs `podman`, `bash`, `curl`, and a JDK 25 at
`/usr/lib/jvm/java-25-openjdk-amd64` (set `JAVA_HOME` to override).

## Pipeline overview

`scripts/e2e.sh` orchestrates ten test categories across four phases:

| Phase | Job | Quarkus needed? | Tool / location | Catches |
|---|---|---|---|---|
| Background, fires immediately | CSS validation | no | `scripts/validate-css.sh` (csstree-validator in node:22-alpine) | Spec-violation CSS values, unknown properties, syntax errors |
| Background, fires immediately | JS type-check | no | `scripts/typecheck-js.sh` (`tsc --checkJs --noEmit` in node:22-alpine) | Semantic JS bugs ESLint can't see (wrong arg counts, missing properties) |
| Background, fires immediately | Reference checks | no | `scripts/check-references.sh` | Broken `/web/*` references, deps↔vendor drift, unused project JS |
| Background, after Quarkus up | HTML validation | yes | `scripts/validate.sh` (vnu in `ghcr.io/validator/validator`) | HTML5/ARIA spec violations |
| Background, after Quarkus up | Server-side smoke + contract | no (own JVM) | `./gradlew :quarkus-pha-integration-tests:test` (`@QuarkusTest` + RestAssured) | Qute compile errors, route-binding bugs, HTMX response-header drift |
| Foreground (Playwright) | E2E component specs | yes | `integration-tests/e2e/tests/*.spec.js` | Component behavior regressions |
| Foreground (Playwright) | Console-error capture | yes | `integration-tests/e2e/tests/console-errors.spec.js` | Runtime JS errors, asset 404s, Alpine init failures |
| Foreground (Playwright) | a11y audit | yes | `integration-tests/e2e/tests/a11y.spec.js` (`@axe-core/playwright`) | Critical/serious WCAG violations |
| Foreground (Playwright) | HTMX target contract | yes | `integration-tests/e2e/tests/htmx-targets.spec.js` | `hx-target` selectors that don't resolve in DOM |
| Foreground (Playwright) | Keyboard navigation | yes | `integration-tests/e2e/tests/keyboard-nav.spec.js` | Pages where Tab can't reach interactive elements; arrow-key tab nav, skip link |

After all jobs finish, `e2e.sh` writes a top-level overview to
`.reports/summary.txt`.

## Running individual checks

Each layer can run standalone — useful during development.

### Linting (style/best-practice)

```bash
bash scripts/lint.sh                # ESLint + Stylelint + HTMLHint in Podman
bash scripts/format.sh              # Prettier in-place
bash scripts/format.sh --check      # Prettier check-only (CI-style)
```

### HTML validation (vnu)

```bash
bash scripts/e2e.sh start              # ensure Quarkus is on :9090
bash scripts/validate.sh               # validate every discovered URL
bash scripts/validate.sh /components/alert  # single path
```

The script POSTs each rendered page to a containerized `vnu` server and
filters out Alpine.js (`x-*`, `:*`, `@*`) and HTMX (`hx-*`) attributes
since those are intentional non-spec choices.

### CSS validation (csstree-validator)

```bash
bash scripts/validate-css.sh           # validate every CSS file
bash scripts/validate-css.sh path/to/file.css  # single file
```

Uses `csstree-validator` (MDN-backed grammar) instead of the W3C/Jigsaw
validator — Jigsaw's CSS support lags far behind modern features that
PatternFly uses (`color-mix()`, `@property`, `scale`, `translate`, etc.),
so it produces overwhelming false positives here.

### JS type-check (tsc --checkJs)

```bash
bash scripts/typecheck-js.sh
```

`tsconfig.json` and `globals.d.ts` are generated inside the container so
the repo stays free of build artifacts and editor TypeScript plugins
don't suddenly start type-checking the JS files.

### Reference / inventory checks

```bash
bash scripts/check-references.sh
```

Three sub-checks bundled in one script:

- **vendor-refs** — every `<script>`/`<link>` URL starting with `/web/`
  in templates resolves to an existing file under `runtime/.../web/`.
- **deps-vs-vendor** — every dep in `runtime/.../web/package.json`
  is copied by `download-frontend-deps.sh`, and every package the script
  copies has a matching dependency entry. Catches drift between the
  manifest and the copy commands.
- **unused-js** — every project `.js` file under `runtime/.../web/js/`
  is referenced from at least one template or another JS file.

### Server-side smoke + contract tests (JUnit)

```bash
JAVA_HOME=/usr/lib/jvm/java-25-openjdk-amd64 \
  ./gradlew :quarkus-pha-integration-tests:test
```

Three test classes under `integration-tests/src/test/java/.../it/`:

- `RoutesSmokeTest` — `@QuarkusTest`, parameterized over `/`, all 100
  component routes, all 4 demo routes. Asserts 200, `text/html`, doctype,
  closing `</html>`. Catches Qute compile errors before any browser.
- `HtmxRoutesSmokeTest` — 6 tests for `/api/htmx/*` endpoints. Asserts
  responses are *fragments* (no `<!DOCTYPE>`, no `<html>`, no `<body>`).
- `HtmxHeadersContractTest` — 16 parameterized + targeted tests pinning
  the response-header contract: `Content-Type: text/html;charset=UTF-8`,
  no stray `Set-Cookie` on idempotent GETs, no unsolicited `HX-Redirect`/
  `HX-Refresh`/`HX-Push-Url`, empty 200 on out-of-range pagination,
  no-XSS path-param contract on `/modal-content/{id}`.

### Playwright specs

The Playwright runner lives in `integration-tests/e2e/`. To run any spec
standalone:

```bash
cd integration-tests/e2e
podman run --rm --network=host --ipc=host \
  -v "$PWD:/work:Z" -w /work \
  mcr.microsoft.com/playwright:v1.61.1-noble \
  bash -c 'npx playwright test tests/<spec-name>.spec.js'
```

Available specs:

| Spec | Purpose |
|---|---|
| `console-errors.spec.js` | For each demo path, assert no `console.error` / `pageerror` and no `>=400` HTTP responses. |
| `a11y.spec.js` | Run `@axe-core/playwright` on each demo path; fail on critical/serious violations only. |
| `htmx-targets.spec.js` | Walk every `[hx-get/post/put/delete/patch]` element on each page; assert its `hx-target` selector resolves. Handles `this`, `body`, and `closest/find/next/previous X` modifiers. |
| `keyboard-nav.spec.js` | Page-level: Tab from initial state must move focus off body when interactive elements exist. Targeted: skip-to-content link first-Tab activation, tabs arrow-key nav, menu arrow-key nav. |
| `accessibility.spec.js` | Modal focus-trap behavior. |
| `<component>.spec.js` (~100 files) | Per-component behavior. |

To pass a per-page report dir to a Playwright spec, set the corresponding
env var before invoking:

```bash
JS_REPORT_DIR=/path           # console-errors.spec.js
A11Y_REPORT_DIR=/path         # a11y.spec.js
HTMX_TARGETS_REPORT_DIR=/path # htmx-targets.spec.js
KEYBOARD_NAV_REPORT_DIR=/path # keyboard-nav.spec.js
```

When unset, the spec writes no per-page files but still passes/fails
normally.

## Report layout

Everything lands under `.reports/` (gitignored):

```
.reports/
├── summary.txt                    ← top-level overview, all jobs
├── playwright.log                 ← captured Playwright stdout
├── validate.log                   ← HTML validator log
├── validate-css.log               ← CSS validator log
├── typecheck-js.log               ← tsc log
├── check-references.log           ← reference check log
├── server-tests.log               ← gradle test log
│
├── server-tests/
│   ├── summary.txt                ← PASS/FAIL line + totals
│   └── TEST-*.xml                 ← JUnit XML per test class
│
├── html-validation/
│   ├── summary.txt                ← TSV: PASS/FAIL/path/error_count
│   └── <slug>.txt                 ← raw vnu gnu-format output (failures only)
│
├── css-validation/
│   ├── summary.txt
│   └── <slug>.json                ← pure csstree-validator JSON (failures only)
│
├── js-validation/
│   ├── summary.txt
│   └── <slug>.txt                 ← path, status, errors list (one per page)
│
├── js-typecheck/
│   ├── summary.txt                ← per-JS-file PASS/FAIL count
│   └── output.txt                 ← raw tsc output
│
├── a11y/
│   ├── summary.txt
│   └── <slug>.json                ← axe violations trimmed for triage (one per page)
│
├── htmx-targets/
│   ├── summary.txt
│   └── <slug>.json                ← path, triggers, passed, broken[] (one per page)
│
├── keyboard-nav/
│   ├── summary.txt
│   └── <slug>.json                ← path, status (PASS/FAIL/SKIP), detail
│
└── references/
    ├── summary.txt                ← three sub-check rows
    ├── vendor-refs.txt            ← missing-reference detail
    ├── deps-vs-vendor.txt         ← drift detail
    └── unused-js.txt              ← unused-file detail
```

Per-file detail reports contain the raw tool output (no header lines), so
they are valid for the format their extension implies. Use `summary.txt`
for the at-a-glance status across pages/files, and per-file detail for
debugging individual failures.

## Adding a new test layer

Two patterns, depending on whether the layer needs a running browser.

### Quarkus-independent shell-driven check

Mirrors `validate-css.sh`, `typecheck-js.sh`, `check-references.sh`.

1. Write `scripts/<name>.sh`. Conventions:
   - Resolve `PROJECT_ROOT` from `$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)`.
   - Default `REPORT_DIR=.reports/<name>`; `rm -rf` and `mkdir -p` it.
   - Write per-file/per-target detail to `$REPORT_DIR/<slug>.<ext>`.
   - Write `$REPORT_DIR/summary.txt` with TSV `STATUS<TAB>name<TAB>count` lines.
   - Exit 0 on all-PASS, 1 otherwise.
   - If the check needs Node/Java, run inside a Podman container — never
     install on host.

2. In `scripts/e2e.sh`, in `cmd_test()`:
   - Add a background launch alongside CSS/JS-typecheck:
     ```bash
     <NAME>_LOG="$REPORTS_DIR/<name>.log"
     bash "$SCRIPT_DIR/<name>.sh" > "$<NAME>_LOG" 2>&1 &
     <NAME>_PID=$!
     ```
   - Add `wait "$<NAME>_PID"; <NAME>_EXIT=$?` in the wait block.
   - Add the log dump after the existing ones.
   - Add `<NAME>_EXIT` to the `EXIT_CODE` `if`.
   - Inside `write_summary`, count PASS/FAIL via `count_status` and add a
     `printf` row to the table.
   - Add to the per-job-reports list and to the failure-detail tail.

### Playwright spec

Mirrors `console-errors.spec.js`, `a11y.spec.js`, `htmx-targets.spec.js`,
`keyboard-nav.spec.js`.

1. Add `integration-tests/e2e/tests/<name>.spec.js`. Conventions:
   - Read `process.env.<NAME>_REPORT_DIR` (allow unset → noop file writes).
   - Use the same `ALL_PATHS` shape (`/` + 100 components + 4 demos)
     unless the check is targeted.
   - For each test, assert and (regardless of pass/fail) write a per-page
     JSON file with at minimum `{ "path": ..., "status": "PASS"|"FAIL" }`.

2. In `scripts/e2e.sh`:
   - Create the report dir before launching Playwright.
   - Add `-e <NAME>_REPORT_DIR=/reports/<name>` and a matching `-v` mount
     to the `podman run` invocation.
   - After Playwright finishes, build `summary.txt` from per-page files.
   - In `write_summary`, count and add the row.

## Architecture decisions

A few choices worth knowing about:

### Why csstree-validator instead of the W3C CSS validator

The Jigsaw validator hasn't kept up with modern CSS. Running it against
`pha.css` produced ~110 errors, ~95% of which were false positives —
modern properties (`scale`, `translate`, `@property`, `color-mix()`)
flagged as unknown. csstree-validator uses MDN's syntax data and finds
real bugs (`padding: auto`, invalid `grid-template-columns`, typos like
`tab-index`).

### Why not Knip / depcheck / ts-prune

These tools assume an npm-package model: ES modules, real `import`
statements, declared dependencies referenced by `require()`. This project
has zero `import`/`export` in its JS — everything works through
`<script>`-tag globals (`Alpine`, `htmx`, `phaAlpine`, `window.PHA`). The
tools would flag every dependency as unused and every file as orphaned.
The hand-rolled `check-references.sh` does the actual job that fits this
project's vendored-frontend architecture.

### Why HTMX/Alpine attributes are filtered out of HTML validation

vnu (correctly per spec) flags every `x-data`, `:class`, `@click`, `hx-get`
etc. as an unknown attribute. The project deliberately uses these
non-`data-` prefixed attributes for terseness — that's an architectural
choice, not a bug. `validate.sh` strips those errors from reports so real
spec violations stand out.

### Why server-side smoke tests don't share Quarkus with Playwright

`@QuarkusTest` spins up its own Quarkus instance on a separate test port
(usually 8081). Playwright's tests run against the packaged jar in a
Podman container on `:9090`. Both can co-exist; the smoke tests don't
interact with the long-running browser-side instance.

### Why JS console errors and a11y "roll into Playwright"

Both specs run inside Playwright's runner, so their failures contribute
to the Playwright pass/fail total. The summary surfaces them as
separate rows for visibility, but the `Status` column inherits Playwright's
overall result. A green Playwright run implies green sub-rows.

### Why no visual regression yet

Visual regression with `toHaveScreenshot()` is the single biggest gap
remaining, but adding it against a baseline that has 250+ existing
findings (font 404s, broken layouts, ARIA structure bugs) produces
useless diffs. Add it after the existing red is mostly green.

## Glossary

- **e2e.sh** — orchestrator script. `cmd_start`, `cmd_stop`, `cmd_test`.
- **fragment endpoint** — server route returning a partial HTML document
  meant to be swapped into the page by HTMX. No `<!DOCTYPE>`, no `<html>`,
  no `<body>`.
- **`hx-target`** — HTMX attribute naming the element a swap should land
  in. Special values: `this`, `body`, `closest X`, `find X`, `next X`,
  `previous X`.
- **rolls into Playwright** — the spec runs inside Playwright's runner,
  so its failures count toward `playwright.log`'s pass/fail totals.
