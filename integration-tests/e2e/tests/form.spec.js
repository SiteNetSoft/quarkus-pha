import { test, expect } from "@playwright/test";

test.describe("Form", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/form");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#horizontal-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-form class", async ({ page }) => {
      await expect(page.locator("#form-basic")).toHaveClass(/pf-v6-c-form/);
    });

    test("has form groups", async ({ page }) => {
      const groups = page.locator("#form-basic .pf-v6-c-form__group");
      await expect(groups.first()).toBeVisible();
    });
  });

  test.describe("Horizontal", () => {
    test("has pf-m-horizontal modifier", async ({ page }) => {
      await expect(page.locator("#form-horizontal")).toHaveClass(
        /pf-m-horizontal/
      );
    });
  });
});
