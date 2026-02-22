import { test, expect } from "@playwright/test";

test.describe("Lazy Modal", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/lazy-modal");
  });

  test("page loads with heading", async ({ page }) => {
    await expect(page.locator("#lazy-modal-heading")).toBeVisible();
  });

  test("open button is present", async ({ page }) => {
    await expect(page.locator("#open-lazy-modal-btn")).toBeVisible();
  });

  test("modal is hidden initially", async ({ page }) => {
    await expect(page.locator("#lazy-modal-demo-backdrop")).not.toBeVisible();
  });

  test("clicking button opens modal", async ({ page }) => {
    await page.locator("#open-lazy-modal-btn").click();
    await expect(page.locator("#lazy-modal-demo-backdrop")).toBeVisible({ timeout: 3000 });
  });

  test("modal has correct title", async ({ page }) => {
    await page.locator("#open-lazy-modal-btn").click();
    await expect(page.locator("#lazy-modal-demo-title")).toContainText("Server Details");
  });

  test("modal content loads via HTMX", async ({ page }) => {
    await page.locator("#open-lazy-modal-btn").click();
    // Content should be fetched and rendered
    await expect(page.locator("#modal-loaded-content-details")).toBeVisible({ timeout: 5000 });
  });

  test("loaded content contains expected data", async ({ page }) => {
    await page.locator("#open-lazy-modal-btn").click();
    await expect(page.locator("#modal-loaded-content-details")).toContainText("Active", { timeout: 5000 });
    await expect(page.locator("#modal-loaded-content-details")).toContainText("/api/htmx/modal-content/details");
  });

  test("escape key closes modal", async ({ page }) => {
    await page.locator("#open-lazy-modal-btn").click();
    await expect(page.locator("#lazy-modal-demo-backdrop")).toBeVisible({ timeout: 3000 });
    await page.keyboard.press("Escape");
    await expect(page.locator("#lazy-modal-demo-backdrop")).not.toBeVisible();
  });

  test("close button closes modal", async ({ page }) => {
    await page.locator("#open-lazy-modal-btn").click();
    await expect(page.locator("#lazy-modal-demo-backdrop")).toBeVisible({ timeout: 3000 });
    await page.locator("#lazy-modal-demo-backdrop .pf-v6-c-modal-box__close button").click();
    await expect(page.locator("#lazy-modal-demo-backdrop")).not.toBeVisible();
  });

  test("modal body has hx-get attribute for lazy loading", async ({ page }) => {
    const hxGet = await page.locator("#lazy-modal-demo-body").getAttribute("hx-get");
    expect(hxGet).toBe("/api/htmx/modal-content/details");
  });

  test("modal has dialog role and aria-modal", async ({ page }) => {
    const modalBox = page.locator("#lazy-modal-demo-backdrop .pf-v6-c-modal-box");
    await expect(modalBox).toHaveAttribute("role", "dialog");
    await expect(modalBox).toHaveAttribute("aria-modal", "true");
  });
});
