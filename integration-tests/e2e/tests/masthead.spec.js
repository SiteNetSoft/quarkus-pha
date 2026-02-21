import { test, expect } from "@playwright/test";

test.describe("Masthead", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/masthead");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#stacked-heading")).toBeVisible();
    await expect(page.locator("#stacked-heading")).toHaveText("Stacked");
  });

  test.describe("Basic", () => {
    test("masthead has pf-v6-c-masthead class", async ({ page }) => {
      await expect(page.locator("#mh-basic")).toHaveClass(/pf-v6-c-masthead/);
    });

    test("masthead has a brand area", async ({ page }) => {
      await expect(page.locator("#mh-basic .pf-v6-c-masthead__logo")).toBeVisible();
    });

    test("masthead has content area", async ({ page }) => {
      await expect(page.locator("#mh-basic")).toContainText("Toolbar area");
    });
  });

  test.describe("Stacked", () => {
    test("stacked masthead has pf-m-display-stack class", async ({ page }) => {
      await expect(page.locator("#mh-stacked")).toHaveClass(/pf-m-display-stack/);
    });

    test("stacked masthead has brand area", async ({ page }) => {
      await expect(page.locator("#mh-stacked .pf-v6-c-masthead__logo")).toBeVisible();
    });
  });
});
