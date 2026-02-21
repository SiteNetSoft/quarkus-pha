import { test, expect } from "@playwright/test";

test.describe("Progress Stepper", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/progress-stepper");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#vertical-heading")).toBeVisible();
    await expect(page.locator("#compact-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-progress-stepper class", async ({ page }) => {
      await expect(page.locator("#ps-basic")).toHaveClass(/pf-v6-c-progress-stepper/);
    });

    test("has step elements with connector and title", async ({ page }) => {
      await expect(page.locator("#ps-basic .pf-v6-c-progress-stepper__step").first()).toBeVisible();
      await expect(page.locator("#ps-basic .pf-v6-c-progress-stepper__step-connector").first()).toBeVisible();
      await expect(page.locator("#ps-basic .pf-v6-c-progress-stepper__step-title").first()).toBeVisible();
    });

    test("one step has pf-m-current", async ({ page }) => {
      await expect(page.locator("#ps-basic .pf-v6-c-progress-stepper__step.pf-m-current")).toBeVisible();
    });

    test("some steps have pf-m-success", async ({ page }) => {
      const successCount = await page.locator("#ps-basic .pf-v6-c-progress-stepper__step.pf-m-success").count();
      expect(successCount).toBeGreaterThan(0);
    });
  });

  test.describe("Vertical", () => {
    test("has class pf-m-vertical", async ({ page }) => {
      await expect(page.locator("#ps-vertical")).toHaveClass(/pf-m-vertical/);
    });
  });

  test.describe("Compact", () => {
    test("has class pf-m-compact", async ({ page }) => {
      await expect(page.locator("#ps-compact")).toHaveClass(/pf-m-compact/);
    });
  });
});
