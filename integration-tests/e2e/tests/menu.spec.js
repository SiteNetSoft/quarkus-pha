import { test, expect } from "@playwright/test";

test.describe("Menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/menu");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-danger-item-heading")).toBeVisible();
    await expect(page.locator("#with-descriptions-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-menu class", async ({ page }) => {
      await expect(page.locator("#mn-basic")).toHaveClass(/pf-v6-c-menu/);
    });

    test("has menu list items", async ({ page }) => {
      await expect(page.locator("#mn-basic .pf-v6-c-menu__list-item").first()).toBeVisible();
    });
  });

  test.describe("With Danger", () => {
    test("has a danger item", async ({ page }) => {
      await expect(page.locator("#mn-danger .pf-v6-c-menu__item.pf-m-danger")).toBeVisible();
    });
  });

  test.describe("With Descriptions", () => {
    test("has menu item description", async ({ page }) => {
      await expect(page.locator("#mn-descriptions .pf-v6-c-menu__item-description").first()).toBeVisible();
    });
  });
});
