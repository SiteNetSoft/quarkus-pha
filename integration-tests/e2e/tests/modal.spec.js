import { test, expect } from "@playwright/test";

test.describe("Modal", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/modal");
  });

  test("page loads with both example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#sizes")).toBeVisible();
  });

  test.describe("Basic", () => {
    const card = '[data-rendered-href="/components/modal/basic"]';

    test("modal is hidden by default", async ({ page }) => {
      await expect(page.locator(`${card} #mo-basic`)).not.toBeVisible();
    });

    test("clicking trigger shows modal with pf-v6-c-modal-box class", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "Open modal" }).first().click();
      const modal = page.locator(`${card} #mo-basic`);
      await expect(modal).toBeVisible();
      await expect(modal).toHaveClass(/pf-v6-c-modal-box/);
    });

    test("dialog has aria-modal and aria-labelledby", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "Open modal" }).first().click();
      const modal = page.locator(`${card} #mo-basic`);
      await expect(modal).toHaveAttribute("role", "dialog");
      await expect(modal).toHaveAttribute("aria-modal", "true");
      await expect(modal).toHaveAttribute("aria-labelledby", "mo-basic-title");
    });

    test("clicking close button hides modal", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "Open modal" }).first().click();
      await expect(page.locator(`${card} #mo-basic`)).toBeVisible();
      // Modal renders inline (no portal), so the page header can overlay it.
      // Even with `force: true`, the real mouse click is intercepted by the
      // overlaying element. Dispatch the click directly on the button so
      // Alpine's @click handler fires and flips `open` to false.
      await page.locator(`${card} .pf-v6-c-modal-box__close button`).evaluate((btn) => btn.click());
      await expect(page.locator(`${card} #mo-basic`)).not.toBeVisible();
    });

    test("Escape closes the modal", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "Open modal" }).first().click();
      await expect(page.locator(`${card} #mo-basic`)).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(page.locator(`${card} #mo-basic`)).not.toBeVisible();
    });
  });

  test.describe("Sizes", () => {
    const card = '[data-rendered-href="/components/modal/sizes"]';

    test("Small button opens modal with pf-m-sm class", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "Small" }).first().click();
      await expect(page.locator(`${card} .pf-v6-c-modal-box`)).toHaveClass(/pf-m-sm/);
    });

    test("Medium button opens modal with pf-m-md class", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "Medium" }).first().click();
      await expect(page.locator(`${card} .pf-v6-c-modal-box`)).toHaveClass(/pf-m-md/);
    });

    test("Large button opens modal with pf-m-lg class", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "Large" }).first().click();
      await expect(page.locator(`${card} .pf-v6-c-modal-box`)).toHaveClass(/pf-m-lg/);
    });
  });
});
