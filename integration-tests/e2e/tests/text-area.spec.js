import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "invalid",
  "validated",
  "resize-vertical",
  "horizontal-resizable",
  "not-resizable",
  "disabled",
  "read-only",
  "disabled-readonly",
  "auto-resizing",
];

test.describe("Text Area", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/text-area");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic renders a textarea inside a pf-m-textarea form-control", async ({ page }) => {
    await expect(page.locator("#ta-basic")).toHaveClass(/pf-m-textarea/);
    await expect(page.locator("#ta-basic textarea")).toBeVisible();
  });

  test("resize modifiers are applied", async ({ page }) => {
    await expect(page.locator("#ta-resize-v")).toHaveClass(/pf-m-resize-vertical/);
    await expect(page.locator("#ta-resize-h")).toHaveClass(/pf-m-resize-horizontal/);
  });

  test("validated flips between error and success while typing", async ({ page }) => {
    const control = page.locator("#ta-validated");
    const area = page.locator("#ta-validated-field");
    await area.fill("short");
    await expect(control).toHaveClass(/pf-m-error/);
    await area.fill("long enough content");
    await expect(control).toHaveClass(/pf-m-success/);
  });

  test("disabled and read-only states", async ({ page }) => {
    await expect(page.locator("#ta-disabled-solo-field")).toBeDisabled();
    await expect(page.locator("#ta-read-only-solo-field")).toHaveAttribute("readonly", "");
  });

  test("auto-resizing grows with content", async ({ page }) => {
    const area = page.locator("#ta-auto-resizing-field");
    const before = (await area.boundingBox()).height;
    await area.click();
    await area.fill("line 1\nline 2\nline 3\nline 4\nline 5\nline 6\nline 7\nline 8");
    const after = (await area.boundingBox()).height;
    expect(after).toBeGreaterThan(before);
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/text-area/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/text-area/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-form-control").first()).toBeAttached();
      });
    }
  });
});
