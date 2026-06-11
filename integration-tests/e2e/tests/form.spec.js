import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "horizontal",
  "limit-width",
  "invalid",
  "invalid-form-alert",
  "validated",
  "horizontal-stacked",
  "horizontal-helper-on-top",
  "group-label-info",
  "sections",
  "grid",
  "field-groups",
  "with-helper",
];

test.describe("Form", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/form");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Layout variants", () => {
    test("basic has groups; horizontal and limit-width carry their modifiers", async ({ page }) => {
      await expect(page.locator("#form-basic .pf-v6-c-form__group").first()).toBeVisible();
      await expect(page.locator("#form-horizontal")).toHaveClass(/pf-m-horizontal/);
      await expect(page.locator("#form-limit-width")).toHaveClass(/pf-m-limit-width/);
    });

    test("horizontal stacked uses no-padding-top label", async ({ page }) => {
      await expect(
        page.locator("#form-horizontal-stacked .pf-v6-c-form__group-label.pf-m-no-padding-top"),
      ).toBeAttached();
    });

    test("grid form lays groups on the PF grid", async ({ page }) => {
      await expect(page.locator("#form-grid .pf-v6-l-grid__item.pf-m-6-col-on-md")).toHaveCount(2);
    });
  });

  test.describe("Validation", () => {
    test("invalid-form-alert shows a danger alert plus field error", async ({ page }) => {
      await expect(page.locator("#form-invalid-alert .pf-v6-c-alert.pf-m-danger")).toBeVisible();
      await expect(page.locator("#form-invalid-alert .pf-v6-c-form-control.pf-m-error")).toBeAttached();
    });

    test("validated flips between error and success as you type", async ({ page }) => {
      const control = page.locator("#form-validated .pf-v6-c-form-control");
      const input = page.locator("#form-validated-age");
      await input.fill("abc");
      await expect(control).toHaveClass(/pf-m-error/);
      await expect(page.locator("#form-validated .pf-v6-c-helper-text__item.pf-m-error")).toBeVisible();
      await input.fill("30");
      await expect(control).toHaveClass(/pf-m-success/);
      await expect(page.locator("#form-validated .pf-v6-c-helper-text__item.pf-m-success")).toBeVisible();
    });
  });

  test.describe("Structure", () => {
    test("label info renders beside the label", async ({ page }) => {
      await expect(page.locator("#form-group-label-info .pf-v6-c-form__group-label-info")).toHaveText(
        "Additional label info",
      );
    });

    test("sections have titles", async ({ page }) => {
      await expect(page.locator("#form-sections .pf-v6-c-form__section-title")).toHaveCount(2);
    });

    test("expandable field group toggles its body", async ({ page }) => {
      const group = page.locator("#form-field-groups .pf-v6-c-form__field-group.pf-m-expandable");
      await expect(group).toHaveClass(/pf-m-expanded/);
      const body = group.locator(".pf-v6-c-form__field-group-body");
      await expect(body).toBeVisible();
      await group.locator(".pf-v6-c-form__field-group-toggle button").click();
      await expect(body).toBeHidden();
      await expect(group).not.toHaveClass(/pf-m-expanded/);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/form/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/form/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-form").first()).toBeAttached();
      });
    }
  });
});
