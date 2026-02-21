import { test, expect } from "@playwright/test";

test.describe("Password Generator", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/password-generator");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Password Generator");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
  });

  test("basic wrapper exists", async ({ page }) => {
    await expect(page.locator("#pg-basic")).toBeVisible();
  });

  test("has input group", async ({ page }) => {
    await expect(
      page.locator("#pg-basic .pf-v6-c-input-group")
    ).toBeVisible();
  });

  test("has password input", async ({ page }) => {
    const input = page.locator('#pg-basic input[type="password"]');
    await expect(input).toBeVisible();
  });

  test("has generate button", async ({ page }) => {
    const generateBtn = page.locator(
      "#pg-basic .pf-v6-c-button.pf-m-control",
    ).last();
    await expect(generateBtn).toBeVisible();
  });

  test("clicking generate fills the password field", async ({ page }) => {
    const generateBtn = page.locator(
      "#pg-basic .pf-v6-c-button.pf-m-control",
    ).last();
    await generateBtn.click();
    const input = page.locator("#pg-basic input");
    await expect(input).not.toHaveValue("");
  });
});
