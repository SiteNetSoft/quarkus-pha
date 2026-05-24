import { test, expect } from "@playwright/test";

test.describe("Tabs", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tabs");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#basic")).toHaveText("Basic");
    await expect(page.locator("#box")).toBeVisible();
    await expect(page.locator("#box")).toHaveText("Box");
    await expect(page.locator("#vertical")).toBeVisible();
    await expect(page.locator("#vertical")).toHaveText("Vertical");
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-tabs")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
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

  test.describe("Box", () => {
    test("box tabs has pf-m-box class", async ({ page }) => {
      await expect(page.locator("#tabs-box")).toHaveClass(/pf-m-box/);
    });

    test("box tabs has tab items", async ({ page }) => {
      await expect(page.locator("#tabs-box .pf-v6-c-tabs__item")).toHaveCount(2);
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
