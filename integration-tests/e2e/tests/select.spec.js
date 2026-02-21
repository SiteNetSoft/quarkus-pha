import { test, expect } from "@playwright/test";

test.describe("Select", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/select");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#preselected-heading")).toBeVisible();
    await expect(page.locator("#preselected-heading")).toHaveText("With preselected");
  });

  test.describe("Basic", () => {
    test("basic select toggle is visible", async ({ page }) => {
      await expect(page.locator("#sel-basic .pf-v6-c-menu-toggle")).toBeVisible();
    });

    test("basic select menu is hidden by default", async ({ page }) => {
      await expect(page.locator("#sel-basic .pf-v6-c-menu")).toBeHidden();
    });

    test("clicking toggle opens the menu", async ({ page }) => {
      await page.locator("#sel-basic .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#sel-basic .pf-v6-c-menu")).toBeVisible();
    });

    test("menu has three options", async ({ page }) => {
      await page.locator("#sel-basic .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#sel-basic .pf-v6-c-menu__list-item")).toHaveCount(3);
    });

    test("selecting an option closes the menu and updates toggle text", async ({ page }) => {
      await page.locator("#sel-basic .pf-v6-c-menu-toggle").click();
      await page.locator("#sel-basic .pf-v6-c-menu__list-item").first().locator("button").click();
      await expect(page.locator("#sel-basic .pf-v6-c-menu")).toBeHidden();
      await expect(page.locator("#sel-basic .pf-v6-c-menu-toggle__text")).toHaveText("Option 1");
    });
  });

  test.describe("With preselected", () => {
    test("preselected select toggle shows preselected value", async ({ page }) => {
      await expect(page.locator("#sel-preselected .pf-v6-c-menu-toggle__text")).toHaveText("Option 2");
    });

    test("preselected select menu is hidden by default", async ({ page }) => {
      await expect(page.locator("#sel-preselected .pf-v6-c-menu")).toBeHidden();
    });

    test("clicking toggle opens the preselected menu", async ({ page }) => {
      await page.locator("#sel-preselected .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#sel-preselected .pf-v6-c-menu")).toBeVisible();
    });
  });
});
