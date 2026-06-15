import { test, expect } from "@playwright/test";

test.describe("Internationalization (Qute message bundles)", () => {
  test("defaults to English when no locale is requested", async ({ page }) => {
    await page.goto("/components/i18n");
    await expect(page.locator("h1")).toHaveText("Internationalization");
    await expect(page.locator("#i18n-greeting")).toContainText("Hello! This text is localized.");
    await expect(page.locator("#i18n-current")).toContainText("English");
    await expect(page.locator("#i18n-skip")).toHaveText("Skip to content");
    // Composed button labels come from the bundle too.
    await expect(page.locator("#i18n-save")).toContainText("Save");
    await expect(page.locator("#i18n-cancel")).toContainText("Cancel");
  });

  test("?lang=fr switches the whole page to French via the locale resolver", async ({ page }) => {
    await page.goto("/components/i18n?lang=fr");
    await expect(page.locator("h1")).toHaveText("Internationalisation");
    await expect(page.locator("#i18n-greeting")).toContainText("Bonjour");
    await expect(page.locator("#i18n-current")).toContainText("Français");
    await expect(page.locator("#i18n-skip")).toHaveText("Passer au contenu");
    await expect(page.locator("#i18n-save")).toContainText("Enregistrer");
    await expect(page.locator("#i18n-cancel")).toContainText("Annuler");
  });

  test("the language switcher links carry the lang query param", async ({ page }) => {
    await page.goto("/components/i18n");
    await expect(page.locator("#i18n-lang-en")).toHaveAttribute("href", "?lang=en");
    await expect(page.locator("#i18n-lang-fr")).toHaveAttribute("href", "?lang=fr");
  });
});
