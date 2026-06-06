import { test, expect } from "@playwright/test";

test.describe("Masthead", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/masthead");
  });

  test("page loads with all 7 example sections in ToC", async ({ page }) => {
    for (const slug of [
      "basic",
      "mixed-content",
      "display-inline",
      "display-stack",
      "display-stack-inline-responsive",
      "insets",
      "custom-logo",
    ]) {
      await expect(page.locator(`#${slug}`)).toBeVisible();
    }
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-masthead")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/masthead/basic"]';

    test("masthead has pf-v6-c-masthead class", async ({ page }) => {
      await expect(page.locator(`${card} #mh-basic`)).toHaveClass(/pf-v6-c-masthead/);
    });

    test("masthead has a brand area", async ({ page }) => {
      await expect(page.locator(`${card} #mh-basic .pf-v6-c-masthead__brand`)).toBeVisible();
    });

    test("masthead has a content area with a toolbar", async ({ page }) => {
      await expect(page.locator(`${card} #mh-basic .pf-v6-c-masthead__content`)).toBeVisible();
      await expect(page.locator(`${card} #mh-basic .pf-v6-c-toolbar`)).toBeVisible();
    });

    test("masthead has settings and help icon buttons", async ({ page }) => {
      await expect(page.locator(`${card} #mh-basic button[aria-label="Settings"]`)).toBeVisible();
      await expect(page.locator(`${card} #mh-basic button[aria-label="Help"]`)).toBeVisible();
    });
  });

  test.describe("Display stack variant", () => {
    const card = '[data-rendered-href="/components/masthead/display-stack"]';

    test("display-stack masthead has pf-m-display-stack class", async ({ page }) => {
      await expect(page.locator(`${card} #mh-stack`)).toHaveClass(/pf-m-display-stack/);
    });

    test("display-stack masthead has brand area", async ({ page }) => {
      await expect(page.locator(`${card} #mh-stack .pf-v6-c-masthead__brand`)).toBeVisible();
    });

    test("display-stack masthead has content row", async ({ page }) => {
      await expect(page.locator(`${card} #mh-stack .pf-v6-c-masthead__content`)).toBeVisible();
    });
  });

  test.describe("Mixed content variant", () => {
    const card = '[data-rendered-href="/components/masthead/mixed-content"]';

    test("content flex row has primary, secondary and tertiary buttons", async ({ page }) => {
      await expect(page.locator(`${card} #mh-mixed`)).toHaveClass(/pf-v6-c-masthead/);
      await expect(page.locator(`${card} #mh-mixed-primary`)).toHaveClass(/pf-m-primary/);
      await expect(page.locator(`${card} #mh-mixed-secondary`)).toHaveClass(/pf-m-secondary/);
      await expect(page.locator(`${card} #mh-mixed-tertiary`)).toHaveClass(/pf-m-tertiary/);
    });
  });

  test.describe("Display variants", () => {
    test("display-inline has pf-m-display-inline", async ({ page }) => {
      const card = '[data-rendered-href="/components/masthead/display-inline"]';
      await expect(page.locator(`${card} #mh-inline`)).toHaveClass(/pf-m-display-inline/);
    });

    test("stack inline-responsive has both display modifiers", async ({ page }) => {
      const card = '[data-rendered-href="/components/masthead/display-stack-inline-responsive"]';
      await expect(page.locator(`${card} #mh-responsive`)).toHaveClass(/pf-m-display-stack/);
      await expect(page.locator(`${card} #mh-responsive`)).toHaveClass(/pf-m-display-inline-on-lg/);
    });

    test("insets masthead carries inset modifiers", async ({ page }) => {
      const card = '[data-rendered-href="/components/masthead/insets"]';
      await expect(page.locator(`${card} #mh-insets`)).toHaveClass(/pf-m-inset-sm/);
    });
  });

  test.describe("Custom logo variant", () => {
    const card = '[data-rendered-href="/components/masthead/custom-logo"]';

    test("logo is an anchor with an image and accessible name", async ({ page }) => {
      const logo = page.locator(`${card} #mh-custom-logo a.pf-v6-c-masthead__logo`);
      await expect(logo).toBeVisible();
      await expect(logo).toHaveAttribute("aria-label", /PHA/);
      await expect(logo.locator("svg")).toBeVisible();
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/masthead/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/masthead/display-stack"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/masthead/display-stack");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
