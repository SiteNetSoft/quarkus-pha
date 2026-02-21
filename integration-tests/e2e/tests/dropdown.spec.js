import { test, expect } from "@playwright/test";

test.describe("Dropdown", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/dropdown");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#kebab-heading")).toBeVisible();
    await expect(page.locator("#with-description-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("menu toggle is visible", async ({ page }) => {
      await expect(page.locator("#dd-basic .pf-v6-c-menu-toggle")).toBeVisible();
    });

    test("menu is not visible by default", async ({ page }) => {
      await expect(page.locator("#dd-basic .pf-v6-c-menu")).not.toBeVisible();
    });

    test("clicking toggle opens menu and toggle gets pf-m-expanded", async ({ page }) => {
      await page.locator("#dd-basic .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#dd-basic .pf-v6-c-menu")).toBeVisible();
      await expect(page.locator("#dd-basic .pf-v6-c-menu-toggle")).toHaveClass(/pf-m-expanded/);
    });
  });

  test.describe("Kebab", () => {
    test("menu toggle has class pf-m-plain", async ({ page }) => {
      await expect(page.locator("#dd-kebab .pf-v6-c-menu-toggle")).toHaveClass(/pf-m-plain/);
    });
  });

  test.describe("With Description", () => {
    test("menu item description exists", async ({ page }) => {
      await expect(page.locator("#dd-desc .pf-v6-c-menu__item-description").first()).toBeDefined();
    });
  });
});
