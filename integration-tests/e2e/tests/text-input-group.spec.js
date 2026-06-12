import { test, expect } from "@playwright/test";

test.describe("Text Input Group", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/text-input-group");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#with-icon")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-text-input-group class", async ({ page }) => {
      await expect(page.locator("#tig-basic")).toHaveClass(
        /pf-v6-c-text-input-group/
      );
    });

    test("has text input", async ({ page }) => {
      await expect(page.locator("#tig-basic input")).toBeVisible();
    });
  });

  test.describe("With icon", () => {
    test("has icon element", async ({ page }) => {
      await expect(
        page.locator("#tig-icon .pf-v6-c-text-input-group__icon")
      ).toBeVisible();
    });
  });

  test.describe("Parity additions", () => {
    test("filters add via Enter and remove via labels", async ({ page }) => {
      const root = page.locator("#tig-filters");
      const labels = root.locator(".pf-v6-c-label-group__list-item");
      await expect(labels).toHaveCount(2);
      await root.locator("input").fill("Delta");
      await root.locator("input").press("Enter");
      await expect(labels).toHaveCount(3);
      await labels.first().locator(".pf-v6-c-label__actions button").click();
      await expect(labels).toHaveCount(2);
    });

    test("status variants carry classes and icons", async ({ page }) => {
      await expect(page.locator("#tig-status-success")).toHaveClass(/pf-m-success/);
      await expect(page.locator("#tig-status-error")).toHaveClass(/pf-m-error/);
      await expect(page.locator("#tig-status-error .pf-v6-c-text-input-group__icon.pf-m-status")).toBeVisible();
    });

    test("/components/text-input-group/filters returns 200", async ({ page }) => {
      const res = await page.goto("/components/text-input-group/filters");
      expect(res.status()).toBe(200);
    });

    test("/components/text-input-group/with-status returns 200", async ({ page }) => {
      const res = await page.goto("/components/text-input-group/with-status");
      expect(res.status()).toBe(200);
    });
  });
});
