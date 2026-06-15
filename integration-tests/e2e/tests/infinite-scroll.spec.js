import { test, expect } from "@playwright/test";

// The sentinel is scoped to the demo's bounded scroll container via an
// IntersectionObserver (intersect), not window-scroll "revealed" — the latter
// never fires for a sentinel inside an overflow:auto box.
const SENTINEL_TRIGGER = "intersect once root:#scroll-list";

test.describe("Infinite scroll — demo page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/infinite-scroll");
  });

  test("renders the canonical demo shape", async ({ page }) => {
    await expect(page.locator("#ws-page-title")).toHaveText("Infinite scroll");
    await expect(page.locator("h2#examples")).toBeVisible();
    await expect(page.locator("h3#basic")).toBeVisible();
    await expect(page.locator("h3#props-infinite-scroll")).toBeVisible();
    await expect(page.locator("h3#usage")).toBeVisible();
  });

  test("embeds the basic example and auto-loads the first page", async ({ page }) => {
    await expect(page.locator("#scroll-list")).toBeVisible();
    await expect(page.locator("#scroll-sentinel-0")).toHaveAttribute("hx-trigger", SENTINEL_TRIGGER);
    // Container-scoped intersect fires even though the example sits below the fold.
    await expect(page.locator("#scroll-item-0")).toBeAttached({ timeout: 5000 });
  });
});

test.describe("Infinite scroll — basic example", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/infinite-scroll/basic");
  });

  test("sentinel is scoped to the scroll container", async ({ page }) => {
    await expect(page.locator("#scroll-sentinel-0")).toHaveAttribute("hx-trigger", SENTINEL_TRIGGER);
  });

  test("auto-loads the first page on load", async ({ page }) => {
    await expect(page.locator("#scroll-item-0")).toBeVisible({ timeout: 5000 });
    await expect(page.locator("#scroll-item-5")).toBeVisible({ timeout: 5000 });
  });

  test("scrolling the container loads every page through to the end", async ({ page }) => {
    await expect(page.locator("#scroll-item-0")).toBeVisible({ timeout: 5000 });
    // intersect-once reveals one sentinel per scroll, so keep scrolling to the bottom
    // until the final item (23) has been appended.
    await expect(async () => {
      await page.locator("#scroll-list").evaluate((el) => (el.scrollTop = el.scrollHeight));
      await expect(page.locator("#scroll-item-23")).toBeAttached({ timeout: 500 });
    }).toPass({ timeout: 15000 });
  });
});
