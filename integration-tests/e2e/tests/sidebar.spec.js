import { test, expect } from "@playwright/test";

test.describe("Sidebar", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/sidebar");
  });

  test("page loads with all 2 example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#panel-right")).toHaveText("Panel right");
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
    test("has pf-m-panel-right and pf-m-gutter modifiers", async ({ page }) => {
      await expect(page.locator("#sb-right")).toHaveClass(/pf-m-panel-right/);
      await expect(page.locator("#sb-right")).toHaveClass(/pf-m-gutter/);
    });
  });
});
