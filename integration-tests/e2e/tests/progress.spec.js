import { test, expect } from "@playwright/test";

test.describe("Progress", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/progress");
  });

  test("page loads with key example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#small")).toBeVisible();
    await expect(page.locator("#large")).toBeVisible();
    await expect(page.locator("#success")).toBeVisible();
    await expect(page.locator("#warning")).toBeVisible();
    await expect(page.locator("#failure")).toBeVisible();
  });

  test("basic progress has bar and indicator", async ({ page }) => {
    await expect(page.locator("#prog-basic .pf-v6-c-progress__bar")).toBeVisible();
    await expect(page.locator("#prog-basic .pf-v6-c-progress__indicator")).toBeVisible();
  });

  test("basic progress shows measure text", async ({ page }) => {
    await expect(page.locator("#prog-basic .pf-v6-c-progress__measure")).toHaveText("33%");
  });

  test("small progress has sm modifier", async ({ page }) => {
    await expect(page.locator("#prog-small")).toHaveClass(/pf-m-sm/);
  });

  test("large progress has lg modifier", async ({ page }) => {
    await expect(page.locator("#prog-large")).toHaveClass(/pf-m-lg/);
  });

  test("success progress has success modifier", async ({ page }) => {
    await expect(page.locator("#prog-success")).toHaveClass(/pf-m-success/);
  });

  test("failure progress has danger modifier and status icon", async ({ page }) => {
    await expect(page.locator("#prog-failure")).toHaveClass(/pf-m-danger/);
    await expect(page.locator("#prog-failure .pf-v6-c-progress__status-icon")).toBeVisible();
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/progress/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/progress/success"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/progress/success");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
  test.describe("Java source tab", () => {
    // All 18 examples are model-driven (ProgressDemoData).
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/progress");
      for (const ex of ["basic", "finite-step", "single-line", "truncate-title"]) {
        const card = page.locator(`[data-rendered-href="/components/progress/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("Java tab opens Monaco with the builder code", async ({ page }) => {
      await page.goto("/components/progress");
      const card = page.locator('[data-rendered-href="/components/progress/basic"]');
      await card.locator('button[aria-label*="Toggle Java"]').click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
      await expect(card.locator(".monaco-editor .view-lines")).toContainText("Progress.of(", { timeout: 10000 });
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/progress/source-java/finite-step");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".range(0, 5)");
    });

    test("step progress indicator width reflects the min-max range", async ({ page }) => {
      await page.goto("/components/progress");
      const indicator = page.locator("#prog-finite-step .pf-v6-c-progress__indicator");
      await expect(indicator).toHaveAttribute("style", /width: 40%/);
    });
  });
});
