import { test, expect } from "@playwright/test";

test.describe("Toggle Group", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/toggle-group");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-icons-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-toggle-group class", async ({ page }) => {
      await expect(page.locator("#tg-basic")).toHaveClass(
        /pf-v6-c-toggle-group/
      );
    });

    test("has toggle buttons", async ({ page }) => {
      const buttons = page.locator("#tg-basic button");
      await expect(buttons.first()).toBeVisible();
    });

    test("one button has pf-m-selected modifier", async ({ page }) => {
      await expect(
        page.locator("#tg-basic .pf-v6-c-toggle-group__button.pf-m-selected")
      ).toBeVisible();
    });
  });

  test.describe("With icons", () => {
    test("has toggle buttons with icons", async ({ page }) => {
      const buttons = page.locator("#tg-icons button");
      await expect(buttons.first()).toBeVisible();
    });
  });
});
