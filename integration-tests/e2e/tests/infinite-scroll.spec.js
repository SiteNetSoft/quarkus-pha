import { test, expect } from "@playwright/test";

test.describe("Infinite Scroll", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/infinite-scroll");
  });

  test("page loads with heading", async ({ page }) => {
    await expect(page.locator("#infinite-scroll-heading")).toBeVisible();
  });

  test("scroll container is present", async ({ page }) => {
    await expect(page.locator("#scroll-list")).toBeVisible();
  });

  test("initial sentinel triggers first page load", async ({ page }) => {
    // The sentinel auto-fires on reveal, loading items
    await expect(page.locator("#scroll-item-0")).toBeVisible({ timeout: 5000 });
  });

  test("first page of items loads automatically", async ({ page }) => {
    await expect(page.locator("#scroll-item-0")).toBeVisible({ timeout: 5000 });
    await expect(page.locator("#scroll-item-5")).toBeVisible({ timeout: 5000 });
  });

  test("sentinel element has correct hx-trigger", async ({ page }) => {
    const sentinel = page.locator("#scroll-sentinel-0");
    const hxTrigger = await sentinel.getAttribute("hx-trigger");
    expect(hxTrigger).toBe("revealed");
  });

  test("scrolling loads more items", async ({ page }) => {
    await expect(page.locator("#scroll-item-0")).toBeVisible({ timeout: 5000 });
    // Scroll to bottom to trigger next page
    await page.locator("#scroll-list").evaluate((el) => el.scrollTop = el.scrollHeight);
    await expect(page.locator("#scroll-item-6")).toBeVisible({ timeout: 5000 });
  });
});
