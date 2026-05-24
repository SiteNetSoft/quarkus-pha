import { test, expect } from "@playwright/test";

test.describe("Password strength", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/password-strength");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Password strength");
  });

  test("Basic section heading is visible", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test("input is rendered", async ({ page }) => {
    const input = page.locator("#ps-basic-input");
    await expect(input).toBeVisible();
    await expect(input).toHaveAttribute("type", "password");
  });

  test("helper text container is attached", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-helper-text")).toBeAttached();
  });

  test("strength label starts as 'Too short' when empty", async ({ page }) => {
    const label = page.locator(".pf-v6-c-helper-text__item-text");
    await expect(label).toHaveText("Too short");
  });

  test("typing a short password keeps the label weak", async ({ page }) => {
    await page.locator("#ps-basic-input").fill("abc");
    const label = page.locator(".pf-v6-c-helper-text__item-text");
    // 3 chars, no caps/digits/symbols => score 0 => "Too short".
    await expect(label).toHaveText("Too short");
  });

  test("typing a strong password updates the label to Strong", async ({ page }) => {
    await page.locator("#ps-basic-input").fill("Abcdefgh1!");
    const label = page.locator(".pf-v6-c-helper-text__item-text");
    await expect(label).toHaveText("Strong");
  });
});
