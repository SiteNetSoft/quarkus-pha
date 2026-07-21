# Contributing to quarkus-pha

## Prerequisites

- **Java 25** (Temurin/OpenJDK). Gradle must run with it:
  `JAVA_HOME=/usr/lib/jvm/java-25-openjdk-amd64 ./gradlew ...` (adjust to your install).
- **Podman.** All Node tooling (Prettier, ESLint, Playwright, dependency vendoring)
  runs inside containers. **Never install or run node/npm/npx on the host** — the
  project scripts do it in Podman for you.

## Project scripts

| Script | Purpose |
|---|---|
| `scripts/download-frontend-deps.sh` | Regenerate the gitignored `vendor/` tree from `web/package.json` pins |
| `scripts/format.sh [--check]` | Prettier over templates/CSS/JS (in Podman) |
| `scripts/lint.sh` | ESLint + Stylelint + HTMLHint (in Podman) |
| `scripts/e2e.sh` | The full pipeline: build, packaged server in a container, Playwright suite, HTML/CSS validation, JS type-check, reference checks. **This is the source of truth** — see `TESTING.md` |
| `scripts/diagrams.sh` | Re-render the README architecture diagrams from `docs/diagrams/*.puml` |
| `scripts/static-site.sh` | Export the static docs/demo site for GitHub Pages into `build/static-site/` |

## Testing

See `TESTING.md`. The packaged `e2e.sh` run is authoritative; dev-server runs can
produce false greens. The Playwright suite must stay at zero failures.

## Code style

- Prettier formats HTML/CSS/JS/JSON (via `scripts/format.sh`); `.editorconfig` covers the rest.
- Qute templates stay simple and data-driven (see `CLAUDE.md` guidelines).
- One component per template file; components are self-contained HTML fragments.

## A note on the `@patternfly/react-*` packages

`web/package.json` lists two React-named packages
(`@patternfly/react-user-feedback`, `@patternfly/react-catalog-view-extension`).
**No React ships in this project.** They are CSS-extraction sources only: the
vendoring script pulls their stylesheets into `vendor/patternfly-extensions/` and
ignores all JS. Do not "fix" this by removing them — the user-feedback and
catalog-view components need that CSS.

## Pull requests

- Target the `develop` branch.
- Before opening a PR, run `bash scripts/format.sh --check` and `bash scripts/lint.sh`,
  and make sure the full `bash scripts/e2e.sh` pipeline passes (see `TESTING.md`).
- Keep PRs focused; unrelated refactoring belongs in its own PR.
