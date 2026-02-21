import { test, expect } from "@playwright/test";

test.describe("Toolbar", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/toolbar");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#with-groups-heading")).toBeVisible();
    await expect(page.locator("#with-groups-heading")).toHaveText("With groups");
  });

  test.describe("Basic", () => {
    test("basic toolbar has pf-v6-c-toolbar class", async ({ page }) => {
      await expect(page.locator("#tb-basic")).toHaveClass(/pf-v6-c-toolbar/);
    });

    test("basic toolbar has toolbar items", async ({ page }) => {
      await expect(page.locator("#tb-basic .pf-v6-c-toolbar__item")).toHaveCount(2);
    });

    test("basic toolbar has an action button", async ({ page }) => {
      await expect(page.locator("#tb-basic .pf-v6-c-button.pf-m-primary")).toBeVisible();
    });
  });

  test.describe("With groups", () => {
    test("groups toolbar has pf-v6-c-toolbar class", async ({ page }) => {
      await expect(page.locator("#tb-groups")).toHaveClass(/pf-v6-c-toolbar/);
    });

    test("groups toolbar has toolbar groups", async ({ page }) => {
      await expect(page.locator("#tb-groups .pf-v6-c-toolbar__group")).toHaveCount(2);
    });

    test("groups toolbar has toolbar items within groups", async ({ page }) => {
      await expect(page.locator("#tb-groups .pf-v6-c-toolbar__item")).toHaveCount(4);
    });
  });
});
