import { test, expect } from "@playwright/test";

test.describe("Page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/page");
  });

  test("page loads with the basic example section in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#basic")).toHaveText("Basic");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-page")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/page/basic"]';

    test("page component has pf-v6-c-page class", async ({ page }) => {
      await expect(page.locator(`${card} #pg-basic`)).toHaveClass(/pf-v6-c-page/);
    });

    test("page has a masthead", async ({ page }) => {
      await expect(page.locator(`${card} #pg-basic .pf-v6-c-masthead`)).toBeVisible();
    });

    test("page has a main content area", async ({ page }) => {
      await expect(page.locator(`${card} #pg-basic .pf-v6-c-page__main`)).toBeVisible();
    });

    test("page main area has content", async ({ page }) => {
      await expect(
        page.locator(`${card} #pg-basic .pf-v6-c-page__main-body`)
      ).toContainText("Page content");
    });

    test("page masthead shows brand", async ({ page }) => {
      await expect(
        page.locator(`${card} #pg-basic .pf-v6-c-masthead__brand`)
      ).toContainText("App name");
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/page/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/page/basic"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/page/basic");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
