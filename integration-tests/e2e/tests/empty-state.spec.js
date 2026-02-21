import { test, expect } from "@playwright/test";

test.describe("Empty state", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/empty-state");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-icon-heading")).toBeVisible();
    await expect(page.locator("#with-actions-heading")).toBeVisible();
  });

  test("basic has title text", async ({ page }) => {
    await expect(page.locator("#es-basic .pf-v6-c-empty-state__title-text")).toHaveText("No results found");
  });

  test("basic has body text", async ({ page }) => {
    await expect(page.locator("#es-basic .pf-v6-c-empty-state__body")).toBeVisible();
  });

  test("with icon has icon element", async ({ page }) => {
    await expect(page.locator("#es-icon .pf-v6-c-empty-state__icon")).toBeVisible();
  });

  test("with actions has primary and secondary buttons", async ({ page }) => {
    await expect(page.locator("#es-actions .pf-v6-c-button.pf-m-primary")).toHaveText("Create item");
    await expect(page.locator("#es-actions .pf-v6-c-button.pf-m-link")).toHaveText("Learn more");
  });
});
