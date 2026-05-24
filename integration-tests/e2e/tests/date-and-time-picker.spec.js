import { test, expect } from "@playwright/test";

test.describe("Date and Time Picker", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/date-and-time-picker");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText(
      "Date and time picker"
    );
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-date-and-time-picker")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic example heading uses slug id", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
  });

  test("date picker wrapper exists", async ({ page }) => {
    await expect(page.locator("#dtp-date")).toBeVisible();
    await expect(page.locator("#dtp-date")).toHaveClass(/pf-v6-c-date-picker/);
  });

  test("time picker wrapper exists", async ({ page }) => {
    await expect(page.locator("#dtp-time")).toBeVisible();
    await expect(page.locator("#dtp-time")).toHaveClass(/pf-v6-c-time-picker/);
  });

  test("date picker has its text input with the seeded value", async ({
    page,
  }) => {
    const dateInput = page.locator('#dtp-date input[type="text"]');
    await expect(dateInput).toBeVisible();
    await expect(dateInput).toHaveValue("2026-05-20");
  });

  test("time picker has its text input with placeholder", async ({ page }) => {
    const timeInput = page.locator(
      '#dtp-time input[type="text"][placeholder="HH:MM"]'
    );
    await expect(timeInput).toBeVisible();
  });
});
