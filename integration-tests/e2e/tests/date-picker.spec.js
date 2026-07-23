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
    await expect(page.locator("#dp-basic-input")).toBeVisible();
    await expect(page.locator("#dp-basic button[aria-label='Toggle date picker']")).toBeVisible();
  });

  test("required shows the error helper after blurring empty", async ({ page }) => {
    const input = page.locator("#dp-required-field");
    await input.click();
    await input.blur();
    await expect(page.locator("#dp-required .pf-v6-c-helper-text__item.pf-m-error")).toBeVisible();
    await expect(page.locator("#dp-required .pf-v6-c-date-picker__input .pf-v6-c-form-control")).toHaveClass(/pf-m-error/);
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

  test.describe("Interactive calendar popover (PF behavior)", () => {
    test("toggle opens a real calendar and picking a date fills the input and closes", async ({ page }) => {
      const picker = page.locator("#dp-basic");
      await picker.locator('button[aria-label="Toggle date picker"]').click();
      const calendar = picker.locator(".pf-v6-c-date-picker__calendar");
      await expect(calendar).toBeVisible();
      await expect(calendar.locator(".pf-v6-c-calendar-month")).toBeVisible();
      await calendar.locator('button[aria-label="21 May 2026"]').click();
      await expect(picker.locator("#dp-basic-input")).toHaveValue("2026-05-21");
      await expect(calendar).toBeHidden(); // picking closes the popover
    });

    test("month navigation works inside the popover", async ({ page }) => {
      const picker = page.locator("#dp-value");
      await picker.locator('button[aria-label="Toggle date picker"]').click();
      const calendar = picker.locator(".pf-v6-c-date-picker__calendar");
      await calendar.locator('button[aria-label="Next month"]').click();
      await expect(calendar.locator(".pf-v6-c-menu-toggle__text")).toHaveText("June");
      await expect(calendar).toBeVisible(); // nav keeps the popover open
    });

    test("with-value calendar opens on the pre-selected date", async ({ page }) => {
      const picker = page.locator("#dp-value");
      await picker.locator('button[aria-label="Toggle date picker"]').click();
      const selected = picker.locator("td.pf-m-selected button");
      await expect(selected).toHaveAttribute("aria-label", "20 May 2026");
    });

    test("american format example formats the picked date as MM/DD/YYYY", async ({ page }) => {
      const picker = page.locator("#dp-american");
      await picker.locator('button[aria-label="Toggle date picker"]').click();
      await picker.locator('button[aria-label="10 March 2026"]').click();
      await expect(page.locator("#dp-american-field")).toHaveValue("03/10/2026");
    });

    test("x-model examples receive the picked date (helper text reacts)", async ({ page }) => {
      const picker = page.locator("#dp-helper-text");
      await picker.locator('button[aria-label="Toggle date picker"]').click();
      await picker.locator('button[aria-label="7 May 2026"]').click();
      await expect(page.locator("#dp-helper-text-field")).toHaveValue("2026-05-07");
      await expect(picker.locator(".pf-v6-c-helper-text__item-text")).toContainText("Selected: 2026-05-07");
    });
  });
});
