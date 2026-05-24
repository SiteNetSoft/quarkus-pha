import { test, expect } from "@playwright/test";

test.describe("Radio", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/radio");
  });

  test("page loads with all section headings", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#disabled")).toBeVisible();
    await expect(page.locator("#with-description")).toBeVisible();
    await expect(page.locator("#with-body")).toBeVisible();
    await expect(page.locator("#standalone")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-radio")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-radio class", async ({ page }) => {
      await expect(page.locator("#r-basic-one")).toHaveClass(/pf-v6-c-radio/);
    });

    test("first radio is checked", async ({ page }) => {
      await expect(page.locator("#r-basic-one-field")).toBeChecked();
    });
  });

  test.describe("With description", () => {
    test("has description element", async ({ page }) => {
      await expect(
        page.locator("#r-desc-one .pf-v6-c-radio__description")
      ).toBeVisible();
    });
  });

  test.describe("Disabled", () => {
    test("radio has disabled attribute", async ({ page }) => {
      await expect(page.locator("#r-dis-one-field")).toBeDisabled();
    });
  });
});
