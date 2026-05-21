import { test, expect } from "@playwright/test";

const LAYOUTS = {
  bullseye: ["basic", "with-content"],
  flex: ["basic", "direction-column", "with-gap", "justify-content"],
  gallery: ["basic", "with-gutter", "custom-min-width"],
  grid: ["basic", "with-gutter", "spans", "responsive-spans"],
  level: ["basic", "with-gutter"],
  split: ["basic", "with-fill", "with-gutter"],
  stack: ["basic", "with-fill", "with-gutter"],
};

for (const [layout, examples] of Object.entries(LAYOUTS)) {
  test.describe(`Layouts — ${layout}`, () => {
    test("demo page TOC anchors render", async ({ page }) => {
      await page.goto(`/layouts/${layout}`);
      const anchors = ["examples", ...examples, "documentation", `props-${layout}`, "usage"];
      for (const id of anchors) {
        await expect(page.locator(`#${id}`)).toBeAttached();
      }
    });

    for (const example of examples) {
      test(`standalone /layouts/${layout}/${example} renders 200`, async ({ page }) => {
        const res = await page.goto(`/layouts/${layout}/${example}`);
        expect(res.status()).toBe(200);
      });

      test(`source /layouts/${layout}/source/${example} returns Qute markup`, async ({ request }) => {
        const res = await request.get(`/layouts/${layout}/source/${example}`);
        expect(res.status()).toBe(200);
        const body = await res.text();
        expect(body.length).toBeGreaterThan(0);
      });
    }
  });
}

test.describe("Layouts — runtime class assertions", () => {
  test("bullseye/basic renders pf-v6-l-bullseye + __item", async ({ page }) => {
    await page.goto("/layouts/bullseye/basic");
    await expect(page.locator(".pf-v6-l-bullseye").first()).toBeAttached();
    await expect(page.locator(".pf-v6-l-bullseye__item").first()).toBeAttached();
  });

  test("flex/direction-column carries pf-m-column", async ({ page }) => {
    await page.goto("/layouts/flex/direction-column");
    await expect(page.locator("#flex-column")).toHaveClass(/pf-m-column/);
  });

  test("gallery/with-gutter carries pf-m-gutter", async ({ page }) => {
    await page.goto("/layouts/gallery/with-gutter");
    await expect(page.locator("#gallery-gutter")).toHaveClass(/pf-m-gutter/);
  });

  test("gallery/custom-min-width sets the CSS variable", async ({ page }) => {
    await page.goto("/layouts/gallery/custom-min-width");
    await expect(page.locator("#gallery-min-width")).toHaveAttribute(
      "style",
      /--pf-v6-l-gallery--GridTemplateColumns--min:\s*350px/,
    );
  });

  test("grid/responsive-spans children have per-breakpoint classes", async ({ page }) => {
    await page.goto("/layouts/grid/responsive-spans");
    const items = page.locator("#grid-responsive .pf-v6-l-grid__item");
    await expect(items).toHaveCount(4);
    for (const cls of ["pf-m-12-col", "pf-m-6-col-on-md", "pf-m-4-col-on-lg"]) {
      await expect(items.first()).toHaveClass(new RegExp(cls));
    }
  });

  test("split/with-fill has exactly one pf-m-fill item", async ({ page }) => {
    await page.goto("/layouts/split/with-fill");
    await expect(page.locator("#split-fill .pf-v6-l-split__item.pf-m-fill")).toHaveCount(1);
  });

  test("stack/with-fill has exactly one pf-m-fill item", async ({ page }) => {
    await page.goto("/layouts/stack/with-fill");
    await expect(page.locator("#stack-fill .pf-v6-l-stack__item.pf-m-fill")).toHaveCount(1);
  });

  test("level/basic distributes children edge-to-edge", async ({ page }) => {
    await page.goto("/layouts/level/basic");
    await expect(page.locator("#level-basic .pf-v6-l-level__item")).toHaveCount(2);
  });
});
