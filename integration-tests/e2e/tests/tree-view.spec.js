import { test, expect } from "@playwright/test";

// All 14 examples are model-driven (TreeViewDemoData) with a Java source tab.
const EXAMPLES = [
  "single-selectable",
  "multiselectable",
  "with-separate-selection-and-expansion",
  "with-search",
  "with-checkboxes",
  "with-icons",
  "with-unique-icon-per-item",
  "with-badges",
  "with-custom-badges",
  "with-action-items",
  "guides",
  "compact",
  "compact-no-background",
  "with-non-expandable-top-level-nodes",
];

test.describe("Tree view", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tree-view");
  });

  test("page loads with key section headings", async ({ page }) => {
    await expect(page.locator("#single-selectable")).toBeVisible();
    await expect(page.locator("#multiselectable")).toBeVisible();
    await expect(page.locator("#with-search")).toBeVisible();
    await expect(page.locator("#with-checkboxes")).toBeVisible();
    await expect(page.locator("#guides")).toBeVisible();
    await expect(page.locator("#compact")).toBeVisible();
  });

  test.describe("Single selectable", () => {
    const root = "#ws-core-c-tree-view-single-selectable";

    test("wrapper exists with phaTreeView Alpine data", async ({ page }) => {
      const wrapper = page.locator(root);
      await expect(wrapper).toBeAttached();
      // The model-driven template owns the Alpine root (a wrapper div here,
      // since the expand-all button sits outside pf-v6-c-tree-view).
      await expect(wrapper.locator('[x-data="phaTreeView()"]').first()).toBeAttached();
    });

    test("renders a single-select tree (aria-multiselectable=false)", async ({ page }) => {
      await expect(page.locator(`${root} ul[role='tree']`).first()).toHaveAttribute("aria-multiselectable", "false");
    });

    test("has list items", async ({ page }) => {
      await expect(page.locator(`${root} .pf-v6-c-tree-view__list-item`)).not.toHaveCount(0);
    });

    test("has expanded items with nested children", async ({ page }) => {
      // Tree-view initialises collapsed; click "Expand all" to surface nested items.
      await page.locator(`${root} button.pf-v6-c-button.pf-m-link`).first().click();
      const expanded = page.locator(`${root} .pf-v6-c-tree-view__list-item.pf-m-expanded`);
      await expect(expanded.first()).toBeVisible();
      const nested = page.locator(
        `${root} .pf-v6-c-tree-view__list-item.pf-m-expanded .pf-v6-c-tree-view__list .pf-v6-c-tree-view__list-item`,
      );
      await expect(nested.first()).toBeAttached();
    });
  });

  test.describe("Multiselectable", () => {
    const root = "#ws-core-c-tree-view-multiselectable";

    test("renders a multi-select tree (aria-multiselectable=true)", async ({ page }) => {
      await expect(page.locator(`${root} ul[role='tree']`).first()).toHaveAttribute("aria-multiselectable", "true");
    });
  });

  test.describe("With search", () => {
    const root = "#ws-core-c-tree-view-with-search";

    test("has a search input", async ({ page }) => {
      await expect(page.locator(`${root} .pf-v6-c-tree-view__search input`)).toBeVisible();
    });
  });

  test.describe("With checkboxes", () => {
    const root = "#ws-core-c-tree-view-with-checkboxes";

    test("renders node-check spans for checkbox cascade", async ({ page }) => {
      await expect(page.locator(`${root} .pf-v6-c-tree-view__node-check`).first()).toBeAttached();
    });
  });

  test.describe("Guides", () => {
    const root = "#ws-core-c-tree-view-guides";

    test("tree-view container has pf-m-guides modifier", async ({ page }) => {
      await expect(page.locator(`${root} .pf-v6-c-tree-view`)).toHaveClass(/pf-m-guides/);
    });

    test("has list items", async ({ page }) => {
      await expect(page.locator(`${root} .pf-v6-c-tree-view__list-item`)).not.toHaveCount(0);
    });
  });

  test.describe("Compact", () => {
    const root = "#ws-core-c-tree-view-compact";

    test("tree-view container has pf-m-compact modifier", async ({ page }) => {
      await expect(page.locator(`${root} .pf-v6-c-tree-view`)).toHaveClass(/pf-m-compact/);
    });
  });

  test.describe("With non-expandable top level nodes", () => {
    const root = "#ws-core-c-tree-view-with-non-expandable-top-level-nodes";

    test("renders a wired tree (regression: opening tag once lost its Alpine root)", async ({ page }) => {
      await expect(page.locator(`${root} [x-data="phaTreeView()"]`).first()).toBeAttached();
      await expect(page.locator(`${root} ul[role='tree']`).first()).toBeAttached();
    });

    test("non-expandable top-level leaves have no toggle", async ({ page }) => {
      const cost = page
        .locator(`${root} ul[role='tree'] > li`)
        .filter({ has: page.locator(".pf-v6-c-tree-view__node-text", { hasText: "Cost management" }) });
      await expect(cost.locator(".pf-v6-c-tree-view__node-toggle")).toHaveCount(0);
    });
  });

  test.describe("Java source tab", () => {
    test("all example cards get a leading Java tab", async ({ page }) => {
      await page.goto("/components/tree-view");
      for (const ex of EXAMPLES) {
        const card = page.locator(`[data-rendered-href="/components/tree-view/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("Java tab opens Monaco with the builder code", async ({ page }) => {
      await page.goto("/components/tree-view");
      const card = page.locator('[data-rendered-href="/components/tree-view/multiselectable"]');
      await card.locator('button[aria-label*="Toggle Java"]').click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
      await expect(card.locator(".monaco-editor .view-lines")).toContainText("TreeView.builder()", {
        timeout: 10000,
      });
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/tree-view/source-java/with-checkboxes");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".checkboxes()");
    });
  });
});
