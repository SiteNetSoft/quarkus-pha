import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "with-headings",
  "compact",
  "plain",
  "checkboxes-actions",
  "actions",
  "expandable",
  "compact-expandable",
  "nested-expandable",
  "mixed-expandable",
  "width-modifiers",
  "clickable-rows",
  "clickable-expandable-rows",
  "controlling-text",
  "as-grid",
  "sm-grid-breakpoint",
  "no-grid",
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

    test("grid family covers always-grid and no-grid", async ({ page }) => {
      await expect(page.locator("#dl-as-grid.pf-m-grid")).toBeAttached();
      await expect(page.locator("#dl-no-grid.pf-m-grid-none")).toBeAttached();
    });
  });

  test.describe("With headings", () => {
    test("heading cells and alignment modifiers render", async ({ page }) => {
      await expect(page.locator("#dl-with-headings h4")).toHaveCount(2);
      await expect(page.locator("#dl-with-headings .pf-v6-c-data-list__cell.pf-m-align-right")).toBeAttached();
    });
  });

  test.describe("Checkboxes and actions", () => {
    test("rows have a check control and an action area", async ({ page }) => {
      await expect(page.locator("#dl-checkboxes-actions .pf-v6-c-data-list__check input")).toHaveCount(3);
      await expect(page.locator("#dl-checkboxes-actions .pf-v6-c-data-list__item-action")).toHaveCount(4);
    });

    test("the responsive row swaps kebab and buttons on breakpoint modifiers", async ({ page }) => {
      await expect(
        page.locator("#dl-checkboxes-actions .pf-v6-c-data-list__item-action.pf-m-hidden-on-lg"),
      ).toBeAttached();
      const buttons = page.locator(
        "#dl-checkboxes-actions .pf-v6-c-data-list__item-action.pf-m-hidden.pf-m-visible-on-lg",
      );
      await expect(buttons.locator("button")).toHaveCount(2);
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

    test("compact expandable combines pf-m-compact with live toggles", async ({ page }) => {
      const list = page.locator("#dl-compact-expandable");
      await expect(list).toHaveClass(/pf-m-compact/);
      const rows = list.locator("> .pf-v6-c-data-list__item");
      await expect(rows.first()).toHaveClass(/pf-m-expanded/);
      await rows.nth(1).locator(".pf-v6-c-data-list__toggle button").click();
      await expect(rows.nth(1)).toHaveClass(/pf-m-expanded/);
    });

    test("nested list expands independently and collapses with its parent", async ({ page }) => {
      const outer = page.locator("#dl-nested-expandable > .pf-v6-c-data-list__item").first();
      await expect(outer).toHaveClass(/pf-m-expanded/);
      const inner = page.locator("#dl-nested-expandable-inner");
      await expect(inner).toBeVisible();
      const innerRows = inner.locator("> .pf-v6-c-data-list__item");
      await expect(innerRows.first()).toHaveClass(/pf-m-expanded/);
      await innerRows.nth(1).locator(".pf-v6-c-data-list__toggle button").click();
      await expect(innerRows.nth(1)).toHaveClass(/pf-m-expanded/);
      await outer.locator(".pf-v6-c-data-list__toggle button").first().click();
      await expect(inner).toBeHidden();
    });
  });

  test.describe("Width modifiers", () => {
    test("flex and no-fill cells render", async ({ page }) => {
      await expect(page.locator("#dl-width-modifiers .pf-v6-c-data-list__cell.pf-m-flex-3")).toBeAttached();
      await expect(page.locator("#dl-width-modifiers .pf-v6-c-data-list__cell.pf-m-flex-4")).toBeAttached();
      await expect(page.locator("#dl-width-modifiers .pf-v6-c-data-list__cell.pf-m-flex-5")).toBeAttached();
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

    test("clickable expandable rows: toggle expands without stealing selection", async ({ page }) => {
      const rows = page.locator("#dl-clickable-expandable > .pf-v6-c-data-list__item");
      await expect(rows.first()).toHaveClass(/pf-m-selected/);
      await expect(rows.first()).toHaveClass(/pf-m-expanded/);
      await rows.nth(1).locator(".pf-v6-c-data-list__toggle button").click();
      await expect(rows.nth(1)).toHaveClass(/pf-m-expanded/);
      await expect(rows.first()).toHaveClass(/pf-m-selected/);
      await expect(rows.nth(1)).not.toHaveClass(/pf-m-selected/);
      await rows.nth(1).locator(".pf-v6-c-data-list__cell").first().click();
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
