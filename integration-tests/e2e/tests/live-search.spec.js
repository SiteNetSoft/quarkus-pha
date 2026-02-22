import { test, expect } from "@playwright/test";

test.describe("Live Search", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/live-search");
  });

  test("page loads with heading", async ({ page }) => {
    await expect(page.locator("#live-search-heading")).toBeVisible();
  });

  test("search input is present", async ({ page }) => {
    await expect(page.locator("#htmx-search-field")).toBeVisible();
  });

  test("search results container is present", async ({ page }) => {
    await expect(page.locator("#search-results")).toBeVisible();
  });

  test("typing triggers server search and shows results", async ({ page }) => {
    const input = page.locator("#htmx-search-field");
    await input.pressSequentially("Alpha", { delay: 50 });
    await expect(page.locator("#search-results")).toContainText("Server Alpha", { timeout: 5000 });
  });

  test("search with no matches shows no results message", async ({ page }) => {
    const input = page.locator("#htmx-search-field");
    await input.pressSequentially("zzzznonexistent", { delay: 30 });
    await expect(page.locator("#search-results")).toContainText("No results found", { timeout: 5000 });
  });

  test("clearing search updates results", async ({ page }) => {
    const input = page.locator("#htmx-search-field");
    await input.pressSequentially("Alpha", { delay: 50 });
    await expect(page.locator("#search-results")).toContainText("Server Alpha", { timeout: 5000 });
    // Clear and trigger new search
    await input.fill("");
    await input.dispatchEvent("keyup");
    await expect(page.locator("#search-results .pf-v6-c-menu__list-item").first()).toBeVisible({ timeout: 5000 });
  });

  test("search input has correct hx-get attribute", async ({ page }) => {
    const hxGet = await page.locator("#htmx-search-field").getAttribute("hx-get");
    expect(hxGet).toBe("/api/htmx/search");
  });

  test("search input has debounce trigger", async ({ page }) => {
    const hxTrigger = await page.locator("#htmx-search-field").getAttribute("hx-trigger");
    expect(hxTrigger).toContain("delay:300ms");
  });

  test("indicator element exists", async ({ page }) => {
    await expect(page.locator("#htmx-search-indicator")).toBeAttached();
  });
});
