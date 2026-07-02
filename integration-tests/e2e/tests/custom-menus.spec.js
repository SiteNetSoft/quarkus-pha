import { test, expect } from "@playwright/test";

test.describe("Custom menus", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/custom-menus");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Custom menus");
  });

  test("Examples and Documentation section headings are visible", async ({ page }) => {
    await expect(page.locator("h2#examples")).toBeVisible();
    await expect(page.locator("h2#documentation")).toBeVisible();
  });

  test("Basic example-card section id is visible", async ({ page }) => {
    await expect(page.locator("h3#basic")).toBeVisible();
    await expect(page.locator("h3#basic")).toHaveText("Basic");
  });

  test("Documentation sub-section ids are visible", async ({ page }) => {
    await expect(page.locator("h3#props-custom-menus")).toBeVisible();
    await expect(page.locator("h3#usage")).toBeVisible();
  });

  test.describe("Basic example", () => {
    test("toggle is visible with default text 'Project'", async ({ page }) => {
      const toggle = page.locator("#cm-basic-toggle");
      await expect(toggle).toBeVisible();
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Project");
    });

    test("menu is hidden by default", async ({ page }) => {
      const menu = page.locator("#cm-basic-toggle").locator("..").locator(".pf-v6-c-menu");
      await expect(menu).not.toBeVisible();
    });

    test("clicking toggle opens menu with header, items, and footer", async ({ page }) => {
      await page.locator("#cm-basic-toggle").click();
      const wrapper = page.locator("#cm-basic-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu")).toBeVisible();
      await expect(wrapper.locator(".pf-v6-c-menu__header")).toBeVisible();
      await expect(wrapper.locator(".pf-v6-c-menu__header strong")).toHaveText("Recent projects");
      await expect(wrapper.locator(".pf-v6-c-menu__list-item")).toHaveCount(2);
      await expect(wrapper.locator(".pf-v6-c-menu__footer")).toBeVisible();
    });

    test("items have description text", async ({ page }) => {
      await page.locator("#cm-basic-toggle").click();
      const wrapper = page.locator("#cm-basic-toggle").locator("..");
      const descriptions = wrapper.locator(".pf-v6-c-menu__item-description");
      await expect(descriptions).toHaveCount(2);
      await expect(descriptions.nth(0)).toHaveText("Backend service");
      await expect(descriptions.nth(1)).toHaveText("Web frontend");
    });

    test("toggle gets expanded modifier when open", async ({ page }) => {
      const toggle = page.locator("#cm-basic-toggle");
      await toggle.click();
      await expect(toggle).toHaveClass(/pf-m-expanded/);
    });

    test("footer contains 'Browse all projects' link", async ({ page }) => {
      await page.locator("#cm-basic-toggle").click();
      const wrapper = page.locator("#cm-basic-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu__footer .pf-v6-c-button__text")).toHaveText("Browse all projects");
    });
  });

  test.describe("With search", () => {
    test("typing in the search slot filters the list", async ({ page }) => {
      await page.locator("#cm-search-toggle").click();
      const wrapper = page.locator("#cm-search-toggle").locator("..");
      const items = wrapper.locator(".pf-v6-c-menu__list-item");
      await expect(items.filter({ hasText: "Apollo" })).toBeVisible();
      await wrapper.locator(".pf-v6-c-menu__search input").fill("mer");
      await expect(items.filter({ hasText: "Mercury" })).toBeVisible();
      await expect(items.filter({ hasText: "Apollo" })).toBeHidden();
    });
  });

  test.describe("Flyout", () => {
    test("hovering the flyout item reveals its submenu", async ({ page }) => {
      await page.locator("#cm-flyout-toggle").click();
      const flyout = page.locator("#cm-flyout-toggle").locator("..").locator(".pf-m-flyout");
      const submenu = flyout.locator(".pf-v6-c-menu");
      await expect(submenu).toBeHidden();
      await flyout.hover();
      await expect(submenu).toBeVisible();
      await expect(submenu).toContainText("Email");
    });
  });
});
