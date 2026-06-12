import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "stack",
  "panel-right",
  "panel-right-gutter",
  "sticky-panel",
  "static-panel",
  "responsive-panel",
  "border",
  "padding-panel",
  "padding-content",
];

const MODIFIERS = {
  "sb-stack": [/pf-m-stack/],
  "sb-panel-right-gutter": [/pf-m-panel-right/, /pf-m-gutter/],
  "sb-border": [/pf-m-split/],
};

test.describe("Sidebar", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/sidebar");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("root modifiers are applied", async ({ page }) => {
    for (const [id, patterns] of Object.entries(MODIFIERS)) {
      for (const pattern of patterns) {
        await expect(page.locator(`#${id}`)).toHaveClass(pattern);
      }
    }
  });

  test("basic renders panel beside content", async ({ page }) => {
    await expect(page.locator("#sb-basic .pf-v6-c-sidebar__panel")).toBeVisible();
    await expect(page.locator("#sb-basic .pf-v6-c-sidebar__content")).toBeVisible();
  });

  test("sticky and static panels carry their modifiers", async ({ page }) => {
    await expect(page.locator("#sb-sticky-panel .pf-v6-c-sidebar__panel")).toHaveClass(/pf-m-sticky/);
    await expect(page.locator("#sb-static-panel .pf-v6-c-sidebar__panel")).toHaveClass(/pf-m-static/);
  });

  test("responsive panel has breakpoint width modifiers", async ({ page }) => {
    const panel = page.locator("#sb-responsive-panel .pf-v6-c-sidebar__panel");
    await expect(panel).toHaveClass(/pf-m-width-25/);
    await expect(panel).toHaveClass(/pf-m-width-33-on-lg/);
  });

  test("padding modifiers apply to one region only", async ({ page }) => {
    await expect(page.locator("#sb-padding-panel .pf-v6-c-sidebar__panel")).toHaveClass(/pf-m-padding/);
    await expect(page.locator("#sb-padding-panel .pf-v6-c-sidebar__content")).not.toHaveClass(/pf-m-padding/);
    await expect(page.locator("#sb-padding-content .pf-v6-c-sidebar__content")).toHaveClass(/pf-m-padding/);
    await expect(page.locator("#sb-padding-content .pf-v6-c-sidebar__panel")).not.toHaveClass(/pf-m-padding/);
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/sidebar/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/sidebar/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-sidebar").first()).toBeAttached();
      });
    }
  });
});
