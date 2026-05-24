import { test, expect } from "@playwright/test";

test.describe("Text Input Group", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/text-input-group");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#with-icon")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-text-input-group class", async ({ page }) => {
      await expect(page.locator("#tig-basic")).toHaveClass(
        /pf-v6-c-text-input-group/
      );
    });

    test("has text input", async ({ page }) => {
      await expect(page.locator("#tig-basic input")).toBeVisible();
    });
  });

  test.describe("With icon", () => {
    test("has icon element", async ({ page }) => {
      await expect(
        page.locator("#tig-icon .pf-v6-c-text-input-group__icon")
      ).toBeVisible();
    });
  });
});
