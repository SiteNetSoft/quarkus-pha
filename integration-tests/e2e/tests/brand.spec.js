import { test, expect } from "@playwright/test";

test.describe("Brand", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/brand");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#width-heading")).toBeVisible();
    await expect(page.locator("#height-heading")).toBeVisible();
    await expect(page.locator("#picture-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("renders as an img element with brand class", async ({ page }) => {
      const brand = page.locator("#brand-basic");
      await expect(brand).toBeVisible();
      await expect(brand).toHaveClass(/pf-v6-c-brand/);
    });

    test("has correct src and alt attributes", async ({ page }) => {
      const brand = page.locator("#brand-basic");
      await expect(brand).toHaveAttribute("src", /brand-logo\.svg/);
      await expect(brand).toHaveAttribute("alt", "Brand logo");
    });
  });

  test.describe("Custom width", () => {
    test("applies width via CSS custom property", async ({ page }) => {
      const brand = page.locator("#brand-width");
      await expect(brand).toHaveAttribute("style", /--pf-v6-c-brand--Width:\s*200px/);
    });
  });

  test.describe("Custom height", () => {
    test("applies height via CSS custom property", async ({ page }) => {
      const brand = page.locator("#brand-height");
      await expect(brand).toHaveAttribute("style", /--pf-v6-c-brand--Height:\s*36px/);
    });
  });

  test.describe("Picture element", () => {
    test("renders as a picture element with picture modifier", async ({ page }) => {
      const brand = page.locator("#brand-picture");
      await expect(brand).toBeVisible();
      await expect(brand).toHaveClass(/pf-v6-c-brand/);
      await expect(brand).toHaveClass(/pf-m-picture/);
    });

    test("contains source and fallback img elements", async ({ page }) => {
      const source = page.locator("#brand-picture source");
      await expect(source).toHaveCount(1);
      await expect(source).toHaveAttribute("media", "(min-width: 768px)");

      const img = page.locator("#brand-picture img");
      await expect(img).toHaveAttribute("alt", "Brand logo");
    });
  });
});
