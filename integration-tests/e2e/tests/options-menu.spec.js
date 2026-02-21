import { test, expect } from "@playwright/test";

test.describe("Options Menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/options-menu");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Options Menu");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#plain-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("basic wrapper exists", async ({ page }) => {
      await expect(page.locator("#om-basic")).toBeVisible();
    });

    test("menu toggle is visible", async ({ page }) => {
      await expect(
        page.locator("#om-basic .pf-v6-c-menu-toggle")
      ).toBeVisible();
    });

    test("clicking toggle opens menu with 3 items", async ({ page }) => {
      await page.locator("#om-basic .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#om-basic .pf-v6-c-menu")).toBeVisible();
      await expect(
        page.locator("#om-basic .pf-v6-c-menu__list-item")
      ).toHaveCount(3);
    });

    test("menu is not visible by default", async ({ page }) => {
      await expect(
        page.locator("#om-basic .pf-v6-c-menu")
      ).not.toBeVisible();
    });
  });

  test.describe("Plain", () => {
    test("plain toggle has pf-m-plain class", async ({ page }) => {
      await expect(
        page.locator("#om-plain .pf-v6-c-menu-toggle")
      ).toHaveClass(/pf-m-plain/);
    });

    test("clicking plain toggle opens menu with 2 items", async ({ page }) => {
      await page.locator("#om-plain .pf-v6-c-menu-toggle").click();
      await expect(
        page.locator("#om-plain .pf-v6-c-menu__list-item")
      ).toHaveCount(2);
    });
  });
});
