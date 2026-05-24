import { test, expect } from "@playwright/test";

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
      await expect(wrapper).toHaveAttribute("x-data", "phaTreeView()");
    });

    test("renders a single-select tree (aria-multiselectable=false)", async ({ page }) => {
      await expect(page.locator(`${root} ul[role='tree']`).first()).toHaveAttribute(
        "aria-multiselectable",
        "false"
      );
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
        `${root} .pf-v6-c-tree-view__list-item.pf-m-expanded .pf-v6-c-tree-view__list .pf-v6-c-tree-view__list-item`
      );
      await expect(nested.first()).toBeAttached();
    });
  });

  test.describe("Multiselectable", () => {
    const root = "#ws-core-c-tree-view-multiselectable";

    test("renders a multi-select tree (aria-multiselectable=true)", async ({ page }) => {
      await expect(page.locator(`${root} ul[role='tree']`).first()).toHaveAttribute(
        "aria-multiselectable",
        "true"
      );
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
      await expect(
        page.locator(`${root} .pf-v6-c-tree-view__node-check`).first()
      ).toBeAttached();
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
});
