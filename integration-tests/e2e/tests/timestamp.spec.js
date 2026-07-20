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
    await expect(page.locator("h3#basic")).toHaveText("Default");
    await expect(page.locator("h3#inline")).toHaveText("Inline");
    await expect(page.locator("h3#with-tooltip")).toHaveText("Default tooltip");
  });

  test("basic timestamp renders human-readable text", async ({ page }) => {
    await expect(page.locator("#ts-basic .pf-v6-c-timestamp__text")).toHaveText("May 20, 2026 at 2:30 PM");
  });

  test("basic timestamp is a <time> element with datetime attribute", async ({ page }) => {
    const ts = page.locator("time#ts-basic");
    await expect(ts).toHaveClass(/pf-v6-c-timestamp/);
    await expect(ts).toHaveAttribute("datetime", "2026-05-20T14:30:00Z");
  });

  test("inline variant has pf-m-inline modifier", async ({ page }) => {
    await expect(page.locator("#ts-inline")).toHaveClass(/pf-m-inline/);
  });

  test("with-tooltip variant has title attribute", async ({ page }) => {
    await expect(page.locator("#ts-tooltip")).toHaveAttribute("title", "2026-05-20 14:30:00 UTC");
  });

  test.describe("Parity additions", () => {
    test("formats render four variants of one instant", async ({ page }) => {
      await expect(page.locator("#ts-formats time")).toHaveCount(4);
    });

    test("custom content keeps the machine-readable datetime", async ({ page }) => {
      const t = page.locator("#ts-custom-content");
      await expect(t).toHaveText("3 weeks ago");
      await expect(t).toHaveAttribute("datetime", "2026-05-20T14:30:00Z");
    });

    test("/components/timestamp/basic-formats returns 200", async ({ page }) => {
      const res = await page.goto("/components/timestamp/basic-formats");
      expect(res.status()).toBe(200);
    });

    test("/components/timestamp/custom-format returns 200", async ({ page }) => {
      const res = await page.goto("/components/timestamp/custom-format");
      expect(res.status()).toBe(200);
    });

    test("/components/timestamp/custom-content returns 200", async ({ page }) => {
      const res = await page.goto("/components/timestamp/custom-content");
      expect(res.status()).toBe(200);
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/timestamp");
      for (const ex of ["basic", "custom-format", "inline"]) {
        const card = page.locator(`[data-rendered-href="/components/timestamp/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/timestamp/source-java/with-tooltip");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.tooltip("2026-05-20 14:30:00 UTC")');
    });

    test("custom-format renders the interpunct character, not a unicode escape", async ({ page }) => {
      await page.goto("/components/timestamp/custom-format");
      const text = page.locator("#ts-custom-format .pf-v6-c-timestamp__text");
      await expect(text).toHaveText("2026-05-20 \u00b7 14:30 UTC");
      await expect(text).not.toContainText("\\u00b7");
    });
  });
});
