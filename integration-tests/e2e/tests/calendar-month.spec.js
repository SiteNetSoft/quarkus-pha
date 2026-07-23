import { test, expect } from "@playwright/test";

const MONTHS = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

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
      await expect(cal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("May");
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

    test("selected date has pf-m-selected modifier", async ({ page }) => {
      const selectedCell = page.locator("#cm-basic .pf-v6-c-calendar-month__dates-cell.pf-m-selected");
      await expect(selectedCell).toHaveCount(1);
      await expect(selectedCell.locator("button")).toHaveAttribute("aria-label", "20 May 2026");
    });

    test("adjacent month dates have pf-m-adjacent-month modifier", async ({ page }) => {
      const adjacentCells = page.locator("#cm-basic .pf-v6-c-calendar-month__dates-cell.pf-m-adjacent-month");
      await expect(adjacentCells).toHaveCount(11);
    });
  });

  test.describe("Navigation (server-driven, PF CalendarMonth behaviors)", () => {
    test("prev/next month arrows re-render the grid", async ({ page }) => {
      const cal = page.locator("#cm-basic");
      await cal.locator('button[aria-label="Next month"]').click();
      await expect(cal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("June");
      await expect(cal.locator('button[aria-label="15 June 2026"]')).toHaveCount(1);
      await cal.locator('button[aria-label="Previous month"]').click();
      await expect(cal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("May");
    });

    test("month select menu opens and picks a month", async ({ page }) => {
      const cal = page.locator("#cm-basic");
      await cal.locator("#cm-basic-month-select").click();
      const menu = cal.locator(".pf-v6-c-menu");
      await expect(menu).toBeVisible();
      await expect(menu.locator(".pf-v6-c-menu__list-item")).toHaveCount(12);
      await menu.locator(".pf-v6-c-menu__item", { hasText: "March" }).click();
      await expect(cal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("March");
      await expect(cal.locator("#cm-basic-year")).toHaveValue("2026");
    });

    test("year input changes the displayed year", async ({ page }) => {
      const cal = page.locator("#cm-basic");
      await cal.locator("#cm-basic-year").fill("2027");
      await cal.locator("#cm-basic-year").blur();
      await expect(cal.locator("#cm-basic-year")).toHaveValue("2027");
      await expect(cal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("May");
      await expect(cal.locator('button[aria-label="20 May 2027"]')).toHaveCount(1);
      // the selected date (20 May 2026) is not visible in the 2027 grid
      await expect(cal.locator("td.pf-m-selected")).toHaveCount(0);
    });

    test("clicking a date selects it", async ({ page }) => {
      const cal = page.locator("#cm-basic");
      await cal.locator('button[aria-label="5 May 2026"]').click();
      const selected = cal.locator("td.pf-m-selected");
      await expect(selected).toHaveCount(1);
      await expect(selected.locator("button")).toHaveAttribute("aria-label", "5 May 2026");
    });

    test("clicking an adjacent-month date selects it and jumps to its month", async ({ page }) => {
      const cal = page.locator("#cm-basic");
      await cal.locator('button[aria-label="1 June 2026"]').click();
      await expect(cal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("June");
      const selected = cal.locator("td.pf-m-selected");
      await expect(selected).toHaveCount(1);
      await expect(selected.locator("button")).toHaveAttribute("aria-label", "1 June 2026");
    });

    test("current day ring appears in the real today's month", async ({ page }) => {
      const now = new Date();
      const cal = page.locator("#cm-basic");
      await cal.locator("#cm-basic-year").fill(String(now.getFullYear()));
      await cal.locator("#cm-basic-year").blur();
      await cal.locator("#cm-basic-month-select").click();
      await cal.locator(".pf-v6-c-menu__item", { hasText: MONTHS[now.getMonth()] }).click();
      await expect(cal.locator(".pf-v6-c-menu-toggle__text")).toHaveText(MONTHS[now.getMonth()]);
      const current = cal.locator("td.pf-m-current");
      await expect(current).toHaveCount(1);
      await expect(current.locator("button")).toHaveAttribute(
        "aria-label",
        `${now.getDate()} ${MONTHS[now.getMonth()]} ${now.getFullYear()}`,
      );
    });

    test("arrow keys move the roving focus (PF keyboard nav)", async ({ page }) => {
      const cal = page.locator("#cm-basic");
      await cal.locator('button[aria-label="15 May 2026"]').focus();
      await page.keyboard.press("ArrowRight");
      await expect(cal.locator('button[aria-label="16 May 2026"]')).toBeFocused();
      await page.keyboard.press("ArrowDown");
      await expect(cal.locator('button[aria-label="23 May 2026"]')).toBeFocused();
      await page.keyboard.press("ArrowLeft");
      await expect(cal.locator('button[aria-label="22 May 2026"]')).toBeFocused();
      await page.keyboard.press("ArrowUp");
      await expect(cal.locator('button[aria-label="15 May 2026"]')).toBeFocused();
    });
  });

  test.describe("Date range", () => {
    test("marks start, in-range, and end cells (endpoints carry in-range, PF shape)", async ({ page }) => {
      await expect(page.locator("#cm-range .pf-m-start-range")).toHaveCount(1);
      // 12 (start) .. 20 (end) inclusive — PF puts pf-m-in-range on the endpoints too
      await expect(page.locator("#cm-range td.pf-m-in-range")).toHaveCount(9);
      await expect(page.locator("#cm-range .pf-m-end-range")).toHaveCount(1);
    });

    test("dates before the range start are disabled (PF shape)", async ({ page }) => {
      // 5 adjacent April days + May 1-11
      await expect(page.locator("#cm-range td.pf-m-disabled")).toHaveCount(16);
      await expect(page.locator("#cm-range td.pf-m-disabled button[disabled]")).toHaveCount(16);
    });

    test("hovering past the range end moves the effective end (PF CalendarMonth semantics)", async ({ page }) => {
      const inRange = page.locator("#cm-range td.pf-m-in-range");
      const endRange = page.locator("#cm-range td.pf-m-end-range");
      // at rest: committed band 12..20, end-range + hover ring sit on the 20th
      await expect(inRange).toHaveCount(9);
      await expect(endRange.locator("button")).toHaveAttribute("aria-label", "20 May 2026");
      await expect(page.locator("#cm-range td button.pf-m-hover")).toHaveAttribute("aria-label", "20 May 2026");

      // hover the 23rd: band extends inclusively, end-range MOVES to the hovered day,
      // the committed 20th stays selected but is no longer the range end
      await page.locator('#cm-range button[aria-label="23 May 2026"]').hover();
      await expect(inRange).toHaveCount(12); // 12..23
      await expect(endRange).toHaveCount(1);
      await expect(endRange.locator("button")).toHaveAttribute("aria-label", "23 May 2026");
      await expect(page.locator("#cm-range td.pf-m-selected")).toHaveCount(2); // 12 and 20 stay selected

      // adjacent-month days participate: hovering June 3 extends across the boundary
      await page.locator('#cm-range button[aria-label="3 June 2026"]').hover();
      await expect(inRange).toHaveCount(23); // May 12..31 (20) + June 1..3 (3)
      await expect(endRange.locator("button")).toHaveAttribute("aria-label", "3 June 2026");

      // hovering back inside the committed range collapses the preview immediately
      await page.locator('#cm-range button[aria-label="15 May 2026"]').hover();
      await expect(inRange).toHaveCount(9);
      await expect(endRange.locator("button")).toHaveAttribute("aria-label", "20 May 2026");

      // leaving the table clears hover state entirely (PF table onMouseLeave)
      await page.locator('#cm-range button[aria-label="27 May 2026"]').hover();
      await expect(inRange).toHaveCount(16);
      await page.locator("#cm-range .pf-v6-c-calendar-month__header-month").hover();
      await expect(inRange).toHaveCount(9);
    });

    test("clicking a date commits a new range end, persisted across navigation", async ({ page }) => {
      const cal = page.locator("#cm-range");
      await cal.locator('button[aria-label="27 May 2026"]').click();
      await expect(cal.locator("td.pf-m-in-range")).toHaveCount(16); // 12..27 committed
      await expect(cal.locator("td.pf-m-end-range button")).toHaveAttribute("aria-label", "27 May 2026");

      // navigate to June: no band there, nothing disabled (all after the start)
      await cal.locator('button[aria-label="Next month"]').click();
      await expect(cal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("June");
      await expect(cal.locator("td.pf-m-in-range")).toHaveCount(0);

      // back to May: the committed 12..27 band survived the round-trip
      await cal.locator('button[aria-label="Previous month"]').click();
      await expect(cal.locator("td.pf-m-in-range")).toHaveCount(16);
      await expect(cal.locator("td.pf-m-end-range button")).toHaveAttribute("aria-label", "27 May 2026");
    });

    test("date buttons carry full-date aria-labels", async ({ page }) => {
      await expect(page.locator('#cm-basic button[aria-label="20 May 2026"]')).toHaveCount(1);
      await expect(page.locator('#cm-range button[aria-label="26 April 2026"]')).toHaveCount(1);
    });

    test("calendar sizes naturally, not squeezed (regression: max-width cap)", async ({ page }) => {
      const box = await page.locator("#cm-basic").boundingBox();
      // ~355px, exactly what patternfly.org renders: the header row (nav arrows +
      // the PF-spec 140px month toggle + year input) drives the inline-flex width.
      expect(box.width).toBeGreaterThan(340);
      expect(box.width).toBeLessThan(370);
    });

    test("/components/calendar-month/date-range returns 200", async ({ page }) => {
      const res = await page.goto("/components/calendar-month/date-range");
      expect(res.status()).toBe(200);
    });
  });
});
