import { test, expect } from "@playwright/test";

test.describe("Expandable section", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/expandable-section");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#collapsed")).toBeVisible();
    await expect(page.locator("#expanded")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-expandable-section")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("collapsed section is collapsed by default", async ({ page }) => {
    await expect(page.locator("#es-collapsed")).not.toHaveClass(/pf-m-expanded/);
    await expect(page.locator("#es-collapsed .pf-v6-c-expandable-section__content")).not.toBeVisible();
  });

  test("clicking toggle expands collapsed section", async ({ page }) => {
    await page.locator("#es-collapsed .pf-v6-c-expandable-section__toggle").click();
    await expect(page.locator("#es-collapsed")).toHaveClass(/pf-m-expanded/);
    await expect(page.locator("#es-collapsed .pf-v6-c-expandable-section__content")).toBeVisible();
  });

  test("expanded section starts open", async ({ page }) => {
    await expect(page.locator("#es-expanded")).toHaveClass(/pf-m-expanded/);
    await expect(page.locator("#es-expanded .pf-v6-c-expandable-section__content")).toBeVisible();
  });

  test("toggle text is visible", async ({ page }) => {
    await expect(page.locator("#es-collapsed .pf-v6-c-expandable-section__toggle-text")).toHaveText("Show advanced settings");
  });
});
