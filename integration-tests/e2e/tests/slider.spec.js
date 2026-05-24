import { test, expect } from "@playwright/test";

test.describe("Slider", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/slider");
  });

  test("page loads with all 3 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#custom-range")).toBeVisible();
    await expect(page.locator("#disabled")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("basic slider has pf-v6-c-slider class", async ({ page }) => {
      await expect(page.locator("#sl-basic")).toHaveClass(/pf-v6-c-slider/);
    });

    test("basic slider has a thumb element", async ({ page }) => {
      await expect(page.locator("#sl-basic .pf-v6-c-slider__thumb")).toBeVisible();
    });
  });

  test.describe("Custom range", () => {
    test("custom-range slider has pf-v6-c-slider class", async ({ page }) => {
      await expect(page.locator("#sl-temp")).toHaveClass(/pf-v6-c-slider/);
    });

    test("custom-range thumb advertises the temperature bounds", async ({ page }) => {
      const thumb = page.locator("#sl-temp .pf-v6-c-slider__thumb");
      await expect(thumb).toBeVisible();
      await expect(thumb).toHaveAttribute("aria-valuemin", "-10");
      await expect(thumb).toHaveAttribute("aria-valuemax", "40");
    });
  });

  test.describe("Disabled", () => {
    test("disabled slider has pf-m-disabled class", async ({ page }) => {
      await expect(page.locator("#sl-disabled")).toHaveClass(/pf-m-disabled/);
    });

    test("disabled slider has a thumb element", async ({ page }) => {
      await expect(page.locator("#sl-disabled .pf-v6-c-slider__thumb")).toBeVisible();
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/slider/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/slider/disabled"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/slider/disabled");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
