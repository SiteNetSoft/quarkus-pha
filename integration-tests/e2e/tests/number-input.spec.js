import { test, expect } from "@playwright/test";

test.describe("Number Input", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/number-input");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#with-unit")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-number-input class", async ({ page }) => {
      await expect(page.locator("#ni-basic")).toHaveClass(
        /pf-v6-c-number-input/
      );
    });

    test("has minus and plus buttons", async ({ page }) => {
      const buttons = page.locator("#ni-basic button");
      await expect(buttons).toHaveCount(2);
    });
  });

  test.describe("With unit", () => {
    test("has unit element", async ({ page }) => {
      await expect(
        page.locator("#ni-unit .pf-v6-c-number-input__unit")
      ).toBeVisible();
    });
  });
});
