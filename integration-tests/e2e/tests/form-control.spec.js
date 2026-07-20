import { test, expect } from "@playwright/test";

test.describe("Form Control", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/form-control");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#validated")).toBeVisible();
    await expect(page.locator("#disabled-readonly")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-form-control class", async ({ page }) => {
      await expect(page.locator("#fc-basic")).toHaveClass(/pf-v6-c-form-control/);
    });
  });

  test.describe("Disabled", () => {
    test("input has disabled attribute", async ({ page }) => {
      await expect(page.locator("#fc-disabled input")).toBeDisabled();
    });
  });

  test.describe("Readonly", () => {
    test("input has readonly attribute", async ({ page }) => {
      const input = page.locator("#fc-readonly input");
      await expect(input).toHaveAttribute("readonly", "");
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/form-control");
      for (const ex of ["basic", "disabled-readonly", "validated"]) {
        const card = page.locator(`[data-rendered-href="/components/form-control/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the TextInput-model snippet", async ({ page }) => {
      const res = await page.request.get("/components/form-control/source-java/validated");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('TextInput.of("fc-success")');
    });
  });
});
