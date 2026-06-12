import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "required",
  "american-format",
  "helper-text",
  "min-max",
  "french",
  "with-value",
  "disabled",
];

test.describe("Date Picker", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/date-picker");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic has a date-picker with input and toggle", async ({ page }) => {
    await expect(page.locator("#dp-basic")).toHaveClass(/pf-v6-c-date-picker/);
    await expect(page.locator("#dp-basic input")).toBeVisible();
    await expect(page.locator("#dp-basic button[aria-label='Toggle date picker']")).toBeVisible();
  });

  test("required shows the error helper after blurring empty", async ({ page }) => {
    const input = page.locator("#dp-required-field");
    await input.click();
    await input.blur();
    await expect(page.locator("#dp-required .pf-v6-c-helper-text__item.pf-m-error")).toBeVisible();
    await expect(page.locator("#dp-required .pf-v6-c-form-control")).toHaveClass(/pf-m-error/);
  });

  test("helper text echoes the typed date", async ({ page }) => {
    await page.locator("#dp-helper-text-field").fill("2026-06-11");
    await expect(page.locator("#dp-helper-text .pf-v6-c-helper-text__item-text")).toHaveText("Selected: 2026-06-11");
  });

  test("min-max flags dates outside 2026", async ({ page }) => {
    const input = page.locator("#dp-min-max-field");
    await input.fill("2025-05-01");
    await expect(page.locator("#dp-min-max .pf-v6-c-helper-text__item.pf-m-error")).toBeVisible();
    await input.fill("2026-05-01");
    await expect(page.locator("#dp-min-max .pf-v6-c-helper-text__item.pf-m-error")).toBeHidden();
  });

  test("format variants show their placeholders", async ({ page }) => {
    await expect(page.locator("#dp-american-field")).toHaveValue("03/05/2026");
    await expect(page.locator("#dp-french-field")).toHaveAttribute("placeholder", "JJ/MM/AAAA");
  });

  test("disabled input and toggle", async ({ page }) => {
    await expect(page.locator("#dp-disabled input")).toBeDisabled();
    await expect(page.locator("#dp-disabled button")).toBeDisabled();
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/date-picker/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/date-picker/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-date-picker").first()).toBeAttached();
      });
    }
  });
});
