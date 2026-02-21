import { test, expect } from "@playwright/test";

test.describe("Rectangle Selection", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/rectangle-selection");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Rectangle Selection");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-cards-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("container has pha-c-rectangle-selection class", async ({ page }) => {
      await expect(page.locator("#rs-basic")).toHaveClass(/pha-c-rectangle-selection/);
    });

    test("has 60 selectable product cards", async ({ page }) => {
      const items = page.locator("#rs-basic [data-selectable]");
      await expect(items).toHaveCount(60);
    });

    test("selection rectangle element exists in DOM", async ({ page }) => {
      const rect = page.locator("#rs-basic .pha-c-rectangle-selection__rect");
      await expect(rect).toHaveCount(1);
    });

    test("selection rectangle is hidden by default", async ({ page }) => {
      const rect = page.locator("#rs-basic .pha-c-rectangle-selection__rect");
      await expect(rect).not.toBeVisible();
    });

    test("dragging on container highlights items", async ({ page }) => {
      const container = page.locator("#rs-basic");
      const box = await container.boundingBox();

      // Drag from top-left area across several items
      await page.mouse.move(box.x + 5, box.y + 5);
      await page.mouse.down();
      await page.mouse.move(box.x + 350, box.y + 250, { steps: 10 });

      // Selection rectangle should be visible during drag
      const rect = page.locator("#rs-basic .pha-c-rectangle-selection__rect");
      await expect(rect).toBeVisible();

      // Some items should be selected
      const selected = page.locator("#rs-basic [data-selectable].pha-m-selected");
      const count = await selected.count();
      expect(count).toBeGreaterThan(0);

      await page.mouse.up();
    });

    test("selected count updates on drag", async ({ page }) => {
      const container = page.locator("#rs-basic");
      const box = await container.boundingBox();

      await page.mouse.move(box.x + 5, box.y + 5);
      await page.mouse.down();
      await page.mouse.move(box.x + 350, box.y + 250, { steps: 10 });
      await page.mouse.up();

      // Wait for Alpine to process the event
      await page.waitForTimeout(100);
      const countText = await page.locator("#rs-count span").textContent();
      const num = parseInt(countText);
      expect(num).toBeGreaterThan(0);
    });
  });

  test.describe("With PatternFly cards", () => {
    test("container has pha-c-rectangle-selection class", async ({ page }) => {
      await expect(page.locator("#rs-cards")).toHaveClass(/pha-c-rectangle-selection/);
    });

    test("has 8 selectable PF cards", async ({ page }) => {
      const cards = page.locator("#rs-cards [data-selectable]");
      await expect(cards).toHaveCount(8);
    });

    test("cards have pf-v6-c-card class", async ({ page }) => {
      await expect(page.locator("#rs-cards .pf-v6-c-card").first()).toBeVisible();
    });
  });
});
