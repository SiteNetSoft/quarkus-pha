import { test, expect } from "@playwright/test";

test.describe("Alert", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/alert");
  });

  test("page loads with all 4 example section headings", async ({ page }) => {
    await expect(page.locator("#variants")).toBeVisible();
    await expect(page.locator("#with-description")).toBeVisible();
    await expect(page.locator("#closable")).toBeVisible();
    await expect(page.locator("#inline")).toBeVisible();
  });

  test.describe("Variants", () => {
    test("correct modifier classes for all 5 variants", async ({ page }) => {
      await expect(page.locator("#al-custom")).toHaveClass(/pf-m-custom/);
      await expect(page.locator("#al-info")).toHaveClass(/pf-m-info/);
      await expect(page.locator("#al-success")).toHaveClass(/pf-m-success/);
      await expect(page.locator("#al-warning")).toHaveClass(/pf-m-warning/);
      await expect(page.locator("#al-danger")).toHaveClass(/pf-m-danger/);
    });

    test("icon element present in each alert", async ({ page }) => {
      const variants = ["custom", "info", "success", "warning", "danger"];
      for (const variant of variants) {
        const alert = page.locator(`#al-${variant}`);
        await expect(alert.locator(".pf-v6-c-alert__icon")).toBeVisible();
      }
    });
  });

  test.describe("With description", () => {
    test("has title and description element", async ({ page }) => {
      const alert = page.locator("#al-desc");
      await expect(alert.locator(".pf-v6-c-alert__title")).toBeVisible();
      await expect(alert.locator(".pf-v6-c-alert__description")).toBeVisible();
    });
  });

  test.describe("Closable", () => {
    test("close button exists with aria-label", async ({ page }) => {
      const alert = page.locator("#al-close");
      const closeBtn = alert.locator(".pf-v6-c-alert__action button");
      await expect(closeBtn).toBeVisible();
      await expect(closeBtn).toHaveAttribute("aria-label");
    });

    test("clicking close hides the alert", async ({ page }) => {
      const alert = page.locator("#al-close");
      await expect(alert).toBeVisible();
      await alert.locator(".pf-v6-c-alert__action button").click();
      await expect(alert).toBeHidden();
    });
  });

  test.describe("Inline", () => {
    test("inline alert has pf-m-inline class", async ({ page }) => {
      await expect(page.locator("#al-inline")).toHaveClass(/pf-m-inline/);
    });
  });
});
