import { test, expect } from "@playwright/test";

test.describe("Title", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/title");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Title");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-title")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("example section headings use slug ids", async ({ page }) => {
    await expect(page.locator("h3#default-sizes")).toHaveText("Default sizes");
    await expect(page.locator("h3#custom-sizes")).toHaveText("Custom sizes");
  });

  test("default-sizes example renders h1–h6 with default size modifiers", async ({
    page,
  }) => {
    // Scope to the section under the "Default sizes" example heading; the
    // example body is the next stack item sibling.
    const card = page
      .locator("h3#default-sizes")
      .locator(
        "xpath=ancestor::div[contains(@class,'pf-v6-l-stack')][1]"
      );
    await expect(card.locator("h1.pf-v6-c-title.pf-m-h1")).toHaveText(
      "h1 — default"
    );
    await expect(card.locator("h2.pf-v6-c-title.pf-m-h2")).toHaveText(
      "h2 — default"
    );
    await expect(card.locator("h3.pf-v6-c-title.pf-m-h3")).toHaveText(
      "h3 — default"
    );
    await expect(card.locator("h4.pf-v6-c-title.pf-m-h4")).toHaveText(
      "h4 — default"
    );
    await expect(card.locator("h5.pf-v6-c-title.pf-m-h5")).toHaveText(
      "h5 — default"
    );
    await expect(card.locator("h6.pf-v6-c-title.pf-m-h6")).toHaveText(
      "h6 — default"
    );
  });

  test("custom-sizes example renders all size modifiers on mismatched heading levels", async ({
    page,
  }) => {
    const card = page
      .locator("h3#custom-sizes")
      .locator(
        "xpath=ancestor::div[contains(@class,'pf-v6-l-stack')][1]"
      );
    await expect(card.locator("h1.pf-v6-c-title.pf-m-4xl")).toHaveText(
      "4xl title"
    );
    await expect(card.locator("h2.pf-v6-c-title.pf-m-3xl")).toHaveText(
      "3xl title"
    );
    await expect(card.locator("h3.pf-v6-c-title.pf-m-2xl")).toHaveText(
      "2xl title"
    );
    await expect(card.locator("h4.pf-v6-c-title.pf-m-xl")).toHaveText(
      "xl title"
    );
    await expect(card.locator("h5.pf-v6-c-title.pf-m-lg")).toHaveText(
      "lg title"
    );
    await expect(card.locator("h6.pf-v6-c-title.pf-m-md")).toHaveText(
      "md title"
    );
  });
});
