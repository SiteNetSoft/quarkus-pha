import { test, expect } from "@playwright/test";

test.describe("Skeleton loading (HTMX)", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/skeleton-loading");
    await expect(page.locator("#skeleton-load-btn")).toBeVisible();
  });

  test("the skeleton placeholder is hidden until a request is in flight", async ({ page }) => {
    await expect(page.locator("#skeleton-placeholder")).toBeHidden();
    await expect(page.locator("#skeleton-result")).toContainText("No profile loaded yet");
  });

  test("clicking shows the skeleton, then swaps in the server-rendered profile", async ({ page }) => {
    await page.locator("#skeleton-load-btn").click();
    // While the (artificially slow) request runs, the skeleton becomes visible.
    await expect(page.locator("#skeleton-placeholder")).toBeVisible();
    // Once the response arrives, the profile replaces the placeholder text...
    await expect(page.locator("#skeleton-loaded")).toBeVisible();
    await expect(page.locator("#skeleton-result")).toContainText("Ada Lovelace");
    // ...and the skeleton hides again.
    await expect(page.locator("#skeleton-placeholder")).toBeHidden();
  });

  test("the loaded profile carries the description-list fields", async ({ page }) => {
    await page.locator("#skeleton-load-btn").click();
    const dl = page.locator("#skeleton-loaded .pf-v6-c-description-list");
    await expect(dl).toContainText("Location");
    await expect(dl).toContainText("Cambridge, UK");
    await expect(dl).toContainText("Status");
  });
});
