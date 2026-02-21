import { test, expect } from "@playwright/test";

test.describe("Data List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/data-list");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#compact-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-data-list class", async ({ page }) => {
      await expect(page.locator("#dl-basic")).toHaveClass(/pf-v6-c-data-list/);
    });

    test("has 3 items", async ({ page }) => {
      await expect(
        page.locator("#dl-basic .pf-v6-c-data-list__item")
      ).toHaveCount(3);
    });
  });

  test.describe("Compact", () => {
    test("has pf-m-compact modifier", async ({ page }) => {
      await expect(page.locator("#dl-compact")).toHaveClass(/pf-m-compact/);
    });
  });
});
