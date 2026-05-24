import { test, expect } from "@playwright/test";

test.describe("Description List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/description-list");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#fill-columns")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-description-list")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-description-list class", async ({ page }) => {
      await expect(page.locator("#dl-basic")).toHaveClass(
        /pf-v6-c-description-list/
      );
    });

    test("has groups", async ({ page }) => {
      const groups = page.locator(
        "#dl-basic .pf-v6-c-description-list__group"
      );
      await expect(groups.first()).toBeVisible();
    });
  });

  test.describe("Fill columns", () => {
    test("has pf-m-fill-columns modifier", async ({ page }) => {
      await expect(page.locator("#dl-fill")).toHaveClass(
        /pf-m-fill-columns/
      );
    });
  });
});
