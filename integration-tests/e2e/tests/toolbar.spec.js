import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "items",
  "insets",
  "vertical",
  "sticky",
  "dynamic-sticky",
  "groups",
  "color-variants",
  "toggle-groups",
  "with-filters",
  "stacked",
  "content-wrap",
  "group-spacers",
  "item-spacers",
];

const MODIFIERS = {
  "tb-insets": [/pf-m-inset-md/, /pf-m-inset-xl-on-lg/],
  "tb-vertical": [/pf-m-vertical/],
  "tb-sticky": [/pf-m-sticky/],
  "tb-color-primary": [/pf-m-primary/],
  "tb-color-secondary": [/pf-m-secondary/],
  "tb-color-none": [/pf-m-no-background/],
};

test.describe("Toolbar", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/toolbar");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of [...EXAMPLES, "documentation", "props-toolbar", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("modifier classes are applied", async ({ page }) => {
    for (const [id, patterns] of Object.entries(MODIFIERS)) {
      for (const pattern of patterns) {
        await expect(page.locator(`#${id}`)).toHaveClass(pattern);
      }
    }
  });

  test.describe("Basic variant", () => {
    test("basic toolbar has groups, end alignment, and a primary button", async ({ page }) => {
      await expect(page.locator("#tb-basic")).toHaveClass(/pf-v6-c-toolbar/);
      await expect(page.locator("#tb-basic .pf-v6-c-toolbar__group")).toHaveCount(2);
      await expect(page.locator("#tb-basic .pf-v6-c-toolbar__group.pf-m-align-end")).toHaveCount(1);
      await expect(page.locator("#tb-basic .pf-v6-c-button.pf-m-primary")).toBeVisible();
    });
  });

  test.describe("Items", () => {
    test("items are separated by a vertical divider", async ({ page }) => {
      await expect(page.locator("#tb-items .pf-v6-c-divider.pf-m-vertical")).toBeAttached();
    });
  });

  test.describe("Dynamic sticky", () => {
    test("checkbox toggles pf-m-sticky", async ({ page }) => {
      const tb = page.locator("#tb-dynamic-sticky");
      await expect(tb).toHaveClass(/pf-m-sticky/);
      await page.locator("#tb-dynamic-sticky-check").uncheck();
      await expect(tb).not.toHaveClass(/pf-m-sticky/);
    });
  });

  test.describe("Groups", () => {
    test("filter group and end-aligned group render", async ({ page }) => {
      await expect(page.locator("#tb-groups .pf-v6-c-toolbar__group.pf-m-filter-group")).toBeAttached();
      await expect(page.locator("#tb-groups .pf-v6-c-toolbar__group.pf-m-align-end")).toBeAttached();
      await expect(page.locator("#tb-groups .pf-v6-c-menu-toggle")).toHaveCount(2);
    });
  });

  test.describe("Toggle groups", () => {
    test("filter toggle opens the expandable content row below the lg breakpoint", async ({ page }) => {
      const tb = page.locator("#tb-toggle-groups");
      await expect(tb.locator(".pf-v6-c-toolbar__group.pf-m-toggle-group")).toHaveClass(/pf-m-show-on-lg/);
      // The toggle is only visible below lg — shrink the viewport first.
      await page.setViewportSize({ width: 600, height: 800 });
      const expandable = tb.locator(".pf-v6-c-toolbar__expandable-content");
      await expect(expandable).toBeHidden();
      await tb.locator(".pf-v6-c-toolbar__toggle button").click();
      await expect(expandable).toBeVisible();
    });
  });

  test.describe("With filters", () => {
    test("labels remove individually and clear-all empties the row", async ({ page }) => {
      const tb = page.locator("#tb-with-filters");
      const labels = tb.locator(".pf-v6-c-label-group__list-item");
      await expect(labels).toHaveCount(3);
      await labels.first().locator(".pf-v6-c-label__actions button").click();
      await expect(labels).toHaveCount(2);
      await tb.locator("button", { hasText: "Clear all filters" }).click();
      await expect(labels).toHaveCount(0);
      await expect(tb.locator(".pf-v6-c-toolbar__item.pf-m-label")).toBeHidden();
    });
  });

  test.describe("Spacers", () => {
    test("group and item gap modifiers are applied", async ({ page }) => {
      await expect(page.locator("#tb-group-spacers .pf-v6-c-toolbar__group.pf-m-column-gap-none")).toBeAttached();
      await expect(page.locator("#tb-group-spacers .pf-v6-c-toolbar__group.pf-m-column-gap-2xl")).toBeAttached();
      await expect(page.locator("#tb-item-spacers .pf-v6-c-toolbar__item.pf-m-gap-none")).toBeAttached();
      await expect(page.locator("#tb-item-spacers .pf-v6-c-toolbar__item.pf-m-gap-4xl")).toBeAttached();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/toolbar/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/toolbar/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-toolbar").first()).toBeAttached();
      });
    }
  });

  test.describe("Per-example code viewer", () => {
    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/toolbar/basic"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/toolbar/basic");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
