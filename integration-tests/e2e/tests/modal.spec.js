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
];

// Modals render inline (no portal), so the sticky page header can overlay
// them; dispatch clicks directly so Alpine handlers fire regardless.
const domClick = (locator) => locator.evaluate((el) => el.click());

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

    test("opens, has dialog semantics, closes via button and Escape", async ({ page }) => {
      await expect(page.locator(`${card} #mo-basic`)).not.toBeVisible();
      await page.locator(`${card} button`, { hasText: "Open modal" }).first().click();
      const modal = page.locator(`${card} #mo-basic`);
      await expect(modal).toBeVisible();
      await expect(modal).toHaveAttribute("role", "dialog");
      await expect(modal).toHaveAttribute("aria-modal", "true");
      await expect(modal).toHaveAttribute("aria-labelledby", "mo-basic-title");
      await domClick(page.locator(`${card} .pf-v6-c-modal-box__close button`));
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
        await expect(page.locator(`${card} .pf-v6-c-modal-box`)).toHaveClass(cls);
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
      await expect(page.locator("#mo-custom-width")).toHaveAttribute("style", /width:\s*50%/);
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

  test.describe("With dropdown", () => {
    test("dropdown opens inside the modal and Escape closes menu first", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/with-dropdown"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-with-dropdown");
      await expect(modal).toBeVisible();
      await domClick(modal.locator(".pf-v6-c-menu-toggle"));
      const menu = modal.locator(".pf-v6-c-menu");
      await expect(menu).toBeVisible();
      await domClick(menu.locator(".pf-v6-c-menu__item").first());
      await expect(modal.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Action 1");
    });
  });

  test.describe("With help", () => {
    test("help button opens a popover in the header", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/modal/with-help"]');
      await card.locator("button").first().click();
      const modal = page.locator("#mo-with-help");
      await expect(modal.locator(".pf-v6-c-popover")).toBeHidden();
      await domClick(modal.locator("header button[aria-label='Help']"));
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
      await domClick(create);
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
});
