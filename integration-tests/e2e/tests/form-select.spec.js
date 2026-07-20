import { test, expect } from "@playwright/test";

test.describe("Form Select", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/form-select");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#disabled")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has select element", async ({ page }) => {
      await expect(page.locator("#fs-basic select")).toBeVisible();
    });

    test("has options", async ({ page }) => {
      const options = page.locator("#fs-basic select option");
      await expect(options).toHaveCount(5);
    });
  });

  test.describe("Disabled", () => {
    test("select has disabled attribute", async ({ page }) => {
      await expect(page.locator("#fs-disabled select")).toBeDisabled();
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/form-select");
      for (const ex of ["basic", "grouped", "validated"]) {
        const card = page.locator(`[data-rendered-href="/components/form-select/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/form-select/source-java/grouped");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('<optgroup label="North America">');
    });
  });
});
