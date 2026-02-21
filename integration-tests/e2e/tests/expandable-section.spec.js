import { test, expect } from "@playwright/test";

test.describe("Expandable section", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/expandable-section");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#expanded-heading")).toBeVisible();
  });

  test("basic section is collapsed by default", async ({ page }) => {
    await expect(page.locator("#es-basic")).not.toHaveClass(/pf-m-expanded/);
    await expect(page.locator("#es-basic .pf-v6-c-expandable-section__content")).not.toBeVisible();
  });

  test("clicking toggle expands basic section", async ({ page }) => {
    await page.locator("#es-basic .pf-v6-c-expandable-section__toggle").click();
    await expect(page.locator("#es-basic")).toHaveClass(/pf-m-expanded/);
    await expect(page.locator("#es-basic .pf-v6-c-expandable-section__content")).toBeVisible();
  });

  test("expanded section starts open", async ({ page }) => {
    await expect(page.locator("#es-expanded")).toHaveClass(/pf-m-expanded/);
    await expect(page.locator("#es-expanded .pf-v6-c-expandable-section__content")).toBeVisible();
  });

  test("toggle text is visible", async ({ page }) => {
    await expect(page.locator("#es-basic .pf-v6-c-expandable-section__toggle-text")).toHaveText("Show more");
  });
});
