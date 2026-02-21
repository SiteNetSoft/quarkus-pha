import { test, expect } from "@playwright/test";

test.describe("Simple List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/simple-list");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#grouped-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-simple-list class", async ({ page }) => {
      await expect(page.locator("#sl-basic")).toHaveClass(
        /pf-v6-c-simple-list/
      );
    });

    test("has item links", async ({ page }) => {
      const links = page.locator(
        "#sl-basic .pf-v6-c-simple-list__item-link"
      );
      await expect(links.first()).toBeVisible();
    });

    test("one link has pf-m-current modifier", async ({ page }) => {
      await expect(
        page.locator("#sl-basic .pf-v6-c-simple-list__item-link.pf-m-current")
      ).toBeVisible();
    });
  });

  test.describe("Grouped", () => {
    test("has title element", async ({ page }) => {
      await expect(
        page.locator("#sl-grouped .pf-v6-c-simple-list__title").first()
      ).toBeVisible();
    });
  });
});
