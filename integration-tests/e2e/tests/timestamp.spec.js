import { test, expect } from "@playwright/test";

test.describe("Timestamp", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/timestamp");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#default-heading")).toBeVisible();
    await expect(page.locator("#help-text-heading")).toBeVisible();
  });

  test("default timestamp has text", async ({ page }) => {
    await expect(page.locator("#ts-default .pf-v6-c-timestamp__text")).toContainText("January 1, 2026");
  });

  test("default timestamp has pf-v6-c-timestamp class", async ({ page }) => {
    await expect(page.locator("#ts-default")).toHaveClass(/pf-v6-c-timestamp/);
  });

  test("help text variant has help-text modifier", async ({ page }) => {
    await expect(page.locator("#ts-help")).toHaveClass(/pf-m-help-text/);
  });
});
