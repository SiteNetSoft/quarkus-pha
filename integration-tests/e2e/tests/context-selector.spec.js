import { test, expect } from "@playwright/test";

test.describe("Context selector", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/context-selector");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText(
      "Context selector"
    );
  });

  test("Examples and Documentation section headings are visible", async ({
    page,
  }) => {
    await expect(page.locator("h2#examples")).toBeVisible();
    await expect(page.locator("h2#documentation")).toBeVisible();
  });

  test("Basic example-card section id is visible", async ({ page }) => {
    await expect(page.locator("h3#basic")).toBeVisible();
    await expect(page.locator("h3#basic")).toHaveText("Basic");
  });

  test("Documentation sub-section ids are visible", async ({ page }) => {
    await expect(page.locator("h3#props-context-selector")).toBeVisible();
    await expect(page.locator("h3#usage")).toBeVisible();
  });

  test.describe("Basic example", () => {
    test("has menu toggle with default text 'My project'", async ({ page }) => {
      const toggle = page.locator("#cs-basic .pf-v6-c-menu-toggle");
      await expect(toggle).toBeVisible();
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText(
        "My project"
      );
    });

    test("menu is hidden by default", async ({ page }) => {
      await expect(page.locator("#cs-basic .pf-v6-c-menu")).not.toBeVisible();
    });

    test("clicking toggle opens menu", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#cs-basic .pf-v6-c-menu")).toBeVisible();
    });

    test("toggle gets expanded modifier when open", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#cs-basic .pf-v6-c-menu-toggle")).toHaveClass(
        /pf-m-expanded/
      );
    });

    test("menu has search input", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      await expect(
        page.locator("#cs-basic .pf-v6-c-menu__search input")
      ).toBeVisible();
    });

    test("menu has 4 items", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      const items = page.locator("#cs-basic .pf-v6-c-menu__list-item");
      await expect(items).toHaveCount(4);
    });

    test("selecting an item updates toggle text and closes menu", async ({
      page,
    }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      await page
        .locator("#cs-basic .pf-v6-c-menu__item-text", { hasText: "Apollo" })
        .click();
      await expect(
        page.locator("#cs-basic .pf-v6-c-menu-toggle__text")
      ).toHaveText("Apollo");
      await expect(page.locator("#cs-basic .pf-v6-c-menu")).not.toBeVisible();
    });

    test("search filters items", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      const searchInput = page.locator(
        "#cs-basic .pf-v6-c-menu__search input"
      );
      await searchInput.fill("apollo");
      const visibleItems = page.locator(
        "#cs-basic .pf-v6-c-menu__list-item:visible"
      );
      await expect(visibleItems).toHaveCount(1);
      await expect(
        visibleItems.first().locator(".pf-v6-c-menu__item-text")
      ).toHaveText("Apollo");
    });
  });
});
