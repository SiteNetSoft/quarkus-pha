import { test, expect } from "@playwright/test";

test.describe("Overflow Menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/overflow-menu");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#icon-button-group-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-overflow-menu class", async ({ page }) => {
      await expect(page.locator("#om-basic")).toHaveClass(/pf-v6-c-overflow-menu/);
    });

    test("has content and control sections", async ({ page }) => {
      await expect(page.locator("#om-basic .pf-v6-c-overflow-menu__content")).toBeVisible();
      await expect(page.locator("#om-basic .pf-v6-c-overflow-menu__control")).toBeVisible();
    });
  });

  test.describe("Icon Button", () => {
    test("group has class pf-m-icon-button-group", async ({ page }) => {
      await expect(page.locator("#om-icons .pf-v6-c-overflow-menu__group.pf-m-icon-button-group")).toBeVisible();
    });
  });
});
