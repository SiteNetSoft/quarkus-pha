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

  test.describe("Range", () => {
    test("range example heading uses slug id", async ({ page }) => {
      await expect(page.locator("h3#range")).toHaveText("Date and time range picker");
    });

    test("end pair stays disabled until a start date is picked", async ({ page }) => {
      const from = page.locator("#dtrp-from");
      const to = page.locator("#dtrp-to");
      await expect(to.locator('input[type="text"]')).toBeDisabled();
      await expect(page.locator('#dtrp-to-time input[type="text"]')).toBeDisabled();
      await from.locator('button[aria-label="Toggle date picker"]').click();
      await from.locator('button[aria-label="20 May 2026"]').click();
      await expect(from.locator('input[type="text"]')).toHaveValue("2026-05-20");
      await expect(to.locator('input[type="text"]')).toBeEnabled();
      await expect(page.locator('#dtrp-to-time input[type="text"]')).toBeEnabled();
    });

    test("end date before start shows the error helper", async ({ page }) => {
      const from = page.locator("#dtrp-from");
      const to = page.locator("#dtrp-to");
      const helper = page.locator("#dtrp .pf-v6-c-helper-text");
      await expect(helper).toBeHidden();
      await from.locator('button[aria-label="Toggle date picker"]').click();
      await from.locator('button[aria-label="20 May 2026"]').click();
      await to.locator('button[aria-label="Toggle date picker"]').click();
      await to.locator('button[aria-label="10 May 2026"]').click();
      await expect(helper).toBeVisible();
      await to.locator('button[aria-label="Toggle date picker"]').click();
      await to.locator('button[aria-label="27 May 2026"]').click();
      await expect(helper).toBeHidden();
    });
  });
});
