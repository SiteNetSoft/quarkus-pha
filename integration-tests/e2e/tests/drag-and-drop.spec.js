import { test, expect } from "@playwright/test";

test.describe("Drag and Drop", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/drag-and-drop");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Drag and drop");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-drag-and-drop")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic wrapper exists", async ({ page }) => {
    await expect(page.locator("#dd-basic")).toBeVisible();
  });

  test("has 4 draggable items", async ({ page }) => {
    await expect(
      page.locator("#dd-basic .pf-v6-c-data-list__item")
    ).toHaveCount(4);
  });

  test("draggable items have draggable attribute", async ({ page }) => {
    const items = page.locator("#dd-basic .pf-v6-c-data-list__item");
    const count = await items.count();
    for (let i = 0; i < count; i++) {
      await expect(items.nth(i)).toHaveAttribute("draggable", "true");
    }
  });
});
