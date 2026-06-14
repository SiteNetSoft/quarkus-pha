import { test, expect } from "@playwright/test";

test.describe("Sortable table (HTMX)", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/sortable-table");
    // results load via hx-trigger="load"
    await expect(page.locator("#htmx-dt-results table tbody tr")).not.toHaveCount(0);
  });

  test("loads the first page from the server", async ({ page }) => {
    await expect(page.locator("#htmx-dt-results")).toContainText("Showing 1–5 of 13 users");
    await expect(page.locator("#htmx-dt-results table tbody tr")).toHaveCount(5);
  });

  test("filtering narrows the rows and the search box keeps focus", async ({ page }) => {
    const search = page.locator("#htmx-dt-search");
    await search.click();
    await search.pressSequentially("admin", { delay: 30 });
    await expect(page.locator("#htmx-dt-results")).toContainText("of 4 users");
    await expect(search).toBeFocused();
  });

  test("clicking a header sorts; clicking again reverses", async ({ page }) => {
    const nameHeader = page.locator("#htmx-dt-results th").first().locator("button");
    await nameHeader.click();
    await expect(page.locator("#htmx-dt-results table tbody tr td").first()).toHaveText("Alice Chen");
    await page.locator("#htmx-dt-results th").first().locator("button").click();
    await expect(page.locator("#htmx-dt-results table tbody tr td").first()).toHaveText("Maria Silva");
  });

  test("pagination moves through pages", async ({ page }) => {
    await page.locator("#htmx-dt-results button[aria-label='Go to next page']").click();
    await expect(page.locator("#htmx-dt-results")).toContainText("Page 2 of 3");
    await expect(page.locator("#htmx-dt-results")).toContainText("Showing 6–10 of 13");
  });
});
