import { test, expect } from "@playwright/test";

test.describe("Context selector", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/context-selector");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Context selector");
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
    await expect(page.locator("h3#props-context-selector")).toBeVisible();
    await expect(page.locator("h3#usage")).toBeVisible();
  });

  test.describe("Basic example", () => {
    test("has menu toggle with default text 'Action'", async ({ page }) => {
      const toggle = page.locator("#cs-basic .pf-v6-c-menu-toggle");
      await expect(toggle).toBeVisible();
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Action");
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
      await expect(page.locator("#cs-basic .pf-v6-c-menu-toggle")).toHaveClass(/pf-m-expanded/);
    });

    test("menu has a search input with a search button", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#cs-basic .pf-v6-c-menu__search input")).toBeVisible();
      await expect(page.locator('#cs-basic button[aria-label="Search menu items"]')).toBeVisible();
    });

    test("menu has 14 items including disabled action and link", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      const items = page.locator("#cs-basic .pf-v6-c-menu__list-item");
      await expect(items).toHaveCount(14);
      await expect(
        page.locator("#cs-basic .pf-v6-c-menu__item", {
          hasText: "Disabled action",
        }),
      ).toBeDisabled();
      const disabledLink = page.locator("#cs-basic .pf-v6-c-menu__item", {
        hasText: "Disabled link",
      });
      await expect(disabledLink).toHaveAttribute("aria-disabled", "true");
    });

    test("menu has a footer with an Action button", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#cs-basic .pf-v6-c-menu__footer .pf-v6-c-button__text")).toHaveText("Action");
    });

    test("selecting an item updates toggle text and closes menu", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      await page
        .locator("#cs-basic .pf-v6-c-menu__item-text", {
          hasText: "OpenShift cluster",
        })
        .first()
        .click();
      await expect(page.locator("#cs-basic .pf-v6-c-menu-toggle__text")).toHaveText("OpenShift cluster");
      await expect(page.locator("#cs-basic .pf-v6-c-menu")).not.toBeVisible();
    });

    test("search filters items", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      const searchInput = page.locator("#cs-basic .pf-v6-c-menu__search input");
      await searchInput.fill("ansible");
      const visibleItems = page.locator("#cs-basic .pf-v6-c-menu__list-item:visible");
      await expect(visibleItems).toHaveCount(2);
      await expect(visibleItems.first().locator(".pf-v6-c-menu__item-text")).toHaveText("Production Ansible");
    });
  });
});
