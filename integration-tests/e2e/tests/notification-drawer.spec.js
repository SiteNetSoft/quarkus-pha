import { test, expect } from "@playwright/test";

test.describe("Notification Drawer", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/notification-drawer");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-notification-drawer class", async ({ page }) => {
      await expect(page.locator("#nd-basic")).toHaveClass(/pf-v6-c-notification-drawer/);
    });

    test("has header title", async ({ page }) => {
      await expect(page.locator("#nd-basic .pf-v6-c-notification-drawer__header-title")).toBeVisible();
    });

    test("has notification list items", async ({ page }) => {
      await expect(page.locator("#nd-basic .pf-v6-c-notification-drawer__list-item").first()).toBeVisible();
    });

    test("items have severity classes", async ({ page }) => {
      const hasInfo = await page.locator("#nd-basic .pf-m-info").count();
      const hasWarning = await page.locator("#nd-basic .pf-m-warning").count();
      const hasDanger = await page.locator("#nd-basic .pf-m-danger").count();
      expect(hasInfo + hasWarning + hasDanger).toBeGreaterThan(0);
    });
  });
});
