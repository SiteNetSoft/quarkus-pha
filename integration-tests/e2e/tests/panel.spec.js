import { test, expect } from "@playwright/test";

test.describe("Panel", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/panel");
  });

  test("page loads with example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#bordered")).toHaveText("Bordered");
    await expect(page.locator("h3#raised")).toHaveText("Raised");
  });

  test("basic panel has pf-v6-c-panel class", async ({ page }) => {
    await expect(page.locator("#panel-basic")).toHaveClass(/pf-v6-c-panel/);
  });

  test("basic panel has body content", async ({ page }) => {
    await expect(page.locator("#panel-basic .pf-v6-c-panel__main-body")).toBeVisible();
  });

  test("bordered panel has bordered modifier", async ({ page }) => {
    await expect(page.locator("#panel-bordered")).toHaveClass(/pf-m-bordered/);
  });

  test("bordered panel has header", async ({ page }) => {
    await expect(page.locator("#panel-bordered .pf-v6-c-panel__header")).toBeVisible();
  });

  test("raised panel has raised modifier", async ({ page }) => {
    await expect(page.locator("#panel-raised")).toHaveClass(/pf-m-raised/);
  });

  test("raised panel has body content", async ({ page }) => {
    await expect(page.locator("#panel-raised .pf-v6-c-panel__main-body")).toBeVisible();
  });

  test.describe("Parity additions", () => {
    test("pill and scrollable-auto-height carry their modifiers", async ({ page }) => {
      await expect(page.locator("#pn-pill")).toHaveClass(/pf-m-pill/);
      await expect(page.locator("#pn-scroll-auto")).toHaveClass(/pf-m-scrollable-auto-height/);
    });

    test("/components/panel/pill returns 200", async ({ page }) => {
      const res = await page.goto("/components/panel/pill");
      expect(res.status()).toBe(200);
    });

    test("/components/panel/scrollable-auto-height returns 200", async ({ page }) => {
      const res = await page.goto("/components/panel/scrollable-auto-height");
      expect(res.status()).toBe(200);
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/panel");
      for (const ex of ["basic", "pill", "scrollable-with-header-and-footer"]) {
        const card = page.locator(`[data-rendered-href="/components/panel/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/panel/source-java/scrollable");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.scrollable().bordered().maxHeight("16rem")');
    });
  });
});
