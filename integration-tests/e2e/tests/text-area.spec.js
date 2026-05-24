import { test, expect } from "@playwright/test";

test.describe("Text Area", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/text-area");
  });

  test("page loads with all 4 example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#resize-vertical")).toHaveText("Resize vertical");
    await expect(page.locator("h3#invalid")).toHaveText("Invalid");
    await expect(page.locator("h3#disabled-readonly")).toHaveText("Disabled / read only");
  });

  test.describe("Basic", () => {
    test("has textarea element", async ({ page }) => {
      await expect(page.locator("#ta-basic-field")).toBeVisible();
    });

    test("wrapper has pf-m-textarea modifier", async ({ page }) => {
      await expect(page.locator("#ta-basic")).toHaveClass(/pf-m-textarea/);
    });
  });

  test.describe("Resize vertical", () => {
    test("wrapper has pf-m-resize-vertical modifier", async ({ page }) => {
      await expect(page.locator("#ta-resize-v")).toHaveClass(/pf-m-resize-vertical/);
    });
  });

  test.describe("Invalid", () => {
    test("wrapper has pf-m-error and aria-invalid is set", async ({ page }) => {
      await expect(page.locator("#ta-invalid")).toHaveClass(/pf-m-error/);
      await expect(page.locator("#ta-invalid-field")).toHaveAttribute("aria-invalid", "true");
    });
  });

  test.describe("Disabled / read only", () => {
    test("disabled textarea is disabled", async ({ page }) => {
      await expect(page.locator("#ta-disabled-field")).toBeDisabled();
    });

    test("read-only textarea has readonly attribute", async ({ page }) => {
      await expect(page.locator("#ta-readonly-field")).toHaveAttribute("readonly", "");
    });
  });
});
