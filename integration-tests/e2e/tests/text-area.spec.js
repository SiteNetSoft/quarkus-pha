import { test, expect } from "@playwright/test";

test.describe("Text Area", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/text-area");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has textarea element", async ({ page }) => {
      await expect(page.locator("#ta-basic textarea")).toBeVisible();
    });
  });

  test.describe("Disabled", () => {
    test("textarea has disabled attribute", async ({ page }) => {
      await expect(page.locator("#ta-disabled textarea")).toBeDisabled();
    });
  });
});
