import { test, expect } from "@playwright/test";

test.describe("Banner", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/banner");
  });

  test("page loads with all 5 section headings", async ({ page }) => {
    await expect(page.locator("#status-heading")).toBeVisible();
    await expect(page.locator("#colors-heading")).toBeVisible();
    await expect(page.locator("#links-heading")).toBeVisible();
    await expect(page.locator("#screen-reader-heading")).toBeVisible();
    await expect(page.locator("#sticky-heading")).toBeVisible();
  });

  test.describe("Status variants", () => {
    test("all 5 status variants have correct modifier and base class", async ({ page }) => {
      const statuses = ["success", "warning", "danger", "info", "custom"];
      for (const status of statuses) {
        const banner = page.locator(`#banner-${status}`);
        await expect(banner).toHaveClass(/pf-v6-c-banner/);
        await expect(banner).toHaveClass(new RegExp(`pf-m-${status}`));
      }
    });
  });

  test.describe("Color variants", () => {
    test("all 8 color variants have correct modifier classes", async ({ page }) => {
      const colors = ["red", "orangered", "orange", "yellow", "green", "teal", "blue", "purple"];
      for (const color of colors) {
        const banner = page.locator(`#banner-${color}`);
        await expect(banner).toHaveClass(/pf-v6-c-banner/);
        await expect(banner).toHaveClass(new RegExp(`pf-m-${color}`));
      }
    });
  });

  test.describe("With links", () => {
    test("banner contains 2 link elements", async ({ page }) => {
      const links = page.locator("#banner-links a");
      await expect(links).toHaveCount(2);
    });
  });

  test.describe("Screen reader text", () => {
    test("screen reader span present with correct text", async ({ page }) => {
      const srSpan = page.locator("#banner-sr .pf-screen-reader");
      await expect(srSpan).toBeAttached();
      await expect(srSpan).toHaveText("Info alert:");
    });
  });

  test.describe("Sticky", () => {
    test("has sticky modifier class", async ({ page }) => {
      await expect(page.locator("#banner-sticky")).toHaveClass(/pf-m-sticky/);
    });
  });
});
