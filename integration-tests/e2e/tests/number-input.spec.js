import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "with-unit",
  "bounded",
  "disabled",
  "with-status",
  "varying-sizes",
  "custom-step",
  "custom-step-threshold",
];

test.describe("Number Input", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/number-input");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic increments and decrements", async ({ page }) => {
    const root = page.locator("#ni-basic");
    await expect(root.locator("input")).toHaveValue("5");
    await root.locator("button[aria-label='Plus']").click();
    await expect(root.locator("input")).toHaveValue("6");
    await root.locator("button[aria-label='Minus']").click();
    await expect(root.locator("input")).toHaveValue("5");
  });

  test("status follows the value", async ({ page }) => {
    const root = page.locator("#ni-status");
    const control = root.locator(".pf-v6-c-form-control");
    await expect(control).toHaveClass(/pf-m-success/);
    await root.locator("button[aria-label='Minus']").click();
    await expect(control).toHaveClass(/pf-m-warning/);
    await root.locator("button[aria-label='Minus']").click();
    await expect(control).toHaveClass(/pf-m-error/);
  });

  test("custom step moves by 3 and threshold clamps", async ({ page }) => {
    const step = page.locator("#ni-custom-step");
    await step.locator("button[aria-label='Plus']").click();
    await expect(step.locator("input")).toHaveValue("93");
    const clamped = page.locator("#ni-custom-step-threshold");
    await clamped.locator("button[aria-label='Plus']").click();
    await clamped.locator("button[aria-label='Plus']").click();
    await expect(clamped.locator("input")).toHaveValue("12");
    await expect(clamped.locator("button[aria-label='Plus']")).toBeDisabled();
  });

  test("bounded and disabled states", async ({ page }) => {
    await expect(page.locator("#ni-bounded")).toBeAttached();
    await expect(page.locator("#ni-disabled input")).toBeDisabled();
  });

  test("varying sizes render three widths", async ({ page }) => {
    for (const id of ["ni-size-1", "ni-size-2", "ni-size-3"]) {
      await expect(page.locator(`#${id} input`)).toBeVisible();
    }
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/number-input/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/number-input/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-number-input").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/number-input");
      for (const ex of ["basic", "custom-step-threshold", "with-status"]) {
        const card = page.locator(`[data-rendered-href="/components/number-input/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/number-input/source-java/custom-step-threshold");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".step(3).min(0).max(12)");
    });

    test("min(0) actually clamps — the shell dropped falsy zero bounds", async ({ page }) => {
      await page.goto("/components/number-input/bounded");
      const input = page.locator("#ni-bounded input");
      await expect(input).toHaveAttribute("min", "0");
      const minus = page.locator('#ni-bounded button[aria-label="Minus"]');
      for (let i = 0; i < 3; i++) await minus.click();
      await expect(input).toHaveValue("0");
      await expect(minus).toBeDisabled();
    });
  });
});
