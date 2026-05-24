import { test, expect } from "@playwright/test";

test.describe("Pagination", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/pagination");
  });

  test("page loads with all 2 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#basic")).toHaveText("Basic");
    await expect(page.locator("#compact")).toBeVisible();
    await expect(page.locator("#compact")).toHaveText("Compact");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-pagination")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/pagination/basic"]';

    test("basic pagination has pf-v6-c-pagination class", async ({ page }) => {
      await expect(page.locator(`${card} #pg-basic`)).toHaveClass(/pf-v6-c-pagination/);
    });

    test("basic pagination has navigation buttons", async ({ page }) => {
      await expect(
        page.locator(`${card} #pg-basic button[aria-label='Go to previous page']`)
      ).toBeVisible();
      await expect(
        page.locator(`${card} #pg-basic button[aria-label='Go to next page']`)
      ).toBeVisible();
      await expect(
        page.locator(`${card} #pg-basic button[aria-label='Go to first page']`)
      ).toBeVisible();
      await expect(
        page.locator(`${card} #pg-basic button[aria-label='Go to last page']`)
      ).toBeVisible();
    });

    test("basic pagination shows total-items text", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-pagination__total-items`)).toBeVisible();
    });
  });

  test.describe("Compact variant", () => {
    const card = '[data-rendered-href="/components/pagination/compact"]';

    test("compact pagination has pf-m-compact class", async ({ page }) => {
      await expect(page.locator(`${card} #pg-compact`)).toHaveClass(/pf-m-compact/);
    });

    test("compact pagination has only prev/next nav buttons", async ({ page }) => {
      await expect(
        page.locator(`${card} #pg-compact button[aria-label='Go to previous page']`)
      ).toBeVisible();
      await expect(
        page.locator(`${card} #pg-compact button[aria-label='Go to next page']`)
      ).toBeVisible();
      await expect(
        page.locator(`${card} #pg-compact button[aria-label='Go to first page']`)
      ).toHaveCount(0);
      await expect(
        page.locator(`${card} #pg-compact button[aria-label='Go to last page']`)
      ).toHaveCount(0);
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/pagination/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/pagination/compact"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/pagination/compact");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
