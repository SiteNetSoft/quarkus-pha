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

  test.describe("Parity additions", () => {
    test("groups expand and collapse with counts", async ({ page }) => {
      const first = page.locator("#nd-groups .pf-v6-c-notification-drawer__group").first();
      await expect(first).toHaveClass(/pf-m-expanded/);
      await expect(first.locator(".pf-v6-c-badge")).toHaveText("2");
      await first.locator(".pf-v6-c-notification-drawer__group-toggle").click();
      await expect(first).not.toHaveClass(/pf-m-expanded/);
    });

    test("lightweight drawer has no header", async ({ page }) => {
      await expect(page.locator("#nd-lightweight .pf-v6-c-notification-drawer__header")).toHaveCount(0);
      await expect(page.locator("#nd-lightweight .pf-v6-c-notification-drawer__list-item")).toHaveCount(2);
    });

    test("/components/notification-drawer/groups returns 200", async ({ page }) => {
      const res = await page.goto("/components/notification-drawer/groups");
      expect(res.status()).toBe(200);
    });

    test("/components/notification-drawer/lightweight returns 200", async ({ page }) => {
      const res = await page.goto("/components/notification-drawer/lightweight");
      expect(res.status()).toBe(200);
    });
  });
});
