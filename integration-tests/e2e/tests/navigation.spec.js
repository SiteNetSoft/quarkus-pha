import { test, expect } from "@playwright/test";

test.describe("Navigation", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/navigation");
  });

  test("page loads with all 2 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#vertical")).toBeVisible();
    await expect(page.locator("#vertical")).toHaveText("Vertical");
    await expect(page.locator("#grouped")).toBeVisible();
    await expect(page.locator("#grouped")).toHaveText("Grouped");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-navigation")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Vertical variant", () => {
    const card = '[data-rendered-href="/components/navigation/vertical"]';

    test("vertical nav has pf-v6-c-nav class", async ({ page }) => {
      await expect(page.locator(`${card} #nav-vertical`)).toHaveClass(/pf-v6-c-nav/);
    });

    test("vertical nav has 5 nav items", async ({ page }) => {
      await expect(page.locator(`${card} #nav-vertical .pf-v6-c-nav__item`)).toHaveCount(5);
    });

    test("vertical nav has one current item with aria-current=page", async ({ page }) => {
      const current = page.locator(`${card} #nav-vertical .pf-v6-c-nav__link.pf-m-current`);
      await expect(current).toHaveCount(1);
      await expect(current).toHaveAttribute("aria-current", "page");
    });
  });

  test.describe("Grouped variant", () => {
    const card = '[data-rendered-href="/components/navigation/grouped"]';

    test("grouped nav has pf-v6-c-nav class", async ({ page }) => {
      await expect(page.locator(`${card} #nav-grouped`)).toHaveClass(/pf-v6-c-nav/);
    });

    test("grouped nav has 2 sections with titles", async ({ page }) => {
      await expect(
        page.locator(`${card} #nav-grouped .pf-v6-c-nav__section`)
      ).toHaveCount(2);
      await expect(
        page.locator(`${card} #nav-grouped .pf-v6-c-nav__section-title`)
      ).toHaveCount(2);
    });

    test("grouped nav has one current item", async ({ page }) => {
      await expect(
        page.locator(`${card} #nav-grouped .pf-v6-c-nav__link.pf-m-current`)
      ).toHaveCount(1);
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/navigation/vertical"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/navigation/grouped"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/navigation/grouped");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
