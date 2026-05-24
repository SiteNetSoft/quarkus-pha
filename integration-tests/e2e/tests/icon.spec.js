import { test, expect } from "@playwright/test";

test.describe("Icon", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/icon");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#sizes")).toBeVisible();
    await expect(page.locator("#inline")).toBeVisible();
    await expect(page.locator("#status-colors")).toBeVisible();
  });

  test("has icons at different sizes", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-icon.pf-m-sm").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon.pf-m-md").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon.pf-m-lg").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon.pf-m-xl").first()).toBeVisible();
  });

  test("has inline icon", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-icon.pf-m-inline").first()).toBeVisible();
  });

  test("has status color icons", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-icon__content.pf-m-info")).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon__content.pf-m-success")).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon__content.pf-m-warning")).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon__content.pf-m-danger")).toBeVisible();
  });
});
