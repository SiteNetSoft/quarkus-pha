import { test, expect } from "@playwright/test";

test.describe("Dual List Selector", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/dual-list-selector");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Dual list selector");
  });

  test("example section heading is visible", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-dual-list-selector")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic wrapper exists with correct class", async ({ page }) => {
    await expect(page.locator("#dls-basic")).toHaveClass(
      /pf-v6-c-dual-list-selector/
    );
  });

  test("has 2 panes", async ({ page }) => {
    await expect(
      page.locator("#dls-basic .pf-v6-c-dual-list-selector__pane")
    ).toHaveCount(2);
  });

  test("available pane has 5 items", async ({ page }) => {
    const availablePane = page
      .locator("#dls-basic .pf-v6-c-dual-list-selector__pane")
      .first();
    await expect(
      availablePane.locator(".pf-v6-c-dual-list-selector__list-item")
    ).toHaveCount(5);
  });

  test("chosen pane starts empty", async ({ page }) => {
    const chosenPane = page
      .locator("#dls-basic .pf-v6-c-dual-list-selector__pane")
      .last();
    await expect(
      chosenPane.locator(".pf-v6-c-dual-list-selector__list-item")
    ).toHaveCount(0);
  });

  test("control buttons exist", async ({ page }) => {
    const controls = page.locator(
      "#dls-basic .pf-v6-c-dual-list-selector__controls button"
    );
    await expect(controls).toHaveCount(2);
  });
});
