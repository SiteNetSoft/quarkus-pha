import { test, expect } from "@playwright/test";

test.describe("Empty state", () => {
  test("page loads with example section headings", async ({ page }) => {
    await page.goto("/components/empty-state");
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#no-icon")).toBeVisible();
    await expect(page.locator("#with-actions")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await page.goto("/components/empty-state");
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-empty-state")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic has title, body, and icon", async ({ page }) => {
    await page.goto("/components/empty-state/basic");
    await expect(page.locator(".pf-v6-c-empty-state__title-text")).toHaveText("No projects yet");
    await expect(page.locator(".pf-v6-c-empty-state__body")).toBeVisible();
    await expect(page.locator(".pf-v6-c-empty-state__icon")).toBeVisible();
  });

  test("no-icon variant has no icon element", async ({ page }) => {
    await page.goto("/components/empty-state/no-icon");
    await expect(page.locator(".pf-v6-c-empty-state__icon")).toHaveCount(0);
  });

  test("actions slot composes buttons via the button component", async ({ page }) => {
    await page.goto("/components/empty-state/with-actions");
    const actions = page.locator(".pf-v6-c-empty-state__actions");
    // every action is a real button component (carries the __text wrapper), not hand-rolled markup
    await expect(actions.locator(".pf-v6-c-button")).toHaveCount(4);
    await expect(actions.locator(".pf-v6-c-button .pf-v6-c-button__text")).toHaveCount(4);
    await expect(actions.locator(".pf-v6-c-button.pf-m-primary")).toHaveText("Create document");
    await expect(actions.locator(".pf-v6-c-button.pf-m-secondary")).toHaveText("Import existing");
    await expect(actions.locator(".pf-v6-c-button.pf-m-link")).toHaveText(["Browse templates", "Learn more"]);
  });
});
