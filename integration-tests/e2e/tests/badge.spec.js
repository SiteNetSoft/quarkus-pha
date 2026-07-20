import { test, expect } from "@playwright/test";

const EXAMPLES = ["read", "unread", "disabled", "screen-reader"];

test.describe("Badge", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/badge");
  });

  test("TOC anchors render", async ({ page }) => {
    const anchors = ["examples", ...EXAMPLES, "documentation", "props-badge", "usage"];
    for (const id of anchors) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Read", () => {
    test("read badges carry pf-m-read", async ({ page }) => {
      for (const v of ["7", "24", "999"]) {
        await expect(page.locator(`#badge-read-${v}`)).toHaveClass(/pf-m-read/);
      }
    });
  });

  test.describe("Unread", () => {
    test("unread badges carry pf-m-unread", async ({ page }) => {
      for (const v of ["7", "24", "999"]) {
        await expect(page.locator(`#badge-unread-${v}`)).toHaveClass(/pf-m-unread/);
      }
    });
  });

  test.describe("Disabled", () => {
    test("disabled badges carry pf-m-disabled", async ({ page }) => {
      for (const v of ["7", "24", "999"]) {
        await expect(page.locator(`#badge-disabled-${v}`)).toHaveClass(/pf-m-disabled/);
      }
    });
  });

  test.describe("Screen reader", () => {
    test("renders a visually-hidden span with the SR text", async ({ page }) => {
      const badge = page.locator("#badge-sr");
      await expect(badge).toContainText("3");
      await expect(badge.locator(".pf-v6-screen-reader")).toHaveText(/unread notifications/);
    });
  });

  test("every badge is a span with pf-v6-c-badge", async ({ page }) => {
    const badges = page.locator(".pf-v6-c-badge");
    const count = await badges.count();
    expect(count).toBeGreaterThan(0);
    for (let i = 0; i < count; i++) {
      const tag = await badges.nth(i).evaluate((el) => el.tagName);
      expect(tag).toBe("SPAN");
    }
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/badge/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/badge/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-badge").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/badge");
      for (const ex of ["unread", "screen-reader"]) {
        const card = page.locator(`[data-rendered-href="/components/badge/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/badge/source-java/screen-reader");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.screenReaderText("unread notifications")');
    });
  });
});
