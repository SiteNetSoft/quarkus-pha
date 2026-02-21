import { test, expect } from "@playwright/test";

test.describe("Input Group", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/input-group");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-button-heading")).toBeVisible();
    await expect(page.locator("#with-select-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-input-group class", async ({ page }) => {
      await expect(page.locator("#ig-basic")).toHaveClass(/pf-v6-c-input-group/);
    });

    test("has pf-v6-c-input-group__item children", async ({ page }) => {
      await expect(page.locator("#ig-basic .pf-v6-c-input-group__item").first()).toBeVisible();
    });
  });

  test.describe("With Button", () => {
    test("has a button element", async ({ page }) => {
      await expect(page.locator("#ig-button button")).toBeVisible();
    });
  });
});
