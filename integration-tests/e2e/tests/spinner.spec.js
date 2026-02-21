import { test, expect } from "@playwright/test";

test.describe("Spinner", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/spinner");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#sizes-heading")).toBeVisible();
    await expect(page.locator("#inline-heading")).toBeVisible();
  });

  test("has spinners at different sizes", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-spinner.pf-m-sm").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-spinner.pf-m-md").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-spinner.pf-m-lg").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-spinner.pf-m-xl").first()).toBeVisible();
  });

  test("spinners are SVG elements with progressbar role", async ({ page }) => {
    const spinner = page.locator(".pf-v6-c-spinner").first();
    await expect(spinner).toHaveAttribute("role", "progressbar");
  });

  test("has inline spinner", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-spinner.pf-m-inline")).toBeVisible();
  });
});
