import { test, expect } from "@playwright/test";

test.describe("Skip to Content", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/skip-to-content");
  });

  test("page loads with all 1 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#basic")).toHaveText("Basic");
  });

  test.describe("Basic", () => {
    test("skip to content has pf-v6-c-skip-to-content class", async ({ page }) => {
      await expect(page.locator("#stc-basic")).toHaveClass(/pf-v6-c-skip-to-content/);
    });

    test("skip to content has an href attribute", async ({ page }) => {
      await expect(page.locator("#stc-basic")).toHaveAttribute("href", "#main-content");
    });

    test("skip to content link is in the DOM", async ({ page }) => {
      const link = page.locator("#stc-basic");
      await expect(link).toBeAttached();
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/skip-to-content");
      for (const ex of ["basic", "custom-text"]) {
        const card = page.locator(`[data-rendered-href="/components/skip-to-content/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/skip-to-content/source-java/custom-text");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('SkipToContent.of("#main-content", "Jump to main content")');
    });
  });
});
