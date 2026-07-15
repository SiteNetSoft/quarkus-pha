import { test, expect } from "@playwright/test";

// All chart-type slugs wired up in ChartExamplesRoutes.
const CHART_TYPES = [
  "area",
  "bar",
  "box-plot",
  "bullet",
  "donut",
  "donut-utilization",
  "line",
  "pie",
  "sankey",
  "scatter",
  "sparkline",
  "stack",
  "threshold",
];

// Representative subset that gets per-type smoke coverage.
const SAMPLE_TYPES = ["bar", "line", "pie"];

// Types with examples beyond "basic" — mirrors CHART_EXAMPLES in ChartExamplesRoutes.
const CHART_EXAMPLES = {
  area: ["teal-with-axis-label"],
  bar: ["alerts-timeline"],
  "box-plot": ["labels"],
  donut: ["right-legend", "small", "small-bottom-subtitle"],
  "donut-utilization": ["inverted", "static-thresholds"],
  sankey: ["toolbox"],
  line: ["brush-zoom"],
  bullet: [
    "segmented-measure",
    "measure-dot",
    "error-measure-custom-ticks",
    "negative-positive",
    "reversed",
    "vertical-segmented",
  ],
  scatter: ["line-combo"],
  stack: ["horizontal", "monthly-data"],
};

test.describe("Chart index page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/chart");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Charts");
  });

  test("renders a card link for every chart type", async ({ page }) => {
    for (const type of CHART_TYPES) {
      const card = page.locator(`#chart-card-${type}`);
      await expect(card).toBeVisible();
      await expect(card).toHaveAttribute("href", `/components/chart/${type}`);
    }
  });

  test("renders sample documentation links", async ({ page }) => {
    await expect(page.locator("#chart-doc-colors")).toHaveAttribute("href", "/components/chart/colors");
    await expect(page.locator("#chart-doc-themes")).toHaveAttribute("href", "/components/chart/themes");
  });
});

test.describe("Per-type chart pages", () => {
  for (const type of SAMPLE_TYPES) {
    test(`${type} chart page renders pha-c-chart container with an ECharts canvas`, async ({ page }) => {
      await page.goto(`/components/chart/${type}`);

      const container = page.locator(`#ch-${type}-basic`);
      await expect(container).toBeVisible();
      await expect(container).toHaveClass(/pha-c-chart/);

      const canvas = container.locator("canvas");
      await expect(canvas.first()).toBeVisible({ timeout: 10000 });
    });
  }
});

test.describe("Multi-example chart pages", () => {
  for (const [type, examples] of Object.entries(CHART_EXAMPLES)) {
    test(`${type} demo page renders a canvas per example`, async ({ page }) => {
      const pageErrors = [];
      page.on("pageerror", (err) => pageErrors.push(err.message));

      await page.goto(`/components/chart/${type}`);

      for (const example of ["basic", ...examples]) {
        const container = page.locator(`#ch-${type}-${example}`);
        await expect(container).toBeVisible();
        await expect(container.locator("canvas").first()).toBeVisible({
          timeout: 10000,
        });
        // Example-card chrome wired to the right standalone/source URLs.
        await expect(page.locator(`h3#${example}`)).toBeVisible();
      }
      expect(pageErrors).toEqual([]);
    });

    for (const example of examples) {
      test(`${type}/${example} standalone page paints without errors`, async ({ page }) => {
        const pageErrors = [];
        page.on("pageerror", (err) => pageErrors.push(err.message));

        await page.goto(`/components/chart/${type}/${example}`);

        const container = page.locator(`#ch-${type}-${example}`);
        await expect(container).toBeVisible();
        await expect(container.locator("canvas").first()).toBeVisible({
          timeout: 10000,
        });
        expect(pageErrors).toEqual([]);
      });
    }
  }

  test("example-card source viewer links point at the per-example routes", async ({ page }) => {
    await page.goto("/components/chart/bullet");
    const card = page.locator('[data-source-href="/components/chart/bullet/source/reversed"]').first();
    await expect(card).toHaveAttribute("data-rendered-href", "/components/chart/bullet/reversed");
    const source = await page.request.get("/components/chart/bullet/source/reversed");
    expect(source.ok()).toBeTruthy();
    expect(await source.text()).toContain("phaChart");
  });
});

test.describe("Chart interactivity (docs)", () => {
  test("legends doc page renders the interactive + tooltip legend charts", async ({ page }) => {
    const pageErrors = [];
    page.on("pageerror", (err) => pageErrors.push(err.message));
    await page.goto("/components/chart/legends");
    for (const id of ["ch-legend-interactive", "ch-legend-tooltips", "ch-legend-links"]) {
      await expect(page.locator(`#${id} canvas`).first()).toBeVisible({ timeout: 10000 });
    }
    expect(pageErrors).toEqual([]);
  });

  test("phaChart forwards legend toggles as a chart-legend-select event", async ({ page }) => {
    await page.goto("/components/chart/legends");
    await expect(page.locator("#ch-legend-links canvas").first()).toBeVisible({ timeout: 10000 });

    const status = page.locator("#ch-legend-links").locator("xpath=preceding-sibling::p[1]");
    await expect(status).toHaveText(/Toggle a legend item/);

    // Drive the ECharts legend toggle programmatically — deterministic, unlike
    // clicking legend glyphs painted on the canvas.
    await page.evaluate(() => {
      const el = document.getElementById("ch-legend-links");
      window.Alpine.$data(el).getChart().dispatchAction({ type: "legendToggleSelect", name: "api" });
    });
    await expect(status).toHaveText("api is now hidden");
  });

  test("tooltips doc page renders all tooltip variants without errors", async ({ page }) => {
    const pageErrors = [];
    page.on("pageerror", (err) => pageErrors.push(err.message));
    await page.goto("/components/chart/tooltips");
    for (const id of [
      "ch-tooltip-crosshair",
      "ch-tooltip-html",
      "ch-tooltip-data-label",
      "ch-tooltip-fixed",
      "ch-tooltip-confine",
    ]) {
      await expect(page.locator(`#${id} canvas`).first()).toBeVisible({ timeout: 10000 });
    }
    expect(pageErrors).toEqual([]);
  });
});

test.describe("Chart patterns, skeletons, resize (docs)", () => {
  test("patterns doc page renders every decal example", async ({ page }) => {
    const pageErrors = [];
    page.on("pageerror", (err) => pageErrors.push(err.message));
    await page.goto("/components/chart/patterns");
    for (const id of [
      "ch-patterns-demo",
      "ch-patterns-pie",
      "ch-patterns-bar",
      "ch-patterns-visibility",
      "ch-patterns-custom",
      "ch-patterns-all",
      "ch-patterns-threshold",
    ]) {
      await expect(page.locator(`#${id} canvas`).first()).toBeVisible({ timeout: 10000 });
    }
    expect(pageErrors).toEqual([]);
  });

  test("skeletons doc page renders the four grayscale skeletons", async ({ page }) => {
    const pageErrors = [];
    page.on("pageerror", (err) => pageErrors.push(err.message));
    await page.goto("/components/chart/skeletons");
    for (const id of ["ch-skeleton-area", "ch-skeleton-bar", "ch-skeleton-donut", "ch-skeleton-donut-threshold"]) {
      await expect(page.locator(`#${id} canvas`).first()).toBeVisible({ timeout: 10000 });
    }
    expect(pageErrors).toEqual([]);
  });

  test("phaChart resizes with its container (ResizeObserver), not just the window", async ({ page }) => {
    await page.goto("/components/chart/resize-observer");
    const canvas = page.locator("#ch-resize-observer canvas").first();
    await expect(canvas).toBeVisible({ timeout: 10000 });

    const box = page.locator("#ch-resize-observer").locator("xpath=ancestor::div[contains(@style,'resize')][1]");

    const wide = (await canvas.boundingBox()).width;
    // Shrink the CONTAINER only — the viewport never changes.
    await box.evaluate((el) => {
      el.style.width = "280px";
    });
    await expect.poll(async () => Math.round((await canvas.boundingBox()).width)).toBeLessThan(Math.round(wide) - 100);

    // And grows back.
    await box.evaluate((el) => {
      el.style.width = "600px";
    });
    await expect.poll(async () => Math.round((await canvas.boundingBox()).width)).toBeGreaterThan(400);
  });
});
