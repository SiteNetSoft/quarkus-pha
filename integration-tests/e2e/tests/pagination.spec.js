import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "top",
  "bottom",
  "compact",
  "indeterminate",
  "disabled",
  "no-items",
  "one-page",
  "offset",
  "inset",
  "sticky",
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

  test.describe("Basic", () => {
    test("renders total items and four nav controls", async ({ page }) => {
      await expect(page.locator("#pg-basic .pf-v6-c-pagination__total-items")).toContainText("of");
      await expect(page.locator("#pg-basic .pf-v6-c-pagination__nav-control")).toHaveCount(4);
    });
  });

  test.describe("Top (functional)", () => {
    test("next/last enable and the page advances", async ({ page }) => {
      const pg = page.locator("#pg-top");
      const first = pg.locator(".pf-m-first button");
      const next = pg.locator(".pf-m-next button");
      await expect(first).toBeDisabled();
      await next.click();
      await expect(pg.locator(".pf-v6-c-pagination__nav-page-select b").first()).toHaveText("2");
      await expect(first).toBeEnabled();
      await pg.locator(".pf-m-last button").click();
      await expect(pg.locator(".pf-v6-c-pagination__nav-page-select b").first()).toHaveText("53");
      await expect(next).toBeDisabled();
    });
  });

  test.describe("States", () => {
    test("bottom variant carries pf-m-bottom", async ({ page }) => {
      await expect(page.locator("#pg-bottom")).toHaveClass(/pf-m-bottom/);
    });

    test("indeterminate shows 'many' and disables last", async ({ page }) => {
      await expect(page.locator("#pg-indeterminate .pf-v6-c-pagination__total-items")).toContainText("many");
      await expect(page.locator("#pg-indeterminate .pf-m-last button")).toBeDisabled();
    });

    test("disabled, no-items, and one-page disable all controls", async ({ page }) => {
      for (const id of ["pg-disabled", "pg-no-items", "pg-one-page"]) {
        await expect(page.locator(`#${id} .pf-v6-c-pagination__nav-control button:disabled`)).toHaveCount(4);
      }
    });

    test("offset shows a mid-list range", async ({ page }) => {
      await expect(page.locator("#pg-offset .pf-v6-c-pagination__total-items")).toContainText("21 - 30");
    });

    test("inset and sticky modifiers are applied", async ({ page }) => {
      await expect(page.locator("#pg-inset")).toHaveClass(/pf-m-inset-md/);
      await expect(page.locator("#pg-sticky")).toHaveClass(/pf-m-sticky/);
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
