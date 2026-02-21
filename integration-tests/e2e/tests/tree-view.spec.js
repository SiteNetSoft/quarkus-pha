import { test, expect } from "@playwright/test";

test.describe("Tree View", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tree-view");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#with-guides-heading")).toBeVisible();
    await expect(page.locator("#with-guides-heading")).toHaveText("With guides");
  });

  test.describe("Basic", () => {
    test("basic tree view has pf-v6-c-tree-view class", async ({ page }) => {
      await expect(page.locator("#tv-basic")).toHaveClass(/pf-v6-c-tree-view/);
    });

    test("basic tree view has list items", async ({ page }) => {
      await expect(page.locator("#tv-basic .pf-v6-c-tree-view__list-item")).not.toHaveCount(0);
    });

    test("basic tree view has an expanded item with children", async ({ page }) => {
      await expect(page.locator("#tv-basic .pf-v6-c-tree-view__list-item.pf-m-expanded")).toHaveCount(1);
    });

    test("basic tree view expanded item has nested children", async ({ page }) => {
      await expect(
        page.locator("#tv-basic .pf-v6-c-tree-view__list-item.pf-m-expanded .pf-v6-c-tree-view__list .pf-v6-c-tree-view__list-item")
      ).toHaveCount(2);
    });
  });

  test.describe("With guides", () => {
    test("tree view with guides has pf-m-guides class", async ({ page }) => {
      await expect(page.locator("#tv-guides")).toHaveClass(/pf-m-guides/);
    });

    test("tree view with guides has list items", async ({ page }) => {
      await expect(page.locator("#tv-guides .pf-v6-c-tree-view__list-item")).not.toHaveCount(0);
    });
  });
});
