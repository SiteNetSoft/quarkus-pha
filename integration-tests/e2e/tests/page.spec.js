import { test, expect } from "@playwright/test";

test.describe("Page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/page");
  });

  test("page loads with all 1 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
  });

  test.describe("Basic", () => {
    test("page component has pf-v6-c-page class", async ({ page }) => {
      await expect(page.locator("#pg-basic")).toHaveClass(/pf-v6-c-page/);
    });

    test("page has a sidebar", async ({ page }) => {
      await expect(page.locator("#pg-basic .pf-v6-c-page__sidebar")).toBeVisible();
    });

    test("page has a main content area", async ({ page }) => {
      await expect(page.locator("#pg-basic .pf-v6-c-page__main")).toBeVisible();
    });

    test("page sidebar has content", async ({ page }) => {
      await expect(page.locator("#pg-basic .pf-v6-c-page__sidebar-body")).toContainText("Sidebar content");
    });

    test("page main area has content", async ({ page }) => {
      await expect(page.locator("#pg-basic .pf-v6-c-page__main-body")).toContainText("Main content area");
    });
  });
});
