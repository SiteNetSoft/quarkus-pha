import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "top",
  "sticky",
  "indeterminate",
  "bottom",
  "bottom-plain",
  "bottom-sticky",
  "sticky-base-stuck",
  "bottom-sticky-base-stuck",
  "bottom-static",
  "disabled",
  "compact",
  "display-summary",
  "display-full",
  "display-responsive",
  "compact-display-full",
  "inset",
  "no-items",
  "one-page",
  "offset",
  "dynamic-sticky",
];

test.describe("Pagination", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/pagination");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of [...EXAMPLES, "documentation", "props-pagination", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Top (live)", () => {
    test("renders total items, per-page menu toggle, and four nav controls", async ({ page }) => {
      await expect(page.locator("#pg-top .pf-v6-c-pagination__total-items")).toContainText("1 - 10 of 523");
      await expect(page.locator("#pg-top .pf-v6-c-pagination__page-menu .pf-v6-c-menu-toggle")).toBeVisible();
      await expect(page.locator("#pg-top .pf-v6-c-pagination__nav-control")).toHaveCount(4);
    });

    test("next/last advance the page and controls disable at either end", async ({ page }) => {
      const pg = page.locator("#pg-top");
      const first = pg.locator(".pf-m-first button");
      const next = pg.locator(".pf-m-next button");
      const input = pg.locator(".pf-v6-c-pagination__nav-page-select input");
      await expect(first).toBeDisabled();
      await next.click();
      await expect(input).toHaveValue("2");
      await expect(pg.locator(".pf-v6-c-pagination__total-items")).toContainText("11 - 20");
      await expect(first).toBeEnabled();
      await pg.locator(".pf-m-last button").click();
      await expect(input).toHaveValue("53");
      await expect(next).toBeDisabled();
    });

    test("the per-page menu changes the page size live", async ({ page }) => {
      const pg = page.locator("#pg-top");
      await pg.locator(".pf-v6-c-pagination__page-menu .pf-v6-c-menu-toggle").click();
      await pg.locator(".pf-v6-c-menu__item", { hasText: "20 per page" }).click();
      await expect(pg.locator(".pf-v6-c-pagination__total-items")).toContainText("1 - 20 of 523");
      await expect(pg.locator(".pf-v6-c-pagination__nav-page-select span[aria-hidden]")).toContainText("of 27");
      await expect(pg.locator(".pf-v6-c-menu")).toBeHidden();
    });

    test("typing in the page input jumps to that page", async ({ page }) => {
      const pg = page.locator("#pg-top");
      await pg.locator(".pf-v6-c-pagination__nav-page-select input").fill("5");
      await expect(pg.locator(".pf-v6-c-pagination__total-items")).toContainText("41 - 50");
    });
  });

  test.describe("States", () => {
    test("bottom variants carry pf-m-bottom and drop total-items", async ({ page }) => {
      for (const id of ["pg-bottom", "pg-bottom-plain", "pg-bottom-sticky", "pg-bottom-static"]) {
        await expect(page.locator(`#${id}`)).toHaveClass(/pf-m-bottom/);
        await expect(page.locator(`#${id} .pf-v6-c-pagination__total-items`)).toHaveCount(0);
      }
      await expect(page.locator("#pg-bottom-plain")).toHaveClass(/pf-m-plain/);
      await expect(page.locator("#pg-bottom-static")).toHaveClass(/pf-m-static/);
    });

    test("indeterminate shows 'many', no input max, and disables last", async ({ page }) => {
      const pg = page.locator("#pg-indeterminate");
      await expect(pg.locator(".pf-v6-c-pagination__total-items")).toContainText("many");
      await expect(pg.locator(".pf-v6-c-pagination__nav-page-select input")).not.toHaveAttribute("max", /.+/);
      await expect(pg.locator(".pf-v6-c-pagination__nav-page-select span[aria-hidden]")).toHaveCount(0);
      await expect(pg.locator(".pf-m-last button")).toBeDisabled();
      await expect(pg.locator(".pf-m-next button")).toBeEnabled();
    });

    test("disabled disables every control including toggle and input", async ({ page }) => {
      await expect(page.locator("#pg-disabled .pf-v6-c-pagination__nav-control button:disabled")).toHaveCount(4);
      await expect(page.locator("#pg-disabled .pf-v6-c-menu-toggle")).toBeDisabled();
      await expect(page.locator("#pg-disabled .pf-v6-c-pagination__nav-page-select input")).toBeDisabled();
    });

    test("no-items and one-page disable all nav controls via live state", async ({ page }) => {
      await expect(page.locator("#pg-no-items .pf-v6-c-pagination__total-items")).toContainText("0 - 0 of 0");
      await expect(page.locator("#pg-one-page .pf-v6-c-pagination__total-items")).toContainText("1 - 8 of 8");
      for (const id of ["pg-no-items", "pg-one-page"]) {
        await expect(page.locator(`#${id} .pf-v6-c-pagination__nav-control button:disabled`)).toHaveCount(4);
      }
    });

    test("offset starts on a mid-list range", async ({ page }) => {
      await expect(page.locator("#pg-offset .pf-v6-c-pagination__total-items")).toContainText("21 - 30 of 523");
      await expect(page.locator("#pg-offset .pf-m-first button")).toBeEnabled();
    });

    test("compact variants render prev/next only", async ({ page }) => {
      for (const id of ["pg-compact", "pg-compact-display-full"]) {
        await expect(page.locator(`#${id}`)).toHaveClass(/pf-m-compact/);
        await expect(page.locator(`#${id} .pf-v6-c-pagination__nav-control`)).toHaveCount(2);
        await expect(page.locator(`#${id} .pf-v6-c-pagination__nav-page-select`)).toHaveCount(0);
      }
      await expect(page.locator("#pg-compact-display-full")).toHaveClass(/pf-m-display-full/);
    });

    test("display and inset modifiers are applied", async ({ page }) => {
      await expect(page.locator("#pg-display-summary")).toHaveClass(/pf-m-display-summary/);
      await expect(page.locator("#pg-display-full")).toHaveClass(/pf-m-display-full/);
      await expect(page.locator("#pg-display-responsive")).toHaveClass(/pf-m-display-full-on-lg/);
      await expect(page.locator("#pg-inset")).toHaveClass(/pf-m-inset-md-on-md/);
    });
  });

  test.describe("Sticky", () => {
    test("sticky variants carry pf-m-sticky", async ({ page }) => {
      await expect(page.locator("#pg-sticky")).toHaveClass(/pf-m-sticky/);
      await expect(page.locator("#pg-bottom-sticky")).toHaveClass(/pf-m-sticky/);
    });

    test("top base-and-stuck applies pf-m-sticky-stuck on scroll", async ({ page }) => {
      const pg = page.locator("#pg-sticky-base-stuck");
      const box = pg.locator("xpath=..");
      await expect(pg).toHaveClass(/pf-m-sticky-base/);
      await expect(pg).not.toHaveClass(/pf-m-sticky-stuck/);
      await box.evaluate((el) => (el.scrollTop = 120));
      await expect(pg).toHaveClass(/pf-m-sticky-stuck/);
      await box.evaluate((el) => (el.scrollTop = 0));
      await expect(pg).not.toHaveClass(/pf-m-sticky-stuck/);
    });

    test("bottom base-and-stuck releases pf-m-sticky-stuck at the end of the scroll", async ({ page }) => {
      const pg = page.locator("#pg-bottom-sticky-base-stuck");
      const box = pg.locator("xpath=..");
      await expect(pg).toHaveClass(/pf-m-sticky-stuck/);
      await box.evaluate((el) => (el.scrollTop = el.scrollHeight));
      await expect(pg).not.toHaveClass(/pf-m-sticky-stuck/);
    });

    test("dynamic sticky toggles via the checkbox", async ({ page }) => {
      const pg = page.locator("#pg-dynamic-sticky");
      await expect(pg).toHaveClass(/pf-m-sticky/);
      await page.locator("#pg-dynamic-sticky-check").uncheck();
      await expect(pg).not.toHaveClass(/pf-m-sticky/);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/pagination/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/pagination/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-pagination").first()).toBeAttached();
      });
    }
  });
});
