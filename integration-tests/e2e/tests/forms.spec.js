import { test, expect } from "@playwright/test";

const FORMS = {
  form: ["basic", "horizontal", "with-helper", "invalid"],
  "form-control": ["basic", "validated", "disabled-readonly"],
  checkbox: ["basic", "checked", "disabled", "reversed", "with-description", "with-body", "required", "standalone"],
  "form-select": ["basic", "grouped", "validated", "disabled"],
  radio: ["basic", "disabled", "with-description", "with-body", "standalone"],
  "text-area": ["basic", "resize-vertical", "invalid", "disabled-readonly"],
  "text-input": ["basic", "disabled", "readonly", "invalid", "with-icon", "types"],
};

for (const [name, examples] of Object.entries(FORMS)) {
  test.describe(`Forms — ${name}`, () => {
    test("demo TOC anchors render", async ({ page }) => {
      await page.goto(`/components/${name}`);
      const anchors = ["examples", ...examples, "documentation", `props-${name}`, "usage"];
      for (const id of anchors) {
        await expect(page.locator(`#${id}`)).toBeAttached();
      }
    });

    for (const example of examples) {
      test(`standalone /components/${name}/${example} renders 200`, async ({ page }) => {
        const res = await page.goto(`/components/${name}/${example}`);
        expect(res.status()).toBe(200);
      });

      test(`source /components/${name}/source/${example} returns Qute markup`, async ({ request }) => {
        const res = await request.get(`/components/${name}/source/${example}`);
        expect(res.status()).toBe(200);
        const body = await res.text();
        expect(body.length).toBeGreaterThan(0);
      });
    }
  });
}

test.describe("Forms — runtime class assertions", () => {
  test("text-input/invalid carries pf-m-error + aria-invalid", async ({ page }) => {
    await page.goto("/components/text-input/invalid");
    await expect(page.locator("#ti-invalid")).toHaveClass(/pf-m-error/);
    await expect(page.locator("#ti-invalid-field")).toHaveAttribute("aria-invalid", "true");
  });

  test("text-input/with-icon carries pf-m-icon + renders the SVG", async ({ page }) => {
    await page.goto("/components/text-input/with-icon");
    await expect(page.locator("#ti-icon")).toHaveClass(/pf-m-icon/);
    await expect(page.locator("#ti-icon .pf-v6-c-form-control__icon svg")).toBeVisible();
  });

  test("text-area/resize-vertical carries pf-m-resize-vertical", async ({ page }) => {
    await page.goto("/components/text-area/resize-vertical");
    await expect(page.locator("#ta-resize-v")).toHaveClass(/pf-m-resize-vertical/);
  });

  test("form-select/validated carries pf-m-error + status icon", async ({ page }) => {
    await page.goto("/components/form-select/validated");
    await expect(page.locator("#fs-error")).toHaveClass(/pf-m-error/);
    await expect(page.locator("#fs-error .pf-v6-c-form-control__icon.pf-m-status svg")).toBeVisible();
  });

  test("checkbox/standalone carries pf-m-standalone + aria-label", async ({ page }) => {
    await page.goto("/components/checkbox/standalone");
    await expect(page.locator("#cb-standalone-wrap")).toHaveClass(/pf-m-standalone/);
    await expect(page.locator("#cb-standalone")).toHaveAttribute("aria-label", "Select all rows");
  });

  test("checkbox/reversed carries pf-m-reversed", async ({ page }) => {
    await page.goto("/components/checkbox/reversed");
    await expect(page.locator("#cb-reversed-wrap")).toHaveClass(/pf-m-reversed/);
  });

  test("radio/basic groups two radios by name", async ({ page }) => {
    await page.goto("/components/radio/basic");
    const radios = page.locator('input[type="radio"][name="r-basic"]');
    await expect(radios).toHaveCount(2);
  });

  test("form/horizontal carries pf-m-horizontal", async ({ page }) => {
    await page.goto("/components/form/horizontal");
    await expect(page.locator("#form-horizontal")).toHaveClass(/pf-m-horizontal/);
  });

  test("form-select/grouped renders optgroup elements", async ({ page }) => {
    await page.goto("/components/form-select/grouped");
    await expect(page.locator("#fs-grouped-field optgroup")).toHaveCount(2);
  });
});
