import { test, expect } from "@playwright/test";

test.describe("Date and Time Picker", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/date-and-time-picker");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Date and Time Picker");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
  });

  test("basic wrapper exists", async ({ page }) => {
    await expect(page.locator("#dtp-basic")).toBeVisible();
  });

  test("has date input with placeholder", async ({ page }) => {
    const dateInput = page.locator(
      '#dtp-basic input[placeholder="YYYY-MM-DD"]'
    );
    await expect(dateInput).toBeVisible();
  });

  test("has time input with type time", async ({ page }) => {
    const timeInput = page.locator('#dtp-basic input[type="time"]');
    await expect(timeInput).toBeVisible();
  });

  test("has pf-v6-c-date-picker class", async ({ page }) => {
    await expect(
      page.locator("#dtp-basic .pf-v6-c-date-picker")
    ).toBeVisible();
  });

  test("has flex layout", async ({ page }) => {
    const wrapper = page.locator("#dtp-basic");
    await expect(wrapper).toHaveCSS("display", "flex");
  });
});
