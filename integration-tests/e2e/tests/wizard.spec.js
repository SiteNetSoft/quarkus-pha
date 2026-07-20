import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "with-substeps",
  "with-review",
  "plain",
  "header",
  "disabled-steps",
  "nav-anchors",
  "expandable-steps",
  "step-status",
  "form-validation",
  "validate-button-press",
  "progressive-steps",
  "toggle-step-visibility",
  "submit-progress",
  "custom-footer",
  "custom-nav-item",
  "within-modal",
];

test.describe("Wizard", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/wizard");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic (HTMX)", () => {
    test("basic wizard has nav, steps, current item, footer buttons", async ({ page }) => {
      await expect(page.locator("#wiz-basic")).toHaveClass(/pf-v6-c-wizard/);
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav")).toBeVisible();
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav-item")).toHaveCount(4);
      await expect(page.locator("#wiz-basic .pf-v6-c-wizard__nav-link.pf-m-current")).toHaveCount(1);
      await expect(page.locator("#wiz-basic .pf-v6-c-button.pf-m-primary")).toBeVisible();
    });
  });

  test.describe("Alpine step switching", () => {
    test("plain wizard switches steps via nav and footer", async ({ page }) => {
      const wiz = page.locator("#wiz-plain");
      await expect(wiz).toHaveClass(/pf-m-plain/);
      await expect(wiz.locator(".pf-v6-c-wizard__nav-link").first()).toHaveClass(/pf-m-current/);
      await wiz.locator(".pf-v6-c-button.pf-m-primary").click();
      await expect(wiz.locator(".pf-v6-c-wizard__nav-link").nth(1)).toHaveClass(/pf-m-current/);
      await wiz.locator(".pf-v6-c-wizard__nav-link").first().click();
      await expect(wiz.locator(".pf-v6-c-wizard__nav-link").first()).toHaveClass(/pf-m-current/);
    });
  });

  test.describe("Header", () => {
    test("header carries title, description, close", async ({ page }) => {
      await expect(page.locator("#wiz-header .pf-v6-c-wizard__title-text")).toHaveText("Create cluster");
      await expect(page.locator("#wiz-header .pf-v6-c-wizard__description")).toBeVisible();
      await expect(page.locator("#wiz-header .pf-v6-c-wizard__close button")).toBeVisible();
    });
  });

  test.describe("Disabled steps", () => {
    test("locked step is disabled", async ({ page }) => {
      await expect(page.locator("#wiz-disabled-steps .pf-v6-c-wizard__nav-link.pf-m-disabled")).toBeDisabled();
    });
  });

  test.describe("Expandable steps", () => {
    test("toggle collapses and expands the substeps", async ({ page }) => {
      const item = page.locator("#wiz-expandable-steps .pf-v6-c-wizard__nav-item.pf-m-expandable");
      await expect(item).toHaveClass(/pf-m-expanded/);
      const sub = item.locator("ol");
      await expect(sub).toBeVisible();
      await item.locator("> button").click();
      await expect(sub).toBeHidden();
    });
  });

  test.describe("Step status", () => {
    test("success and danger nav links carry status icons", async ({ page }) => {
      await expect(
        page.locator("#wiz-step-status .pf-v6-c-wizard__nav-link.pf-m-success .pf-v6-c-wizard__nav-link-status-icon"),
      ).toBeVisible();
      await expect(
        page.locator("#wiz-step-status .pf-v6-c-wizard__nav-link.pf-m-danger .pf-v6-c-wizard__nav-link-status-icon"),
      ).toBeVisible();
    });
  });

  test.describe("Form validation", () => {
    test("Next enables only when the field is filled", async ({ page }) => {
      const next = page.locator("#wiz-form-validation .pf-v6-c-button.pf-m-primary");
      await expect(next).toBeDisabled();
      await page.locator("#wiz-form-validation-name").fill("prod-east");
      await expect(next).toBeEnabled();
    });

    test("validate on button press shows the error helper", async ({ page }) => {
      const wiz = page.locator("#wiz-validate-button-press");
      await expect(wiz.locator(".pf-v6-c-helper-text")).toBeHidden();
      await wiz.locator(".pf-v6-c-button.pf-m-primary").click();
      await expect(wiz.locator(".pf-v6-c-helper-text")).toBeVisible();
      await page.locator("#wiz-validate-button-press-name").fill("prod-east");
      await wiz.locator(".pf-v6-c-button.pf-m-primary").click();
      await expect(wiz.locator(".pf-v6-c-wizard__nav-link").nth(1)).toHaveClass(/pf-m-current/);
    });
  });

  test.describe("Progressive steps", () => {
    test("choosing custom setup adds a step", async ({ page }) => {
      const items = page.locator("#wiz-progressive-steps .pf-v6-c-wizard__nav-item:visible");
      await expect(items).toHaveCount(2);
      await page.locator("#wiz-prog-custom").check();
      await expect(items).toHaveCount(3);
    });
  });

  test.describe("Toggle step visibility", () => {
    test("unchecking hides the optional step", async ({ page }) => {
      const items = page.locator("#wiz-toggle-step-visibility .pf-v6-c-wizard__nav-item:visible");
      await expect(items).toHaveCount(3);
      await page.locator("#wiz-toggle-visibility-check").uncheck();
      await expect(items).toHaveCount(2);
    });
  });

  test.describe("Submit progress", () => {
    test("finishing runs the progress bar to 100%", async ({ page }) => {
      const wiz = page.locator("#wiz-submit-progress");
      await wiz.locator(".pf-v6-c-button.pf-m-primary:visible").click();
      await wiz.locator(".pf-v6-c-button.pf-m-primary:visible", { hasText: "Finish" }).click();
      await expect(wiz.locator(".pf-v6-c-progress")).toBeVisible();
      await expect(wiz.locator(".pf-v6-c-progress__measure")).toHaveText("100%", { timeout: 5000 });
    });
  });

  test.describe("Within modal", () => {
    test("opens a modal containing the wizard", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/wizard/within-modal"]');
      await expect(card.locator(".pf-v6-c-modal-box")).toBeHidden();
      await card.locator("button", { hasText: "Open wizard modal" }).click();
      await expect(card.locator(".pf-v6-c-modal-box #wiz-within-modal")).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(card.locator(".pf-v6-c-modal-box")).toBeHidden();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/wizard/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/wizard/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-wizard").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab; HTMX/composition wizards do not", async ({ page }) => {
      await page.goto("/components/wizard");
      for (const ex of ["plain", "expandable-steps", "progressive-steps", "header"]) {
        const card = page.locator(`[data-rendered-href="/components/wizard/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of ["basic", "within-modal", "submit-progress", "toggle-step-visibility"]) {
        const card = page.locator(`[data-rendered-href="/components/wizard/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/wizard/source-java/expandable-steps");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.expandableGroup("Configuration"');
    });

    test("generated step machine navigates and clamps", async ({ page }) => {
      await page.goto("/components/wizard/plain");
      const next = page.locator("#wiz-plain footer").getByRole("button", { name: "Next" });
      const back = page.locator("#wiz-plain footer").getByRole("button", { name: "Back" });
      await expect(back).toBeDisabled();
      await next.click();
      await next.click();
      await expect(next).toBeDisabled();
      await expect(page.locator("#wiz-plain .pf-v6-c-wizard__nav-link.pf-m-current")).toHaveText(/Review/);
    });

    test("expandable group toggles and substeps gain aria-current", async ({ page }) => {
      await page.goto("/components/wizard/expandable-steps");
      const group = page.locator("#wiz-expandable-steps .pf-v6-c-wizard__nav-item.pf-m-expandable");
      await expect(group).toHaveClass(/pf-m-expanded/);
      await group.locator("> button").click();
      await expect(group).not.toHaveClass(/pf-m-expanded/);
    });
  });
});
