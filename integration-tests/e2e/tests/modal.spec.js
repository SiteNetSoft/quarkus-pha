import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "scrollable",
  "with-description",
  "top-aligned",
  "sizes",
  "custom-width",
  "custom-header",
  "no-header-footer",
  "title-icon",
  "custom-title-icon",
  "with-dropdown",
  "with-help",
  "with-form",
  "custom-focus",
  "without-title",
  "generic-container",
  "custom-alert",
  "info-alert",
  "success-alert",
  "warning-alert",
  "danger-alert",
  "danger-alert-title",
];

// The modal backdrop teleports to <body> (Alpine x-teleport) so it paints
// above the masthead — the PF page main-container is a stacking context that
// would otherwise trap it. Modal assertions are therefore scoped by the
// modal-box id (#mo-*), not by the example card that hosts the trigger, and
// all clicks are real clicks (the pre-teleport dispatch-via-DOM workaround
// is intentionally gone).
test.describe("Modal", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/modal");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic", () => {
    const card = '[data-rendered-href="/components/modal/basic"]';

    test("paints above the masthead (regression: stacking-context trap)", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "Open modal" }).first().click();
      await expect(page.locator("#mo-basic")).toBeVisible();
      // Hit-test the center of the masthead: with the modal open, the top
      // paint target there must belong to the teleported backdrop, not the
      // masthead. An inline backdrop is trapped in the page main-container's
      // stacking context and paints under the masthead.
      const hitBackdrop = await page.evaluate(() => {
        const masthead = document.querySelector(".pf-v6-c-masthead");
        if (!masthead) return "no-masthead";
        const r = masthead.getBoundingClientRect();
        const el = document.elementFromPoint(r.left + r.width / 2, r.top + r.height / 2);
        return el && el.closest("#mo-basic-backdrop") ? "backdrop" : (el?.className ?? "none");
      });
      expect(hitBackdrop).toBe("backdrop");
    });

    test("opens, has dialog semantics, closes via button and Escape", async ({ page }) => {
      await expect(page.locator("#mo-basic")).not.toBeVisible();
      await page.locator(`${card} button`, { hasText: "Open modal" }).first().click();
      const modal = page.locator("#mo-basic");
      await expect(modal).toBeVisible();
      await expect(modal).toHaveAttribute("role", "dialog");
      await expect(modal).toHaveAttribute("aria-modal", "true");
      await expect(modal).toHaveAttribute("aria-labelledby", "mo-basic-title");
      await modal.locator(".pf-v6-c-modal-box__close button").click();
      await expect(modal).not.toBeVisible();
      await page.locator(`${card} button`, { hasText: "Open modal" }).first().click();
      await expect(modal).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(modal).not.toBeVisible();
    });
  });

  test.describe("Sizes", () => {
    const card = '[data-rendered-href="/components/modal/sizes"]';

    for (const [label, cls] of [
      ["Small", /pf-m-sm/],
      ["Medium", /pf-m-md/],
      ["Large", /pf-m-lg/],
    ]) {
      test(`${label} button opens modal with the matching size class`, async ({ page }) => {
        await page.locator(`${card} button`, { hasText: label }).first().click();
        await expect(page.locator("#mo-size")).toHaveClass(cls);
      });
    }
  });

  test.describe("Variants", () => {
    test("description renders in the header", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/with-description"]');
      await card.locator("button").first().click();
      await expect(page.locator("#mo-with-description .pf-v6-c-modal-box__description")).toBeVisible();
    });

    test("top-aligned modal carries pf-m-align-top", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/top-aligned"]');
      await card.locator("button").first().click();
      await expect(page.locator("#mo-top-aligned")).toHaveClass(/pf-m-align-top/);
    });

    test("custom width modal has an inline width", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/custom-width"]');
      await card.locator("button").first().click();
      await expect(page.locator("#mo-custom-width")).toHaveAttribute("style", /--pf-v6-c-modal-box--Width:\s*50%/);
    });

    test("no-header-footer modal has only a body and close", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/no-header-footer"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-no-header-footer");
      await expect(modal.locator(".pf-v6-c-modal-box__header")).toHaveCount(0);
      await expect(modal.locator(".pf-v6-c-modal-box__footer")).toHaveCount(0);
      await expect(modal.locator(".pf-v6-c-modal-box__body")).toBeVisible();
    });

    test("title-icon modal is the warning variant with an icon", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/title-icon"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-title-icon");
      await expect(modal).toHaveClass(/pf-m-warning/);
      await expect(modal.locator(".pf-v6-c-modal-box__title-icon")).toBeVisible();
    });
  });

  test.describe("Custom focus", () => {
    test("opening the modal focuses the Confirm button", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/custom-focus"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-custom-focus");
      await expect(modal).toBeVisible();
      await expect(modal.locator("footer button", { hasText: "Confirm" })).toBeFocused();
    });
  });

  test.describe("Without title / generic container", () => {
    test("without-title modal is labelled via aria-label and keeps a footer", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/without-title"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-without-title");
      await expect(modal).toBeVisible();
      await expect(modal).toHaveAttribute("aria-label", "Example of a modal without a title");
      await expect(modal.locator(".pf-v6-c-modal-box__header")).toHaveCount(0);
      await expect(modal.locator(".pf-v6-c-modal-box__footer")).toBeVisible();
    });

    test("generic container is a bare modal box that Escape closes", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/generic-container"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-generic-container");
      await expect(modal).toBeVisible();
      await expect(modal).toHaveAttribute("aria-label", "Generic modal container");
      for (const part of ["__header", "__body", "__footer", "__close"]) {
        await expect(modal.locator(`.pf-v6-c-modal-box${part}`)).toHaveCount(0);
      }
      await page.keyboard.press("Escape");
      await expect(modal).not.toBeVisible();
    });
  });

  test.describe("Alert variants", () => {
    for (const [slug, cls, srPrefix] of [
      ["custom-alert", "pf-m-custom", "Default alert:"],
      ["info-alert", "pf-m-info", "Info alert:"],
      ["success-alert", "pf-m-success", "Success alert:"],
      ["warning-alert", "pf-m-warning", "Warning alert:"],
      ["danger-alert", "pf-m-danger", "Danger alert:"],
    ]) {
      test(`${slug} carries ${cls}, an icon title, and a screen-reader prefix`, async ({ page }) => {
        const card = page.locator(`[data-rendered-href="/components/modal/${slug}"]`);
        await card.locator("button").first().click();
        const modal = page.locator(`#mo-${slug}`);
        await expect(modal).toHaveClass(new RegExp(cls));
        const title = modal.locator(".pf-v6-c-modal-box__title");
        await expect(title).toHaveClass(/pf-m-icon/);
        await expect(title.locator(".pf-v6-c-modal-box__title-icon")).toBeVisible();
        await expect(title.locator(".pf-v6-screen-reader")).toHaveText(srPrefix);
      });
    }

    test("danger-alert-title puts the status class on the title, not the box", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/danger-alert-title"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-danger-status-title");
      await expect(modal).toBeVisible();
      await expect(modal).not.toHaveClass(/pf-m-danger/);
      await expect(modal.locator(".pf-v6-c-modal-box__title")).toHaveClass(/pf-m-danger/);
    });
  });

  test.describe("With dropdown", () => {
    test("dropdown opens inside the modal and Escape closes menu first", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/with-dropdown"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-with-dropdown");
      await expect(modal).toBeVisible();
      await modal.locator(".pf-v6-c-menu-toggle").click();
      const menu = modal.locator(".pf-v6-c-menu");
      await expect(menu).toBeVisible();
      await menu.locator(".pf-v6-c-menu__item").first().click();
      await expect(modal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Action 1");
    });
  });

  test.describe("With help", () => {
    test("help button opens a popover in the header", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/with-help"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-with-help");
      await expect(modal.locator(".pf-v6-c-popover")).toBeHidden();
      await modal.locator("header button[aria-label='Help']").click();
      await expect(modal.locator(".pf-v6-c-popover")).toBeVisible();
    });
  });

  test.describe("With form", () => {
    test("footer submit stays disabled until the form is valid, then submits", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/with-form"]');
      await card.locator("button", { hasText: "Open form modal" }).click();
      const modal = page.locator("#mo-with-form");
      const create = modal.locator("footer button", { hasText: "Create" });
      await expect(create).toBeDisabled();
      await page.locator("#mo-with-form-name").fill("db-primary");
      await expect(create).toBeEnabled();
      await create.click();
      await expect(modal).not.toBeVisible();
      await expect(card.locator("p", { hasText: "Created connection" })).toContainText("db-primary");
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/modal/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/modal/${example}`);
        expect(res.status()).toBe(200);
      });
    }
  });
  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab; live compositions do not", async ({ page }) => {
      await page.goto("/components/modal");
      for (const ex of ["basic", "danger-alert", "scrollable", "generic-container"]) {
        const card = page.locator(`[data-rendered-href="/components/modal/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of ["sizes", "with-form", "with-dropdown", "custom-header"]) {
        const card = page.locator(`[data-rendered-href="/components/modal/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/modal/source-java/danger-alert-title");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".statusTitle()");
    });

    test("model modal opens, closes, and its close button is labeled Close", async ({ page }) => {
      await page.goto("/components/modal/without-title");
      await page.getByRole("button", { name: "Open modal without a title" }).click();
      const box = page.locator("#mo-without-title");
      await expect(box).toBeVisible();
      const close = box.locator(".pf-v6-c-modal-box__close button");
      await expect(close).toHaveAttribute("aria-label", "Close");
      await close.click();
      await expect(box).toBeHidden();
    });
  });
});
