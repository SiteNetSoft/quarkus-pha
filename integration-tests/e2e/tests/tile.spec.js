import { test, expect } from "@playwright/test";

test.describe("Tile", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tile");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#with-icon-heading")).toHaveText("With icon");
    await expect(page.locator("#selected-heading")).toHaveText("Selected");
  });

  test("deprecation banner is visible", async ({ page }) => {
    const banner = page.locator(".pf-v6-c-banner.pf-m-gold");
    await expect(banner).toBeVisible();
    await expect(banner).toContainText("Deprecated");
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-tile class and title", async ({ page }) => {
      await expect(page.locator("#tile-basic")).toHaveClass(/pf-v6-c-tile/);
      await expect(page.locator("#tile-basic .pf-v6-c-tile__title")).toHaveText("Basic tile");
    });
  });

  test.describe("With icon", () => {
    test("has icon and body text", async ({ page }) => {
      await expect(page.locator("#tile-icon .pf-v6-c-tile__icon")).toBeVisible();
      await expect(page.locator("#tile-icon .pf-v6-c-tile__body")).toContainText("This tile has");
    });
  });

  test.describe("Selected", () => {
    test("has pf-m-selected modifier", async ({ page }) => {
      await expect(page.locator("#tile-selected")).toHaveClass(/pf-m-selected/);
    });
  });
});
