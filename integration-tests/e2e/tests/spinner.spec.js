import { test, expect } from "@playwright/test";

test.describe("Spinner", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/spinner");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    await expect(page.locator("#examples")).toBeAttached();
    await expect(page.locator("#basic")).toBeAttached();
    await expect(page.locator("#size-variations")).toBeAttached();
    await expect(page.locator("#custom-size")).toBeAttached();
    await expect(page.locator("#inline")).toBeAttached();
    await expect(page.locator("#documentation")).toBeAttached();
    await expect(page.locator("#props-spinner")).toBeAttached();
    await expect(page.locator("#usage")).toBeAttached();
  });

  test("renders all five size modifiers", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-spinner.pf-m-xs").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-spinner.pf-m-sm").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-spinner.pf-m-md").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-spinner.pf-m-lg").first()).toBeVisible();
    await expect(page.locator(".pf-v6-c-spinner.pf-m-xl").first()).toBeVisible();
  });

  test("renders a custom-diameter spinner with inline style", async ({ page }) => {
    const customSized = page.locator('.pf-v6-c-spinner[style*="--pf-v6-c-spinner--diameter"]').first();
    await expect(customSized).toBeVisible();
  });

  test("renders an inline spinner", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-spinner.pf-m-inline").first()).toBeVisible();
  });

  test("spinner SVGs have role=progressbar and aria-valuetext", async ({ page }) => {
    const spinner = page.locator(".pf-v6-c-spinner").first();
    await expect(spinner).toHaveAttribute("role", "progressbar");
    await expect(spinner).toHaveAttribute("aria-valuetext", /.+/);
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of ["basic", "size-variations", "custom-size", "inline"]) {
      const res = await page.goto(`/components/spinner/${slug}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-spinner").first()).toBeVisible();
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/spinner");
      for (const ex of ["basic", "custom-size", "size-variations"]) {
        const card = page.locator(`[data-rendered-href="/components/spinner/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/spinner/source-java/custom-size");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.diameter("80px")');
    });
  });
});
