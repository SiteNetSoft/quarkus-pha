import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "with-width", "with-height", "responsive"];

test.describe("Brand", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/brand");
  });

  test("TOC anchors render", async ({ page }) => {
    const anchors = ["examples", ...EXAMPLES, "documentation", "props-brand", "usage"];
    for (const id of anchors) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic", () => {
    test("renders as img with brand class + correct attrs", async ({ page }) => {
      const b = page.locator("#brand-basic");
      await expect(b).toHaveClass(/pf-v6-c-brand/);
      await expect(b).toHaveAttribute("src", /quarkus-pha-logo\.svg/);
      await expect(b).toHaveAttribute("alt", "Quarkus PHA logo");
      const tag = await b.evaluate((el) => el.tagName);
      expect(tag).toBe("IMG");
    });
  });

  test.describe("With width", () => {
    test("sets --pf-v6-c-brand--Width on the inline style", async ({ page }) => {
      await expect(page.locator("#brand-width")).toHaveAttribute("style", /--pf-v6-c-brand--Width:\s*200px/);
    });
  });

  test.describe("With height", () => {
    test("sets --pf-v6-c-brand--Height on the inline style", async ({ page }) => {
      await expect(page.locator("#brand-height")).toHaveAttribute("style", /--pf-v6-c-brand--Height:\s*36px/);
    });
  });

  test.describe("Responsive", () => {
    test("renders <picture> with pf-m-picture", async ({ page }) => {
      const b = page.locator("#brand-picture");
      await expect(b).toHaveClass(/pf-v6-c-brand/);
      await expect(b).toHaveClass(/pf-m-picture/);
      const tag = await b.evaluate((el) => el.tagName);
      expect(tag).toBe("PICTURE");
    });

    test("contains a <source> and a fallback <img>", async ({ page }) => {
      await expect(page.locator("#brand-picture source")).toHaveCount(1);
      await expect(page.locator("#brand-picture source")).toHaveAttribute("media", "(min-width: 768px)");
      await expect(page.locator("#brand-picture img")).toHaveAttribute("alt", "Quarkus PHA logo");
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/brand/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/brand/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-brand").first()).toBeAttached();
      });
    }
  });
});
