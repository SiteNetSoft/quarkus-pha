import { test, expect } from "@playwright/test";

test.describe("Accordion", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/accordion");
  });

  test("page loads with all 5 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#definition-list")).toBeVisible();
    await expect(page.locator("#single-expand")).toBeVisible();
    await expect(page.locator("#fixed-multiple")).toBeVisible();
    await expect(page.locator("#bordered")).toBeVisible();
    await expect(page.locator("#toggle-start")).toBeVisible();
  });

  test.describe("Definition list variant", () => {
    test("root element is a dl with 4 dt/dd pairs", async ({ page }) => {
      const accordion = page.locator("#acc-dl");
      await expect(accordion).toBeVisible();
      const tagName = await accordion.evaluate((el) => el.tagName.toLowerCase());
      expect(tagName).toBe("dl");
      await expect(accordion.locator("dt")).toHaveCount(4);
      await expect(accordion.locator("dd")).toHaveCount(4);
    });

    test("toggle expands content and updates aria-expanded", async ({ page }) => {
      const accordion = page.locator("#acc-dl");
      const firstToggle = accordion.locator(".pf-v6-c-accordion__toggle").first();
      const firstContent = accordion.locator(".pf-v6-c-accordion__expandable-content").first();

      await expect(firstContent).toBeHidden();
      await expect(firstToggle).toHaveAttribute("aria-expanded", "false");

      await firstToggle.click();
      await expect(firstContent).toBeVisible();
      await expect(firstToggle).toHaveAttribute("aria-expanded", "true");
      await expect(firstContent).toHaveClass(/pf-m-expanded/);
    });

    test("toggle ids and content aria-labelledby are linked", async ({ page }) => {
      const firstToggle = page.locator("#acc-dl-item-1-toggle");
      const firstContent = page.locator("#acc-dl-item-1-content");
      await expect(firstToggle).toHaveAttribute("aria-controls", "acc-dl-item-1-content");
      await expect(firstContent).toHaveAttribute("aria-labelledby", "acc-dl-item-1-toggle");
    });

    test("multiple items can be open at once (independent state)", async ({ page }) => {
      const accordion = page.locator("#acc-dl");
      const toggles = accordion.locator(".pf-v6-c-accordion__toggle");
      const contents = accordion.locator(".pf-v6-c-accordion__expandable-content");

      await toggles.nth(0).click();
      await toggles.nth(1).click();
      await expect(contents.nth(0)).toBeVisible();
      await expect(contents.nth(1)).toBeVisible();
    });
  });

  test.describe("Single expand variant", () => {
    test("opening one item collapses the other", async ({ page }) => {
      const accordion = page.locator("#acc-single");
      const toggles = accordion.locator(".pf-v6-c-accordion__toggle");
      const contents = accordion.locator(".pf-v6-c-accordion__expandable-content");

      await toggles.nth(0).click();
      await expect(contents.nth(0)).toBeVisible();
      await expect(contents.nth(1)).toBeHidden();

      await toggles.nth(1).click();
      await expect(contents.nth(0)).toBeHidden();
      await expect(contents.nth(1)).toBeVisible();
    });

    test("clicking the open item closes it (no item open)", async ({ page }) => {
      const accordion = page.locator("#acc-single");
      const firstToggle = accordion.locator(".pf-v6-c-accordion__toggle").first();
      const firstContent = accordion.locator(".pf-v6-c-accordion__expandable-content").first();

      await firstToggle.click();
      await expect(firstContent).toBeVisible();
      await firstToggle.click();
      await expect(firstContent).toBeHidden();
    });
  });

  test.describe("Fixed variant", () => {
    test("each content panel has pf-m-fixed", async ({ page }) => {
      const accordion = page.locator("#acc-fixed");
      const contents = accordion.locator(".pf-v6-c-accordion__expandable-content");
      const count = await contents.count();
      for (let i = 0; i < count; i++) {
        await expect(contents.nth(i)).toHaveClass(/pf-m-fixed/);
      }
    });
  });

  test.describe("Bordered variant", () => {
    test("root has pf-m-bordered", async ({ page }) => {
      await expect(page.locator("#acc-bordered")).toHaveClass(/pf-m-bordered/);
    });
  });

  test.describe("Toggle at start variant", () => {
    test("root has pf-m-toggle-start", async ({ page }) => {
      await expect(page.locator("#acc-toggle-start")).toHaveClass(/pf-m-toggle-start/);
    });

    test("icon span precedes text span in DOM order", async ({ page }) => {
      const firstToggle = page.locator("#acc-toggle-start .pf-v6-c-accordion__toggle").first();
      const order = await firstToggle.evaluate((el) => {
        const children = Array.from(el.children);
        const iconIndex = children.findIndex((c) => c.classList.contains("pf-v6-c-accordion__toggle-icon"));
        const textIndex = children.findIndex((c) => c.classList.contains("pf-v6-c-accordion__toggle-text"));
        return { iconIndex, textIndex };
      });
      expect(order.iconIndex).toBeLessThan(order.textIndex);
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens a Monaco editor with the fragment source", async ({ page }) => {
      const card = page.locator('[data-source-href="/components/accordion/definition-list"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-source-href="/components/accordion/bordered"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/accordion/bordered");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
