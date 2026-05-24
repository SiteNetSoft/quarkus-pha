import { test, expect } from "@playwright/test";

test.describe("Popover", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/popover");
  });

  test("page loads with both example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#danger")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-popover and pf-m-top classes", async ({ page }) => {
      await expect(page.locator("#po-basic")).toHaveClass(/pf-v6-c-popover/);
      await expect(page.locator("#po-basic")).toHaveClass(/pf-m-top/);
    });

    test("has arrow, header, and body", async ({ page }) => {
      await expect(page.locator("#po-basic .pf-v6-c-popover__arrow")).toBeVisible();
      await expect(page.locator("#po-basic .pf-v6-c-popover__header")).toBeVisible();
      await expect(page.locator("#po-basic .pf-v6-c-popover__body")).toBeVisible();
    });

    test("has role dialog and aria-labelledby pointing at title", async ({ page }) => {
      const popover = page.locator("#po-basic");
      await expect(popover).toHaveAttribute("role", "dialog");
      await expect(popover).toHaveAttribute("aria-labelledby", "po-basic-title");
      await expect(page.locator("#po-basic-title")).toBeVisible();
    });
  });

  test.describe("Danger", () => {
    test("has classes pf-m-top and pf-m-danger", async ({ page }) => {
      await expect(page.locator("#po-danger")).toHaveClass(/pf-m-top/);
      await expect(page.locator("#po-danger")).toHaveClass(/pf-m-danger/);
    });

    test("has header with title id and a footer with actions", async ({ page }) => {
      await expect(page.locator("#po-danger .pf-v6-c-popover__header")).toBeVisible();
      await expect(page.locator("#po-danger-title")).toBeVisible();
      await expect(page.locator("#po-danger .pf-v6-c-popover__footer")).toBeVisible();
      await expect(page.locator("#po-danger .pf-v6-c-popover__footer button")).toHaveCount(2);
    });

    test("has role dialog and aria-labelledby pointing at danger title", async ({ page }) => {
      const popover = page.locator("#po-danger");
      await expect(popover).toHaveAttribute("role", "dialog");
      await expect(popover).toHaveAttribute("aria-labelledby", "po-danger-title");
    });
  });
});
