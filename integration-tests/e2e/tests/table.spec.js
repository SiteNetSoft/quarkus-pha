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
});
