import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "with-subtext",
  "with-icon",
  "stacked-icon",
  "large-icons",
  "long-subtext",
  "single-selection",
  "multiple-selection",
];

test.describe("Tile", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tile");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("deprecation banner points to Card", async ({ page }) => {
    const banner = page.locator(".pf-v6-c-banner.pf-m-gold");
    await expect(banner).toBeVisible();
    await expect(banner).toContainText("Deprecated");
    await expect(banner.locator("a[href='/components/card']")).toBeVisible();
  });

  test("basic trio renders default, selected, and disabled", async ({ page }) => {
    await expect(page.locator("#tile-basic")).toHaveClass(/pf-v6-c-tile/);
    await expect(page.locator("#tile-basic-selected")).toHaveClass(/pf-m-selected/);
    await expect(page.locator("#tile-basic-disabled")).toHaveClass(/pf-m-disabled/);
  });

  test("icon variants carry their modifiers", async ({ page }) => {
    await expect(page.locator("#tile-icon .pf-v6-c-tile__icon svg")).toBeVisible();
    await expect(page.locator("#tile-stacked .pf-v6-c-tile__header")).toHaveClass(/pf-m-stacked/);
    await expect(page.locator("#tile-large")).toHaveClass(/pf-m-display-lg/);
  });

  test("single selection keeps exactly one tile selected", async ({ page }) => {
    const tiles = page.locator("#tile-single .pf-v6-c-tile");
    await expect(tiles.nth(0)).toHaveClass(/pf-m-selected/);
    await tiles.nth(2).click();
    await expect(tiles.nth(2)).toHaveClass(/pf-m-selected/);
    await expect(tiles.nth(0)).not.toHaveClass(/pf-m-selected/);
    await expect(page.locator("#tile-single .pf-m-selected")).toHaveCount(1);
  });

  test("multiple selection toggles tiles independently", async ({ page }) => {
    const tiles = page.locator("#tile-multi .pf-v6-c-tile");
    await tiles.nth(0).click();
    await tiles.nth(1).click();
    await expect(page.locator("#tile-multi .pf-m-selected")).toHaveCount(2);
    await tiles.nth(0).click();
    await expect(page.locator("#tile-multi .pf-m-selected")).toHaveCount(1);
    await expect(tiles.nth(1)).toHaveClass(/pf-m-selected/);
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/tile/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/tile/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-tile").first()).toBeAttached();
      });
    }
  });
});
