import { test, expect } from "@playwright/test";

test.describe("Calendar month", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/calendar-month");
  });

  test("page loads with the Basic section heading", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#basic")).toHaveText("Basic");
  });

  test.describe("Basic", () => {
    test("has calendar-month class and header with month and year", async ({ page }) => {
      const cal = page.locator("#cm-basic");
      await expect(cal).toHaveClass(/pf-v6-c-calendar-month/);
      await expect(cal.locator(".pf-v6-c-calendar-month__header-month")).toHaveText("May");
      await expect(cal.locator("#cm-basic-year")).toHaveValue("2026");
    });

    test("has 7 day-of-week headers", async ({ page }) => {
      const days = page.locator("#cm-basic .pf-v6-c-calendar-month__day");
      await expect(days).toHaveCount(7);
    });

    test("has 6 date rows with 7 cells each", async ({ page }) => {
      const rows = page.locator("#cm-basic .pf-v6-c-calendar-month__dates-row");
      await expect(rows).toHaveCount(6);
      const cells = page.locator("#cm-basic .pf-v6-c-calendar-month__dates-cell");
      await expect(cells).toHaveCount(42);
    });

    test("current date has pf-m-current modifier", async ({ page }) => {
      const currentCell = page.locator("#cm-basic .pf-v6-c-calendar-month__dates-cell.pf-m-current");
      await expect(currentCell).toHaveCount(1);
    });

    test("selected date has pf-m-selected modifier", async ({ page }) => {
      const selectedCell = page.locator("#cm-basic .pf-v6-c-calendar-month__dates-cell.pf-m-selected");
      await expect(selectedCell).toHaveCount(1);
    });

    test("adjacent month dates have pf-m-adjacent-month modifier", async ({ page }) => {
      const adjacentCells = page.locator("#cm-basic .pf-v6-c-calendar-month__dates-cell.pf-m-adjacent-month");
      await expect(adjacentCells).toHaveCount(11);
    });
  });
});
