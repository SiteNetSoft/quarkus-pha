import { test, expect } from "@playwright/test";

test.describe("Multiple File Upload", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/multiple-file-upload");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Multiple file upload");
  });

  test("both example sections appear in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#horizontal")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("basic wrapper has the multiple-file-upload class", async ({ page }) => {
      await expect(page.locator("#mfu-basic")).toHaveClass(/pf-v6-c-multiple-file-upload/);
    });

    test("has a title-text block", async ({ page }) => {
      await expect(
        page.locator("#mfu-basic .pf-v6-c-multiple-file-upload__title-text").first()
      ).toBeVisible();
    });

    test("has an Upload button", async ({ page }) => {
      const btn = page.locator("#mfu-basic .pf-v6-c-button.pf-m-secondary");
      await expect(btn).toBeVisible();
      await expect(btn).toContainText("Upload");
    });
  });

  test.describe("Horizontal", () => {
    test("horizontal wrapper has pf-m-horizontal class", async ({ page }) => {
      await expect(page.locator("#mfu-horizontal")).toHaveClass(/pf-m-horizontal/);
    });
  });
});
