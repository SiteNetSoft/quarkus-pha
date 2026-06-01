import { test, expect } from "@playwright/test";

test.describe("Ripple", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/ripple");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    await expect(page.locator("#examples")).toBeAttached();
    await expect(page.locator("#basic")).toBeAttached();
    await expect(page.locator("#custom-color")).toBeAttached();
    await expect(page.locator("#on-dark")).toBeAttached();
    await expect(page.locator("#documentation")).toBeAttached();
    await expect(page.locator("#props-ripple")).toBeAttached();
    await expect(page.locator("#usage")).toBeAttached();
  });

  test("renders a ripple with two animated rings", async ({ page }) => {
    const ripple = page.locator(".lds-ripple").first();
    await expect(ripple).toBeVisible();
    await expect(ripple.locator("div")).toHaveCount(2);
  });

  test("ripple root exposes progressbar role and accessible label", async ({ page }) => {
    const ripple = page.locator(".lds-ripple").first();
    await expect(ripple).toHaveAttribute("role", "progressbar");
    await expect(ripple).toHaveAttribute("aria-label", /.+/);
  });

  test("custom color is applied via --pha-ripple-color", async ({ page }) => {
    const tinted = page.locator('.lds-ripple[style*="--pha-ripple-color"]').first();
    await expect(tinted).toBeVisible();
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of ["basic", "custom-color", "on-dark"]) {
      const res = await page.goto(`/components/ripple/${slug}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".lds-ripple").first()).toBeVisible();
    }
  });

  test("source endpoint returns the raw Qute fragment", async ({ page }) => {
    const res = await page.goto("/components/ripple/source/basic");
    expect(res.status()).toBe(200);
    expect(await res.text()).toContain("components/feedback/ripple");
  });
});
