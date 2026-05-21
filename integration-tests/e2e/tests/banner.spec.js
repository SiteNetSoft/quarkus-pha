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
        await expect(page.locator(`#banner-${s} .pf-v6-c-banner__icon svg`)).toBeVisible();
      }
    });

    test("custom variant has no auto icon", async ({ page }) => {
      await expect(page.locator("#banner-custom .pf-v6-c-banner__icon")).toHaveCount(0);
    });
  });

  test.describe("With links", () => {
    test("renders anchor tags inside the message", async ({ page }) => {
      await expect(page.locator("#banner-links a")).toHaveCount(2);
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
});
