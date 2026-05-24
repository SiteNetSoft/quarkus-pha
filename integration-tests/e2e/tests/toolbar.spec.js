import { test, expect } from "@playwright/test";

test.describe("Toolbar", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/toolbar");
  });

  test("page loads with the basic example section in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#basic")).toHaveText("Basic");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-toolbar")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/toolbar/basic"]';

    test("basic toolbar has pf-v6-c-toolbar class", async ({ page }) => {
      await expect(page.locator(`${card} #tb-basic`)).toHaveClass(/pf-v6-c-toolbar/);
    });

    test("basic toolbar has two toolbar groups (left + right)", async ({ page }) => {
      await expect(page.locator(`${card} #tb-basic .pf-v6-c-toolbar__group`)).toHaveCount(2);
    });

    test("right group is aligned to the end", async ({ page }) => {
      await expect(
        page.locator(`${card} #tb-basic .pf-v6-c-toolbar__group.pf-m-align-end`)
      ).toHaveCount(1);
    });

    test("basic toolbar has four toolbar items across groups", async ({ page }) => {
      await expect(page.locator(`${card} #tb-basic .pf-v6-c-toolbar__item`)).toHaveCount(4);
    });

    test("basic toolbar has a primary Create button", async ({ page }) => {
      await expect(
        page.locator(`${card} #tb-basic .pf-v6-c-button.pf-m-primary`)
      ).toBeVisible();
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/toolbar/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/toolbar/basic"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/toolbar/basic");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
