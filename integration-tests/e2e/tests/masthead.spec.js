import { test, expect } from "@playwright/test";

test.describe("Masthead", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/masthead");
  });

  test("page loads with all 2 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#basic")).toHaveText("Basic");
    await expect(page.locator("#display-stack")).toBeVisible();
    await expect(page.locator("#display-stack")).toHaveText("Display stack");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-masthead")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/masthead/basic"]';

    test("masthead has pf-v6-c-masthead class", async ({ page }) => {
      await expect(page.locator(`${card} #mh-basic`)).toHaveClass(/pf-v6-c-masthead/);
    });

    test("masthead has a brand area", async ({ page }) => {
      await expect(page.locator(`${card} #mh-basic .pf-v6-c-masthead__brand`)).toBeVisible();
    });

    test("masthead has a content area with a toolbar", async ({ page }) => {
      await expect(page.locator(`${card} #mh-basic .pf-v6-c-masthead__content`)).toBeVisible();
      await expect(page.locator(`${card} #mh-basic .pf-v6-c-toolbar`)).toBeVisible();
    });

    test("masthead has settings and help icon buttons", async ({ page }) => {
      await expect(
        page.locator(`${card} #mh-basic button[aria-label="Settings"]`)
      ).toBeVisible();
      await expect(page.locator(`${card} #mh-basic button[aria-label="Help"]`)).toBeVisible();
    });
  });

  test.describe("Display stack variant", () => {
    const card = '[data-rendered-href="/components/masthead/display-stack"]';

    test("display-stack masthead has pf-m-display-stack class", async ({ page }) => {
      await expect(page.locator(`${card} #mh-stack`)).toHaveClass(/pf-m-display-stack/);
    });

    test("display-stack masthead has brand area", async ({ page }) => {
      await expect(page.locator(`${card} #mh-stack .pf-v6-c-masthead__brand`)).toBeVisible();
    });

    test("display-stack masthead has content row", async ({ page }) => {
      await expect(page.locator(`${card} #mh-stack .pf-v6-c-masthead__content`)).toBeVisible();
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/masthead/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/masthead/display-stack"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/masthead/display-stack");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
