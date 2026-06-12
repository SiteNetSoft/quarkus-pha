import { test, expect } from "@playwright/test";

test.describe("Hint", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/hint");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Hint");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-hint")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("example section headings use slug ids", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#with-content")).toHaveText("With content");
  });

  test("basic hint has title and body text", async ({ page }) => {
    await expect(page.locator("#ht-basic .pf-v6-c-hint__title")).toHaveText(
      "Pro tip"
    );
    await expect(page.locator("#ht-basic .pf-v6-c-hint__body")).toBeVisible();
  });

  test("with-content hint has title, body, and footer", async ({ page }) => {
    await expect(page.locator("#ht-content .pf-v6-c-hint__title")).toHaveText(
      "Hint with action"
    );
    await expect(page.locator("#ht-content .pf-v6-c-hint__body")).toBeVisible();
    await expect(
      page.locator("#ht-content .pf-v6-c-hint__footer")
    ).toBeVisible();
  });

  test.describe("Parity additions", () => {
    test("without-title hint has only a body", async ({ page }) => {
      await expect(page.locator("#ht-without-title .pf-v6-c-hint__body")).toBeVisible();
      await expect(page.locator("#ht-without-title .pf-v6-c-hint__title")).toHaveCount(0);
    });

    test("/components/hint/without-title returns 200", async ({ page }) => {
      const res = await page.goto("/components/hint/without-title");
      expect(res.status()).toBe(200);
    });
  });
});
