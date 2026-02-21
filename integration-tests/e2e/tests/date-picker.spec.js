import { test, expect } from "@playwright/test";

test.describe("Date Picker", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/date-picker");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-value-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-date-picker class", async ({ page }) => {
      await expect(page.locator("#dp-basic")).toHaveClass(/pf-v6-c-date-picker/);
    });

    test("has input element inside", async ({ page }) => {
      await expect(page.locator("#dp-basic input")).toBeVisible();
    });
  });

  test.describe("With Value", () => {
    test("input has value attribute", async ({ page }) => {
      const input = page.locator("#dp-value input");
      await expect(input).toHaveAttribute("value");
    });
  });

  test.describe("Disabled", () => {
    test("button has disabled attribute", async ({ page }) => {
      const button = page.locator("#dp-disabled button");
      await expect(button).toBeDisabled();
    });
  });
});
