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
    await expect(page.locator("#chart-doc-colors")).toHaveAttribute(
      "href",
      "/components/chart/colors",
    );
    await expect(page.locator("#chart-doc-themes")).toHaveAttribute(
      "href",
      "/components/chart/themes",
    );
  });
});

test.describe("Per-type chart pages", () => {
  for (const type of SAMPLE_TYPES) {
    test(`${type} chart page renders pha-c-chart container with an ECharts canvas`, async ({
      page,
    }) => {
      await page.goto(`/components/chart/${type}`);

      const container = page.locator(`#ch-${type}-basic`);
      await expect(container).toBeVisible();
      await expect(container).toHaveClass(/pha-c-chart/);

      const canvas = container.locator("canvas");
      await expect(canvas.first()).toBeVisible({ timeout: 10000 });
    });
  }
});
