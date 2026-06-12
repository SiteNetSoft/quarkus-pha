import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "controlled",
  "reversed",
  "label-wraps",
  "disabled",
  "with-description",
  "with-body",
  "description-and-body",
  "standalone",
];

test.describe("Radio", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/radio");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("controlled group reports the selection", async ({ page }) => {
    const card = page.locator('[data-rendered-href="/components/radio/controlled"]');
    await expect(card.locator("p strong")).toHaveText("one");
    await page.locator("#rd-controlled-2-field").check();
    await expect(card.locator("p strong")).toHaveText("two");
    await expect(page.locator("#rd-controlled-1-field")).not.toBeChecked();
  });

  test("reversed puts the label before the input", async ({ page }) => {
    const first = page.locator("#rd-reversed > *").first();
    await expect(first).toHaveClass(/pf-v6-c-radio__label/);
  });

  test("description and body render together", async ({ page }) => {
    await expect(page.locator("#rd-desc-body .pf-v6-c-radio__description")).toBeVisible();
    await expect(page.locator("#rd-desc-body .pf-v6-c-radio__body")).toBeVisible();
  });

  test("disabled and standalone states", async ({ page }) => {
    await expect(page.locator("#r-dis-one input")).toBeDisabled();
    await expect(page.locator("#r-standalone")).toHaveClass(/pf-m-standalone/);
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/radio/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/radio/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-radio").first()).toBeAttached();
      });
    }
  });
});
