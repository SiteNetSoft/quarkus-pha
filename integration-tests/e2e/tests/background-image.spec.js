import { test, expect } from "@playwright/test";

test.describe("Background image", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/background-image");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of ["examples", "basic", "documentation", "props-background-image", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic example renders pf-v6-c-background-image with the src as inline style", async ({ page }) => {
    const el = page.locator("#bg-image-basic");
    await expect(el).toHaveClass(/pf-v6-c-background-image/);
    await expect(el).toHaveAttribute("style", /pf-background\.svg/);
  });

  test("standalone example route renders", async ({ page }) => {
    const res = await page.goto(`/components/background-image/basic`);
    expect(res.status()).toBe(200);
    await expect(page.locator(".pf-v6-c-background-image")).toBeVisible();
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/background-image");
      for (const ex of ["basic"]) {
        const card = page.locator(`[data-rendered-href="/components/background-image/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/background-image/source-java/basic");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('BackgroundImage.of("/web/images/pf-background.svg")');
    });
  });
});
