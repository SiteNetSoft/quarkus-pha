import { test, expect } from "@playwright/test";

test.describe("Select", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/select");
  });

  test("page loads with both section headings", async ({ page }) => {
    await expect(page.locator("#single")).toBeVisible();
    await expect(page.locator("#single")).toHaveText("Single");
    await expect(page.locator("#checkboxes")).toBeVisible();
    await expect(page.locator("#checkboxes")).toHaveText("Checkboxes");
  });

  test.describe("Single", () => {
    const toggle = "#sl-single-toggle";
    const menu = "#sl-single-toggle + .pf-v6-c-menu";

    test("toggle is visible with default label", async ({ page }) => {
      await expect(page.locator(toggle)).toBeVisible();
      await expect(page.locator(`${toggle} .pf-v6-c-menu-toggle__text`)).toHaveText("Pick one");
    });

    test("menu is hidden by default", async ({ page }) => {
      await expect(page.locator(menu)).toBeHidden();
    });

    test("clicking toggle opens the menu", async ({ page }) => {
      await page.locator(toggle).click();
      await expect(page.locator(menu)).toBeVisible();
    });

    test("menu has five options", async ({ page }) => {
      await page.locator(toggle).click();
      await expect(page.locator(`${menu} .pf-v6-c-menu__list-item`)).toHaveCount(5);
    });

    test("menu uses listbox role with option items", async ({ page }) => {
      await page.locator(toggle).click();
      await expect(page.locator(`${menu} .pf-v6-c-menu__list`)).toHaveAttribute("role", "listbox");
      const opts = page.locator(`${menu} .pf-v6-c-menu__item[role='option']`);
      await expect(opts).toHaveCount(5);
    });

    test("selecting an option closes the menu and updates the toggle label", async ({ page }) => {
      await page.locator(toggle).click();
      await page.locator(`${menu} .pf-v6-c-menu__item`).first().click();
      await expect(page.locator(menu)).toBeHidden();
      await expect(page.locator(`${toggle} .pf-v6-c-menu-toggle__text`)).toHaveText("Mr");
    });
  });

  test.describe("Checkboxes", () => {
    const toggle = "#sl-multi-toggle";
    const menu = "#sl-multi-toggle + .pf-v6-c-menu";

    test("toggle shows pre-selected count", async ({ page }) => {
      // x-data initialises picked=['Apple', 'Cherry'] => "2 selected".
      await expect(page.locator(`${toggle} .pf-v6-c-menu-toggle__text`)).toHaveText("2 selected");
    });

    test("menu is hidden by default", async ({ page }) => {
      await expect(page.locator(menu)).toBeHidden();
    });

    test("clicking toggle opens the menu", async ({ page }) => {
      await page.locator(toggle).click();
      await expect(page.locator(menu)).toBeVisible();
    });

    test("menu uses menuitemcheckbox role with checkbox inputs", async ({ page }) => {
      await page.locator(toggle).click();
      const items = page.locator(`${menu} .pf-v6-c-menu__item[role='menuitemcheckbox']`);
      await expect(items).toHaveCount(4);
      await expect(page.locator(`${menu} input[type='checkbox']`)).toHaveCount(4);
    });
  });
});
