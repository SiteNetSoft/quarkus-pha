import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic-colors", "status", "with-links", "screen-reader", "sticky"];

test.describe("Banner", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/banner");
  });

  test("TOC anchors render", async ({ page }) => {
    const anchors = ["examples", ...EXAMPLES, "documentation", "props-banner", "usage"];
    for (const id of anchors) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic colors", () => {
    test("renders all 8 color modifiers", async ({ page }) => {
      for (const c of ["red", "orangered", "orange", "yellow", "green", "teal", "blue", "purple"]) {
        await expect(page.locator(`#banner-${c}`)).toHaveClass(new RegExp(`pf-m-${c}`));
      }
    });
  });

  test.describe("Status", () => {
    test("renders all 5 status modifiers", async ({ page }) => {
      for (const s of ["success", "warning", "danger", "info", "custom"]) {
        await expect(page.locator(`#banner-${s}`)).toHaveClass(new RegExp(`pf-m-${s}`));
      }
    });

    test("non-custom status banners include an icon", async ({ page }) => {
      for (const s of ["success", "warning", "danger", "info"]) {
        await expect(page.locator(`#banner-${s} span[aria-hidden="true"] svg`)).toBeVisible();
      }
    });

    test("custom variant has no auto icon", async ({ page }) => {
      await expect(page.locator('#banner-custom span[aria-hidden="true"]')).toHaveCount(0);
    });
  });

  test.describe("With links", () => {
    test("renders anchor tags + a disabled link + an inline link-button inside banner messages", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/banner/with-links"]');
      // First banner: one plain anchor.
      // Second banner: one disabled-styled anchor (role=link, aria-disabled=true).
      // Third banner: blue variant with an inline link-button.
      await expect(card.locator(".pf-v6-c-banner")).toHaveCount(3);
      await expect(card.locator(".pf-v6-c-banner a[aria-disabled='true']")).toHaveCount(1);
      await expect(card.locator(".pf-v6-c-banner button.pf-v6-c-button.pf-m-link")).toHaveCount(1);
    });
  });

  test.describe("Screen reader text", () => {
    test("prepends a visually-hidden span", async ({ page }) => {
      await expect(page.locator("#banner-sr .pf-v6-screen-reader")).toHaveText(/Info alert:/);
    });
  });

  test.describe("Sticky", () => {
    test("carries pf-m-sticky", async ({ page }) => {
      await expect(page.locator("#banner-sticky")).toHaveClass(/pf-m-sticky/);
    });

    test("scroll container exists", async ({ page }) => {
      await expect(page.locator("#sticky-container")).toBeAttached();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/banner/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/banner/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-banner").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/banner");
      for (const ex of ["basic-colors", "status", "with-links"]) {
        const card = page.locator(`[data-rendered-href="/components/banner/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/banner/source-java/status");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.screenReaderText("Warning:")');
    });
  });
});
