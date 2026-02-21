import { test, expect } from "@playwright/test";

test.describe("Popover", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/popover");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#top-heading")).toBeVisible();
    await expect(page.locator("#right-heading")).toBeVisible();
    await expect(page.locator("#with-header-heading")).toBeVisible();
  });

  test.describe("Top", () => {
    test("has pf-v6-c-popover and pf-m-top classes", async ({ page }) => {
      await expect(page.locator("#po-top")).toHaveClass(/pf-v6-c-popover/);
      await expect(page.locator("#po-top")).toHaveClass(/pf-m-top/);
    });

    test("has arrow and body", async ({ page }) => {
      await expect(page.locator("#po-top .pf-v6-c-popover__arrow")).toBeVisible();
      await expect(page.locator("#po-top .pf-v6-c-popover__body")).toBeVisible();
    });
  });

  test.describe("Right", () => {
    test("has class pf-m-right", async ({ page }) => {
      await expect(page.locator("#po-right")).toHaveClass(/pf-m-right/);
    });
  });

  test.describe("With Header", () => {
    test("popover header is visible", async ({ page }) => {
      await expect(page.locator("#po-header .pf-v6-c-popover__header")).toBeVisible();
    });
  });
});
