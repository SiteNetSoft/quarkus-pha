import { test, expect } from "@playwright/test";

test.describe("Search Input", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/search-input");
  });

  test("page loads with all 2 example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#with-clear")).toHaveText("With clear");
  });

  test.describe("Basic", () => {
    test("basic search input has pf-v6-c-text-input-group class", async ({ page }) => {
      await expect(page.locator("#si-basic")).toHaveClass(/pf-v6-c-text-input-group/);
    });

    test("basic search input has a search icon", async ({ page }) => {
      await expect(page.locator("#si-basic .pf-v6-c-text-input-group__icon")).toBeVisible();
    });

    test("basic search input has a search input field", async ({ page }) => {
      await expect(page.locator("#si-basic input[type='search']")).toBeVisible();
    });

    test("basic search input has correct placeholder", async ({ page }) => {
      await expect(page.locator("#si-basic input[type='search']")).toHaveAttribute("placeholder", "Search…");
    });
  });

  test.describe("With clear", () => {
    test("has pf-v6-c-text-input-group class", async ({ page }) => {
      await expect(page.locator("#si-clear")).toHaveClass(/pf-v6-c-text-input-group/);
    });

    test("has a clear button", async ({ page }) => {
      await expect(page.locator("#si-clear button[aria-label='Clear search']")).toBeVisible();
    });

    test("clicking clear button empties the input", async ({ page }) => {
      await page.locator("#si-clear button[aria-label='Clear search']").click();
      await expect(page.locator("#si-clear input[type='search']")).toHaveValue("");
    });
  });
});
