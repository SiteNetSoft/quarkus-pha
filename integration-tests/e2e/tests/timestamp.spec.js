import { test, expect } from "@playwright/test";

test.describe("Timestamp", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/timestamp");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Timestamp");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-timestamp")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("all 3 example section headings use slug ids", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#inline")).toHaveText("Inline");
    await expect(page.locator("h3#with-tooltip")).toHaveText("With tooltip");
  });

  test("basic timestamp renders human-readable text", async ({ page }) => {
    await expect(
      page.locator("#ts-basic .pf-v6-c-timestamp__text")
    ).toHaveText("May 20, 2026 at 2:30 PM");
  });

  test("basic timestamp is a <time> element with datetime attribute", async ({
    page,
  }) => {
    const ts = page.locator("time#ts-basic");
    await expect(ts).toHaveClass(/pf-v6-c-timestamp/);
    await expect(ts).toHaveAttribute("datetime", "2026-05-20T14:30:00Z");
  });

  test("inline variant has pf-m-inline modifier", async ({ page }) => {
    await expect(page.locator("#ts-inline")).toHaveClass(/pf-m-inline/);
  });

  test("with-tooltip variant has title attribute", async ({ page }) => {
    await expect(page.locator("#ts-tooltip")).toHaveAttribute(
      "title",
      "2026-05-20 14:30:00 UTC"
    );
  });
});
