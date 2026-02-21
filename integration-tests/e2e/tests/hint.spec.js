import { test, expect } from "@playwright/test";

test.describe("Hint", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/hint");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-title-heading")).toBeVisible();
    await expect(page.locator("#with-actions-heading")).toBeVisible();
  });

  test("basic hint has body text", async ({ page }) => {
    await expect(page.locator("#hint-basic .pf-v6-c-hint__body")).toBeVisible();
  });

  test("hint with title has title element", async ({ page }) => {
    await expect(page.locator("#hint-title .pf-v6-c-hint__title")).toHaveText("Helpful tip");
  });

  test("hint with actions has footer", async ({ page }) => {
    await expect(page.locator("#hint-actions .pf-v6-c-hint__footer")).toBeVisible();
  });
});
