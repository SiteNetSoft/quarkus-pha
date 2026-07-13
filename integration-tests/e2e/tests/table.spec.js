import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "plain",
  "compact",
  "compact-expandable",
  "striped",
  "striped-expandable",
  "striped-multiple-tbody",
  "striped-tr",
  "borderless",
  "borderless-compact",
  "borderless-expandable",
  "borderless-compound-expandable",
  "sortable",
  "expandable",
  "animated-expandable",
  "expandable-set-width",
  "expandable-nested-table",
  "selectable-checkbox",
  "selectable-radio",
  "actions",
  "empty-state",
  "width",
  "favoritable",
  "clickable-rows",
  "clickable-expandable",
  "nested-column-headers",
  "editable-rows",
  "compound-expandable",
  "compound-expandable-nested-table",
  "animated-compound-expandable",
  "sticky",
  "tree-table",
  "draggable-rows",
  "footer",
];

const bg = (locator) => locator.evaluate((el) => getComputedStyle(el).backgroundColor);

test.describe("Table", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/table");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Table");
  });

  test("all example sections are present", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
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

  test.describe("Plain", () => {
    test("has pf-m-plain modifier and a caption", async ({ page }) => {
      await expect(page.locator("#tbl-plain")).toHaveClass(/pf-m-plain/);
      await expect(page.locator("#tbl-plain caption.pf-v6-c-table__caption")).toContainText("Q2 releases");
    });
  });

  test.describe("Compact", () => {
    test("has pf-m-compact modifier", async ({ page }) => {
      await expect(page.locator("#tbl-compact")).toHaveClass(/pf-m-compact/);
    });
  });

  test.describe("Compact expandable", () => {
    test("is compact and expandable; toggle reveals the detail row", async ({ page }) => {
      const t = page.locator("#tbl-compact-expandable");
      await expect(t).toHaveClass(/pf-m-compact/);
      await expect(t).toHaveClass(/pf-m-expandable/);
      const detail = t.locator(".pf-v6-c-table__expandable-row").first();
      await expect(detail).toBeHidden();
      await t.locator(".pf-v6-c-table__toggle button").first().click();
      await expect(detail).toBeVisible();
      await expect(detail).toContainText("312 tests");
    });
  });

  test.describe("Striped", () => {
    test("has pf-m-striped modifier", async ({ page }) => {
      await expect(page.locator("#tbl-striped")).toHaveClass(/pf-m-striped/);
    });

    test("odd rows actually get the stripe background", async ({ page }) => {
      const rows = page.locator("#tbl-striped tbody tr");
      expect(await bg(rows.nth(0))).not.toBe(await bg(rows.nth(1)));
    });
  });

  test.describe("Striped expandable", () => {
    test("stripes whole odd tbodies", async ({ page }) => {
      const t = page.locator("#tbl-striped-expandable");
      await expect(t).toHaveClass(/pf-m-striped/);
      await expect(t).toHaveClass(/pf-m-expandable/);
      const firstRow = t.locator("tbody").nth(0).locator("tr").first();
      const secondRow = t.locator("tbody").nth(1).locator("tr").first();
      expect(await bg(firstRow)).not.toBe(await bg(secondRow));
    });

    test("rows still expand", async ({ page }) => {
      const t = page.locator("#tbl-striped-expandable");
      const detail = t.locator(".pf-v6-c-table__expandable-row").first();
      await expect(detail).toBeHidden();
      await t.locator(".pf-v6-c-table__toggle button").first().click();
      await expect(detail).toBeVisible();
    });
  });

  test.describe("Striped multiple tbody", () => {
    test("first tbody stripes odd rows, second stripes even rows", async ({ page }) => {
      const t = page.locator("#tbl-striped-tbody");
      const odd = t.locator("tbody.pf-m-striped");
      const even = t.locator("tbody.pf-m-striped-even");
      await expect(odd).toHaveCount(1);
      await expect(even).toHaveCount(1);
      expect(await bg(odd.locator("tr").nth(0))).not.toBe(await bg(odd.locator("tr").nth(1)));
      expect(await bg(even.locator("tr").nth(1))).not.toBe(await bg(even.locator("tr").nth(0)));
    });
  });

  test.describe("Striped tr", () => {
    test("only rows with pf-m-striped are shaded", async ({ page }) => {
      const t = page.locator("#tbl-striped-tr");
      await expect(t.locator("tbody tr.pf-m-striped")).toHaveCount(2);
      const striped = t.locator("tbody tr").nth(0);
      const plainRow = t.locator("tbody tr").nth(1);
      expect(await bg(striped)).not.toBe(await bg(plainRow));
    });
  });

  test.describe("Borderless", () => {
    test("has pf-m-no-border-rows modifier", async ({ page }) => {
      await expect(page.locator("#tbl-borderless")).toHaveClass(/pf-m-no-border-rows/);
    });
  });

  test.describe("Borderless compact", () => {
    test("combines pf-m-no-border-rows and pf-m-compact", async ({ page }) => {
      const t = page.locator("#tbl-borderless-compact");
      await expect(t).toHaveClass(/pf-m-no-border-rows/);
      await expect(t).toHaveClass(/pf-m-compact/);
      await expect(t.locator("tbody tr")).toHaveCount(4);
    });
  });

  test.describe("Borderless expandable", () => {
    test("expands without row borders", async ({ page }) => {
      const t = page.locator("#tbl-borderless-expandable");
      await expect(t).toHaveClass(/pf-m-no-border-rows/);
      const detail = t.locator(".pf-v6-c-table__expandable-row").first();
      await expect(detail).toBeHidden();
      await t.locator(".pf-v6-c-table__toggle button").first().click();
      await expect(detail).toBeVisible();
    });
  });

  test.describe("Borderless compound expandable", () => {
    test("cell toggles expand their own detail rows", async ({ page }) => {
      const t = page.locator("#tbl-borderless-compound");
      await expect(t).toHaveClass(/pf-m-no-border-rows/);
      const tbody = t.locator("tbody").first();
      const imagesDetail = tbody.locator(".pf-v6-c-table__expandable-row").nth(0);
      const tagsDetail = tbody.locator(".pf-v6-c-table__expandable-row").nth(1);
      await expect(imagesDetail).toBeHidden();
      await tbody.locator(".pf-v6-c-table__compound-expansion-toggle button").nth(0).click();
      await expect(imagesDetail).toBeVisible();
      await tbody.locator(".pf-v6-c-table__compound-expansion-toggle button").nth(1).click();
      await expect(imagesDetail).toBeHidden();
      await expect(tagsDetail).toBeVisible();
    });
  });

  test.describe("Expandable", () => {
    test("toggle expands and collapses the detail row", async ({ page }) => {
      const tbody = page.locator("#tbl-expandable tbody").first();
      const detail = tbody.locator(".pf-v6-c-table__expandable-row");
      await expect(detail).toBeHidden();
      await tbody.locator(".pf-v6-c-table__toggle button").click();
      await expect(detail).toBeVisible();
      await expect(tbody).toHaveClass(/pf-m-expanded/);
    });
  });

  test.describe("Animated expandable", () => {
    test("table has pf-m-animate-expand and the first row starts expanded", async ({ page }) => {
      const t = page.locator("#tbl-animated-expandable");
      await expect(t).toHaveClass(/pf-m-animate-expand/);
      await expect(t.locator("tbody").nth(0)).toHaveClass(/pf-m-expanded/);
      await expect(t.locator(".pf-v6-c-table__expandable-row").nth(0)).toHaveClass(/pf-m-expanded/);
    });

    test("toggling collapses via class change, keeping the row in the DOM", async ({ page }) => {
      const t = page.locator("#tbl-animated-expandable");
      const detail = t.locator(".pf-v6-c-table__expandable-row").nth(0);
      await t.locator(".pf-v6-c-table__toggle button").nth(0).click();
      await expect(detail).not.toHaveClass(/pf-m-expanded/);
      await expect(detail).toBeAttached();
      await expect(detail).toBeHidden();
    });
  });

  test.describe("Expandable with set width columns", () => {
    test("width modifiers are on the header cells and rows expand", async ({ page }) => {
      const t = page.locator("#tbl-expandable-set-width");
      await expect(t.locator("thead th.pf-m-width-30")).toHaveCount(1);
      await expect(t.locator("thead th.pf-m-width-20")).toHaveCount(1);
      const detail = t.locator(".pf-v6-c-table__expandable-row").first();
      await t.locator(".pf-v6-c-table__toggle button").first().click();
      await expect(detail).toBeVisible();
    });
  });

  test.describe("Expandable with nested table", () => {
    test("first row starts open with a compact borderless inner table", async ({ page }) => {
      const detail = page.locator("#tbl-expandable-nested .pf-v6-c-table__expandable-row").first();
      await expect(detail).toBeVisible();
      const inner = detail.locator("table.pf-v6-c-table");
      await expect(inner).toHaveClass(/pf-m-compact/);
      await expect(inner).toHaveClass(/pf-m-no-border-rows/);
      await expect(inner.locator("tbody tr")).toHaveCount(3);
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

  test.describe("Clickable and expandable", () => {
    test("clicking a row selects its tbody", async ({ page }) => {
      const t = page.locator("#tbl-clickable-expandable");
      await expect(t.locator("tbody").nth(1)).toHaveClass(/pf-m-selected/);
      await t.locator("tbody").nth(0).locator("td[data-label='Cluster']").click();
      await expect(t.locator("tbody").nth(0)).toHaveClass(/pf-m-selected/);
      await expect(t.locator("tbody").nth(1)).not.toHaveClass(/pf-m-selected/);
    });

    test("the toggle expands without stealing the selection", async ({ page }) => {
      const t = page.locator("#tbl-clickable-expandable");
      const prod = t.locator("tbody").nth(0);
      const detail = prod.locator(".pf-v6-c-table__expandable-row");
      await prod.locator(".pf-v6-c-table__toggle button").click();
      await expect(detail).toBeVisible();
      await expect(prod).not.toHaveClass(/pf-m-selected/);
      await expect(t.locator("tbody").nth(1)).toHaveClass(/pf-m-selected/);
    });
  });

  test.describe("Nested column headers", () => {
    test("uses pf-m-nested-column-header with subhead cells", async ({ page }) => {
      const t = page.locator("table.pf-m-nested-column-header");
      await expect(t).toBeVisible();
      await expect(t.locator("thead th.pf-v6-c-table__subhead")).toHaveCount(4);
    });
  });

  test.describe("Editable rows", () => {
    test("Edit reveals inputs; Save commits the draft", async ({ page }) => {
      const row = page.locator("#tbl-editable tbody tr").first();
      const nameInput = row.locator('input[aria-label="Name"]');
      await expect(nameInput).toBeHidden();
      await row.getByRole("button", { name: "Edit" }).click();
      await expect(nameInput).toBeVisible();
      await nameInput.fill("Johnny Doe");
      await row.getByRole("button", { name: "Save edits" }).click();
      await expect(row).toContainText("Johnny Doe");
    });
  });

  test.describe("Compound expandable", () => {
    test("clicking a cell toggle expands its detail row", async ({ page }) => {
      const detail = page.locator("#tbl-compound .pf-v6-c-table__expandable-row");
      await expect(detail).toBeHidden();
      await page.locator("#tbl-compound .pf-v6-c-table__compound-expansion-toggle button").first().click();
      await expect(detail).toBeVisible();
      await expect(detail).toContainText("branches");
    });
  });

  test.describe("Compound expandable with nested table", () => {
    test("branches cell starts open showing the nested table", async ({ page }) => {
      const tbody = page.locator("#tbl-compound-nested tbody").first();
      const branchesDetail = tbody.locator(".pf-v6-c-table__expandable-row").nth(0);
      await expect(branchesDetail).toBeVisible();
      await expect(branchesDetail.locator("table.pf-v6-c-table tbody tr")).toHaveCount(3);
    });

    test("switching to the pull requests cell swaps the detail row", async ({ page }) => {
      const tbody = page.locator("#tbl-compound-nested tbody").first();
      await tbody.locator(".pf-v6-c-table__compound-expansion-toggle button").nth(1).click();
      await expect(tbody.locator(".pf-v6-c-table__expandable-row").nth(0)).toBeHidden();
      await expect(tbody.locator(".pf-v6-c-table__expandable-row").nth(1)).toContainText("under review");
    });
  });

  test.describe("Animated compound expandable", () => {
    test("has pf-m-animate-expand and control rows; members starts open", async ({ page }) => {
      const t = page.locator("#tbl-animated-compound");
      await expect(t).toHaveClass(/pf-m-animate-expand/);
      const tbody = t.locator("tbody").first();
      await expect(tbody.locator("tr.pf-v6-c-table__control-row")).toHaveCount(1);
      await expect(tbody.locator(".pf-v6-c-table__expandable-row").nth(0)).toHaveClass(/pf-m-expanded/);
    });

    test("switching cells sets pf-m-no-animate-expand on the control row", async ({ page }) => {
      const tbody = page.locator("#tbl-animated-compound tbody").first();
      await tbody.locator(".pf-v6-c-table__compound-expansion-toggle button").nth(1).click();
      await expect(tbody.locator("tr.pf-v6-c-table__control-row")).toHaveClass(/pf-m-no-animate-expand/);
      await expect(tbody.locator(".pf-v6-c-table__expandable-row").nth(1)).toHaveClass(/pf-m-expanded/);
      await expect(tbody.locator(".pf-v6-c-table__expandable-row").nth(0)).not.toHaveClass(/pf-m-expanded/);
    });
  });

  test.describe("Sticky", () => {
    test("has a sticky header table with a sticky first column", async ({ page }) => {
      const t = page.locator("table.pf-m-sticky-header");
      await expect(t).toBeVisible();
      await expect(t.locator("thead th.pf-v6-c-table__sticky-cell.pf-m-left")).toHaveCount(1);
      await expect(t.locator("tbody th.pf-v6-c-table__sticky-cell.pf-m-left").first()).toBeVisible();
    });
  });

  test.describe("Tree table", () => {
    test("is a treegrid; toggling a parent reveals its children", async ({ page }) => {
      const t = page.locator("table.pf-m-tree-view");
      await expect(t).toHaveAttribute("role", "treegrid");
      const child = t.locator('tbody tr[aria-level="2"]', { hasText: "db-primary" });
      await expect(child).toBeHidden();
      await t.locator('tbody tr[aria-level="1"]', { hasText: "Databases" }).locator("button").click();
      await expect(child).toBeVisible();
    });
  });

  test.describe("Draggable rows", () => {
    test("rows are draggable with a grip cell", async ({ page }) => {
      const rows = page.locator("#tbl-draggable tbody tr");
      await expect(rows).toHaveCount(3);
      await expect(rows.first()).toHaveAttribute("draggable", "true");
      await expect(page.locator("#tbl-draggable tbody td.pf-v6-c-table__draggable")).toHaveCount(3);
    });
  });

  test.describe("Footer", () => {
    test("tfoot carries the PF class and summarises the body", async ({ page }) => {
      const tfoot = page.locator("#tbl-footer tfoot.pf-v6-c-table__tfoot");
      await expect(tfoot).toBeAttached();
      await expect(tfoot.locator('th[scope="row"]')).toContainText("Total users");
      await expect(tfoot.locator('td[colspan="2"]')).toContainText("3");
    });
  });

  test("all example standalone routes render", async ({ page }) => {
    for (const ex of EXAMPLES) {
      const res = await page.goto(`/components/table/${ex}`);
      expect(res.status()).toBe(200);
      await expect(page.locator("table.pf-v6-c-table").first()).toBeAttached();
    }
  });
});
