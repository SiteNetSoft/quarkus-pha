import { test, expect } from "@playwright/test";

test.describe("Text Input", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/text-input");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#disabled")).toBeVisible();
    await expect(page.locator("#readonly")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-form-control class", async ({ page }) => {
      await expect(page.locator("#ti-basic")).toHaveClass(
        /pf-v6-c-form-control/
      );
    });
  });

  test.describe("Disabled", () => {
    test("input has disabled attribute", async ({ page }) => {
      await expect(page.locator("#ti-disabled input")).toBeDisabled();
    });
  });

  test.describe("Readonly", () => {
    test("input has readonly attribute", async ({ page }) => {
      const input = page.locator("#ti-readonly input");
      await expect(input).toHaveAttribute("readonly", "");
    });
  });
});
