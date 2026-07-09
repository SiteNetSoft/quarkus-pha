import { test, expect } from "@playwright/test";

test.describe("Notification Badge", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/notification-badge");
  });

  test("page loads with all 3 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#read")).toBeVisible();
    await expect(page.locator("#unread")).toBeVisible();
    await expect(page.locator("#attention")).toBeVisible();
  });

  test.describe("Read", () => {
    test("is a stateful button with pf-m-read", async ({ page }) => {
      await expect(page.locator("#nb-read")).toHaveClass(/pf-v6-c-button/);
      await expect(page.locator("#nb-read")).toHaveClass(/pf-m-stateful/);
      await expect(page.locator("#nb-read")).toHaveClass(/pf-m-read/);
    });
  });

  test.describe("Unread", () => {
    test("has class pf-m-unread and a count of 3", async ({ page }) => {
      await expect(page.locator("#nb-unread")).toHaveClass(/pf-m-unread/);
      await expect(page.locator("#nb-unread .pf-v6-c-button__count .pf-v6-c-badge")).toHaveText("3");
    });
  });

  test.describe("Attention", () => {
    test("has class pf-m-attention and an exclamation count", async ({ page }) => {
      await expect(page.locator("#nb-attention")).toHaveClass(/pf-m-attention/);
      await expect(page.locator("#nb-attention .pf-v6-c-button__count .pf-v6-c-badge")).toHaveText("!");
    });
  });

  test("all badges render an inline bell SVG icon", async ({ page }) => {
    for (const id of ["nb-read", "nb-unread", "nb-attention"]) {
      await expect(page.locator(`#${id} .pf-v6-c-button__icon svg`)).toBeVisible();
    }
  });
});
