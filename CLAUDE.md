# quarkus-pha

## Project Overview
A Quarkus extension providing a framework-free frontend component library built on PatternFly, HTMX, and Alpine.js with Qute templates.

**GitHub:** https://github.com/SiteNetSoft/quarkus-pha

---

## What This Project Is
A Quarkus extension that delivers ready-to-use HTML components to any Quarkus/Qute application. Components are server-rendered HTML fragments — no virtual DOM, no JS framework, no build step required for consumers.

## What This Project Is NOT
- ❌ No React
- ❌ No Angular
- ❌ No Vue
- ❌ No PatternFly React components or PatternFly JS
- ❌ No JS framework of any kind

---

## The Stack

| Layer | Technology | Purpose |
|---|---|---|
| Design System | PatternFly (CSS + tokens only) | UI components and design language |
| Interactivity | Alpine.js | Lightweight reactivity via HTML attributes |
| Server UI | HTMX | Ajax, partial page updates, server-driven UI |
| Scripting | Vanilla JS | Anything Alpine and HTMX don't cover |
| Data Viz | Apache ECharts, D3.js | Dashboards and charts |
| Maps | MapLibre | Map components |
| Templates | Qute | Quarkus-native templating engine |

---

## How Components Work
- Components are pure HTML fragments — no `<html>`, no `<body>`, just the element itself
- Rendered server-side via Qute templates
- Served to the client and swapped into the page via HTMX Ajax
- Any Quarkus backend can consume them by adding this extension as a dependency
- Components carry their own Alpine.js behavior inline via `x-data` attributes
- No client-side routing — the server drives all UI transitions

---

## Backend
- **Framework:** Quarkus
- **Templating:** Qute
- **Build:** Gradle (Groovy)

---

## Qute Template Guidelines
Keep templates simple and data-driven. Avoid deep Qute-specific logic inside component templates where possible. This keeps components portable and maintainable.

**Allowed in component templates:**
- Simple variable expressions: `{variable}`
- Basic loops: `{#for item in items}...{/for}`
- Basic conditionals: `{#if condition}...{/if}`
- Includes: `{#include component/}`
- Comments: `{! comment }`

**Avoid in component templates:**
- Deep `@CheckedTemplate` coupling inside component logic
- Heavy business logic in templates
- Qute-specific magic that would make future portability difficult

---

## Component Guidelines
- One component per file
- Components must be self-contained HTML fragments
- Alpine.js state goes inline on the root element via `x-data`
- HTMX attributes go directly on elements (`hx-get`, `hx-target`, etc.)
- No external JS dependencies beyond the core stack unless absolutely necessary
- For dashboards: ECharts or D3.js
- For maps: MapLibre

---

## Project Structure
```
quarkus-pha/
├── CLAUDE.md
├── LICENSE                          # Apache 2.0
├── README.md
├── build.gradle
├── settings.gradle
├── runtime/
│   └── src/main/
│       ├── java/                            # Extension runtime Java code
│       └── resources/
│           ├── META-INF/
│           │   └── resources/
│           │       └── web/                 # Static assets, served at /web/...
│           │           ├── css/
│           │           │   ├── pha.css      # Project-wide styles
│           │           │   └── components/  # Component-specific styles
│           │           ├── js/
│           │           │   ├── alpine/
│           │           │   │   ├── components/   # Alpine.data() factories
│           │           │   │   └── stores/       # Alpine.store() definitions
│           │           │   ├── htmx/
│           │           │   │   └── extensions/   # Custom HTMX event handlers
│           │           │   ├── charts/
│           │           │   │   ├── echarts/      # Reusable ECharts config builders
│           │           │   │   └── d3/           # Reusable D3 utilities
│           │           │   ├── maps/
│           │           │   │   └── maplibre/     # MapLibre wrappers
│           │           │   ├── utils/            # Vanilla JS utilities
│           │           │   └── main.js           # Entry point
│           │           └── vendor/          # Vendored bundles (git-ignored, regenerated)
│           └── templates/
│               ├── layouts/                 # Base page layouts
│               ├── components/              # HTML fragment components
│               │   ├── navigation/
│               │   ├── forms/
│               │   ├── tables/
│               │   ├── charts/
│               │   ├── maps/
│               │   └── feedback/
│               └── partials/                # HTMX partial swap targets
├── deployment/
│   └── src/main/java/               # Extension deployment/build-time code
└── integration-tests/
    └── src/                         # Extension integration tests
```

---

## Formatting
HTML, CSS, JS, and JSON files are formatted with Prettier, run inside a Podman container so nothing touches the host. Use the project script:

```bash
bash scripts/format.sh                          # format default targets in-place
bash scripts/format.sh --check                  # check only (CI-style); fails if changes are needed
bash scripts/format.sh path/to/file-or-dir      # format a specific file or directory
```

Default targets:
- `runtime/src/main/resources/templates/**/*.html`
- `runtime/src/main/resources/META-INF/resources/web/css/**/*.css`
- `runtime/src/main/resources/META-INF/resources/web/js/**/*.js`
- `integration-tests/src/main/resources/templates/**/*.html`

Excluded via `.prettierignore`: `runtime/.../web/vendor/`, `build/`, `node_modules/`, Playwright reports, lock files. Qute expressions (`{...}`, `{#...}{/...}`) are treated as plain text by Prettier and pass through untouched.

---

## License
Apache License 2.0

Copyright 2026 SiteNetSoft