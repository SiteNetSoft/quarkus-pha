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
