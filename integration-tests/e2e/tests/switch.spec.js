import { test, expect } from "@playwright/test";

test.describe("Switch", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/switch");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#checked-heading")).toBeVisible();
    await expect(page.locator("#reversed-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-switch class", async ({ page }) => {
      await expect(page.locator("#sw-basic")).toHaveClass(/pf-v6-c-switch/);
    });

    test("has label text", async ({ page }) => {
      await expect(
        page.locator("#sw-basic .pf-v6-c-switch__label")
      ).toBeVisible();
    });
  });

  test.describe("Checked", () => {
    test("switch input is checked", async ({ page }) => {
      await expect(page.locator("#sw-checked input")).toBeChecked();
    });
  });

  test.describe("Reversed", () => {
    test("has pf-m-reverse modifier", async ({ page }) => {
      await expect(page.locator("#sw-reversed")).toHaveClass(/pf-m-reverse/);
    });
  });
});
