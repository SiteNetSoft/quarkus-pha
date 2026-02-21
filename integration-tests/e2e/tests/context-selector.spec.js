import { test, expect } from "@playwright/test";

test.describe("Context selector", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/context-selector");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#preselected-heading")).toBeVisible();
    await expect(page.locator("#plain-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has menu toggle with default text", async ({ page }) => {
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

    test("menu has 5 items", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      const items = page.locator("#cs-basic .pf-v6-c-menu__list-item");
      await expect(items).toHaveCount(5);
    });

    test("selecting an item updates toggle text and closes menu", async ({
      page,
    }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      await page.locator("#cs-basic .pf-v6-c-menu__item-text", { hasText: "Production" }).click();
      await expect(
        page.locator("#cs-basic .pf-v6-c-menu-toggle__text")
      ).toHaveText("Production");
      await expect(page.locator("#cs-basic .pf-v6-c-menu")).not.toBeVisible();
    });

    test("search filters items", async ({ page }) => {
      await page.locator("#cs-basic .pf-v6-c-menu-toggle").click();
      const searchInput = page.locator("#cs-basic .pf-v6-c-menu__search input");
      await searchInput.fill("prod");
      const visibleItems = page.locator(
        "#cs-basic .pf-v6-c-menu__list-item:visible"
      );
      await expect(visibleItems).toHaveCount(1);
      await expect(visibleItems.first().locator(".pf-v6-c-menu__item-text")).toHaveText("Production");
    });
  });

  test.describe("With preselected item", () => {
    test("toggle shows preselected text", async ({ page }) => {
      await expect(
        page.locator("#cs-preselected .pf-v6-c-menu-toggle__text")
      ).toHaveText("Production");
    });

    test("has 3 menu items", async ({ page }) => {
      await page.locator("#cs-preselected .pf-v6-c-menu-toggle").click();
      const items = page.locator("#cs-preselected .pf-v6-c-menu__list-item");
      await expect(items).toHaveCount(3);
    });
  });

  test.describe("Plain toggle", () => {
    test("toggle has plain modifier", async ({ page }) => {
      await expect(
        page.locator("#cs-plain .pf-v6-c-menu-toggle")
      ).toHaveClass(/pf-m-plain/);
    });

    test("has no search input", async ({ page }) => {
      await page.locator("#cs-plain .pf-v6-c-menu-toggle").click();
      await expect(
        page.locator("#cs-plain .pf-v6-c-menu__search")
      ).toHaveCount(0);
    });

    test("selecting an item updates toggle text", async ({ page }) => {
      await page.locator("#cs-plain .pf-v6-c-menu-toggle").click();
      await page.locator("#cs-plain .pf-v6-c-menu__item-text", { hasText: "Beta" }).click();
      await expect(
        page.locator("#cs-plain .pf-v6-c-menu-toggle__text")
      ).toHaveText("Beta");
    });
  });
});
