// Showcase shell behaviors, exercised on the pilot page (/components/backdrop):
// masthead search, sidebar nav, hamburger, masthead theme selector, ToC rail
// modes, back-to-top. Extends to all pages as the shell rolls out.
import { test, expect } from "@playwright/test";

test.describe("showcase shell pilot", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/backdrop");
  });

  test("masthead search opens and filters the page index", async ({ page }) => {
    await page.locator('.pha-global-search button[aria-label="Search the showcase"]').click();
    const menu = page.locator("#pha-global-search-menu");
    await expect(menu).toBeVisible();
    const search = menu.locator('input[type="search"], .pf-v6-c-menu__search input');
    await search.fill("wizard");
    await expect(menu.locator(".pf-v6-c-menu__list-item:visible")).toHaveCount(1);
    await expect(menu.locator('.pf-v6-c-menu__list-item:visible a[href="/components/wizard"]')).toBeVisible();
  });

  test("sidebar nav expands the component group and links pages", async ({ page }) => {
    const nav = page.locator("#showcase-nav");
    await expect(nav).toBeVisible();
    await nav.getByRole("button", { name: "All components" }).click();
    await expect(nav.locator('a[href="/components/backdrop"]')).toBeVisible();
  });

  test("hamburger collapses and expands the sidebar", async ({ page }) => {
    const sidebar = page.locator(".pf-v6-c-page__sidebar");
    await expect(sidebar).toBeVisible();
    await page.locator('button[aria-label="Global navigation"]').click();
    await expect(sidebar).toBeHidden();
    await page.locator('button[aria-label="Global navigation"]').click();
    await expect(sidebar).toBeVisible();
  });

  test("theme selector opens from the masthead", async ({ page }) => {
    await page.locator(".pha-theme-selector button").first().click();
    await expect(page.locator(".pha-theme-selector .pf-v6-c-menu")).toBeVisible();
    await expect(page.locator(".pha-theme-selector").getByRole("button", { name: "Dark" })).toBeVisible();
  });

  test("toc is a right rail at 1600px and an expandable widget at 1200px", async ({ page }) => {
    await page.setViewportSize({ width: 1600, height: 900 });
    await page.reload();
    const tb = await page.locator(".ws-toc").boundingBox();
    const cb = await page.locator(".ws-example-page-wrapper").boundingBox();
    expect(tb.x).toBeGreaterThan(cb.x + cb.width - 50);

    await page.setViewportSize({ width: 1200, height: 900 });
    await page.reload();
    const toggle = page.locator(".ws-toc .pf-v6-c-jump-links__toggle button");
    await expect(toggle).toBeVisible();
    await toggle.click();
    await expect(page.locator('.ws-toc a[href="#examples"]')).toBeVisible();
  });

  test("back-to-top is present and floating theme widget is gone", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-back-to-top")).toBeAttached();
    await expect(page.locator(".pha-theme-selector")).toHaveCount(1);
  });
});
