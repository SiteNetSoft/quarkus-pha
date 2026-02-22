import { test, expect } from "@playwright/test";

test.describe("Chart", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/chart");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Chart");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#bar-heading")).toBeVisible();
    await expect(page.locator("#line-heading")).toBeVisible();
    await expect(page.locator("#pie-heading")).toBeVisible();
    await expect(page.locator("#area-heading")).toBeVisible();
    await expect(page.locator("#gauge-heading")).toBeVisible();
    await expect(page.locator("#mixed-heading")).toBeVisible();
  });

  test.describe("Bar Chart", () => {
    test("container has pha-c-chart class", async ({ page }) => {
      await expect(page.locator("#chart-bar")).toHaveClass(/pha-c-chart/);
    });

    test("ECharts renders a canvas", async ({ page }) => {
      const canvas = page.locator("#chart-bar canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("Line Chart", () => {
    test("container has pha-c-chart class", async ({ page }) => {
      await expect(page.locator("#chart-line")).toHaveClass(/pha-c-chart/);
    });

    test("ECharts renders a canvas", async ({ page }) => {
      const canvas = page.locator("#chart-line canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("Pie Chart", () => {
    test("container has pha-c-chart class", async ({ page }) => {
      await expect(page.locator("#chart-pie")).toHaveClass(/pha-c-chart/);
    });

    test("ECharts renders a canvas", async ({ page }) => {
      const canvas = page.locator("#chart-pie canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("Area Chart", () => {
    test("container has pha-c-chart class", async ({ page }) => {
      await expect(page.locator("#chart-area")).toHaveClass(/pha-c-chart/);
    });

    test("ECharts renders a canvas", async ({ page }) => {
      const canvas = page.locator("#chart-area canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("Gauge", () => {
    test("container has pha-c-chart class", async ({ page }) => {
      await expect(page.locator("#chart-gauge")).toHaveClass(/pha-c-chart/);
    });

    test("ECharts renders a canvas", async ({ page }) => {
      const canvas = page.locator("#chart-gauge canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("Mixed Chart", () => {
    test("container has pha-c-chart class", async ({ page }) => {
      await expect(page.locator("#chart-mixed")).toHaveClass(/pha-c-chart/);
    });

    test("ECharts renders a canvas", async ({ page }) => {
      const canvas = page.locator("#chart-mixed canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });
  });
});
