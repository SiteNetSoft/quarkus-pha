import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "compact",
  "plain",
  "checkboxes-actions",
  "actions",
  "expandable",
  "mixed-expandable",
  "width-modifiers",
  "clickable-rows",
  "controlling-text",
  "sm-grid-breakpoint",
];

test.describe("Data List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/data-list");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Modifiers", () => {
    test("basic, compact, plain, grid-sm carry their classes", async ({ page }) => {
      await expect(page.locator("#dl-basic")).toHaveClass(/pf-v6-c-data-list/);
      await expect(page.locator("#dl-compact")).toHaveClass(/pf-m-compact/);
      await expect(page.locator("#dl-plain")).toHaveClass(/pf-m-plain/);
      await expect(page.locator("#dl-sm-grid")).toHaveClass(/pf-m-grid-sm/);
    });
  });

  test.describe("Checkboxes and actions", () => {
    test("rows have a check control and an action button", async ({ page }) => {
      await expect(page.locator("#dl-checkboxes-actions .pf-v6-c-data-list__check input")).toHaveCount(2);
      await expect(page.locator("#dl-checkboxes-actions .pf-v6-c-data-list__item-action button")).toHaveCount(2);
    });

    test("kebab action opens a row menu", async ({ page }) => {
      const action = page.locator("#dl-actions .pf-v6-c-data-list__item-action").nth(1);
      await action.locator("> button").click();
      await expect(action.locator(".pf-v6-c-menu")).toBeVisible();
      await expect(action.locator(".pf-v6-c-menu__list-item.pf-m-danger")).toBeAttached();
    });
  });

  test.describe("Expandable", () => {
    test("first row starts expanded; toggling collapses it", async ({ page }) => {
      const first = page.locator("#dl-expandable .pf-v6-c-data-list__item").first();
      await expect(first).toHaveClass(/pf-m-expanded/);
      await expect(first.locator(".pf-v6-c-data-list__expandable-content")).toBeVisible();
      await first.locator(".pf-v6-c-data-list__toggle button").click();
      await expect(first).not.toHaveClass(/pf-m-expanded/);
      await expect(first.locator(".pf-v6-c-data-list__expandable-content")).toBeHidden();
    });

    test("mixed list has one toggle and one plain row", async ({ page }) => {
      await expect(page.locator("#dl-mixed-expandable .pf-v6-c-data-list__toggle")).toHaveCount(1);
      await expect(page.locator("#dl-mixed-expandable .pf-v6-c-data-list__item")).toHaveCount(2);
    });
  });

  test.describe("Width modifiers", () => {
    test("flex and no-fill cells render", async ({ page }) => {
      await expect(page.locator("#dl-width-modifiers .pf-v6-c-data-list__cell.pf-m-flex-3")).toBeAttached();
      await expect(page.locator("#dl-width-modifiers .pf-v6-c-data-list__cell.pf-m-no-fill")).toBeAttached();
    });
  });

  test.describe("Clickable rows", () => {
    test("clicking moves pf-m-selected", async ({ page }) => {
      const rows = page.locator("#dl-clickable-rows .pf-v6-c-data-list__item");
      await expect(rows.first()).toHaveClass(/pf-m-selected/);
      await rows.nth(1).click();
      await expect(rows.nth(1)).toHaveClass(/pf-m-selected/);
      await expect(rows.first()).not.toHaveClass(/pf-m-selected/);
    });
  });

  test.describe("Controlling text", () => {
    test("break-word, truncate, and nowrap variants render", async ({ page }) => {
      await expect(page.locator("#dl-text-break")).toHaveClass(/pf-m-break-word/);
      await expect(page.locator("#dl-text-truncate")).toHaveClass(/pf-m-truncate/);
      await expect(page.locator("#dl-text-nowrap")).toHaveClass(/pf-m-nowrap/);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/data-list/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/data-list/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-data-list").first()).toBeAttached();
      });
    }
  });
});
