import { test, expect } from "@playwright/test";

test.describe("Wizard", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/wizard");
  });

  test("page loads with all 3 example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#with-substeps")).toHaveText("With substeps");
    await expect(page.locator("h3#with-review")).toHaveText("With review step");
  });

  test.describe("Basic", () => {
    test("basic wizard has pf-v6-c-wizard class", async ({ page }) => {
      await expect(page.locator("#wiz-basic")).toHaveClass(/pf-v6-c-wizard/);
    });

    test("wizard has a nav with steps", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav")).toBeVisible();
    });

    test("wizard has four nav items (General info, Connection details, Permissions, Review)", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav-item")).toHaveCount(4);
    });

    test("wizard first nav item is current", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav-link.pf-m-current")).toHaveCount(1);
    });

    test("wizard footer has Next and Back buttons", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-button.pf-m-primary")).toBeVisible();
      await expect(page.locator("#wiz-basic .pf-v6-c-button.pf-m-secondary")).toBeVisible();
    });

    test("wizard footer has a Cancel button", async ({ page }) => {
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__footer-cancel")).toBeVisible();
    });
  });

  test.describe("With substeps", () => {
    test("substeps wizard renders", async ({ page }) => {
      await expect(page.locator("#wiz-substeps")).toHaveClass(/pf-v6-c-wizard/);
    });
  });

  test.describe("With review", () => {
    test("review wizard renders", async ({ page }) => {
      await expect(page.locator("#wiz-review")).toHaveClass(/pf-v6-c-wizard/);
    });
  });
});
