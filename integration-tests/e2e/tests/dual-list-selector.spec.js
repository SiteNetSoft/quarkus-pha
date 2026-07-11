import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "with-tooltips", "with-search", "complex-actions", "tree-view", "tree-view-disabled"];

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
    await root.locator("button[aria-label='Add selected']").click();
    const chosen = root.locator(".pf-v6-c-dual-list-selector__pane").nth(1);
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item", { hasText: "Apple" })).toBeVisible();
    await chosen.locator(".pf-v6-c-dual-list-selector__list-item", { hasText: "Apple" }).click();
    await root.locator("button[aria-label='Remove selected']").click();
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(0);
  });

  test("basic add-all and remove-all move every item", async ({ page }) => {
    const root = page.locator("#dls-basic");
    const chosen = root.locator(".pf-v6-c-dual-list-selector__pane").nth(1);
    await root.locator("button[aria-label='Add all']").click();
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(5);
    await root.locator("button[aria-label='Remove all']").click();
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(0);
  });

  test("tree view cascades group checks and transfers leaves", async ({ page }) => {
    const root = page.locator("#dls-tree");
    await expect(root).toHaveClass(/pf-m-animate-expand/);
    const chosen = root.locator(".pf-v6-c-dual-list-selector__pane.pf-m-chosen");
    await root.locator("input[aria-label='Select all greens']").check();
    await expect(
      root.locator(".pf-v6-c-dual-list-selector__pane.pf-m-available .pf-v6-c-dual-list-selector__status-text"),
    ).toHaveText("2 of 5 options selected");
    await root.locator("button[aria-label='Move checked to chosen']").click();
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(2);
    const availPane = root.locator(".pf-v6-c-dual-list-selector__pane.pf-m-available");
    await expect(availPane.locator("input[aria-label='Select Light green']")).toBeHidden();
    await chosen.locator("input[aria-label='Select Light green']").check();
    await root.locator("button[aria-label='Move checked to available']").click();
    await expect(chosen.locator(".pf-v6-c-dual-list-selector__list-item")).toHaveCount(1);
    await expect(availPane.locator("input[aria-label='Select Light green']")).toBeVisible();
  });

  test("tree view groups collapse on toggle click", async ({ page }) => {
    const root = page.locator("#dls-tree");
    const groupItem = root.locator(".pf-v6-c-dual-list-selector__list-item.pf-m-expandable").first();
    await expect(groupItem).toHaveClass(/pf-m-expanded/);
    await groupItem.locator(".pf-v6-c-dual-list-selector__item-toggle").first().click();
    await expect(groupItem).not.toHaveClass(/pf-m-expanded/);
    await expect(root.locator(".pf-m-available input[aria-label='Select Orange']")).toBeHidden();
  });

  test("tree view with disabled options renders disabled items and checks", async ({ page }) => {
    const root = page.locator("#dls-tree-disabled");
    await expect(root.locator(".pf-v6-c-dual-list-selector__list-item.pf-m-disabled")).toHaveCount(2);
    await expect(root.locator("input[aria-label='Select Red (disabled)']")).toBeDisabled();
    await expect(root.locator("input[aria-label='Select Orange']")).toBeEnabled();
    await expect(root.locator(".pf-v6-c-dual-list-selector__pane.pf-m-chosen [role='tree']")).toBeAttached();
  });

  test("draggable cross-link points at the drag-and-drop demo", async ({ page }) => {
    await expect(page.locator("#draggable")).toBeAttached();
    await expect(page.locator('a[href="/components/drag-and-drop"]')).toBeAttached();
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
