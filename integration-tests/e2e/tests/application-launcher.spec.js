import { test, expect } from "@playwright/test";

test.describe("Application Launcher", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/application-launcher");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Application Launcher");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
  });

  test("basic wrapper exists", async ({ page }) => {
    await expect(page.locator("#al-basic")).toBeVisible();
  });

  test("menu toggle has pf-m-plain class", async ({ page }) => {
    await expect(
      page.locator("#al-basic .pf-v6-c-menu-toggle")
    ).toHaveClass(/pf-m-plain/);
  });

  test("menu is not visible by default", async ({ page }) => {
    await expect(page.locator("#al-basic .pf-v6-c-menu")).not.toBeVisible();
  });

  test("clicking toggle opens menu", async ({ page }) => {
    await page.locator("#al-basic .pf-v6-c-menu-toggle").click();
    await expect(page.locator("#al-basic .pf-v6-c-menu")).toBeVisible();
  });

  test("menu has 3 application items", async ({ page }) => {
    await page.locator("#al-basic .pf-v6-c-menu-toggle").click();
    await expect(
      page.locator("#al-basic .pf-v6-c-menu__list-item")
    ).toHaveCount(3);
  });
});
