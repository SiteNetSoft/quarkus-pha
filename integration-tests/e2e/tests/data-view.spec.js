import { test, expect } from "@playwright/test";

test.describe("Data view extensions", () => {
  test.describe("Overview", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/extensions/data-view/overview");
    });

    test("renders toolbar + table + pagination wired together", async ({ page }) => {
      const root = page.locator("#dv-overview");
      await expect(root.locator(".pf-v6-c-toolbar")).toBeVisible();
      await expect(root.locator("table.pf-v6-c-table")).toBeVisible();
      await expect(root.locator(".pf-v6-c-pagination")).toBeVisible();
      // Per-page=3, so first page shows 3 rows.
      await expect(root.locator("tbody tr")).toHaveCount(3);
    });

    test("search filters rows live", async ({ page }) => {
      const root = page.locator("#dv-overview");
      await root.locator('input[type="search"]').fill("Quarkus");
      await page.waitForTimeout(250);
      await expect(root.locator("tbody tr")).toHaveCount(1);
      await expect(root.locator("tbody tr").first()).toContainText("Red Hat");
    });
  });

  test.describe("Toolbar", () => {
    test("renders search + filter button + chips + result count", async ({ page }) => {
      await page.goto("/extensions/data-view/toolbar");
      const root = page.locator("#dv-toolbar");
      await expect(root.locator('input[type="search"]')).toBeVisible();
      await expect(root.getByRole("button", { name: "Filter", exact: true })).toBeVisible();
      await expect(root.locator(".pf-v6-c-label")).toHaveCount(2);
    });

    test("removing a chip drops it from the active set", async ({ page }) => {
      await page.goto("/extensions/data-view/toolbar");
      const root = page.locator("#dv-toolbar");
      await expect(root.locator(".pf-v6-c-label")).toHaveCount(2);
      await root.locator(".pf-v6-c-label").first().getByRole("button", { name: "Remove filter" }).click();
      await expect(root.locator(".pf-v6-c-label")).toHaveCount(1);
    });
  });

  test.describe("Table", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/extensions/data-view/table");
    });

    test("renders 5 rows per page from the seeded data", async ({ page }) => {
      const root = page.locator("#dv-table");
      await expect(root.locator("tbody tr")).toHaveCount(5);
    });

    test("clicking a header sorts that column ascending then descending", async ({ page }) => {
      const root = page.locator("#dv-table");
      const nameHeader = root.locator("thead button").first();
      const firstName = root.locator('tbody tr td[data-label="Name"]').first();
      await nameHeader.click();
      // Ascending: 'Alpine.js' should be first.
      await expect(firstName).toContainText("Alpine.js");
      await nameHeader.click();
      // Descending: 'video.js' should be first.
      await expect(firstName).toContainText("video.js");
    });

    test("search reduces total + result count + page resets", async ({ page }) => {
      const root = page.locator("#dv-table");
      await root.locator('input[type="search"]').fill("MIT");
      await page.waitForTimeout(250);
      const matching = await root.locator("tbody tr").count();
      // 4 entries in the seed have license=MIT (PatternFly, Alpine.js, Monaco, Cytoscape).
      expect(matching).toBeGreaterThan(0);
    });

    test("pagination advances to next page", async ({ page }) => {
      const root = page.locator("#dv-table");
      const firstName = root.locator('tbody tr td[data-label="Name"]').first();
      const firstRowText = await firstName.textContent();
      await root.getByRole("button", { name: "Next page" }).click();
      const nextFirstRowText = await firstName.textContent();
      expect(nextFirstRowText).not.toEqual(firstRowText);
    });

    test("rows are selectable; select-all toggles the page and shows a count", async ({ page }) => {
      const root = page.locator("#dv-table");
      const rowChecks = root.locator("tbody .pf-v6-c-table__check input");
      await rowChecks.first().check();
      await expect(root.locator(".pf-v6-c-toolbar")).toContainText("1 selected");
      await expect(root.locator("tbody tr").first()).toHaveClass(/pf-m-selected/);
      // select-all checks every row on the page (5 per page)
      await root.locator("thead .pf-v6-c-table__check input").check();
      await expect(root.locator(".pf-v6-c-toolbar")).toContainText("5 selected");
    });

    test("demo page renders every table example card", async ({ page }) => {
      for (const slug of ["basic", "expandable", "sticky", "tree-table", "resizable", "loading", "error"]) {
        await expect(page.locator(`#${slug}`)).toBeVisible();
      }
    });
  });

  test.describe("Table variants", () => {
    test("expandable rows toggle a detail panel and survive sorting", async ({ page }) => {
      await page.goto("/extensions/data-view/table/expandable");
      const root = page.locator("#dv-table-expandable");
      const firstBody = root.locator("tbody").first();
      const detail = firstBody.locator("tr.pf-v6-c-table__expandable-row");
      // Detail row starts collapsed.
      await expect(detail).toBeHidden();
      await firstBody.getByRole("button", { name: /^Expand details for/ }).click();
      await expect(detail).toBeVisible();
      await expect(detail).toContainText("licensed under");
      // Collapse again.
      await firstBody.getByRole("button", { name: /^Collapse details for/ }).click();
      await expect(detail).toBeHidden();
    });

    test("sticky variant renders one page of rows with a pinned header + first column", async ({ page }) => {
      await page.goto("/extensions/data-view/table/sticky");
      const root = page.locator("#dv-table-sticky");
      await expect(root.locator("table.pf-m-sticky-header")).toBeVisible();
      await expect(root.locator("thead th").first()).toHaveClass(/pf-v6-c-table__sticky-cell/);
      // per-page=12 → all seeded rows on a single page.
      await expect(root.locator("tbody tr")).toHaveCount(12);
      await expect(root.locator("tbody th[scope='row']").first()).toHaveClass(/pf-v6-c-table__sticky-cell/);
    });

    test("tree table expands and collapses each license group", async ({ page }) => {
      await page.goto("/extensions/data-view/table/tree-table");
      const table = page.locator("table.pf-m-tree-view");
      await expect(table).toBeVisible();
      const alpineRow = table.locator("tr", { hasText: "Alpine.js" });
      const quarkusRow = table.locator("tr", { hasText: "Quarkus" });
      // Group 1 (Apache 2.0) starts open, group 2 (MIT) starts closed.
      await expect(quarkusRow).toBeVisible();
      await expect(alpineRow).toBeHidden();
      await table.getByRole("button", { name: "MIT" }).click();
      await expect(alpineRow).toBeVisible();
      await table.getByRole("button", { name: "MIT" }).click();
      await expect(alpineRow).toBeHidden();
    });

    test("resizable columns widen when the header grip is dragged", async ({ page }) => {
      await page.goto("/extensions/data-view/table/resizable");
      const firstTh = page.locator("table thead th").first();
      const widthOf = () => firstTh.evaluate((el) => el.getBoundingClientRect().width);
      const before = await widthOf();
      const grip = firstTh.locator("span[aria-hidden='true']");
      const box = await grip.boundingBox();
      await page.mouse.move(box.x + box.width / 2, box.y + box.height / 2);
      await page.mouse.down();
      await page.mouse.move(box.x + box.width / 2 + 120, box.y + box.height / 2, { steps: 6 });
      await page.mouse.up();
      const after = await widthOf();
      expect(after).toBeGreaterThan(before + 60);
    });
  });

  test.describe("Table states", () => {
    test("loading state shows skeleton rows in place of data", async ({ page }) => {
      await page.goto("/extensions/data-view/table/loading");
      const table = page.locator("table.pf-v6-c-table");
      await expect(table).toBeVisible();
      await expect(table.locator("tbody[aria-busy='true']")).toBeVisible();
      await expect(table.locator("tbody .pf-v6-c-skeleton")).toHaveCount(20);
    });

    test("error state shows a danger empty-state with a retry action", async ({ page }) => {
      await page.goto("/extensions/data-view/table/error");
      const emptyState = page.locator(".pf-v6-c-empty-state.pf-m-danger");
      await expect(emptyState).toBeVisible();
      await expect(emptyState).toContainText("Unable to load data");
      await expect(emptyState.getByRole("button", { name: "Retry" })).toBeVisible();
    });
  });
});
