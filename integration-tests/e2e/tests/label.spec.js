import { test, expect } from "@playwright/test";

test.describe("Label", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/label");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#filled-heading")).toBeVisible();
    await expect(page.locator("#outline-heading")).toBeVisible();
    await expect(page.locator("#with-icon-heading")).toBeVisible();
    await expect(page.locator("#removable-heading")).toBeVisible();
  });

  test("filled labels have color modifiers", async ({ page }) => {
    await expect(page.locator("#lbl-blue")).toHaveClass(/pf-m-blue/);
    await expect(page.locator("#lbl-green")).toHaveClass(/pf-m-green/);
    await expect(page.locator("#lbl-red")).toHaveClass(/pf-m-red/);
    await expect(page.locator("#lbl-purple")).toHaveClass(/pf-m-purple/);
  });

  test("outline labels have outline modifier", async ({ page }) => {
    await expect(page.locator("#lbl-outline-grey")).toHaveClass(/pf-m-outline/);
    await expect(page.locator("#lbl-outline-blue")).toHaveClass(/pf-m-outline/);
  });

  test("label with icon has icon element", async ({ page }) => {
    await expect(page.locator("#lbl-icon .pf-v6-c-label__icon")).toBeVisible();
  });

  test("removable label has close button", async ({ page }) => {
    await expect(page.locator("#lbl-removable .pf-v6-c-label__actions .pf-v6-c-button")).toBeVisible();
  });

  test("labels have text content", async ({ page }) => {
    await expect(page.locator("#lbl-blue .pf-v6-c-label__text")).toHaveText("Blue");
  });
});
