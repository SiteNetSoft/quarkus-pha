import { test, expect } from "@playwright/test";

test.describe("Password generator", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/password-generator");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Password generator");
  });

  test("Basic section heading is visible", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test("input is present and pre-populated on init", async ({ page }) => {
    const input = page.locator("#pg-basic-input");
    await expect(input).toBeVisible();
    // x-init="generate()" fills the field immediately.
    await expect(input).not.toHaveValue("");
  });

  test("input is rendered inside a text-input-group", async ({ page }) => {
    const group = page
      .locator("#pg-basic-input")
      .locator("xpath=ancestor::div[contains(@class,'pf-v6-c-text-input-group')]");
    await expect(group).toBeVisible();
  });

  test("regenerate produces a different value", async ({ page }) => {
    const input = page.locator("#pg-basic-input");
    const initial = await input.inputValue();
    await page.locator("button[aria-label='Regenerate']").click();
    const next = await input.inputValue();
    expect(next).not.toBe("");
    // 16 chars from a 70-char alphabet — collision is astronomically unlikely.
    expect(next).not.toBe(initial);
  });
});
