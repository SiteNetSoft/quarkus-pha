import { test, expect } from "@playwright/test";

test.describe("Divider", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/divider");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#hr-heading")).toBeVisible();
    await expect(page.locator("#inset-heading")).toBeVisible();
    await expect(page.locator("#vertical-heading")).toBeVisible();
  });

  test("has horizontal divider with hr element", async ({ page }) => {
    const hr = page.locator("#hr-heading ~ p + hr.pf-v6-c-divider");
    await expect(hr).toBeVisible();
  });

  test("has dividers with inset modifiers", async ({ page }) => {
    await expect(page.locator(".pf-v6-c-divider.pf-m-inset-md")).toBeVisible();
    await expect(page.locator(".pf-v6-c-divider.pf-m-inset-xl")).toBeVisible();
  });

  test("has vertical dividers", async ({ page }) => {
    const verticals = page.locator(".pf-v6-c-divider.pf-m-vertical");
    await expect(verticals.first()).toBeVisible();
  });
});
