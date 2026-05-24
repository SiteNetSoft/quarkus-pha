import { test, expect } from "@playwright/test";

test.describe("Menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/menu");
  });

  test("page loads with both example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#with-descriptions")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-menu class", async ({ page }) => {
      await expect(page.locator("#mn-basic")).toHaveClass(/pf-v6-c-menu/);
    });

    test("has menu list items", async ({ page }) => {
      await expect(page.locator("#mn-basic .pf-v6-c-menu__list-item").first()).toBeVisible();
    });

    test("includes a danger item", async ({ page }) => {
      await expect(page.locator("#mn-basic .pf-v6-c-menu__item.pf-m-danger")).toBeVisible();
    });
  });

  test.describe("With descriptions", () => {
    test("has pf-v6-c-menu class", async ({ page }) => {
      await expect(page.locator("#mn-desc")).toHaveClass(/pf-v6-c-menu/);
    });

    test("renders menu item descriptions", async ({ page }) => {
      await expect(page.locator("#mn-desc .pf-v6-c-menu__item-description").first()).toBeVisible();
    });
  });
});
