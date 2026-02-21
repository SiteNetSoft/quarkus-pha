import { test, expect } from "@playwright/test";

test.describe("Description List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/description-list");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#fill-columns-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-description-list class", async ({ page }) => {
      await expect(page.locator("#desc-basic")).toHaveClass(
        /pf-v6-c-description-list/
      );
    });

    test("has groups", async ({ page }) => {
      const groups = page.locator(
        "#desc-basic .pf-v6-c-description-list__group"
      );
      await expect(groups.first()).toBeVisible();
    });
  });

  test.describe("Fill columns", () => {
    test("has pf-m-fill-columns modifier", async ({ page }) => {
      await expect(page.locator("#desc-fill")).toHaveClass(
        /pf-m-fill-columns/
      );
    });
  });
});
