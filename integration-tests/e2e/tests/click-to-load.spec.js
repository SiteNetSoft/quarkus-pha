import { test, expect } from "@playwright/test";

test.describe("Click to Load", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/click-to-load");
  });

  test("page loads with heading", async ({ page }) => {
    await expect(page.locator("#click-to-load-heading")).toBeVisible();
  });

  test("load more button is present", async ({ page }) => {
    await expect(page.locator("#load-more-btn-0 button")).toBeVisible();
  });

  test("load more button has correct text", async ({ page }) => {
    await expect(page.locator("#load-more-btn-0 button")).toContainText("Load more");
  });

  test("clicking load more fetches first batch of items", async ({ page }) => {
    await page.locator("#load-more-btn-0 button").click();
    await expect(page.locator("#load-item-0")).toBeVisible({ timeout: 5000 });
    await expect(page.locator("#load-item-5")).toBeVisible({ timeout: 5000 });
  });

  test("clicking load more replaces button with new items and new button", async ({ page }) => {
    await page.locator("#load-more-btn-0 button").click();
    await expect(page.locator("#load-item-0")).toBeVisible({ timeout: 5000 });
    // Original button should be gone, new one should appear
    await expect(page.locator("#load-more-btn-0")).not.toBeAttached({ timeout: 5000 });
    await expect(page.locator("#load-more-btn-1 button")).toBeVisible({ timeout: 5000 });
  });

  test("loading two batches shows 12 items", async ({ page }) => {
    await page.locator("#load-more-btn-0 button").click();
    await expect(page.locator("#load-more-btn-1 button")).toBeVisible({ timeout: 5000 });
    await page.locator("#load-more-btn-1 button").click();
    await expect(page.locator("#load-item-6")).toBeVisible({ timeout: 5000 });
    await expect(page.locator("#load-item-11")).toBeVisible({ timeout: 5000 });
  });

  test("button has correct hx-get attribute", async ({ page }) => {
    const hxGet = await page.locator("#load-more-btn-0 button").getAttribute("hx-get");
    expect(hxGet).toBe("/api/htmx/load-more?page=0");
  });
});
