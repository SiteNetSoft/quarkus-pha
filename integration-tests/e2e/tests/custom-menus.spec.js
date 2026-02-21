import { test, expect } from "@playwright/test";

test.describe("Custom menus", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/custom-menus");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#actions-heading")).toBeVisible();
    await expect(page.locator("#grouped-heading")).toBeVisible();
    await expect(page.locator("#descriptions-heading")).toBeVisible();
  });

  test.describe("Actions menu", () => {
    test("has menu toggle with text", async ({ page }) => {
      await expect(
        page.locator("#cm-actions .pf-v6-c-menu-toggle__text")
      ).toHaveText("Actions");
    });

    test("menu is hidden by default", async ({ page }) => {
      await expect(
        page.locator("#cm-actions .pf-v6-c-menu")
      ).not.toBeVisible();
    });

    test("clicking toggle opens menu with 4 items and a divider", async ({
      page,
    }) => {
      await page.locator("#cm-actions .pf-v6-c-menu-toggle").click();
      await expect(page.locator("#cm-actions .pf-v6-c-menu")).toBeVisible();
      const items = page.locator("#cm-actions .pf-v6-c-menu__list-item");
      await expect(items).toHaveCount(4);
      await expect(page.locator("#cm-actions .pf-v6-c-divider")).toBeVisible();
    });

    test("last item has danger modifier", async ({ page }) => {
      await page.locator("#cm-actions .pf-v6-c-menu-toggle").click();
      const dangerItem = page.locator(
        "#cm-actions .pf-v6-c-menu__list-item.pf-m-danger"
      );
      await expect(dangerItem).toBeVisible();
      await expect(
        dangerItem.locator(".pf-v6-c-menu__item-text")
      ).toHaveText("Delete");
    });

    test("clicking an item closes the menu", async ({ page }) => {
      await page.locator("#cm-actions .pf-v6-c-menu-toggle").click();
      await page
        .locator("#cm-actions .pf-v6-c-menu__item-text", { hasText: "Edit" })
        .click();
      await expect(
        page.locator("#cm-actions .pf-v6-c-menu")
      ).not.toBeVisible();
    });
  });

  test.describe("Grouped menu", () => {
    test("has two group sections", async ({ page }) => {
      await page.locator("#cm-grouped .pf-v6-c-menu-toggle").click();
      const groups = page.locator("#cm-grouped .pf-v6-c-menu__group");
      await expect(groups).toHaveCount(2);
    });

    test("groups have titles", async ({ page }) => {
      await page.locator("#cm-grouped .pf-v6-c-menu-toggle").click();
      const titles = page.locator("#cm-grouped .pf-v6-c-menu__group-title");
      await expect(titles).toHaveCount(2);
      await expect(titles.nth(0)).toHaveText("Group 1");
      await expect(titles.nth(1)).toHaveText("Group 2");
    });

    test("each group has 2 items", async ({ page }) => {
      await page.locator("#cm-grouped .pf-v6-c-menu-toggle").click();
      const group1Items = page.locator(
        "#cm-grouped .pf-v6-c-menu__group:first-of-type .pf-v6-c-menu__list-item"
      );
      await expect(group1Items).toHaveCount(2);
    });
  });

  test.describe("Menu with descriptions", () => {
    test("toggle shows placeholder text", async ({ page }) => {
      await expect(
        page.locator("#cm-descriptions .pf-v6-c-menu-toggle__text")
      ).toHaveText("Choose an option");
    });

    test("items have description text", async ({ page }) => {
      await page.locator("#cm-descriptions .pf-v6-c-menu-toggle").click();
      const descriptions = page.locator(
        "#cm-descriptions .pf-v6-c-menu__item-description"
      );
      await expect(descriptions).toHaveCount(3);
      await expect(descriptions.first()).toContainText("production");
    });

    test("selecting an item updates toggle text", async ({ page }) => {
      await page.locator("#cm-descriptions .pf-v6-c-menu-toggle").click();
      await page
        .locator("#cm-descriptions .pf-v6-c-menu__item-text", {
          hasText: "Pause rollout",
        })
        .click();
      await expect(
        page.locator("#cm-descriptions .pf-v6-c-menu-toggle__text")
      ).toHaveText("Pause rollout");
    });
  });
});
