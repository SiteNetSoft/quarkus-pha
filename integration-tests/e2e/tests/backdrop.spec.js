import { test, expect } from "@playwright/test";

test.describe("Backdrop", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/backdrop");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of [
      "examples",
      "basic",
      "with-content",
      "non-dismissible",
      "documentation",
      "props-backdrop",
      "usage",
    ]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic", () => {
    test("backdrop is hidden by default", async ({ page }) => {
      await expect(page.locator("#backdrop-basic")).not.toBeVisible();
    });

    test("toggle shows backdrop; click dismisses", async ({ page }) => {
      await page.locator("#backdrop-basic-toggle").click();
      await expect(page.locator("#backdrop-basic")).toBeVisible();
      await expect(page.locator("#backdrop-basic")).toHaveClass(/pf-v6-c-backdrop/);
      await page.locator("#backdrop-basic").click();
      await expect(page.locator("#backdrop-basic")).not.toBeVisible();
    });
  });

  test.describe("With content", () => {
    test("overlay content renders inside the backdrop", async ({ page }) => {
      await page.locator("#backdrop-content-toggle").click();
      await expect(page.locator("#backdrop-content")).toBeVisible();
      // Content slot should be a descendant of the backdrop.
      await expect(page.locator("#backdrop-content #backdrop-content-overlay")).toBeVisible();
      // Spinner inside the content slot.
      await expect(page.locator("#backdrop-content .pf-v6-c-spinner")).toBeVisible();
    });

    test("clicking the inner panel does NOT dismiss", async ({ page }) => {
      await page.locator("#backdrop-content-toggle").click();
      await page.locator("#backdrop-content-overlay").click();
      await expect(page.locator("#backdrop-content")).toBeVisible();
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

  test("standalone example routes render", async ({ page }) => {
    for (const slug of ["basic", "with-content", "non-dismissible"]) {
      const res = await page.goto(`/components/backdrop/${slug}`);
      expect(res.status()).toBe(200);
    }
  });
});
