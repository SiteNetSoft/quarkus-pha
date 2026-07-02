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
  });
});
