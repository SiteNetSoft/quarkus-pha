import { test, expect } from "@playwright/test";

test.describe("Helper text", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/helper-text");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#static-heading")).toBeVisible();
    await expect(page.locator("#success-heading")).toBeVisible();
    await expect(page.locator("#warning-heading")).toBeVisible();
    await expect(page.locator("#error-heading")).toBeVisible();
  });

  test("static helper text has text content", async ({ page }) => {
    await expect(page.locator("#ht-static .pf-v6-c-helper-text__item-text")).toHaveText("Enter your username.");
  });

  test("success variant has success modifier", async ({ page }) => {
    await expect(page.locator("#ht-success .pf-v6-c-helper-text__item")).toHaveClass(/pf-m-success/);
  });

  test("warning variant has warning modifier", async ({ page }) => {
    await expect(page.locator("#ht-warning .pf-v6-c-helper-text__item")).toHaveClass(/pf-m-warning/);
  });

  test("error variant has error modifier", async ({ page }) => {
    await expect(page.locator("#ht-error .pf-v6-c-helper-text__item")).toHaveClass(/pf-m-error/);
  });

  test("dynamic variants have icon", async ({ page }) => {
    await expect(page.locator("#ht-success .pf-v6-c-helper-text__item-icon")).toBeVisible();
  });
});
