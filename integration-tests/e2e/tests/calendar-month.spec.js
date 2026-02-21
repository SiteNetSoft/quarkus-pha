import { test, expect } from "@playwright/test";

test.describe("Calendar month", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/calendar-month");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#selected-heading")).toBeVisible();
    await expect(page.locator("#range-heading")).toBeVisible();
  });

  test.describe("Date selected", () => {
    test("has calendar-month class and header with month and year", async ({ page }) => {
      const cal = page.locator("#cal-selected");
      await expect(cal).toHaveClass(/pf-v6-c-calendar-month/);
      await expect(cal.locator(".pf-v6-c-calendar-month__header-month")).toHaveText("October");
      await expect(cal.locator("#cal-selected-year")).toHaveValue("2020");
    });

    test("has 7 day-of-week headers", async ({ page }) => {
      const days = page.locator("#cal-selected .pf-v6-c-calendar-month__day");
      await expect(days).toHaveCount(7);
    });

    test("has 5 date rows with 7 cells each", async ({ page }) => {
      const rows = page.locator("#cal-selected .pf-v6-c-calendar-month__dates-row");
      await expect(rows).toHaveCount(5);
      const cells = page.locator("#cal-selected .pf-v6-c-calendar-month__dates-cell");
      await expect(cells).toHaveCount(35);
    });

    test("current date has pf-m-current modifier", async ({ page }) => {
      const currentCell = page.locator("#cal-selected .pf-v6-c-calendar-month__dates-cell.pf-m-current");
      await expect(currentCell).toHaveCount(1);
    });

    test("selected date has pf-m-selected modifier", async ({ page }) => {
      const selectedCell = page.locator("#cal-selected .pf-v6-c-calendar-month__dates-cell.pf-m-selected");
      await expect(selectedCell).toHaveCount(1);
    });

    test("adjacent month dates have pf-m-adjacent-month modifier", async ({ page }) => {
      const adjacentCells = page.locator("#cal-selected .pf-v6-c-calendar-month__dates-cell.pf-m-adjacent-month");
      await expect(adjacentCells).toHaveCount(4);
    });
  });

  test.describe("Date range", () => {
    test("start and end range cells have correct modifiers", async ({ page }) => {
      const startCell = page.locator("#cal-range .pf-v6-c-calendar-month__dates-cell.pf-m-start-range");
      await expect(startCell).toHaveCount(1);
      const endCell = page.locator("#cal-range .pf-v6-c-calendar-month__dates-cell.pf-m-end-range");
      await expect(endCell).toHaveCount(1);
    });

    test("in-range cells have pf-m-in-range modifier", async ({ page }) => {
      const inRangeCells = page.locator("#cal-range .pf-v6-c-calendar-month__dates-cell.pf-m-in-range");
      const count = await inRangeCells.count();
      expect(count).toBeGreaterThan(2);
    });
  });
});
