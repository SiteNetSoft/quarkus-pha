import { test, expect } from "@playwright/test";

test.describe("Application Launcher", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/application-launcher");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Application launcher");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-application-launcher")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic example section heading is visible", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/application-launcher/basic"]';

    test("toggle button is rendered", async ({ page }) => {
      await expect(page.locator(`${card} #al-basic-toggle`)).toBeVisible();
    });

    test("menu toggle has pf-m-plain class", async ({ page }) => {
      await expect(page.locator(`${card} #al-basic-toggle`)).toHaveClass(/pf-m-plain/);
    });

    test("menu is not visible by default", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu`)).not.toBeVisible();
    });

    test("clicking toggle opens menu", async ({ page }) => {
      await page.locator(`${card} #al-basic-toggle`).click();
      await expect(page.locator(`${card} .pf-v6-c-menu`)).toBeVisible();
    });

    test("menu has 6 application items", async ({ page }) => {
      await page.locator(`${card} #al-basic-toggle`).click();
      await expect(page.locator(`${card} .pf-v6-c-menu__item`)).toHaveCount(6);
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/application-launcher/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/application-launcher/basic"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/application-launcher/basic");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
