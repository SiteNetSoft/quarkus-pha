import { test, expect } from "@playwright/test";

test.describe("Radio", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/radio");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-description-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-radio class", async ({ page }) => {
      await expect(page.locator("#radio-1")).toHaveClass(/pf-v6-c-radio/);
    });

    test("first radio is checked", async ({ page }) => {
      await expect(page.locator("#radio-1 input")).toBeChecked();
    });
  });

  test.describe("With description", () => {
    test("has description element", async ({ page }) => {
      await expect(
        page.locator("#radio-desc .pf-v6-c-radio__description")
      ).toBeVisible();
    });
  });

  test.describe("Disabled", () => {
    test("radio has disabled attribute", async ({ page }) => {
      await expect(page.locator("#radio-disabled input")).toBeDisabled();
    });
  });
});
