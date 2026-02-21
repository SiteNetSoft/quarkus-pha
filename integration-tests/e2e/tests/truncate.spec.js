import { test, expect } from "@playwright/test";

test.describe("Truncate", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/truncate");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#default-heading")).toBeVisible();
    await expect(page.locator("#middle-heading")).toBeVisible();
  });

  test("default truncate has start span", async ({ page }) => {
    await expect(page.locator("#trunc-default .pf-v6-c-truncate__start")).toBeVisible();
  });

  test("default truncate has pf-v6-c-truncate class", async ({ page }) => {
    await expect(page.locator("#trunc-default")).toHaveClass(/pf-v6-c-truncate/);
  });

  test("middle truncate has both start and end spans", async ({ page }) => {
    await expect(page.locator("#trunc-middle .pf-v6-c-truncate__start")).toBeVisible();
    await expect(page.locator("#trunc-middle .pf-v6-c-truncate__end")).toBeVisible();
  });
});
