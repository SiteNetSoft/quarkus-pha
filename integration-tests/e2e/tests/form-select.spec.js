import { test, expect } from "@playwright/test";

test.describe("Form Select", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/form-select");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has select element", async ({ page }) => {
      await expect(page.locator("#fs-basic select")).toBeVisible();
    });

    test("has options", async ({ page }) => {
      const options = page.locator("#fs-basic select option");
      await expect(options).toHaveCount(4);
    });
  });

  test.describe("Disabled", () => {
    test("select has disabled attribute", async ({ page }) => {
      await expect(page.locator("#fs-disabled select")).toBeDisabled();
    });
  });
});
