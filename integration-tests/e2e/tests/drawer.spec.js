import { test, expect } from "@playwright/test";

test.describe("Drawer", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/drawer");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#inline-heading")).toBeVisible();
    await expect(page.locator("#panel-left-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-drawer class", async ({ page }) => {
      await expect(page.locator("#dr-basic")).toHaveClass(/pf-v6-c-drawer/);
    });

    test("panel is hidden by default", async ({ page }) => {
      await expect(page.locator("#dr-basic")).not.toHaveClass(/pf-m-expanded/);
    });

    test("clicking toggle button opens panel", async ({ page }) => {
      await page.locator("#dr-basic button").first().click();
      await expect(page.locator("#dr-basic")).toHaveClass(/pf-m-expanded/);
    });
  });

  test.describe("Inline", () => {
    test("has class pf-m-inline", async ({ page }) => {
      await expect(page.locator("#dr-inline")).toHaveClass(/pf-m-inline/);
    });
  });

  test.describe("Panel Left", () => {
    test("has class pf-m-panel-left", async ({ page }) => {
      await expect(page.locator("#dr-panel-left")).toHaveClass(/pf-m-panel-left/);
    });
  });
});
