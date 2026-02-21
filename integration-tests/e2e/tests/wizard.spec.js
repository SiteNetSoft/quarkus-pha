import { test, expect } from "@playwright/test";

test.describe("Wizard", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/wizard");
  });

  test("page loads with all 1 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
  });

  test.describe("Basic", () => {
    test("basic wizard has pf-v6-c-wizard class", async ({ page }) => {
      await expect(page.locator("#wiz-basic")).toHaveClass(/pf-v6-c-wizard/);
    });

    test("wizard has a nav with steps", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav")).toBeVisible();
    });

    test("wizard has three nav items", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav-item")).toHaveCount(3);
    });

    test("wizard first nav item is current", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav-link.pf-m-current")).toHaveCount(1);
    });

    test("wizard has a footer", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__footer")).toBeVisible();
    });

    test("wizard footer has Next and Back buttons", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__footer .pf-v6-c-button.pf-m-primary")).toBeVisible();
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__footer .pf-v6-c-button.pf-m-secondary")).toBeVisible();
    });

    test("wizard footer has a Cancel button", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__footer-cancel")).toBeVisible();
    });
  });
});
