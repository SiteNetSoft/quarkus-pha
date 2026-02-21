import { test, expect } from "@playwright/test";

test.describe("Background Image", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/background-image");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#custom-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has correct class and background style", async ({ page }) => {
      const el = page.locator("#bg-image-basic");
      await expect(el).toHaveClass(/pf-v6-c-background-image/);
      await expect(el).toHaveAttribute("style", /pf-background\.svg/);
    });
  });

  test.describe("Custom", () => {
    test("has correct class and gradient in style", async ({ page }) => {
      const el = page.locator("#bg-image-custom");
      await expect(el).toHaveClass(/pf-v6-c-background-image/);
      await expect(el).toHaveAttribute("style", /linear-gradient/);
    });
  });
});
