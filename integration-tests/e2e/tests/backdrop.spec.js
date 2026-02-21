import { test, expect } from "@playwright/test";

test.describe("Backdrop", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/backdrop");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#with-content-heading")).toBeVisible();
    await expect(page.locator("#non-dismissible-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("backdrop is hidden by default", async ({ page }) => {
      await expect(page.locator("#backdrop-basic")).not.toBeVisible();
    });

    test("toggle shows backdrop then click dismisses", async ({ page }) => {
      await page.locator("#backdrop-basic-toggle").click();
      await expect(page.locator("#backdrop-basic")).toBeVisible();
      await page.locator("#backdrop-basic").click();
      await expect(page.locator("#backdrop-basic")).not.toBeVisible();
    });

    test("has correct pf-v6-c-backdrop class", async ({ page }) => {
      await expect(page.locator("#backdrop-basic")).toHaveClass(/pf-v6-c-backdrop/);
    });
  });

  test.describe("With content", () => {
    test("overlay is visible on toggle", async ({ page }) => {
      await page.locator("#backdrop-content-toggle").click();
      await expect(page.locator("#backdrop-content")).toBeVisible();
      await expect(page.locator("#backdrop-content-overlay")).toBeVisible();
    });
  });

  test.describe("Non-dismissible", () => {
    test("click on backdrop does not dismiss", async ({ page }) => {
      await page.locator("#backdrop-nondismiss-toggle").click();
      await expect(page.locator("#backdrop-nondismiss")).toBeVisible();
      await page.locator("#backdrop-nondismiss").click({ position: { x: 10, y: 10 }, force: true });
      await expect(page.locator("#backdrop-nondismiss")).toBeVisible();
    });

    test("close button dismisses backdrop", async ({ page }) => {
      await page.locator("#backdrop-nondismiss-toggle").click();
      await expect(page.locator("#backdrop-nondismiss")).toBeVisible();
      await page.locator("#backdrop-nondismiss-close").click();
      await expect(page.locator("#backdrop-nondismiss")).not.toBeVisible();
    });
  });
});
