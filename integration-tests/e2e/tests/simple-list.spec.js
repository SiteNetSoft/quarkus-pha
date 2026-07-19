import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "grouped", "links", "selectable"];

test.describe("Simple List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/simple-list");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#grouped")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-simple-list class", async ({ page }) => {
      await expect(page.locator("#sl-basic")).toHaveClass(/pf-v6-c-simple-list/);
    });

    test("has item links", async ({ page }) => {
      const links = page.locator("#sl-basic .pf-v6-c-simple-list__item-link");
      await expect(links.first()).toBeVisible();
    });

    test("one link has pf-m-current modifier", async ({ page }) => {
      await expect(page.locator("#sl-basic .pf-v6-c-simple-list__item-link.pf-m-current")).toBeVisible();
    });
  });

  test.describe("Grouped", () => {
    test("has title element", async ({ page }) => {
      await expect(page.locator("#sl-grouped .pf-v6-c-simple-list__title").first()).toBeVisible();
    });
  });

  test.describe("Parity additions", () => {
    test("links variant renders anchors with a current item", async ({ page }) => {
      await expect(page.locator("#slist-links a.pf-v6-c-simple-list__item-link")).toHaveCount(3);
      await expect(page.locator("#slist-links .pf-m-current")).toHaveCount(1);
    });

    test("selectable moves pf-m-current on click", async ({ page }) => {
      const items = page.locator("#slist-selectable .pf-v6-c-simple-list__item-link");
      await items.nth(2).click();
      await expect(items.nth(2)).toHaveClass(/pf-m-current/);
      await expect(items.nth(0)).not.toHaveClass(/pf-m-current/);
    });

    test("/components/simple-list/links returns 200", async ({ page }) => {
      const res = await page.goto("/components/simple-list/links");
      expect(res.status()).toBe(200);
    });

    test("/components/simple-list/selectable returns 200", async ({ page }) => {
      const res = await page.goto("/components/simple-list/selectable");
      expect(res.status()).toBe(200);
    });
  });

  test.describe("Java source tab", () => {
    test("all example cards get a leading Java tab", async ({ page }) => {
      await page.goto("/components/simple-list");
      for (const ex of EXAMPLES) {
        const card = page.locator(`[data-rendered-href="/components/simple-list/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("Java tab opens Monaco with the builder code", async ({ page }) => {
      await page.goto("/components/simple-list");
      const card = page.locator('[data-rendered-href="/components/simple-list/basic"]');
      await card.locator('button[aria-label*="Toggle Java"]').click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
      await expect(card.locator(".monaco-editor .view-lines")).toContainText("SimpleList.builder()", {
        timeout: 10000,
      });
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/simple-list/source-java/grouped");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('SimpleList.section("Account"');
    });
  });
});
