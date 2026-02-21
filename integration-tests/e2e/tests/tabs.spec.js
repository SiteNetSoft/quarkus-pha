import { test, expect } from "@playwright/test";

test.describe("Tabs", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tabs");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
    await expect(page.locator("#boxed-heading")).toBeVisible();
    await expect(page.locator("#boxed-heading")).toHaveText("Boxed");
    await expect(page.locator("#vertical-heading")).toBeVisible();
    await expect(page.locator("#vertical-heading")).toHaveText("Vertical");
  });

  test.describe("Basic", () => {
    test("basic tabs has pf-v6-c-tabs class", async ({ page }) => {
      await expect(page.locator("#tabs-basic")).toHaveClass(/pf-v6-c-tabs/);
    });

    test("basic tabs has tab items", async ({ page }) => {
      await expect(page.locator("#tabs-basic .pf-v6-c-tabs__item")).toHaveCount(3);
    });

    test("basic tabs has one current item", async ({ page }) => {
      await expect(page.locator("#tabs-basic .pf-v6-c-tabs__item.pf-m-current")).toHaveCount(1);
    });
  });

  test.describe("Boxed", () => {
    test("boxed tabs has pf-m-box class", async ({ page }) => {
      await expect(page.locator("#tabs-boxed")).toHaveClass(/pf-m-box/);
    });

    test("boxed tabs has tab items", async ({ page }) => {
      await expect(page.locator("#tabs-boxed .pf-v6-c-tabs__item")).toHaveCount(3);
    });
  });

  test.describe("Vertical", () => {
    test("vertical tabs has pf-m-vertical class", async ({ page }) => {
      await expect(page.locator("#tabs-vertical")).toHaveClass(/pf-m-vertical/);
    });

    test("vertical tabs has tab items", async ({ page }) => {
      await expect(page.locator("#tabs-vertical .pf-v6-c-tabs__item")).toHaveCount(3);
    });
  });
});
