import { test, expect } from "@playwright/test";

test.describe("Toggle Group", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/toggle-group");
  });

  test("page loads with all section headings", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#single-select")).toBeVisible();
    await expect(page.locator("#multi-select")).toBeVisible();
    await expect(page.locator("#with-icons")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-toggle-group")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Single select", () => {
    test("has pf-v6-c-toggle-group class", async ({ page }) => {
      await expect(page.locator("#tg-single")).toHaveClass(
        /pf-v6-c-toggle-group/
      );
    });

    test("has toggle buttons", async ({ page }) => {
      const buttons = page.locator("#tg-single button");
      await expect(buttons.first()).toBeVisible();
    });

    test("one button has pf-m-selected modifier", async ({ page }) => {
      await expect(
        page.locator("#tg-single .pf-v6-c-toggle-group__button.pf-m-selected")
      ).toBeVisible();
    });
  });

  test.describe("Multi select", () => {
    test("has pf-v6-c-toggle-group class", async ({ page }) => {
      await expect(page.locator("#tg-multi")).toHaveClass(
        /pf-v6-c-toggle-group/
      );
    });

    test("has toggle buttons", async ({ page }) => {
      const buttons = page.locator("#tg-multi button");
      await expect(buttons.first()).toBeVisible();
    });
  });

  test.describe("With icons", () => {
    test("has toggle buttons with icons", async ({ page }) => {
      const buttons = page.locator("#tg-icons button");
      await expect(buttons.first()).toBeVisible();
    });
  });
});
