import { test, expect } from "@playwright/test";

test.describe("Menu Toggle", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/menu-toggle");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#primary-heading")).toBeVisible();
    await expect(page.locator("#plain-heading")).toBeVisible();
    await expect(page.locator("#expanded-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-menu-toggle class", async ({ page }) => {
      await expect(page.locator("#mt-basic")).toHaveClass(/pf-v6-c-menu-toggle/);
    });

    test("has toggle text", async ({ page }) => {
      await expect(page.locator("#mt-basic .pf-v6-c-menu-toggle__text")).toBeVisible();
    });
  });

  test.describe("Primary", () => {
    test("has class pf-m-primary", async ({ page }) => {
      await expect(page.locator("#mt-primary")).toHaveClass(/pf-m-primary/);
    });
  });

  test.describe("Plain", () => {
    test("has class pf-m-plain", async ({ page }) => {
      await expect(page.locator("#mt-plain")).toHaveClass(/pf-m-plain/);
    });
  });

  test.describe("Expanded", () => {
    test("has class pf-m-expanded", async ({ page }) => {
      await expect(page.locator("#mt-expanded")).toHaveClass(/pf-m-expanded/);
    });
  });
});
