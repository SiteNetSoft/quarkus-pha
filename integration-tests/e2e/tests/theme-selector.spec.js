import { test, expect } from "@playwright/test";

test.describe("Theme selector", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/");
  });

  test("dropdown is closed by default", async ({ page }) => {
    const menu = page.locator(".pf-v6-c-menu");
    await expect(menu).toBeHidden();
  });

  test("click toggle opens dropdown with Color scheme and High contrast sections", async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();

    const menu = page.locator(".pf-v6-c-menu");
    await expect(menu).toBeVisible();

    // Color scheme section — menu list items with icons and descriptions
    await expect(menu.locator("#theme-selector-color-scheme-title")).toHaveText("Color scheme");
    const colorItems = menu.locator('[aria-labelledby="theme-selector-color-scheme-title"] .pf-v6-c-menu__item');
    await expect(colorItems).toHaveCount(3);
    await expect(colorItems.nth(0).locator(".pf-v6-c-menu__item-text")).toHaveText("System");
    await expect(colorItems.nth(1).locator(".pf-v6-c-menu__item-text")).toHaveText("Light");
    await expect(colorItems.nth(2).locator(".pf-v6-c-menu__item-text")).toHaveText("Dark");

    // Descriptions
    await expect(colorItems.nth(0).locator(".pf-v6-c-menu__item-description")).toHaveText("Follow system preference");
    await expect(colorItems.nth(1).locator(".pf-v6-c-menu__item-description")).toHaveText("Always use light mode");
    await expect(colorItems.nth(2).locator(".pf-v6-c-menu__item-description")).toHaveText("Always use dark mode");

    // Icons present on each item
    await expect(colorItems.nth(0).locator(".pf-v6-c-menu__item-icon svg")).toBeVisible();
    await expect(colorItems.nth(1).locator(".pf-v6-c-menu__item-icon svg")).toBeVisible();
    await expect(colorItems.nth(2).locator(".pf-v6-c-menu__item-icon svg")).toBeVisible();

    // Divider
    await expect(menu.locator("hr.pf-v6-c-divider")).toBeVisible();

    // High contrast section — toggle group
    await expect(menu.locator("#theme-selector-contrast-title")).toHaveText("High contrast");
    const contrastButtons = menu.locator('[aria-labelledby="theme-selector-contrast-title"] .pf-v6-c-toggle-group__text');
    await expect(contrastButtons).toHaveCount(3);
    await expect(contrastButtons.nth(0)).toHaveText("System");
    await expect(contrastButtons.nth(1)).toHaveText("On");
    await expect(contrastButtons.nth(2)).toHaveText("Off");
  });

  test('select "Dark" adds pf-v6-theme-dark class to html', async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Dark" }).click();

    const hasDark = await page.evaluate(() =>
      document.documentElement.classList.contains("pf-v6-theme-dark")
    );
    expect(hasDark).toBe(true);
  });

  test('select "Light" removes pf-v6-theme-dark class from html', async ({ page }) => {
    // First set dark
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Dark" }).click();

    // Reopen and switch to light
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Light" }).click();

    const hasDark = await page.evaluate(() =>
      document.documentElement.classList.contains("pf-v6-theme-dark")
    );
    expect(hasDark).toBe(false);
  });

  test('select "System" respects system preference', async ({ page }) => {
    // Emulate dark color scheme
    await page.emulateMedia({ colorScheme: "dark" });
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "System" }).click();

    const hasDark = await page.evaluate(() =>
      document.documentElement.classList.contains("pf-v6-theme-dark")
    );
    expect(hasDark).toBe(true);

    // Switch to light system preference
    await page.emulateMedia({ colorScheme: "light" });
    await page.waitForFunction(
      () => !document.documentElement.classList.contains("pf-v6-theme-dark")
    );
  });

  test("theme persists across page reload", async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Dark" }).click();

    await page.reload();

    const hasDark = await page.evaluate(() =>
      document.documentElement.classList.contains("pf-v6-theme-dark")
    );
    expect(hasDark).toBe(true);

    const stored = await page.evaluate(() =>
      localStorage.getItem("pha-theme")
    );
    expect(stored).toBe("dark");
  });

  test("selected item shows checkmark and pf-m-selected", async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();

    // Default is auto/System — System item should be selected
    const systemItem = page.locator(".pf-v6-c-menu__item", { hasText: "System" }).first();
    await expect(systemItem).toHaveClass(/pf-m-selected/);
    await expect(systemItem.locator(".pf-v6-c-menu__item-select-icon")).toBeVisible();

    // Light and Dark should not be selected
    const lightItem = page.locator(".pf-v6-c-menu__item", { hasText: "Light" }).first();
    await expect(lightItem).not.toHaveClass(/pf-m-selected/);

    // Click Dark
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Dark" }).click();
    await page.locator(".pf-v6-c-menu-toggle").click();

    const darkItem = page.locator(".pf-v6-c-menu__item", { hasText: "Dark" }).first();
    await expect(darkItem).toHaveClass(/pf-m-selected/);
    await expect(darkItem.locator(".pf-v6-c-menu__item-select-icon")).toBeVisible();
  });

  test("toggle icon changes based on selected color scheme", async ({ page }) => {
    const toggleIcon = page.locator(".pf-v6-c-menu-toggle__icon");

    // Default is auto — desktop/system icon should be visible
    await expect(toggleIcon.locator('svg[x-show="theme === \'auto\'"]')).toBeVisible();
    await expect(toggleIcon.locator('svg[x-show="theme === \'light\'"]')).toBeHidden();
    await expect(toggleIcon.locator('svg[x-show="theme === \'dark\'"]')).toBeHidden();

    // Switch to dark
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Dark" }).click();

    await expect(toggleIcon.locator('svg[x-show="theme === \'dark\'"]')).toBeVisible();
    await expect(toggleIcon.locator('svg[x-show="theme === \'auto\'"]')).toBeHidden();

    // Switch to light
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Light" }).click();

    await expect(toggleIcon.locator('svg[x-show="theme === \'light\'"]')).toBeVisible();
    await expect(toggleIcon.locator('svg[x-show="theme === \'dark\'"]')).toBeHidden();
  });

  test("high contrast toggle works", async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();

    // Enable high contrast
    await page.locator('[aria-labelledby="theme-selector-contrast-title"] .pf-v6-c-toggle-group__button', { hasText: "On" }).click();

    const hasHc = await page.evaluate(() =>
      document.documentElement.classList.contains("pf-v6-theme-high-contrast")
    );
    expect(hasHc).toBe(true);

    // Disable high contrast
    await page.locator('[aria-labelledby="theme-selector-contrast-title"] .pf-v6-c-toggle-group__button', { hasText: "Off" }).click();

    const hasHcAfter = await page.evaluate(() =>
      document.documentElement.classList.contains("pf-v6-theme-high-contrast")
    );
    expect(hasHcAfter).toBe(false);
  });

  test("click outside closes dropdown", async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();
    await expect(page.locator(".pf-v6-c-menu")).toBeVisible();

    await page.locator("body").click({ position: { x: 10, y: 300 } });
    await expect(page.locator(".pf-v6-c-menu")).toBeHidden();
  });

  test("Escape closes dropdown", async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();
    await expect(page.locator(".pf-v6-c-menu")).toBeVisible();

    await page.keyboard.press("Escape");
    await expect(page.locator(".pf-v6-c-menu")).toBeHidden();
  });

  test("aria-expanded toggles correctly", async ({ page }) => {
    const toggle = page.locator(".pf-v6-c-menu-toggle");

    await expect(toggle).toHaveAttribute("aria-expanded", "false");

    await toggle.click();
    await expect(toggle).toHaveAttribute("aria-expanded", "true");

    // Click outside to close
    await page.locator("body").click({ position: { x: 10, y: 300 } });
    await expect(toggle).toHaveAttribute("aria-expanded", "false");
  });
});
