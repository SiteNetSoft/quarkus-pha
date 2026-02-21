import { test, expect } from "@playwright/test";

test.describe("Card", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/card");
  });

  test("page loads with all 6 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#compact-heading")).toBeVisible();
    await expect(page.locator("#large-heading")).toBeVisible();
    await expect(page.locator("#plain-heading")).toBeVisible();
    await expect(page.locator("#flat-heading")).toBeVisible();
    await expect(page.locator("#no-footer-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has card class with title, body, and footer", async ({ page }) => {
      const card = page.locator("#card-basic");
      await expect(card).toHaveClass(/pf-v6-c-card/);
      await expect(card.locator(".pf-v6-c-card__title-text")).toHaveText("Title");
      await expect(card.locator(".pf-v6-c-card__body")).toHaveText("Body");
      await expect(card.locator(".pf-v6-c-card__footer")).toHaveText("Footer");
    });
  });

  test.describe("Compact", () => {
    test("has pf-m-compact modifier", async ({ page }) => {
      await expect(page.locator("#card-compact")).toHaveClass(/pf-m-compact/);
    });
  });

  test.describe("Large", () => {
    test("has pf-m-display-lg modifier", async ({ page }) => {
      await expect(page.locator("#card-large")).toHaveClass(/pf-m-display-lg/);
    });
  });

  test.describe("Plain", () => {
    test("has pf-m-plain modifier", async ({ page }) => {
      await expect(page.locator("#card-plain")).toHaveClass(/pf-m-plain/);
    });
  });

  test.describe("Flat", () => {
    test("has pf-m-flat modifier", async ({ page }) => {
      await expect(page.locator("#card-flat")).toHaveClass(/pf-m-flat/);
    });
  });

  test.describe("No footer", () => {
    test("has no footer element", async ({ page }) => {
      const footer = page.locator("#card-no-footer .pf-v6-c-card__footer");
      await expect(footer).toHaveCount(0);
    });
  });
});
