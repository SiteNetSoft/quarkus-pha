# quarkus-pha

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Testing

The project ships a multi-layer test pipeline (lint, HTML/CSS/JS validation,
type-check, server-side smoke + contract, Playwright E2E with console-error
capture, axe a11y, HTMX target/header contracts, keyboard-nav, reference
checks). Run everything with:

```shell script
bash scripts/e2e.sh
```

Reports land under `.reports/` (gitignored). See [TESTING.md](TESTING.md) for
the full breakdown of what each layer catches, how to run each one
standalone, and how to add a new test layer.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkus-pha-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

## Units
- [components](runtime/src/main/resources/templates/components)
- [layouts](runtime/src/main/resources/templates/layouts)
- [partials](runtime/src/main/resources/templates/partials)
- [structure](runtime/src/main/resources/templates/structure)

## E2E Integration Tests
- [components](integration-tests/src/main/resources/templates/components)

## TODO
- [ ] Icon (https://www.patternfly.org/components/icon) 90% done
- [ ] Title (https://www.patternfly.org/components/title)
- [ ] Helper text (https://www.patternfly.org/components/helper-text)
- [ ] List (https://www.patternfly.org/components/list)
- [ ] Content (https://www.patternfly.org/components/content)
- [ ] Divider (https://www.patternfly.org/components/divider)
- [ ] Panel (https://www.patternfly.org/components/panel)
- [ ] Button (https://www.patternfly.org/components/button)
- [ ] Label (https://www.patternfly.org/components/label/)
- [ ] Skeleton (https://www.patternfly.org/components/skeleton)
- [ ] Progress (https://www.patternfly.org/components/progress)
- [ ] Backdrop (https://www.patternfly.org/components/backdrop)
- [ ] Background image (https://www.patternfly.org/components/background-image)
- [ ] Breadcrumb (https://www.patternfly.org/components/breadcrumb)
- [ ] Back to the top (https://www.patternfly.org/components/back-to-top)
- [ ] Action list (https://www.patternfly.org/components/action-list/)
- [ ] Avatar (https://www.patternfly.org/components/avatar/)
- [ ] Badge (https://www.patternfly.org/components/badge/)
- [ ] Banner (https://www.patternfly.org/components/banner/)
- [ ] Brand (https://www.patternfly.org/components/brand/)
- [ ] Layouts
  - [ ] Bullseye (https://www.patternfly.org/layouts/bullseye)
  - [ ] Flex (https://www.patternfly.org/layouts/flex)
  - [ ] Gallery (https://www.patternfly.org/layouts/gallery)
  - [ ] Grid (https://www.patternfly.org/layouts/grid)
  - [ ] Level (https://www.patternfly.org/layouts/level)
  - [ ] Split (https://www.patternfly.org/layouts/split)
  - [ ] Stack (https://www.patternfly.org/layouts/stack)
- [ ] Form
  - [ ] Form control (https://www.patternfly.org/components/forms/form-control)
  - [ ] Checkbox (https://www.patternfly.org/components/forms/checkbox/)
  - [ ] Form select (https://www.patternfly.org/components/forms/form-select)
  - [ ] Form (https://www.patternfly.org/components/forms/form)
  - [ ] Radio (https://www.patternfly.org/components/forms/radio)
  - [ ] Text area (https://www.patternfly.org/components/forms/text-area)
  - [ ] Text input (https://www.patternfly.org/components/forms/text-input)
- [ ] Timestamp (https://www.patternfly.org/components/timestamp)
- [ ] Truncate (https://www.patternfly.org/components/truncate)
- [ ] Tooltip (https://www.patternfly.org/components/tooltip)
- [ ] Toggle group (https://www.patternfly.org/components/toggle-group)
- [ ] Search input (https://www.patternfly.org/components/search-input)
- [ ] Popover (https://www.patternfly.org/components/popover)
- [ ] Slider (https://www.patternfly.org/components/slider)
- [ ] Spinner (https://www.patternfly.org/components/spinner)
- [ ] Switch (https://www.patternfly.org/components/switch)
- [ ] Text input group (https://www.patternfly.org/components/text-input-group)
- [ ] Jump links (https://www.patternfly.org/components/jump-links) -> This is what is used in the web page of Tree view
- [ ] Simple list (https://www.patternfly.org/components/simple-list)
- [ ] Table (https://www.patternfly.org/components/table)
- [ ] Tabs (https://www.patternfly.org/components/tabs)
- [ ] Tree view (https://www.patternfly.org/components/tree-view/) 80% done
- [ ] Alert (https://www.patternfly.org/components/alert)
- [ ] Clipboard copy (https://www.patternfly.org/components/clipboard-copy)
- [ ] Drag and drop (https://www.patternfly.org/components/drag-and-drop)
- [ ] Menus
  - [ ] Menu (https://www.patternfly.org/components/menus/menu)
  - [ ] Application launcher (https://www.patternfly.org/components/menus/application-launcher)
  - [ ] Context selector (https://www.patternfly.org/components/menus/context-selector)
  - [ ] Custom menus (https://www.patternfly.org/components/menus/custom-menus)
  - [ ] Dropdown (https://www.patternfly.org/components/menus/dropdown)
  - [ ] Menu toggle (https://www.patternfly.org/components/menus/menu-toggle)
  - [ ] Options menu (https://www.patternfly.org/components/menus/options-menu)
  - [ ] Select (https://www.patternfly.org/components/menus/select)
- [ ] Overflow menu (https://www.patternfly.org/components/overflow-menu)
- [ ] Navigation (https://www.patternfly.org/components/navigation)
- [ ] Notification badge (https://www.patternfly.org/components/notification-badge)
- [ ] Notification drawer (https://www.patternfly.org/components/notification-drawer)
- [ ] Number input (https://www.patternfly.org/components/number-input)
- [ ] Card (https://www.patternfly.org/components/card/)
- [ ] Description list (https://www.patternfly.org/components/description-list)
- [ ] Hint (https://www.patternfly.org/components/hint)
- [ ] Expandable section (https://www.patternfly.org/components/expandable-section)
- [ ] Masthead (https://www.patternfly.org/components/masthead)
- [ ] Page (https://www.patternfly.org/components/page)
- [ ] Pagination (https://www.patternfly.org/components/pagination)
- [ ] Password generator (https://www.patternfly.org/components/password-generator)
- [ ] Password strength (https://www.patternfly.org/components/password-strength)
- [ ] Progress stepper (https://www.patternfly.org/components/progress-stepper)
- [ ] Sidebar (https://www.patternfly.org/components/sidebar)
- [ ] Drawer (https://www.patternfly.org/components/drawer)
- [ ] Skip to content (https://www.patternfly.org/components/skip-to-content)
- [ ] Toolbar (https://www.patternfly.org/components/toolbar)
- [ ] Wizard (https://www.patternfly.org/components/wizard)
- [ ] Empty state (https://www.patternfly.org/components/empty-state/)
- [ ] Multiple file upload (https://www.patternfly.org/components/file-upload/multiple-file-upload)
- [ ] Simple file upload (https://www.patternfly.org/components/file-upload/simple-file-upload)
- [ ] Code block (https://www.patternfly.org/components/code-block)
- [ ] Code editor (https://www.patternfly.org/components/code-editor)
- [ ] Inline edit (https://www.patternfly.org/components/inline-edit)
- [ ] Login page (https://www.patternfly.org/components/login-page)
- [ ] Modal (https://www.patternfly.org/components/modal)
- [ ] Date and time
  - [ ] Calendar month (https://www.patternfly.org/components/date-and-time/calendar-month)
  - [ ] Date and time picker (https://www.patternfly.org/components/date-and-time/date-and-time-picker)
  - [ ] Date picker (https://www.patternfly.org/components/date-and-time/date-picker)
  - [ ] Time picker (https://www.patternfly.org/components/date-and-time/time-picker)
- [ ] Data list (https://www.patternfly.org/components/data-list)
- [ ] Dual list selector (https://www.patternfly.org/components/dual-list-selector)
- [ ] Topology
  - [ ] Custom nodes (https://www.patternfly.org/topology/custom-nodes)
  - [ ] Custom edges (https://www.patternfly.org/topology/custom-edges)
  - [ ] Anchors (https://www.patternfly.org/topology/anchors)
  - [ ] Selection (https://www.patternfly.org/topology/selection)
  - [ ] Pan and zoom (https://www.patternfly.org/topology/pan-and-zoom)
  - [ ] Context menu (https://www.patternfly.org/topology/context-menu)
  - [ ] Drag and drop (https://www.patternfly.org/topology/drag-and-drop)
  - [ ] Control bar (https://www.patternfly.org/topology/control-bar)
  - [ ] Toolbar (https://www.patternfly.org/topology/toolbar)
  - [ ] Sidebar (https://www.patternfly.org/topology/sidebar)
  - [ ] Layouts (https://www.patternfly.org/topology/layouts)
  - [ ] Pipelines (https://www.patternfly.org/topology/pipelines)
- [ ] Charts
  - [ ] Colors for charts (https://www.patternfly.org/charts/colors-for-charts)
  - [ ] Area chart (https://www.patternfly.org/charts/area-chart)
  - [ ] Bar chart (https://www.patternfly.org/charts/bar-chart)
  - [ ] Box plot chart (https://www.patternfly.org/charts/box-plot-chart)
  - [ ] Bullet chart (https://www.patternfly.org/charts/bullet-chart)
  - [ ] Donut chart (https://www.patternfly.org/charts/donut-chart)
  - [ ] Donut utilization chart (https://www.patternfly.org/charts/donut-utilization-chart)
  - [ ] Legends (https://www.patternfly.org/charts/legends)
  - [ ] Line chart (https://www.patternfly.org/charts/line-chart)
  - [ ] Patterns (https://www.patternfly.org/charts/patterns)
  - [ ] Pie chart (https://www.patternfly.org/charts/pie-chart)
  - [ ] Resize observer (https://www.patternfly.org/charts/resize-observer)
  - [ ] Sankey chart (https://www.patternfly.org/charts/sankey-chart)
  - [ ] Scatter chart (https://www.patternfly.org/charts/scatter-chart)
  - [ ] Skeletons (https://www.patternfly.org/charts/skeletons)
  - [ ] Sparkline chart (https://www.patternfly.org/charts/sparkline-chart)
  - [ ] Stack chart (https://www.patternfly.org/charts/stack-chart)
  - [ ] Themes (https://www.patternfly.org/charts/themes)
  - [ ] Threshold chart (https://www.patternfly.org/charts/threshold-chart)
  - [ ] Tooltips (https://www.patternfly.org/charts/tooltips)
- [ ] Patterns
  - [ ] Card view (https://www.patternfly.org/patterns/card-view)
  - [ ] Dashboard (https://www.patternfly.org/patterns/dashboard)
  - [ ] Filters (https://www.patternfly.org/patterns/filters)
  - [ ] Password generator (https://www.patternfly.org/patterns/password-generator)
  - [ ] Password strength (https://www.patternfly.org/patterns/password-strength)
  - [ ] Primary-detail (https://www.patternfly.org/patterns/primary-detail)
  - [ ] Right-to-left (https://www.patternfly.org/patterns/right-to-left)
- [ ] Extensions
  - [ ] Log viewer (https://www.patternfly.org/extensions/log-viewer)
  - [ ] User feedback (https://www.patternfly.org/extensions/user-feedback)
  - [ ] Data view
    - [ ] Data view overview (https://www.patternfly.org/extensions/data-view/overview)
    - [ ] Data view toolbar (https://www.patternfly.org/extensions/data-view/toolbar/?page=1&perPage=5)
    - [ ] Data view table (https://www.patternfly.org/extensions/data-view/table)
  - [ ] Catalog view
    - [ ] Catalog item header (https://www.patternfly.org/extensions/catalog-view/catalog-item-header)
    - [ ] Catalog tile (https://www.patternfly.org/extensions/catalog-view/catalog-tile)
    - [ ] Filter side panel (https://www.patternfly.org/extensions/catalog-view/filter-side-panel)
    - [ ] Properties side panel (https://www.patternfly.org/extensions/catalog-view/properties-side-panel)
    - [ ] Vertical tabs (https://www.patternfly.org/extensions/catalog-view/vertical-tabs)