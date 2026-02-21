import { test, expect } from "@playwright/test";

test.describe("Pagination", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/pagination");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#compact-heading")).toBeVisible();
    await expect(page.locator("#compact-heading")).toHaveText("Compact");
  });

  test.describe("Basic", () => {
    test("basic pagination has pf-v6-c-pagination class", async ({ page }) => {
      await expect(page.locator("#pn-basic")).toHaveClass(/pf-v6-c-pagination/);
    });

    test("basic pagination has a nav element", async ({ page }) => {
      await expect(page.locator("#pn-basic nav")).toBeVisible();
    });

    test("basic pagination has navigation buttons", async ({ page }) => {
      await expect(page.locator("#pn-basic button[aria-label='Go to previous page']")).toBeVisible();
      await expect(page.locator("#pn-basic button[aria-label='Go to next page']")).toBeVisible();
    });
  });

  test.describe("Compact", () => {
    test("compact pagination has pf-m-compact class", async ({ page }) => {
      await expect(page.locator("#pn-compact")).toHaveClass(/pf-m-compact/);
    });

    test("compact pagination has a nav element", async ({ page }) => {
      await expect(page.locator("#pn-compact nav")).toBeVisible();
    });
  });
});
