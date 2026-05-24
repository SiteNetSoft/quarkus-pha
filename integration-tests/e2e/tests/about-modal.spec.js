import { test, expect } from "@playwright/test";

test.describe("About modal", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/about-modal");
  });

  test("all three example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#without-product-name")).toBeVisible();
    await expect(page.locator("#complex-content")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/about-modal/basic"]';

    test("modal hidden by default", async ({ page }) => {
      const modal = page.locator(`${card} .pf-v6-c-about-modal-box`);
      await expect(modal).toBeHidden();
    });

    test("opens via id-scoped event with product name title", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const modal = page.locator(`${card} .pf-v6-c-about-modal-box`);
      await expect(modal).toBeVisible();
      await expect(page.locator("#basic-title")).toHaveText("quarkus-pha");
    });

    test("dialog aria attributes link to the basic title", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const modal = page.locator(`${card} .pf-v6-c-about-modal-box`);
      await expect(modal).toHaveAttribute("role", "dialog");
      await expect(modal).toHaveAttribute("aria-modal", "true");
      await expect(modal).toHaveAttribute("aria-labelledby", "basic-title");
    });

    test("brand image, description-list body, trademark, strapline render", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      await expect(page.locator(`${card} .pf-v6-c-about-modal-box__brand-image`)).toBeVisible();
      await expect(page.locator(`${card} .pf-v6-c-about-modal-box__body`)).toContainText("1.0.0-SNAPSHOT");
      await expect(page.locator(`${card} .pf-v6-c-about-modal-box__strapline`).last()).toContainText("Copyright");
    });

    test("Escape closes", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const modal = page.locator(`${card} .pf-v6-c-about-modal-box`);
      await expect(modal).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(modal).toBeHidden();
    });

    test("close button closes", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const modal = page.locator(`${card} .pf-v6-c-about-modal-box`);
      await expect(modal).toBeVisible();
      await page.locator(`${card} .pf-v6-c-about-modal-box__close button`).click();
      await expect(modal).toBeHidden();
    });
  });

  test.describe("Without product name variant", () => {
    const card = '[data-rendered-href="/components/about-modal/without-product-name"]';

    test("dialog uses aria-label instead of aria-labelledby", async ({ page }) => {
      await page.locator(`${card} button`).first().click();
      const modal = page.locator(`${card} .pf-v6-c-about-modal-box`);
      await expect(modal).toHaveAttribute("aria-label", "About this product");
      await expect(modal).not.toHaveAttribute("aria-labelledby", /.+/);
    });

    test("no h1 title element", async ({ page }) => {
      await page.locator(`${card} button`).first().click();
      await expect(page.locator("#noname-title")).toHaveCount(0);
    });
  });

  test.describe("Complex content variant", () => {
    const card = '[data-rendered-href="/components/about-modal/complex-content"]';

    test("default __content wrapper is skipped (only the example's own wrapper is present)", async ({ page }) => {
      await page.locator(`${card} button`).first().click();
      // noContentContainer=true → the runtime template does NOT emit a default __content wrapper;
      // the only __content div is the one the example fragment renders itself
      await expect(page.locator(`${card} .pf-v6-c-about-modal-box__content`)).toHaveCount(1);
    });
  });

  test.describe("Multiple instances coexist", () => {
    test("opening basic does not open complex", async ({ page }) => {
      await page
        .locator('[data-rendered-href="/components/about-modal/basic"] button', { hasText: "About" })
        .first()
        .click();
      await expect(page.locator('[data-rendered-href="/components/about-modal/basic"] .pf-v6-c-about-modal-box')).toBeVisible();
      await expect(page.locator('[data-rendered-href="/components/about-modal/complex-content"] .pf-v6-c-about-modal-box')).toBeHidden();
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/about-modal/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/about-modal/complex-content"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/about-modal/complex-content");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
