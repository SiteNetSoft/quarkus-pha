import { test, expect } from "@playwright/test";

test.describe("Theme selector", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/");
  });

  test("dropdown is closed by default", async ({ page }) => {
    const menu = page.locator(".pf-v6-c-menu");
    await expect(menu).toBeHidden();
  });

  test("click toggle opens dropdown with 3 options", async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();

    const menu = page.locator(".pf-v6-c-menu");
    await expect(menu).toBeVisible();

    const items = menu.locator(".pf-v6-c-menu__item-text");
    await expect(items).toHaveCount(3);
    await expect(items.nth(0)).toHaveText("Light");
    await expect(items.nth(1)).toHaveText("Dark");
    await expect(items.nth(2)).toHaveText("Auto");
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

    // Then switch to light
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Light" }).click();

    const hasDark = await page.evaluate(() =>
      document.documentElement.classList.contains("pf-v6-theme-dark")
    );
    expect(hasDark).toBe(false);
  });

  test('select "Auto" respects system preference', async ({ page }) => {
    // Emulate dark color scheme
    await page.emulateMedia({ colorScheme: "dark" });
    await page.locator(".pf-v6-c-menu-toggle").click();
    await page.locator(".pf-v6-c-menu__item-text", { hasText: "Auto" }).click();

    const hasDark = await page.evaluate(() =>
      document.documentElement.classList.contains("pf-v6-theme-dark")
    );
    expect(hasDark).toBe(true);

    // Switch to light system preference
    await page.emulateMedia({ colorScheme: "light" });
    // Wait for the media query listener to fire
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

    // Verify the stored value
    const stored = await page.evaluate(() =>
      localStorage.getItem("pha-theme")
    );
    expect(stored).toBe("dark");
  });

  test("click outside closes dropdown", async ({ page }) => {
    await page.locator(".pf-v6-c-menu-toggle").click();
    await expect(page.locator(".pf-v6-c-menu")).toBeVisible();

    // Click on the page body, away from the dropdown
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

    await toggle.click();
    await expect(toggle).toHaveAttribute("aria-expanded", "false");
  });
});
