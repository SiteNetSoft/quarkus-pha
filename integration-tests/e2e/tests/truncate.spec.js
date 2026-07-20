import { test, expect } from "@playwright/test";

test.describe("Truncate", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/truncate");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#end")).toBeVisible();
    await expect(page.locator("#start")).toBeVisible();
    await expect(page.locator("#middle")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-truncate")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("end truncate has start span", async ({ page }) => {
    await expect(page.locator("#trunc-end .pf-v6-c-truncate__start")).toBeVisible();
  });

  test("end truncate has pf-v6-c-truncate class", async ({ page }) => {
    await expect(page.locator("#trunc-end")).toHaveClass(/pf-v6-c-truncate/);
  });

  test("start truncate has end span", async ({ page }) => {
    await expect(page.locator("#trunc-start .pf-v6-c-truncate__end")).toBeVisible();
  });

  test("middle truncate has both start and end spans", async ({ page }) => {
    await expect(page.locator("#trunc-middle .pf-v6-c-truncate__start")).toBeVisible();
    await expect(page.locator("#trunc-middle .pf-v6-c-truncate__end")).toBeVisible();
  });

  test.describe("Parity additions", () => {
    test("tooltip variant reveals full text on hover", async ({ page }) => {
      const wrap = page.locator('[data-rendered-href="/components/truncate/custom-tooltip-position"]');
      await expect(wrap.locator(".pf-v6-c-tooltip")).toBeHidden();
      await page.locator("#trunc-tooltip").hover();
      await expect(wrap.locator(".pf-v6-c-tooltip")).toBeVisible();
    });

    test("max-chars clamps to 20 characters plus ellipsis", async ({ page }) => {
      const text = await page.locator("#trunc-max-chars .pf-v6-c-truncate__start").textContent();
      expect(text.length).toBe(21);
    });

    test("link variant truncates the anchor", async ({ page }) => {
      await expect(page.locator("#trunc-link")).toHaveCSS("text-overflow", "ellipsis");
    });

    test("/components/truncate/custom-tooltip-position returns 200", async ({ page }) => {
      const res = await page.goto("/components/truncate/custom-tooltip-position");
      expect(res.status()).toBe(200);
    });

    test("/components/truncate/max-chars returns 200", async ({ page }) => {
      const res = await page.goto("/components/truncate/max-chars");
      expect(res.status()).toBe(200);
    });

    test("/components/truncate/with-links returns 200", async ({ page }) => {
      const res = await page.goto("/components/truncate/with-links");
      expect(res.status()).toBe(200);
    });
  });
  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab; custom-tooltip-position does not", async ({ page }) => {
      await page.goto("/components/truncate");
      for (const ex of ["end", "middle", "max-chars", "with-links"]) {
        const card = page.locator(`[data-rendered-href="/components/truncate/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      const custom = page.locator('[data-rendered-href="/components/truncate/custom-tooltip-position"]');
      await expect(custom.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/truncate/source-java/middle");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.position("middle").trailingNumChars(8)');
    });

    test("model max-chars flavour still truncates via Alpine", async ({ page }) => {
      await page.goto("/components/truncate/max-chars");
      const start = page.locator("#trunc-max-chars .pf-v6-c-truncate__start");
      await expect(start).toHaveText(/^Vestibulum interdum \u2026$/);
    });
  });
});
