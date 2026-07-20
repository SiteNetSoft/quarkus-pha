import { test, expect } from "@playwright/test";

test.describe("Text Input", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/text-input");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#disabled")).toBeVisible();
    await expect(page.locator("#readonly")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-form-control class", async ({ page }) => {
      await expect(page.locator("#ti-basic")).toHaveClass(/pf-v6-c-form-control/);
    });
  });

  test.describe("Disabled", () => {
    test("input has disabled attribute", async ({ page }) => {
      await expect(page.locator("#ti-disabled input")).toBeDisabled();
    });
  });

  test.describe("Readonly", () => {
    test("input has readonly attribute", async ({ page }) => {
      const input = page.locator("#ti-readonly input");
      await expect(input).toHaveAttribute("readonly", "");
    });
  });

  test.describe("Parity additions", () => {
    test("start-truncated keeps the tail visible via rtl direction", async ({ page }) => {
      await expect(page.locator("#ti-start-truncated-field")).toHaveCSS("direction", "rtl");
    });

    test("icon-invalid combines a custom icon with error status", async ({ page }) => {
      await expect(page.locator("#ti-icon-invalid")).toHaveClass(/pf-m-error/);
      await expect(page.locator("#ti-icon-invalid .pf-v6-c-form-control__icon")).toHaveCount(2);
    });

    test("/components/text-input/start-truncated returns 200", async ({ page }) => {
      const res = await page.goto("/components/text-input/start-truncated");
      expect(res.status()).toBe(200);
    });

    test("/components/text-input/icon-invalid returns 200", async ({ page }) => {
      const res = await page.goto("/components/text-input/icon-invalid");
      expect(res.status()).toBe(200);
    });
  });
  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab; raw-anatomy variants do not", async ({ page }) => {
      await page.goto("/components/text-input");
      for (const ex of ["basic", "types", "with-icon"]) {
        const card = page.locator(`[data-rendered-href="/components/text-input/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of ["icon-invalid", "start-truncated"]) {
        const card = page.locator(`[data-rendered-href="/components/text-input/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/text-input/source-java/with-icon");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.icon("fa:magnifying-glass")');
    });
  });
});
