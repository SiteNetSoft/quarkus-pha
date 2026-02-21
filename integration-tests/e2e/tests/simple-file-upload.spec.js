import { test, expect } from "@playwright/test";

test.describe("Simple File Upload", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/simple-file-upload");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-file-upload class", async ({ page }) => {
      await expect(page.locator("#sfu-basic")).toHaveClass(
        /pf-v6-c-file-upload/
      );
    });

    test("has browse and clear buttons", async ({ page }) => {
      const buttons = page.locator("#sfu-basic button");
      await expect(buttons).toHaveCount(2);
    });
  });

  test.describe("Disabled", () => {
    test("buttons have disabled attribute", async ({ page }) => {
      const buttons = page.locator("#sfu-disabled button");
      const count = await buttons.count();
      for (let i = 0; i < count; i++) {
        await expect(buttons.nth(i)).toBeDisabled();
      }
    });
  });
});
