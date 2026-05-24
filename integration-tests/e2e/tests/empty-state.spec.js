import { test, expect } from "@playwright/test";

test.describe("Empty state", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/empty-state");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#no-icon")).toBeVisible();
    await expect(page.locator("#with-actions")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-empty-state")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic has title text", async ({ page }) => {
    await expect(page.locator("#es-basic .pf-v6-c-empty-state__title-text")).toHaveText("No projects yet");
  });

  test("basic has body text", async ({ page }) => {
    await expect(page.locator("#es-basic .pf-v6-c-empty-state__body")).toBeVisible();
  });

  test("basic has icon element", async ({ page }) => {
    await expect(page.locator("#es-basic .pf-v6-c-empty-state__icon")).toBeVisible();
  });

  test("no-icon variant has no icon element", async ({ page }) => {
    await expect(page.locator("#es-no-icon .pf-v6-c-empty-state__icon")).toHaveCount(0);
  });

  test("with actions has primary and secondary buttons", async ({ page }) => {
    await expect(page.locator("#es-actions .pf-v6-c-button.pf-m-primary")).toHaveText("Create document");
    await expect(page.locator("#es-actions .pf-v6-c-button.pf-m-link")).toHaveText("Import existing");
  });
});
