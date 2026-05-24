import { test, expect } from "@playwright/test";

test.describe("Time Picker", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/time-picker");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Time picker");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-time-picker")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic example heading uses slug id", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
  });

  test("basic wrapper exists", async ({ page }) => {
    await expect(page.locator("#tp-basic")).toBeVisible();
    await expect(page.locator("#tp-basic")).toHaveClass(/pf-v6-c-time-picker/);
  });

  test("has input group", async ({ page }) => {
    await expect(
      page.locator("#tp-basic .pf-v6-c-input-group")
    ).toBeVisible();
  });

  test("has text input with HH:MM placeholder", async ({ page }) => {
    const timeInput = page.locator(
      '#tp-basic input[type="text"][placeholder="HH:MM"]'
    );
    await expect(timeInput).toBeVisible();
  });

  test("has clock-icon control button", async ({ page }) => {
    await expect(
      page.locator("#tp-basic .pf-v6-c-button.pf-m-control")
    ).toBeVisible();
  });
});
