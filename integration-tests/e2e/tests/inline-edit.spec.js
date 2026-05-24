import { test, expect } from "@playwright/test";

test.describe("Inline Edit", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/inline-edit");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-inline-edit class", async ({ page }) => {
      await expect(page.locator("#ie-basic")).toHaveClass(/pf-v6-c-inline-edit/);
    });

    test("shows value text by default", async ({ page }) => {
      await expect(page.locator("#ie-basic .pf-v6-c-inline-edit__value")).toBeVisible();
    });

    test("clicking edit button shows input with pf-m-inline-editable class", async ({ page }) => {
      await page.locator("#ie-basic button[aria-label*='edit'], #ie-basic .pf-v6-c-inline-edit__action button").first().click();
      await expect(page.locator("#ie-basic")).toHaveClass(/pf-m-inline-editable/);
      await expect(page.locator("#ie-basic input")).toBeVisible();
    });

    test("clicking save button returns to view mode", async ({ page }) => {
      await page.locator("#ie-basic button[aria-label*='edit'], #ie-basic .pf-v6-c-inline-edit__action button").first().click();
      await page.locator("#ie-basic button[aria-label*='save'], #ie-basic .pf-v6-c-inline-edit__action button").last().click();
      await expect(page.locator("#ie-basic")).not.toHaveClass(/pf-m-inline-editable/);
    });
  });
});
