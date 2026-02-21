import { test, expect } from "@playwright/test";

test.describe("Modal", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/modal");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#large-heading")).toBeVisible();
    await expect(page.locator("#warning-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("modal backdrop is hidden by default", async ({ page }) => {
      await expect(page.locator("#modal-basic-backdrop")).not.toBeVisible();
    });

    test("clicking trigger shows modal with pf-v6-c-modal-box class", async ({ page }) => {
      await page.locator('button:has-text("Open basic modal")').click();
      await expect(page.locator("#modal-basic-backdrop")).toBeVisible();
      await expect(page.locator("#modal-basic-backdrop .pf-v6-c-modal-box")).toBeVisible();
    });

    test("clicking close button hides modal", async ({ page }) => {
      await page.locator('button:has-text("Open basic modal")').click();
      await expect(page.locator("#modal-basic-backdrop")).toBeVisible();
      await page.locator("#modal-basic-backdrop .pf-v6-c-modal-box__close button").click();
      await expect(page.locator("#modal-basic-backdrop")).not.toBeVisible();
    });
  });

  test.describe("Large", () => {
    test("modal-box has class pf-m-lg", async ({ page }) => {
      await page.locator('button:has-text("Open large modal")').click();
      await expect(page.locator("#modal-large-backdrop .pf-v6-c-modal-box")).toHaveClass(/pf-m-lg/);
    });
  });

  test.describe("Warning", () => {
    test("modal-box has class pf-m-warning", async ({ page }) => {
      await page.locator('button:has-text("Open warning modal")').click();
      await expect(page.locator("#modal-warning-backdrop .pf-v6-c-modal-box")).toHaveClass(/pf-m-warning/);
    });
  });
});
