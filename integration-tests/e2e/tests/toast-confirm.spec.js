import { test, expect } from "@playwright/test";

test.describe("Toast & confirm (HTMX)", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/toast-confirm");
    await expect(page.locator("#toast-save-btn")).toBeVisible();
  });

  test("a successful action renders a server-signalled toast", async ({ page }) => {
    await page.locator("#toast-save-btn").click();
    const toast = page.locator("#pha-toast-region .pf-v6-c-alert.pf-m-success");
    await expect(toast).toBeVisible();
    await expect(toast).toContainText("Changes saved successfully");
  });

  test("a toast can be dismissed with its close button", async ({ page }) => {
    await page.locator("#toast-save-btn").click();
    const toast = page.locator("#pha-toast-region .pf-v6-c-alert").first();
    await expect(toast).toBeVisible();
    await toast.locator("button[aria-label='Close alert']").click();
    await expect(page.locator("#pha-toast-region .pf-v6-c-alert")).toHaveCount(0);
  });

  test("a destructive action opens the confirm modal instead of window.confirm", async ({ page }) => {
    await page.locator("#toast-delete-btn").click();
    await expect(page.locator("#pha-confirm-modal")).toBeVisible();
    await expect(page.locator("#pha-confirm-text")).toContainText("Delete this item?");
  });

  test("confirming issues the request and shows the danger toast", async ({ page }) => {
    await page.locator("#toast-delete-btn").click();
    await expect(page.locator("#pha-confirm-modal")).toBeVisible();
    await page.locator("#pha-confirm-yes").click();
    const toast = page.locator("#pha-toast-region .pf-v6-c-alert.pf-m-danger");
    await expect(toast).toBeVisible();
    await expect(toast).toContainText("Item deleted");
  });

  test("cancelling does not fire the request", async ({ page }) => {
    await page.locator("#toast-delete-btn").click();
    await expect(page.locator("#pha-confirm-modal")).toBeVisible();
    await page.locator("#pha-confirm-modal button", { hasText: "Cancel" }).click();
    await expect(page.locator("#pha-toast-region .pf-v6-c-alert.pf-m-danger")).toHaveCount(0);
  });
});
