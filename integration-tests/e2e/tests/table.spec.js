import { test, expect } from "@playwright/test";

test.describe("Table", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/table");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Table");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#compact")).toBeVisible();
    await expect(page.locator("#striped")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("basic table exists with correct class", async ({ page }) => {
      await expect(page.locator("#tbl-basic")).toHaveClass(/pf-v6-c-table/);
    });

    test("has thead and tbody", async ({ page }) => {
      await expect(page.locator("#tbl-basic thead")).toBeVisible();
      await expect(page.locator("#tbl-basic tbody")).toBeVisible();
    });

    test("has 3 data rows", async ({ page }) => {
      await expect(page.locator("#tbl-basic tbody tr")).toHaveCount(3);
    });

    test("has 4 column headers", async ({ page }) => {
      await expect(page.locator("#tbl-basic thead th")).toHaveCount(4);
    });
  });

  test.describe("Compact", () => {
    test("has pf-m-compact modifier", async ({ page }) => {
      await expect(page.locator("#tbl-compact")).toHaveClass(/pf-m-compact/);
    });
  });

  test.describe("Striped", () => {
    test("has pf-m-striped modifier", async ({ page }) => {
      await expect(page.locator("#tbl-striped")).toHaveClass(/pf-m-striped/);
    });
  });

  test.describe("Selectable (checkbox)", () => {
    test("has a check column with select-all and per-row checkboxes", async ({ page }) => {
      await expect(page.locator('#tbl-select-check thead input[type="checkbox"]')).toHaveCount(1);
      await expect(page.locator('#tbl-select-check tbody .pf-v6-c-table__check input[type="checkbox"]')).toHaveCount(3);
    });
  });

  test.describe("Selectable (radio)", () => {
    test("has one radio group across the rows", async ({ page }) => {
      await expect(page.locator('#tbl-select-radio tbody input[type="radio"]')).toHaveCount(3);
    });
  });

  test.describe("Actions", () => {
    test("has an action cell with a kebab that opens a menu", async ({ page }) => {
      const kebab = page.locator("#tbl-actions .pf-v6-c-table__action .pf-v6-c-menu-toggle").first();
      await expect(kebab).toBeVisible();
      await kebab.click();
      await expect(page.locator("#tbl-actions .pf-v6-c-menu").first()).toBeVisible();
    });
  });

  test.describe("Empty state", () => {
    test("renders an empty state in a spanning cell", async ({ page }) => {
      await expect(page.locator("#tbl-empty tbody .pf-v6-c-empty-state")).toBeVisible();
      await expect(page.locator("#tbl-empty tbody td[colspan]")).toHaveCount(1);
    });
  });

  test.describe("Width modifiers", () => {
    test("applies width and text modifiers on headers", async ({ page }) => {
      await expect(page.locator("#tbl-width thead th.pf-m-width-20")).toHaveCount(1);
      await expect(page.locator("#tbl-width thead th.pf-m-truncate")).toHaveCount(1);
    });
  });

  test.describe("Favoritable", () => {
    test("clicking a star toggles pf-m-favorited", async ({ page }) => {
      const star = page.locator("#tbl-favorite tbody tr").nth(1).locator(".pf-v6-c-table__favorite button");
      await expect(star).not.toHaveClass(/pf-m-favorited/);
      await star.click();
      await expect(star).toHaveClass(/pf-m-favorited/);
    });
  });

  test.describe("Clickable rows", () => {
    test("clicking a row selects it", async ({ page }) => {
      const row = page.locator("#tbl-clickable tbody tr").first();
      await expect(row).not.toHaveClass(/pf-m-selected/);
      await row.click();
      await expect(row).toHaveClass(/pf-m-selected/);
    });
  });

  test.describe("Nested column headers", () => {
    test("uses pf-m-nested-column-header with subhead cells", async ({ page }) => {
      await expect(page.locator("#tbl-clickable")).toBeVisible();
      const t = page.locator("table.pf-m-nested-column-header");
      await expect(t).toBeVisible();
      await expect(t.locator("thead th.pf-v6-c-table__subhead")).toHaveCount(4);
    });
  });
});
