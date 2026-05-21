import { test, expect } from "@playwright/test";

test.describe("Button", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/button");
  });

  test("page loads with all 6 section headings", async ({ page }) => {
    await expect(page.locator("#variations-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
    await expect(page.locator("#small-heading")).toBeVisible();
    await expect(page.locator("#block-heading")).toBeVisible();
    await expect(page.locator("#links-heading")).toBeVisible();
    await expect(page.locator("#cta-heading")).toBeVisible();
  });

  test.describe("Variations", () => {
    test("all 8 variants render with correct modifier classes", async ({ page }) => {
      const variants = ["primary", "secondary", "tertiary", "danger", "warning", "link", "plain", "control"];
      for (const variant of variants) {
        const btn = page.locator(`#btn-${variant}`);
        await expect(btn).toHaveClass(/pf-v6-c-button/);
        await expect(btn).toHaveClass(new RegExp(`pf-m-${variant}`));
      }
    });

    test("buttons are button elements with type=button", async ({ page }) => {
      const btn = page.locator("#btn-primary");
      await expect(btn).toHaveAttribute("type", "button");
    });
  });

  test.describe("Disabled", () => {
    test("disabled buttons have disabled attribute", async ({ page }) => {
      await expect(page.locator("#btn-disabled-primary")).toBeDisabled();
      await expect(page.locator("#btn-disabled-secondary")).toBeDisabled();
    });
  });

  test.describe("Small", () => {
    test("has pf-m-small modifier", async ({ page }) => {
      await expect(page.locator("#btn-small-primary")).toHaveClass(/pf-m-small/);
      await expect(page.locator("#btn-small-secondary")).toHaveClass(/pf-m-small/);
    });
  });

  test.describe("Block level", () => {
    test("has pf-m-block modifier", async ({ page }) => {
      await expect(page.locator("#btn-block")).toHaveClass(/pf-m-block/);
    });
  });

  test.describe("Links as buttons", () => {
    test("renders as anchor elements with href", async ({ page }) => {
      const primaryLink = page.locator("#btn-anchor-primary");
      await expect(primaryLink).toHaveAttribute("href", "#links-heading");

      const tagName = await primaryLink.evaluate((el) => el.tagName.toLowerCase());
      expect(tagName).toBe("a");
    });
  });

  test.describe("Call to action", () => {
    test("has pf-m-display-lg modifier", async ({ page }) => {
      await expect(page.locator("#btn-cta-primary")).toHaveClass(/pf-m-display-lg/);
      await expect(page.locator("#btn-cta-secondary")).toHaveClass(/pf-m-display-lg/);
      await expect(page.locator("#btn-cta-tertiary")).toHaveClass(/pf-m-display-lg/);
    });
  });
});
