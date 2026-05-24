import { test, expect } from "@playwright/test";

test.describe("Panel", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/panel");
  });

  test("page loads with example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#bordered")).toHaveText("Bordered");
    await expect(page.locator("h3#raised")).toHaveText("Raised");
  });

  test("basic panel has pf-v6-c-panel class", async ({ page }) => {
    await expect(page.locator("#panel-basic")).toHaveClass(/pf-v6-c-panel/);
  });

  test("basic panel has body content", async ({ page }) => {
    await expect(page.locator("#panel-basic .pf-v6-c-panel__main-body")).toBeVisible();
  });

  test("bordered panel has bordered modifier", async ({ page }) => {
    await expect(page.locator("#panel-bordered")).toHaveClass(/pf-m-bordered/);
  });

  test("bordered panel has header", async ({ page }) => {
    await expect(page.locator("#panel-bordered .pf-v6-c-panel__header")).toBeVisible();
  });

  test("raised panel has raised modifier", async ({ page }) => {
    await expect(page.locator("#panel-raised")).toHaveClass(/pf-m-raised/);
  });

  test("raised panel has body content", async ({ page }) => {
    await expect(page.locator("#panel-raised .pf-v6-c-panel__main-body")).toBeVisible();
  });
});
