import { test, expect } from "@playwright/test";

test.describe("Options menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/options-menu");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Options menu");
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
    await expect(page.locator("h3#props-options-menu")).toBeVisible();
    await expect(page.locator("h3#usage")).toBeVisible();
  });

  test.describe("Basic example", () => {
    test("toggle is visible with default 'Sort by: Newest' label", async ({
      page,
    }) => {
      const toggle = page.locator("#om-basic-toggle");
      await expect(toggle).toBeVisible();
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText(
        "Sort by: Newest"
      );
    });

    test("menu is hidden by default", async ({ page }) => {
      const menu = page
        .locator("#om-basic-toggle")
        .locator("..")
        .locator(".pf-v6-c-menu");
      await expect(menu).not.toBeVisible();
    });

    test("clicking toggle opens menu with 4 radio items", async ({ page }) => {
      await page.locator("#om-basic-toggle").click();
      const wrapper = page.locator("#om-basic-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu")).toBeVisible();
      await expect(wrapper.locator(".pf-v6-c-menu__list-item")).toHaveCount(4);
      await expect(
        wrapper.locator('[role="menuitemradio"]')
      ).toHaveCount(4);
    });

    test("toggle gets expanded modifier when open", async ({ page }) => {
      const toggle = page.locator("#om-basic-toggle");
      await toggle.click();
      await expect(toggle).toHaveClass(/pf-m-expanded/);
    });

    test("default choice 'Newest' is marked as selected", async ({ page }) => {
      await page.locator("#om-basic-toggle").click();
      const wrapper = page.locator("#om-basic-toggle").locator("..");
      const selected = wrapper.locator(".pf-v6-c-menu__list-item.pf-m-selected");
      await expect(selected).toHaveCount(1);
      await expect(selected.locator(".pf-v6-c-menu__item-text")).toHaveText(
        "Newest"
      );
      await expect(
        selected.locator(".pf-v6-c-menu__item-select-icon")
      ).toBeVisible();
    });

    test("selecting a different option updates toggle label and selection", async ({
      page,
    }) => {
      await page.locator("#om-basic-toggle").click();
      const wrapper = page.locator("#om-basic-toggle").locator("..");
      await wrapper
        .locator(".pf-v6-c-menu__item-text", { hasText: "Oldest" })
        .click();
      await expect(
        page.locator("#om-basic-toggle .pf-v6-c-menu-toggle__text")
      ).toHaveText("Sort by: Oldest");
      // Re-open to verify the selection moved.
      await page.locator("#om-basic-toggle").click();
      const selected = wrapper.locator(".pf-v6-c-menu__list-item.pf-m-selected");
      await expect(selected).toHaveCount(1);
      await expect(selected.locator(".pf-v6-c-menu__item-text")).toHaveText(
        "Oldest"
      );
    });
  });
});
