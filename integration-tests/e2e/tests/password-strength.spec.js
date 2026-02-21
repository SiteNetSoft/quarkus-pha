import { test, expect } from "@playwright/test";

test.describe("Password Strength", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/password-strength");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Password Strength");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
  });

  test("basic wrapper exists", async ({ page }) => {
    await expect(page.locator("#ps-basic")).toBeVisible();
  });

  test("has password input", async ({ page }) => {
    const input = page.locator('#ps-basic input[type="password"]');
    await expect(input).toBeVisible();
  });

  test("has helper text container", async ({ page }) => {
    await expect(
      page.locator("#ps-basic .pf-v6-c-helper-text")
    ).toBeAttached();
  });

  test("strength indicators are hidden when empty", async ({ page }) => {
    await expect(
      page.locator("#ps-basic .pf-v6-c-helper-text__item.pf-m-error")
    ).not.toBeVisible();
    await expect(
      page.locator("#ps-basic .pf-v6-c-helper-text__item.pf-m-warning")
    ).not.toBeVisible();
    await expect(
      page.locator("#ps-basic .pf-v6-c-helper-text__item.pf-m-success")
    ).not.toBeVisible();
  });

  test("typing a short password shows weak indicator", async ({ page }) => {
    await page.locator("#ps-basic input").fill("abc");
    await expect(
      page.locator("#ps-basic .pf-v6-c-helper-text__item.pf-m-error")
    ).toBeVisible();
  });
});
