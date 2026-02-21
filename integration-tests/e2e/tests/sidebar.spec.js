import { test, expect } from "@playwright/test";

test.describe("Sidebar", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/sidebar");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#panel-right-heading")).toBeVisible();
    await expect(page.locator("#panel-right-heading")).toHaveText("Panel right");
    await expect(page.locator("#with-gutter-heading")).toBeVisible();
    await expect(page.locator("#with-gutter-heading")).toHaveText("With gutter");
  });

  test.describe("Basic", () => {
    test("basic sidebar has pf-v6-c-sidebar class", async ({ page }) => {
      await expect(page.locator("#sb-basic")).toHaveClass(/pf-v6-c-sidebar/);
    });

    test("basic sidebar has a panel", async ({ page }) => {
      await expect(page.locator("#sb-basic .pf-v6-c-sidebar__panel")).toBeVisible();
    });

    test("basic sidebar has main content", async ({ page }) => {
      await expect(page.locator("#sb-basic .pf-v6-c-sidebar__main")).toBeVisible();
    });
  });

  test.describe("Panel right", () => {
    test("panel right sidebar has pf-m-panel-right class", async ({ page }) => {
      await expect(page.locator("#sb-right")).toHaveClass(/pf-m-panel-right/);
    });
  });

  test.describe("With gutter", () => {
    test("gutter sidebar has pf-m-gutter class", async ({ page }) => {
      await expect(page.locator("#sb-gutter")).toHaveClass(/pf-m-gutter/);
    });
  });
});
