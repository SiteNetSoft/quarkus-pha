import { test, expect } from "@playwright/test";

test.describe("Time Picker", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/time-picker");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Time Picker");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
  });

  test("basic wrapper exists", async ({ page }) => {
    await expect(page.locator("#tp-basic")).toBeVisible();
  });

  test("has input group", async ({ page }) => {
    await expect(
      page.locator("#tp-basic .pf-v6-c-input-group")
    ).toBeVisible();
  });

  test("has time input with type time", async ({ page }) => {
    const timeInput = page.locator('#tp-basic input[type="time"]');
    await expect(timeInput).toBeVisible();
  });

  test("time input has default value", async ({ page }) => {
    const timeInput = page.locator('#tp-basic input[type="time"]');
    await expect(timeInput).toHaveValue("12:00");
  });

  test("has clock icon button", async ({ page }) => {
    await expect(
      page.locator("#tp-basic .pf-v6-c-button.pf-m-control")
    ).toBeVisible();
  });
});
