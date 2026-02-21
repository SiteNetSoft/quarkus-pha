import { test, expect } from "@playwright/test";

test.describe("Jump Links", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/jump-links");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#vertical-heading")).toBeVisible();
    await expect(page.locator("#horizontal-heading")).toBeVisible();
  });

  test.describe("Vertical", () => {
    test("has pf-v6-c-jump-links and pf-m-vertical classes", async ({ page }) => {
      await expect(page.locator("#jl-vertical")).toHaveClass(/pf-v6-c-jump-links/);
      await expect(page.locator("#jl-vertical")).toHaveClass(/pf-m-vertical/);
    });

    test("has pf-v6-c-jump-links__item elements", async ({ page }) => {
      await expect(page.locator("#jl-vertical .pf-v6-c-jump-links__item").first()).toBeVisible();
    });

    test("one item has pf-m-current", async ({ page }) => {
      await expect(page.locator("#jl-vertical .pf-v6-c-jump-links__item.pf-m-current")).toBeVisible();
    });
  });

  test.describe("Horizontal", () => {
    test("does not have class pf-m-vertical", async ({ page }) => {
      await expect(page.locator("#jl-horizontal")).not.toHaveClass(/pf-m-vertical/);
    });
  });
});
