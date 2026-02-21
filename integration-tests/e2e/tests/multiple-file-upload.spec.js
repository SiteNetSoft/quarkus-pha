import { test, expect } from "@playwright/test";

test.describe("Multiple File Upload", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/multiple-file-upload");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Multiple File Upload");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#horizontal-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("basic wrapper exists with correct class", async ({ page }) => {
      await expect(page.locator("#mfu-basic")).toHaveClass(
        /pf-v6-c-multiple-file-upload/
      );
    });

    test("has upload title text", async ({ page }) => {
      await expect(
        page.locator(
          "#mfu-basic .pf-v6-c-multiple-file-upload__title-text"
        ).first()
      ).toBeVisible();
    });

    test("has Browse button", async ({ page }) => {
      await expect(
        page.locator("#mfu-basic .pf-v6-c-button.pf-m-secondary")
      ).toBeVisible();
    });

    test("has status text", async ({ page }) => {
      await expect(
        page.locator("#mfu-basic .pf-v6-c-multiple-file-upload__status-text")
      ).toBeVisible();
    });
  });

  test.describe("Horizontal", () => {
    test("horizontal wrapper has pf-m-horizontal class", async ({ page }) => {
      await expect(page.locator("#mfu-horizontal")).toHaveClass(
        /pf-m-horizontal/
      );
    });
  });
});
