import { test, expect } from "@playwright/test";

test.describe("Drag and Drop", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/drag-and-drop");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Drag and Drop");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
  });

  test("basic wrapper exists", async ({ page }) => {
    await expect(page.locator("#dnd-basic")).toBeVisible();
  });

  test("has 3 draggable items", async ({ page }) => {
    await expect(
      page.locator("#dnd-basic .pf-v6-c-draggable")
    ).toHaveCount(3);
  });

  test("draggable items contain grip icon", async ({ page }) => {
    const icons = page.locator("#dnd-basic .fas.fa-grip-vertical");
    await expect(icons).toHaveCount(3);
  });

  test("draggable items have draggable attribute", async ({ page }) => {
    const items = page.locator("#dnd-basic .pf-v6-c-draggable");
    const count = await items.count();
    for (let i = 0; i < count; i++) {
      await expect(items.nth(i)).toHaveAttribute("draggable", "true");
    }
  });
});
