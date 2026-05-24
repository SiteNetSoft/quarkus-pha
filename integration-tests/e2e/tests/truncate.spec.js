import { test, expect } from "@playwright/test";

test.describe("Truncate", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/truncate");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#end")).toBeVisible();
    await expect(page.locator("#start")).toBeVisible();
    await expect(page.locator("#middle")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-truncate")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("end truncate has start span", async ({ page }) => {
    await expect(page.locator("#trunc-end .pf-v6-c-truncate__start")).toBeVisible();
  });

  test("end truncate has pf-v6-c-truncate class", async ({ page }) => {
    await expect(page.locator("#trunc-end")).toHaveClass(/pf-v6-c-truncate/);
  });

  test("start truncate has end span", async ({ page }) => {
    await expect(page.locator("#trunc-start .pf-v6-c-truncate__end")).toBeVisible();
  });

  test("middle truncate has both start and end spans", async ({ page }) => {
    await expect(page.locator("#trunc-middle .pf-v6-c-truncate__start")).toBeVisible();
    await expect(page.locator("#trunc-middle .pf-v6-c-truncate__end")).toBeVisible();
  });
});
