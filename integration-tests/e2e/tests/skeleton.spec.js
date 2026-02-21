import { test, expect } from "@playwright/test";

test.describe("Skeleton", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/skeleton");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#text-heading")).toBeVisible();
    await expect(page.locator("#shapes-heading")).toBeVisible();
    await expect(page.locator("#sizes-heading")).toBeVisible();
  });

  test("has text skeleton with text size modifier", async ({ page }) => {
    await expect(page.locator("#sk-heading")).toHaveClass(/pf-m-text-4xl/);
  });

  test("has circle skeleton", async ({ page }) => {
    await expect(page.locator("#sk-circle")).toHaveClass(/pf-m-circle/);
  });

  test("has square skeleton", async ({ page }) => {
    await expect(page.locator("#sk-square")).toHaveClass(/pf-m-square/);
  });

  test("has skeletons with width modifiers", async ({ page }) => {
    await expect(page.locator("#sk-w25")).toHaveClass(/pf-m-width-25/);
    await expect(page.locator("#sk-w50")).toHaveClass(/pf-m-width-50/);
    await expect(page.locator("#sk-w75")).toHaveClass(/pf-m-width-75/);
  });
});
