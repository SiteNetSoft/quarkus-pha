import { test, expect } from "@playwright/test";

test.describe("Click to edit (HTMX)", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/click-to-edit");
    await expect(page.locator("#htmx-click-to-edit")).toBeVisible();
  });

  test("shows a read-only view with an Edit button", async ({ page }) => {
    await expect(page.locator("#htmx-click-to-edit .pf-v6-c-description-list")).toBeVisible();
    await expect(page.locator("#htmx-click-to-edit button", { hasText: "Edit" })).toBeVisible();
  });

  test("Edit swaps to a form; Cancel restores the view", async ({ page }) => {
    await page.locator("#htmx-click-to-edit button", { hasText: "Edit" }).click();
    await expect(page.locator("#htmx-click-to-edit input[name=name]")).toBeVisible();
    await page.locator("#htmx-click-to-edit button", { hasText: "Cancel" }).click();
    await expect(page.locator("#htmx-click-to-edit .pf-v6-c-description-list")).toBeVisible();
    await expect(page.locator("#htmx-click-to-edit input[name=name]")).toHaveCount(0);
  });

  test("Save persists the new values to the server and shows them", async ({ page }) => {
    await page.locator("#htmx-click-to-edit button", { hasText: "Edit" }).click();
    await page.locator("#htmx-click-to-edit input[name=name]").fill("Morgan Lee");
    await page.locator("#htmx-click-to-edit input[name=title]").fill("Principal Engineer");
    await page.locator("#htmx-click-to-edit button", { hasText: "Save" }).click();
    await expect(page.locator("#htmx-click-to-edit .pf-v6-c-description-list")).toContainText("Morgan Lee");
    await expect(page.locator("#htmx-click-to-edit .pf-v6-c-description-list")).toContainText("Principal Engineer");
    // persisted: reloading the fragment still shows it
    await page.reload();
    await expect(page.locator("#htmx-click-to-edit")).toContainText("Morgan Lee");
  });
});
