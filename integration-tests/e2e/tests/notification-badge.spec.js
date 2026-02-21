import { test, expect } from "@playwright/test";

test.describe("Notification Badge", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/notification-badge");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#read-heading")).toBeVisible();
    await expect(page.locator("#unread-heading")).toBeVisible();
    await expect(page.locator("#attention-heading")).toBeVisible();
  });

  test.describe("Read", () => {
    test("has pf-v6-c-button and pf-m-read classes", async ({ page }) => {
      await expect(page.locator("#nb-read")).toHaveClass(/pf-v6-c-button/);
      await expect(page.locator("#nb-read")).toHaveClass(/pf-m-read/);
    });
  });

  test.describe("Unread", () => {
    test("has class pf-m-unread", async ({ page }) => {
      await expect(page.locator("#nb-unread")).toHaveClass(/pf-m-unread/);
    });
  });

  test.describe("Attention", () => {
    test("has class pf-m-attention", async ({ page }) => {
      await expect(page.locator("#nb-attention")).toHaveClass(/pf-m-attention/);
    });
  });

  test("all badges have bell icon", async ({ page }) => {
    await expect(page.locator("#nb-read .fas.fa-bell, #nb-read i.fa-bell")).toBeVisible();
    await expect(page.locator("#nb-unread .fas.fa-bell, #nb-unread i.fa-bell")).toBeVisible();
    await expect(page.locator("#nb-attention .fas.fa-bell, #nb-attention i.fa-bell")).toBeVisible();
  });
});
