import { test, expect } from "@playwright/test";

test.describe("Tile", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tile");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-icon-heading")).toBeVisible();
    await expect(page.locator("#selected-heading")).toBeVisible();
  });

  test("deprecation notice is visible", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-banner")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-tile class", async ({ page }) => {
      await expect(page.locator("#tile-basic")).toHaveClass(/pf-v6-c-tile/);
    });
  });

  test.describe("With icon", () => {
    test("has icon", async ({ page }) => {
      await expect(
        page.locator("#tile-icon .pf-v6-c-tile__icon")
      ).toBeVisible();
    });
  });

  test.describe("Selected", () => {
    test("has pf-m-selected modifier", async ({ page }) => {
      await expect(page.locator("#tile-selected")).toHaveClass(
        /pf-m-selected/
      );
    });
  });
});
