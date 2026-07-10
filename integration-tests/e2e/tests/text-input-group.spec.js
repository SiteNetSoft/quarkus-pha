import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "with-icon",
  "disabled",
  "plain",
  "autocomplete-hint",
  "filters",
  "filters-expanded",
  "with-status",
];

test.describe("Text Input Group", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/text-input-group");
  });

  test("page loads with all example section headings", async ({ page }) => {
    for (const slug of EXAMPLES) {
      await expect(page.locator(`h3#${slug}`)).toBeAttached();
    }
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

  test.describe("With icon and utilities", () => {
    test("has icon element", async ({ page }) => {
      await expect(
        page.locator("#tig-icon .pf-v6-c-text-input-group__icon")
      ).toBeVisible();
    });

    test("clear utility appears with text and clears it", async ({ page }) => {
      const root = page.locator("#tig-icon");
      const clear = root.locator(".pf-v6-c-text-input-group__utilities button");
      await expect(clear).toBeHidden();
      await root.locator("input").fill("query");
      await expect(clear).toBeVisible();
      await clear.click();
      await expect(root.locator("input")).toHaveValue("");
      await expect(clear).toBeHidden();
    });
  });

  test.describe("Plain", () => {
    test("carries pf-m-plain and a prefilled value", async ({ page }) => {
      await expect(page.locator("#tig-plain")).toHaveClass(/pf-m-plain/);
      await expect(page.locator("#tig-plain input:not([disabled])")).toHaveValue(
        "Text input group with plain styling"
      );
    });
  });

  test.describe("Autocomplete hint", () => {
    test("renders the disabled pf-m-hint ghost input behind the real one", async ({ page }) => {
      const hint = page.locator("#tig-hint input.pf-m-hint");
      await expect(hint).toBeAttached();
      await expect(hint).toBeDisabled();
      await expect(hint).toHaveValue("appleseed");
      await expect(page.locator("#tig-hint input:not(.pf-m-hint)")).toHaveValue("apples");
    });
  });

  test.describe("Filters", () => {
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

    test("expanded variant shows the long chip list and Clear all empties it", async ({ page }) => {
      const root = page.locator("#tig-filters-expanded");
      const labels = root.locator(".pf-v6-c-label-group__list-item");
      await expect(labels).toHaveCount(8);
      await root.locator(".pf-v6-c-text-input-group__utilities button").click();
      await expect(labels).toHaveCount(0);
    });
  });

  test.describe("With status", () => {
    test("status variants carry classes and icons", async ({ page }) => {
      await expect(page.locator("#tig-status-success")).toHaveClass(/pf-m-success/);
      await expect(page.locator("#tig-status-error")).toHaveClass(/pf-m-error/);
      await expect(page.locator("#tig-status-error .pf-v6-c-text-input-group__icon.pf-m-status")).toBeVisible();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/text-input-group/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/text-input-group/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-text-input-group").first()).toBeAttached();
      });
    }
  });
});
