import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "glass"];

test.describe("Hero", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/hero");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic renders a hero with a body", async ({ page }) => {
    await expect(page.locator("#hero-basic")).toHaveClass(/pf-v6-c-hero/);
    await expect(page.locator("#hero-basic .pf-v6-c-hero__body")).toBeVisible();
  });

  test("glass variant carries pf-m-glass", async ({ page }) => {
    await expect(page.locator("#hero-glass")).toHaveClass(/pf-m-glass/);
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/hero/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/hero/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-hero").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/hero");
      for (const ex of ["basic", "glass"]) {
        const card = page.locator(`[data-rendered-href="/components/hero/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/hero/source-java/basic");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('Button.of("Get started").variant("primary")');
    });
  });
});
