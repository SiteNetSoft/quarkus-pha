import { test, expect } from "@playwright/test";

test.describe("Overflow Menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/overflow-menu");
  });

  test("page loads with the Basic example section in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-overflow-menu class", async ({ page }) => {
      await expect(page.locator("#om-basic")).toHaveClass(/pf-v6-c-overflow-menu/);
    });

    test("has content and control sections", async ({ page }) => {
      await expect(page.locator("#om-basic .pf-v6-c-overflow-menu__content")).toBeVisible();
      await expect(page.locator("#om-basic .pf-v6-c-overflow-menu__control")).toBeVisible();
    });

    test("group has pf-m-button-group class", async ({ page }) => {
      await expect(page.locator("#om-basic .pf-v6-c-overflow-menu__group.pf-m-button-group")).toBeVisible();
    });
  });
});
