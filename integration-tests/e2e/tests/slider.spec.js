import { test, expect } from "@playwright/test";

test.describe("Slider", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/slider");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#with-value-heading")).toBeVisible();
    await expect(page.locator("#with-value-heading")).toHaveText("With value");
    await expect(page.locator("#disabled-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toHaveText("Disabled");
  });

  test.describe("Basic", () => {
    test("basic slider has pf-v6-c-slider class", async ({ page }) => {
      await expect(page.locator("#sl-basic")).toHaveClass(/pf-v6-c-slider/);
    });

    test("basic slider has a thumb element", async ({ page }) => {
      await expect(page.locator("#sl-basic .pf-v6-c-slider__thumb")).toBeVisible();
    });
  });

  test.describe("With value", () => {
    test("slider with value has pf-v6-c-slider class", async ({ page }) => {
      await expect(page.locator("#sl-value")).toHaveClass(/pf-v6-c-slider/);
    });

    test("slider with value has a thumb element", async ({ page }) => {
      await expect(page.locator("#sl-value .pf-v6-c-slider__thumb")).toBeVisible();
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
});
