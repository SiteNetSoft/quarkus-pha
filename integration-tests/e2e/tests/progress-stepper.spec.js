import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "horizontal",
  "vertical",
  "compact",
  "with-alignment",
  "with-issue",
  "with-failure",
  "custom-icons",
  "help-popover",
];

test.describe("Progress Stepper", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/progress-stepper");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("base variants carry their modifiers", async ({ page }) => {
    await expect(page.locator("#ps-horizontal .pf-v6-c-progress-stepper__step")).toHaveCount(3);
    await expect(page.locator("#ps-vertical")).toHaveClass(/pf-m-vertical/);
    await expect(page.locator("#ps-compact")).toHaveClass(/pf-m-compact/);
    await expect(page.locator("#ps-alignment")).toHaveClass(/pf-m-center/);
  });

  test("issue and failure steps carry status classes", async ({ page }) => {
    await expect(page.locator("#ps-issue .pf-v6-c-progress-stepper__step.pf-m-warning")).toBeAttached();
    await expect(page.locator("#ps-failure .pf-v6-c-progress-stepper__step.pf-m-danger")).toBeAttached();
  });

  test("custom icons render in pf-m-custom steps", async ({ page }) => {
    const steps = page.locator("#ps-custom-icons .pf-v6-c-progress-stepper__step");
    await expect(steps.first()).toHaveClass(/pf-m-custom/);
    await expect(steps.first().locator(".pf-v6-c-progress-stepper__step-icon svg")).toBeVisible();
  });

  test("help popover opens from the step title", async ({ page }) => {
    const step = page.locator("#ps-help-popover .pf-v6-c-progress-stepper__step.pf-m-current");
    const title = step.locator(".pf-v6-c-progress-stepper__step-title.pf-m-help-text");
    await expect(step.locator(".pf-v6-c-popover")).toBeHidden();
    await title.click();
    await expect(step.locator(".pf-v6-c-popover")).toBeVisible();
    await page.keyboard.press("Escape");
    await expect(step.locator(".pf-v6-c-popover")).toBeHidden();
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/progress-stepper/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/progress-stepper/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-progress-stepper").first()).toBeAttached();
      });
    }
  });
});
