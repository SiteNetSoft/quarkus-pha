import { test, expect } from "@playwright/test";

test.describe("Log viewer", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/extensions/log-viewer");
  });

  test("page loads with all 6 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#line-numbers")).toBeVisible();
    await expect(page.locator("#with-toolbar")).toBeVisible();
    await expect(page.locator("#wrap-nowrap")).toBeVisible();
    await expect(page.locator("#ansi-colors")).toBeVisible();
    await expect(page.locator("#streaming")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    test("renders log lines inside scroll container", async ({ page }) => {
      const root = page.locator("#lv-basic");
      await expect(root).toBeVisible();
      await expect(root).toHaveClass(/pf-v6-c-log-viewer/);
      const items = root.locator(".pf-v6-c-log-viewer__list-item");
      const count = await items.count();
      expect(count).toBeGreaterThan(0);
    });

    test("no line-number gutter without lineNumbers=true", async ({ page }) => {
      const root = page.locator("#lv-basic");
      await expect(root).not.toHaveClass(/pf-m-line-numbers/);
      await expect(root.locator(".pf-v6-c-log-viewer__index")).toHaveCount(0);
    });
  });

  test.describe("Line numbers variant", () => {
    test("has pf-m-line-numbers and __index elements", async ({ page }) => {
      const root = page.locator("#lv-line-numbers");
      await expect(root).toHaveClass(/pf-m-line-numbers/);
      const indexes = root.locator(".pf-v6-c-log-viewer__index");
      const count = await indexes.count();
      expect(count).toBeGreaterThan(0);
      await expect(indexes.first()).toHaveText("1");
    });
  });

  test.describe("With toolbar variant", () => {
    test("header renders search input + nav buttons", async ({ page }) => {
      const root = page.locator("#lv-toolbar");
      await expect(root.locator(".pf-v6-c-log-viewer__header")).toBeVisible();
      await expect(root.locator('input[type="search"]')).toBeVisible();
      await expect(root.locator('button[aria-label="Previous match"]')).toBeVisible();
      await expect(root.locator('button[aria-label="Next match"]')).toBeVisible();
    });

    test("search highlights matches and counter updates", async ({ page }) => {
      const root = page.locator("#lv-toolbar");
      const input = root.locator('input[type="search"]');
      await input.fill("ERROR");
      // Debounced 150ms.
      await page.waitForTimeout(250);
      const matches = root.locator(".pf-v6-c-log-viewer__string.pf-m-match");
      const count = await matches.count();
      expect(count).toBeGreaterThan(0);
      await expect(root.locator(".pf-v6-c-log-viewer__string.pf-m-current")).toHaveCount(1);
    });

    test("nextMatch advances the current match", async ({ page }) => {
      const root = page.locator("#lv-toolbar");
      await root.locator('input[type="search"]').fill("DEBUG");
      await page.waitForTimeout(250);
      const total = await root.locator(".pf-v6-c-log-viewer__string.pf-m-match").count();
      expect(total).toBeGreaterThan(1);
      const first = root.locator(".pf-v6-c-log-viewer__string.pf-m-current").first();
      const firstIndex = await first.getAttribute("data-match-index");
      await root.locator('button[aria-label="Next match"]').click();
      const second = root.locator(".pf-v6-c-log-viewer__string.pf-m-current").first();
      const secondIndex = await second.getAttribute("data-match-index");
      expect(secondIndex).not.toBe(firstIndex);
    });

    test("empty query clears highlights", async ({ page }) => {
      const root = page.locator("#lv-toolbar");
      const input = root.locator('input[type="search"]');
      await input.fill("INFO");
      await page.waitForTimeout(250);
      expect(await root.locator(".pf-v6-c-log-viewer__string.pf-m-match").count()).toBeGreaterThan(0);
      await input.fill("");
      await page.waitForTimeout(250);
      await expect(root.locator(".pf-v6-c-log-viewer__string.pf-m-match")).toHaveCount(0);
    });
  });

  test.describe("Wrap toggle variant", () => {
    test("toggle button flips pf-m-nowrap on the root", async ({ page }) => {
      const root = page.locator("#lv-wrap-nowrap");
      await expect(root).not.toHaveClass(/pf-m-nowrap/);
      const button = root.locator("button").first();
      await button.click();
      await expect(root).toHaveClass(/pf-m-nowrap/);
      await button.click();
      await expect(root).not.toHaveClass(/pf-m-nowrap/);
    });
  });

  test.describe("ANSI colors variant", () => {
    test("renders inline-colored spans for SGR codes", async ({ page }) => {
      const root = page.locator("#lv-ansi");
      await expect(root).toBeVisible();
      // After init, the text nodes contain <span style="color:..."> wrappers
      // for the OK / FAIL / WARN markers.
      const colored = root.locator(".pf-v6-c-log-viewer__text span[style*='color']");
      const count = await colored.count();
      expect(count).toBeGreaterThan(0);
    });

    test("raw escape bytes are stripped from rendered output", async ({ page }) => {
      const root = page.locator("#lv-ansi");
      const text = await root.locator(".pf-v6-c-log-viewer__list").innerText();
      // 0x1B (ESC) should not appear in the visible text.
      expect(text).not.toContain("\u001b");
    });
  });

  test.describe("Streaming variant", () => {
    test("HTMX SSE appends streamed lines to the list", async ({ page }) => {
      const root = page.locator("#lv-streaming");
      await expect(root).toBeVisible();
      const list = root.locator(".pf-v6-c-log-viewer__list");
      // Initial render has a "Waiting for stream..." placeholder; the SSE ticker
      // adds more items every 1.2s. Wait until at least a second item arrives
      // (avoids racing the placeholder — the placeholder may already be there
      // or already followed by streamed items by the time we assert).
      await expect(list.locator(".pf-v6-c-log-viewer__list-item").nth(1)).toBeVisible({ timeout: 5000 });
      const count = await list.locator(".pf-v6-c-log-viewer__list-item").count();
      expect(count).toBeGreaterThan(1);
    });
  });
});
