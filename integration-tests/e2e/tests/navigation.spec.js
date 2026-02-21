import { test, expect } from "@playwright/test";

test.describe("Navigation", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/navigation");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#vertical-heading")).toBeVisible();
    await expect(page.locator("#vertical-heading")).toHaveText("Vertical");
    await expect(page.locator("#horizontal-heading")).toBeVisible();
    await expect(page.locator("#horizontal-heading")).toHaveText("Horizontal");
  });

  test.describe("Vertical", () => {
    test("vertical nav has pf-v6-c-nav class", async ({ page }) => {
      await expect(page.locator("#nav-vertical")).toHaveClass(/pf-v6-c-nav/);
    });

    test("vertical nav has nav items", async ({ page }) => {
      await expect(page.locator("#nav-vertical .pf-v6-c-nav__item")).toHaveCount(3);
    });

    test("vertical nav has one current item", async ({ page }) => {
      await expect(page.locator("#nav-vertical .pf-v6-c-nav__link.pf-m-current")).toHaveCount(1);
    });
  });

  test.describe("Horizontal", () => {
    test("horizontal nav has pf-m-horizontal class", async ({ page }) => {
      await expect(page.locator("#nav-horizontal")).toHaveClass(/pf-m-horizontal/);
    });

    test("horizontal nav has nav items", async ({ page }) => {
      await expect(page.locator("#nav-horizontal .pf-v6-c-nav__item")).toHaveCount(3);
    });

    test("horizontal nav has one current item", async ({ page }) => {
      await expect(page.locator("#nav-horizontal .pf-v6-c-nav__link.pf-m-current")).toHaveCount(1);
    });
  });
});
