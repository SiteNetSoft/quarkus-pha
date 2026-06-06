import { test, expect } from "@playwright/test";

// PatternFly v6 theme switcher: three independent toggle-group sections
//   Theme        Default | Felt          -> pf-v6-theme-felt
//   Color scheme System  | Light | Dark  -> pf-v6-theme-dark
//   Contrast     System  | Default | High contrast | Glass
//                  High contrast -> pf-v6-theme-high-contrast
//                  Glass         -> pf-v6-theme-glass
// Selections do NOT close the menu (matches patternfly.org).

const SECTION = {
  theme: "theme-selector-variant-title",
  color: "theme-selector-color-scheme-title",
  contrast: "theme-selector-contrast-title",
};

test.describe("Theme selector", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/");
  });

  // Scope to our component — other menus/toggles may exist on the page.
  const root = (page) => page.locator(".pha-theme-selector");
  const toggle = (page) => root(page).locator(".pf-v6-c-menu-toggle");
  const menu = (page) => root(page).locator(".pf-v6-c-menu");
  const button = (page, section, label) =>
    menu(page).locator(`[aria-labelledby="${SECTION[section]}"] .pf-v6-c-toggle-group__button`, { hasText: label });
  const htmlHas = (page, cls) => page.evaluate((c) => document.documentElement.classList.contains(c), cls);

  test("dropdown is closed by default", async ({ page }) => {
    await expect(menu(page)).toBeHidden();
    await expect(toggle(page)).toHaveAttribute("aria-expanded", "false");
  });

  test("click toggle opens the three toggle-group sections", async ({ page }) => {
    await toggle(page).click();
    await expect(menu(page)).toBeVisible();

    await expect(menu(page).locator(`#${SECTION.theme}`)).toHaveText("Theme");
    await expect(menu(page).locator(`#${SECTION.color}`)).toHaveText("Color scheme");
    await expect(menu(page).locator(`#${SECTION.contrast}`)).toHaveText("Contrast mode");

    const text = (section) => menu(page).locator(`[aria-labelledby="${SECTION[section]}"] .pf-v6-c-toggle-group__text`);
    await expect(text("theme")).toHaveText(["Default", "Felt"]);
    await expect(text("color")).toHaveText(["System", "Light", "Dark"]);
    await expect(text("contrast")).toHaveText(["System", "Default", "High contrast", "Glass"]);

    // Two dividers between the three sections
    await expect(menu(page).locator("hr.pf-v6-c-divider")).toHaveCount(2);
  });

  test('"High contrast" label stays on a single line', async ({ page }) => {
    await toggle(page).click();
    const hc = button(page, "contrast", "High contrast");
    await expect(hc).toBeVisible(); // wait out the menu's open transition
    await expect(hc).toHaveCSS("white-space", "nowrap"); // the fix is applied
    const box = await hc.boundingBox();
    // One line is ~35px; a wrap roughly doubles the height.
    expect(box.height).toBeLessThan(45);
  });

  test('select "Felt" toggles pf-v6-theme-felt', async ({ page }) => {
    await toggle(page).click();
    await button(page, "theme", "Felt").click();
    expect(await htmlHas(page, "pf-v6-theme-felt")).toBe(true);
    await button(page, "theme", "Default").click();
    expect(await htmlHas(page, "pf-v6-theme-felt")).toBe(false);
  });

  test('select "Dark" adds pf-v6-theme-dark', async ({ page }) => {
    await toggle(page).click();
    await button(page, "color", "Dark").click();
    expect(await htmlHas(page, "pf-v6-theme-dark")).toBe(true);
  });

  test('select "Light" removes pf-v6-theme-dark', async ({ page }) => {
    await toggle(page).click();
    await button(page, "color", "Dark").click();
    await button(page, "color", "Light").click();
    expect(await htmlHas(page, "pf-v6-theme-dark")).toBe(false);
  });

  test('color scheme "System" respects OS preference', async ({ page }) => {
    await page.emulateMedia({ colorScheme: "dark" });
    await toggle(page).click();
    await button(page, "color", "System").click();
    expect(await htmlHas(page, "pf-v6-theme-dark")).toBe(true);

    // Live-updates when the OS preference flips.
    await page.emulateMedia({ colorScheme: "light" });
    await page.waitForFunction(() => !document.documentElement.classList.contains("pf-v6-theme-dark"));
  });

  test('contrast "High contrast" and "Glass" are mutually exclusive', async ({ page }) => {
    await toggle(page).click();

    await button(page, "contrast", "High contrast").click();
    expect(await htmlHas(page, "pf-v6-theme-high-contrast")).toBe(true);
    expect(await htmlHas(page, "pf-v6-theme-glass")).toBe(false);

    // Glass replaces high contrast (same single-select group).
    await button(page, "contrast", "Glass").click();
    expect(await htmlHas(page, "pf-v6-theme-glass")).toBe(true);
    expect(await htmlHas(page, "pf-v6-theme-high-contrast")).toBe(false);

    // Default clears both.
    await button(page, "contrast", "Default").click();
    expect(await htmlHas(page, "pf-v6-theme-glass")).toBe(false);
    expect(await htmlHas(page, "pf-v6-theme-high-contrast")).toBe(false);
  });

  test("color scheme persists across reload", async ({ page }) => {
    await toggle(page).click();
    await button(page, "color", "Dark").click();

    await page.reload();
    expect(await htmlHas(page, "pf-v6-theme-dark")).toBe(true);
    const stored = await page.evaluate(() => localStorage.getItem("pha-color-scheme"));
    expect(stored).toBe("dark");
  });

  test("selected button gets pf-m-selected and aria-pressed", async ({ page }) => {
    await toggle(page).click();

    // Defaults: System color scheme is selected, Light is not.
    await expect(button(page, "color", "System")).toHaveClass(/pf-m-selected/);
    await expect(button(page, "color", "System")).toHaveAttribute("aria-pressed", "true");
    await expect(button(page, "color", "Light")).not.toHaveClass(/pf-m-selected/);

    await button(page, "color", "Dark").click();
    await expect(button(page, "color", "Dark")).toHaveClass(/pf-m-selected/);
    await expect(button(page, "color", "Dark")).toHaveAttribute("aria-pressed", "true");
    await expect(button(page, "color", "System")).not.toHaveClass(/pf-m-selected/);
  });

  test("toggle icon follows the color scheme", async ({ page }) => {
    const icon = toggle(page).locator(".pf-v6-c-menu-toggle__icon");

    // Default System -> desktop icon visible.
    await expect(icon.locator("svg[x-show=\"colorScheme === 'system'\"]")).toBeVisible();
    await expect(icon.locator("svg[x-show=\"colorScheme === 'dark'\"]")).toBeHidden();

    await toggle(page).click();
    await button(page, "color", "Dark").click();
    await expect(icon.locator("svg[x-show=\"colorScheme === 'dark'\"]")).toBeVisible();
    await expect(icon.locator("svg[x-show=\"colorScheme === 'system'\"]")).toBeHidden();

    await button(page, "color", "Light").click();
    await expect(icon.locator("svg[x-show=\"colorScheme === 'light'\"]")).toBeVisible();
    await expect(icon.locator("svg[x-show=\"colorScheme === 'dark'\"]")).toBeHidden();
  });

  test("menu stays open across selections", async ({ page }) => {
    await toggle(page).click();
    await button(page, "color", "Dark").click();
    await expect(menu(page)).toBeVisible();
    await button(page, "contrast", "High contrast").click();
    await expect(menu(page)).toBeVisible();
  });

  test("click outside closes the dropdown", async ({ page }) => {
    await toggle(page).click();
    await expect(menu(page)).toBeVisible();
    await page.locator("body").click({ position: { x: 10, y: 300 } });
    await expect(menu(page)).toBeHidden();
  });

  test("Escape closes the dropdown", async ({ page }) => {
    await toggle(page).click();
    await expect(menu(page)).toBeVisible();
    await page.keyboard.press("Escape");
    await expect(menu(page)).toBeHidden();
  });

  test("aria-expanded reflects open state", async ({ page }) => {
    await expect(toggle(page)).toHaveAttribute("aria-expanded", "false");
    await toggle(page).click();
    await expect(toggle(page)).toHaveAttribute("aria-expanded", "true");
    await page.locator("body").click({ position: { x: 10, y: 300 } });
    await expect(toggle(page)).toHaveAttribute("aria-expanded", "false");
  });
});
