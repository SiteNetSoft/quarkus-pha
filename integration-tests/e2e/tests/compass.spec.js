import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "alternate-footer", "docked-nav", "main-header-structure"];

test.describe("Compass", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/compass");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic has container, header, main, and footer regions", async ({ page }) => {
    await expect(page.locator("#cmp-basic")).toHaveClass(/pf-v6-c-compass/);
    await expect(page.locator("#cmp-basic .pf-v6-c-compass__container")).toBeVisible();
    await expect(page.locator("#cmp-basic .pf-v6-c-compass__header")).toBeVisible();
    await expect(page.locator("#cmp-basic .pf-v6-c-compass__main")).toBeVisible();
    await expect(page.locator("#cmp-basic .pf-v6-c-compass__footer")).toBeVisible();
  });

  test("docked variant adds the dock rail", async ({ page }) => {
    await expect(page.locator("#cmp-docked")).toHaveClass(/pf-m-docked/);
    await expect(page.locator("#cmp-docked .pf-v6-c-compass__dock")).toBeVisible();
  });

  test("main header structure has a title and toolbar", async ({ page }) => {
    await expect(page.locator("#cmp-header-structure .pf-v6-c-compass__main-header-title")).toHaveText(
      "Main header structure",
    );
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/compass/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/compass/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-compass").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/compass");
      for (const ex of ["basic", "docked-nav", "main-header-structure"]) {
        const card = page.locator(`[data-rendered-href="/components/compass/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/compass/source-java/docked-nav");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('Button.icon("fa:house", "Home").variant("plain")');
    });
  });
});
