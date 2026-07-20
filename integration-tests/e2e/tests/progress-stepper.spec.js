import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "horizontal",
  "vertical",
  "vertical-responsive",
  "compact",
  "compact-vertical",
  "compact-vertical-responsive",
  "compact-vertical-center",
  "compact-center",
  "with-alignment",
  "center-vertical",
  "with-issue",
  "with-failure",
  "in-process",
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

  test("with-alignment and compact carry descriptions", async ({ page }) => {
    await expect(page.locator("#ps-alignment .pf-v6-c-progress-stepper__step-description")).toHaveCount(3);
    await expect(page.locator("#ps-compact .pf-v6-c-progress-stepper__step-description")).toHaveCount(3);
  });

  test("permutation variants carry their modifier combinations", async ({ page }) => {
    await expect(page.locator("#ps-vertical-responsive")).toHaveClass(/pf-m-vertical-on-lg/);
    await expect(page.locator("#ps-vertical-responsive")).toHaveClass(/pf-m-horizontal-on-2xl/);
    await expect(page.locator("#ps-center-vertical")).toHaveClass(/pf-m-center/);
    await expect(page.locator("#ps-center-vertical")).toHaveClass(/pf-m-vertical/);
    await expect(page.locator("#ps-compact-vertical")).toHaveClass(/pf-m-compact/);
    await expect(page.locator("#ps-compact-vertical")).toHaveClass(/pf-m-vertical/);
    await expect(page.locator("#ps-compact-vertical-responsive")).toHaveClass(/pf-m-vertical-on-lg/);
    await expect(page.locator("#ps-compact-vertical-responsive")).toHaveClass(/pf-m-horizontal-on-xl/);
    const cvc = page.locator("#ps-compact-vertical-center");
    await expect(cvc).toHaveClass(/pf-m-compact/);
    await expect(cvc).toHaveClass(/pf-m-vertical/);
    await expect(cvc).toHaveClass(/pf-m-center/);
    await expect(page.locator("#ps-compact-center")).toHaveClass(/pf-m-center/);
  });

  test("in-process steppers show a spinner on the current step", async ({ page }) => {
    await expect(page.locator("#ps-in-process .pf-m-current .pf-v6-c-spinner")).toBeVisible();
    await expect(page.locator("#ps-compact .pf-m-current .pf-v6-c-spinner")).toBeVisible();
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

  test.describe("Java source tab", () => {
    // All examples except help-popover are model-driven (ProgressStepperDemoData).
    const MODEL_DRIVEN = EXAMPLES.filter((e) => e !== "help-popover");

    test("model-driven cards get a leading Java tab; help-popover does not", async ({ page }) => {
      for (const ex of MODEL_DRIVEN) {
        const card = page.locator(`[data-rendered-href="/components/progress-stepper/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      const handWritten = page.locator('[data-rendered-href="/components/progress-stepper/help-popover"]');
      await expect(handWritten.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
    });

    test("Java tab opens Monaco with the builder code", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/progress-stepper/horizontal"]');
      await card.locator('button[aria-label*="Toggle Java"]').click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
      await expect(card.locator(".monaco-editor .view-lines")).toContainText("ProgressStepper.builder()", {
        timeout: 10000,
      });
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/progress-stepper/source-java/with-failure");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".asDanger().asCurrent()");
    });
  });
});
