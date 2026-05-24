import { test, expect } from "@playwright/test";

test.describe("Progress Stepper", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/progress-stepper");
  });

  test("page loads with all section headings", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#horizontal")).toBeVisible();
    await expect(page.locator("#vertical")).toBeVisible();
    await expect(page.locator("#compact")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-progress-stepper")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Horizontal", () => {
    test("has pf-v6-c-progress-stepper class", async ({ page }) => {
      await expect(page.locator("#ps-horizontal")).toHaveClass(/pf-v6-c-progress-stepper/);
    });

    test("has step elements with connector and title", async ({ page }) => {
      await expect(page.locator("#ps-horizontal .pf-v6-c-progress-stepper__step").first()).toBeVisible();
      await expect(page.locator("#ps-horizontal .pf-v6-c-progress-stepper__step-connector").first()).toBeVisible();
      await expect(page.locator("#ps-horizontal .pf-v6-c-progress-stepper__step-title").first()).toBeVisible();
    });

    test("one step has pf-m-current", async ({ page }) => {
      await expect(page.locator("#ps-horizontal .pf-v6-c-progress-stepper__step.pf-m-current")).toBeVisible();
    });

    test("some steps have pf-m-success", async ({ page }) => {
      const successCount = await page.locator("#ps-horizontal .pf-v6-c-progress-stepper__step.pf-m-success").count();
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
