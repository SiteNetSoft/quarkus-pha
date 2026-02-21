import { test, expect } from "@playwright/test";

test.describe("List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/list");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#inline-heading")).toBeVisible();
    await expect(page.locator("#plain-heading")).toBeVisible();
    await expect(page.locator("#bordered-heading")).toBeVisible();
  });

  test("basic list has pf-v6-c-list class", async ({ page }) => {
    await expect(page.locator("#list-basic")).toHaveClass(/pf-v6-c-list/);
  });

  test("basic list has 3 items", async ({ page }) => {
    await expect(page.locator("#list-basic > li")).toHaveCount(3);
  });

  test("inline list has inline modifier", async ({ page }) => {
    await expect(page.locator("#list-inline")).toHaveClass(/pf-m-inline/);
  });

  test("plain list has plain modifier", async ({ page }) => {
    await expect(page.locator("#list-plain")).toHaveClass(/pf-m-plain/);
  });

  test("bordered list has bordered modifier", async ({ page }) => {
    await expect(page.locator("#list-bordered")).toHaveClass(/pf-m-bordered/);
  });
});
