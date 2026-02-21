import { test, expect } from "@playwright/test";

test.describe("Tooltip", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tooltip");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#top-heading")).toBeVisible();
    await expect(page.locator("#bottom-heading")).toBeVisible();
    await expect(page.locator("#left-heading")).toBeVisible();
    await expect(page.locator("#right-heading")).toBeVisible();
  });

  test.describe("Top", () => {
    test("has pf-v6-c-tooltip and pf-m-top classes", async ({ page }) => {
      await expect(page.locator("#tt-top")).toHaveClass(/pf-v6-c-tooltip/);
      await expect(page.locator("#tt-top")).toHaveClass(/pf-m-top/);
    });

    test("has arrow and content", async ({ page }) => {
      await expect(page.locator("#tt-top .pf-v6-c-tooltip__arrow")).toBeVisible();
      await expect(page.locator("#tt-top .pf-v6-c-tooltip__content")).toBeVisible();
    });
  });

  test.describe("Bottom", () => {
    test("has class pf-m-bottom", async ({ page }) => {
      await expect(page.locator("#tt-bottom")).toHaveClass(/pf-m-bottom/);
    });
  });

  test.describe("Left", () => {
    test("has class pf-m-left", async ({ page }) => {
      await expect(page.locator("#tt-left")).toHaveClass(/pf-m-left/);
    });
  });

  test.describe("Right", () => {
    test("has class pf-m-right", async ({ page }) => {
      await expect(page.locator("#tt-right")).toHaveClass(/pf-m-right/);
    });
  });
});
