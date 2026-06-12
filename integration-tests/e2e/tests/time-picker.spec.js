import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "twelve-hour",
  "custom-delimiter",
  "min-max",
  "with-seconds",
  "twenty-four-with-seconds",
];

test.describe("Time Picker", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/time-picker");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic opens the slot menu and picking fills the input", async ({ page }) => {
    await page.locator("#tp-basic button[aria-label='Toggle time picker']").click();
    const menu = page.locator("#tp-basic .pf-v6-c-menu");
    await expect(menu).toBeVisible();
    await menu.locator(".pf-v6-c-menu__item").first().click();
    await expect(page.locator("#tp-basic input")).toHaveValue("00:00");
  });

  test("12-hour slots carry AM/PM labels", async ({ page }) => {
    await page.locator("#tp-12h button[aria-label='Toggle time picker']").click();
    await expect(page.locator("#tp-12h .pf-v6-c-menu__item-text").first()).toHaveText("12:00 AM");
  });

  test("custom delimiter uses a period", async ({ page }) => {
    await page.locator("#tp-delimiter button[aria-label='Toggle time picker']").click();
    await expect(page.locator("#tp-delimiter .pf-v6-c-menu__item-text").first()).toHaveText("00.00");
  });

  test("seconds variants include seconds", async ({ page }) => {
    await page.locator("#tp-24-seconds button[aria-label='Toggle time picker']").click();
    await expect(page.locator("#tp-24-seconds .pf-v6-c-menu__item-text").first()).toHaveText("00:00:00");
  });

  test("min-max errors outside the window and offers only valid slots", async ({ page }) => {
    const input = page.locator("#tp-min-max-field");
    await input.fill("08:00");
    await expect(page.locator("#tp-min-max .pf-v6-c-helper-text__item.pf-m-error")).toBeVisible();
    await input.fill("10:30");
    await expect(page.locator("#tp-min-max .pf-v6-c-helper-text__item.pf-m-error")).toBeHidden();
    await page.locator("#tp-min-max button[aria-label='Toggle time picker']").click();
    await expect(page.locator("#tp-min-max .pf-v6-c-menu__item-text").first()).toHaveText("09:00");
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/time-picker/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/time-picker/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-time-picker").first()).toBeAttached();
      });
    }
  });
});
