import { test, expect } from "@playwright/test";

test.describe("Label", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/label");
  });

  test("page loads with example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#filled-labels")).toHaveText("Filled labels");
    await expect(page.locator("h3#outlined-labels")).toHaveText("Outlined labels");
    await expect(page.locator("h3#compact-labels")).toHaveText("Compact labels");
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

  test("outline label with icon renders the icon span", async ({ page }) => {
    await expect(page.locator("#lbl-outline-icon .pf-v6-c-label__icon")).toBeVisible();
  });

  test("removable label has close button", async ({ page }) => {
    await expect(page.locator("#lbl-removable .pf-v6-c-label__actions .pf-v6-c-button")).toBeVisible();
  });

  test("label text from the content slot renders inside __text", async ({ page }) => {
    await expect(page.locator("#lbl-blue .pf-v6-c-label__text")).toHaveText("Blue");
  });
});
