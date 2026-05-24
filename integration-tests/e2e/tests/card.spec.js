import { test, expect } from "@playwright/test";

test.describe("Card", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/card");
  });

  test("page loads with all 3 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#compact")).toBeVisible();
    await expect(page.locator("#flat")).toBeVisible();
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-card")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/card/basic"]';

    test("has card class with title, body, and footer", async ({ page }) => {
      const cd = page.locator(`${card} #cd-basic`);
      await expect(cd).toHaveClass(/pf-v6-c-card/);
      await expect(cd.locator(".pf-v6-c-card__title-text")).toHaveText("Project Apollo");
      await expect(cd.locator(".pf-v6-c-card__body")).toContainText("dashboard");
      await expect(cd.locator(".pf-v6-c-card__footer")).toContainText("Last updated");
    });
  });

  test.describe("Compact variant", () => {
    const card = '[data-rendered-href="/components/card/compact"]';

    test("has pf-m-compact modifier", async ({ page }) => {
      await expect(page.locator(`${card} #cd-compact`)).toHaveClass(/pf-m-compact/);
    });
  });

  test.describe("Flat variant", () => {
    const card = '[data-rendered-href="/components/card/flat"]';

    test("has pf-m-flat modifier", async ({ page }) => {
      await expect(page.locator(`${card} #cd-flat`)).toHaveClass(/pf-m-flat/);
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/card/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/card/flat"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/card/flat");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
