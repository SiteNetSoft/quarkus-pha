import { test, expect } from "@playwright/test";

test.describe("Progress", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/progress");
  });

  test("page loads with all 6 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#small-heading")).toBeVisible();
    await expect(page.locator("#large-heading")).toBeVisible();
    await expect(page.locator("#success-heading")).toBeVisible();
    await expect(page.locator("#warning-heading")).toBeVisible();
    await expect(page.locator("#danger-heading")).toBeVisible();
  });

  test("basic progress has bar and indicator", async ({ page }) => {
    await expect(page.locator("#prog-basic .pf-v6-c-progress__bar")).toBeVisible();
    await expect(page.locator("#prog-basic .pf-v6-c-progress__indicator")).toBeVisible();
  });

  test("basic progress shows measure text", async ({ page }) => {
    await expect(page.locator("#prog-basic .pf-v6-c-progress__measure")).toHaveText("50%");
  });

  test("small progress has sm modifier", async ({ page }) => {
    await expect(page.locator("#prog-small")).toHaveClass(/pf-m-sm/);
  });

  test("large progress has lg modifier", async ({ page }) => {
    await expect(page.locator("#prog-large")).toHaveClass(/pf-m-lg/);
  });

  test("success progress has success modifier", async ({ page }) => {
    await expect(page.locator("#prog-success")).toHaveClass(/pf-m-success/);
  });

  test("danger progress has danger modifier and icon", async ({ page }) => {
    await expect(page.locator("#prog-danger")).toHaveClass(/pf-m-danger/);
    await expect(page.locator("#prog-danger .pf-v6-c-progress__status-icon")).toBeVisible();
  });
});
