import { test, expect } from "@playwright/test";

test.describe("Options menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/options-menu");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Options menu");
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
    await expect(page.locator("h3#props-options-menu")).toBeVisible();
    await expect(page.locator("h3#usage")).toBeVisible();
  });

  test.describe("Basic example", () => {
    test("toggle is visible with static 'Options menu' label", async ({ page }) => {
      const toggle = page.locator("#om-basic-toggle");
      await expect(toggle).toBeVisible();
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Options menu");
    });

    test("menu is hidden by default", async ({ page }) => {
      const menu = page.locator("#om-basic-toggle").locator("..").locator(".pf-v6-c-menu");
      await expect(menu).not.toBeVisible();
    });

    test("menu shows 6 options, two titled groups, and dividers", async ({ page }) => {
      await page.locator("#om-basic-toggle").click();
      const wrapper = page.locator("#om-basic-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu")).toBeVisible();
      await expect(wrapper.locator(".pf-v6-c-menu__list-item")).toHaveCount(6);
      await expect(wrapper.locator(".pf-v6-c-menu__group-title")).toHaveText(["Group 1", "Group 2"]);
      await expect(wrapper.locator(".pf-v6-c-divider")).toHaveCount(2);
    });

    test("the Disabled Option is disabled", async ({ page }) => {
      await page.locator("#om-basic-toggle").click();
      const wrapper = page.locator("#om-basic-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu__item", { hasText: "Disabled Option" })).toBeDisabled();
    });

    test("toggle gets expanded modifier when open", async ({ page }) => {
      const toggle = page.locator("#om-basic-toggle");
      await toggle.click();
      await expect(toggle).toHaveClass(/pf-m-expanded/);
    });

    test("selecting checks the option and keeps the menu open", async ({ page }) => {
      await page.locator("#om-basic-toggle").click();
      const wrapper = page.locator("#om-basic-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu__list-item.pf-m-selected")).toHaveCount(0);
      const groupOption = wrapper
        .locator(".pf-v6-c-menu__group", { hasText: "Group 2" })
        .locator(".pf-v6-c-menu__item", { hasText: "Option 2" });
      await groupOption.click();
      await expect(wrapper.locator(".pf-v6-c-menu")).toBeVisible();
      const selected = wrapper.locator(".pf-v6-c-menu__list-item.pf-m-selected");
      await expect(selected).toHaveCount(1);
      await expect(selected.locator(".pf-v6-c-menu__item-select-icon")).toBeVisible();
      await expect(groupOption).toHaveAttribute("aria-selected", "true");
    });

    test("selection is single-select across groups", async ({ page }) => {
      await page.locator("#om-basic-toggle").click();
      const wrapper = page.locator("#om-basic-toggle").locator("..");
      const first = wrapper.locator(".pf-v6-c-menu__item", { hasText: "Option 1" }).first();
      await first.click();
      const group1Option = wrapper
        .locator(".pf-v6-c-menu__group", { hasText: "Group 1" })
        .locator(".pf-v6-c-menu__item", { hasText: "Option 1" });
      await group1Option.click();
      await expect(wrapper.locator(".pf-v6-c-menu__list-item.pf-m-selected")).toHaveCount(1);
      await expect(first).toHaveAttribute("aria-selected", "false");
    });
  });
});
