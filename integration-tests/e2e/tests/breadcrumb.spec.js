import { test, expect } from "@playwright/test";

test.describe("Breadcrumb", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/breadcrumb");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of [
      "examples",
      "basic",
      "without-home-link",
      "with-heading",
      "with-dropdown",
      "documentation",
      "props-breadcrumb",
      "props-item",
      "usage",
    ]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic breadcrumb is a nav with aria-label + 4 items + 4 dividers + 1 current", async ({ page }) => {
    const nav = page.locator("#breadcrumb-basic");
    await expect(nav).toHaveClass(/pf-v6-c-breadcrumb/);
    await expect(nav).toHaveAttribute("aria-label", "Breadcrumb");
    await expect(nav.locator(".pf-v6-c-breadcrumb__item")).toHaveCount(4);
    await expect(nav.locator(".pf-v6-c-breadcrumb__item-divider")).toHaveCount(4);
    const current = nav.locator(".pf-v6-c-breadcrumb__link.pf-m-current");
    await expect(current).toHaveCount(1);
    await expect(current).toHaveAttribute("aria-current", "page");
  });

  test("without-home-link: first item is plain text (no anchor)", async ({ page }) => {
    const firstItem = page.locator("#breadcrumb-no-home .pf-v6-c-breadcrumb__item").first();
    await expect(firstItem.locator(".pf-v6-c-breadcrumb__link")).toHaveCount(0);
  });

  test("with-heading: current crumb is wrapped in a heading.pf-v6-c-breadcrumb__heading", async ({ page }) => {
    const heading = page.locator("#breadcrumb-heading h2.pf-v6-c-breadcrumb__heading");
    await expect(heading).toBeVisible();
    const link = heading.locator(".pf-v6-c-breadcrumb__link.pf-m-current");
    await expect(link).toHaveAttribute("aria-current", "page");
  });

  test("with-dropdown: renders dropdown chrome + menu list", async ({ page }) => {
    const dropdown = page.locator("#breadcrumb-dropdown .pf-v6-c-breadcrumb__dropdown");
    await expect(dropdown).toBeVisible();
    await expect(dropdown.locator(".pf-v6-c-menu-toggle")).toBeVisible();
    const toggle = dropdown.locator(".pf-v6-c-menu-toggle");
    await expect(toggle).toHaveAttribute("aria-expanded", "false");
    // Click toggles open.
    await toggle.click();
    await expect(toggle).toHaveAttribute("aria-expanded", "true");
    await expect(dropdown.locator(".pf-v6-c-menu")).toBeVisible();
    await expect(dropdown.locator(".pf-v6-c-menu__list .pf-v6-c-menu__item")).toHaveCount(3);
    // Click outside closes.
    await page.locator("h1#ws-page-title").click();
    await expect(toggle).toHaveAttribute("aria-expanded", "false");
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of ["basic", "without-home-link", "with-heading", "with-dropdown"]) {
      const res = await page.goto(`/components/breadcrumb/${slug}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-breadcrumb")).toBeVisible();
    }
  });
});
