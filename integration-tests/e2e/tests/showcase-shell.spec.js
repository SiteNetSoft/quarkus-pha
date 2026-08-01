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

  test.describe("mobile viewport", () => {
    test.use({ viewport: { width: 360, height: 740 } });

    test("masthead search menu stays inside the viewport", async ({ page }) => {
      await page.locator('.pha-global-search button[aria-label="Search the showcase"]').click();
      const menu = page.locator("#pha-global-search-menu");
      await expect(menu).toBeVisible();
      const box = await menu.boundingBox();
      expect(box.x).toBeGreaterThanOrEqual(0);
      expect(box.x + box.width).toBeLessThanOrEqual(360);
    });

    test("masthead theme selector menu stays inside the viewport", async ({ page }) => {
      await page.locator(".pha-theme-selector button").first().click();
      const menu = page.locator(".pha-theme-selector .pf-v6-c-menu");
      await expect(menu).toBeVisible();
      const box = await menu.boundingBox();
      expect(box.x).toBeGreaterThanOrEqual(0);
      expect(box.x + box.width).toBeLessThanOrEqual(360);
      // The widest toggle row must be reachable too, not just the menu box
      const glass = await page.locator(".pha-theme-selector").getByRole("button", { name: "Glass" }).boundingBox();
      expect(glass.x).toBeGreaterThanOrEqual(0);
      expect(glass.x + glass.width).toBeLessThanOrEqual(360);
    });
  });

  test("toc scrollspy marks the section scrolled to as current", async ({ page }) => {
    await page.setViewportSize({ width: 1600, height: 900 });
    await page.reload();
    const toc = page.locator(".ws-toc");
    // On load the first item is current
    await expect(toc.locator("li.pf-m-current")).toHaveCount(1);
    await expect(toc.locator('li.pf-m-current a[href="#examples"]')).toBeVisible();
    // Scroll the page main to the bottom: the last section becomes current
    await page.evaluate(() => {
      const main = document.querySelector("#ws-page-main");
      main.scrollTo(0, main.scrollHeight);
    });
    await expect(toc.locator("li.pf-m-current")).toHaveCount(1);
    await expect(toc.locator("li.pf-m-current").first()).not.toHaveText(/^Examples/);
    await expect(toc.locator("li.pf-m-current")).toHaveAttribute("aria-current", "location");
  });

  test("toc link click scrolls the page main and updates the hash", async ({ page }) => {
    await page.setViewportSize({ width: 1600, height: 900 });
    await page.reload();
    const link = page.locator('.ws-toc a[href="#documentation"]');
    await link.click();
    await expect(page).toHaveURL(/#documentation$/);
    const scrolled = await page.evaluate(() => document.querySelector("#ws-page-main").scrollTop);
    expect(scrolled).toBeGreaterThan(0);
    await expect(page.locator('.ws-toc li.pf-m-current a[href="#documentation"]')).toBeVisible();
  });

  test("toc sticks to the top of the scrollport", async ({ page }) => {
    await page.setViewportSize({ width: 1600, height: 900 });
    await page.reload();
    await page.evaluate(() => document.querySelector("#ws-page-main").scrollTo(0, 600));
    const box = await page.locator(".ws-toc").boundingBox();
    const mainBox = await page.locator("#ws-page-main").boundingBox();
    // Sticky: the rail hugs the scrollport top instead of scrolling away
    expect(box.y).toBeGreaterThanOrEqual(mainBox.y - 1);
    expect(box.y).toBeLessThan(mainBox.y + 40);
  });
});
