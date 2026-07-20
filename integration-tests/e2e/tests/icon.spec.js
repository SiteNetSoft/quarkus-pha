import { test, expect } from "@playwright/test";

test.describe("Icon", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/icon");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#sizes")).toBeVisible();
    await expect(page.locator("#inline")).toBeVisible();
    await expect(page.locator("#status-colors")).toBeVisible();
  });

  test("has icons at different sizes", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-icon.pf-m-sm").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon.pf-m-md").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon.pf-m-lg").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon.pf-m-xl").first()).toBeVisible();
  });

  test("has inline icon", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-icon.pf-m-inline").first()).toBeVisible();
  });

  test("has status color icons", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-icon__content.pf-m-info")).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon__content.pf-m-success")).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon__content.pf-m-warning")).toBeVisible();
    await expect(page.locator(".pf-v6-c-icon__content.pf-m-danger")).toBeVisible();
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/icon");
      for (const ex of ["basic", "sizing-within-container", "in-progress"]) {
        const card = page.locator(`[data-rendered-href="/components/icon/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/icon/source-java/sizing-within-container");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.size("3xl").iconSize("sm")');
    });

    test("model in-progress icon keeps the size-matched spinner", async ({ page }) => {
      await page.goto("/components/icon/in-progress");
      const spinner = page.locator(".pf-v6-c-icon__progress .pf-v6-c-spinner");
      await expect(spinner).toHaveClass(/pf-m-xl/);
    });
  });
});
