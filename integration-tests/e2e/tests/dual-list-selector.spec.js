import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "with-tooltips", "with-search", "complex-actions"];

test.describe("Dual List Selector", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/dual-list-selector");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic moves an item right and back", async ({ page }) => {
    const root = page.locator("#dls-basic");
    await root.locator(".pf-v6-c-dual-list-selector__list-item", { hasText: "Apple" }).click();
    await root.locator("button[aria-label='Move selected to chosen']").click();
    const chosen = root.locator(".pf-v6-c-dual-list-selector__pane").nth(1);
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item", { hasText: "Apple" })).toBeVisible();
    await chosen.locator(".pf-v6-c-dual-list-selector__list-item", { hasText: "Apple" }).click();
    await root.locator("button[aria-label='Move selected to available']").click();
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(0);
  });

  test("search filters the available pane", async ({ page }) => {
    const root = page.locator("#dls-search");
    const avail = root.locator(".pf-v6-c-dual-list-selector__pane").first();
    await expect(avail.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(5);
    await avail.locator("input[type='search']").fill("an");
    await expect(avail.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(1);
  });

  test("tooltips appear on hovering the controls", async ({ page }) => {
    const root = page.locator("#dls-tooltips");
    // Disabled buttons have pointer-events: none — select an item first.
    await root.locator(".pf-v6-c-dual-list-selector__list-item", { hasText: "Apple" }).click();
    const btn = root.locator("button[aria-label='Move selected to chosen']");
    await expect(root.locator(".pf-v6-c-tooltip").first()).toBeHidden();
    await btn.hover();
    await expect(root.locator(".pf-v6-c-tooltip").first()).toBeVisible();
  });

  test("complex actions move all items at once", async ({ page }) => {
    const root = page.locator("#dls-complex");
    const chosen = root.locator(".pf-v6-c-dual-list-selector__pane").nth(1);
    await root.locator("button[aria-label='Move all right']").click();
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(3);
    await expect(chosen.locator(".pf-v6-u-color-200").first()).toBeVisible();
    await root.locator("button[aria-label='Move all left']").click();
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(0);
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/dual-list-selector/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/dual-list-selector/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-dual-list-selector").first()).toBeAttached();
      });
    }
  });
});
