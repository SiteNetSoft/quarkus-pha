import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "vertical",
  "grouped",
  "expandable",
  "expandable-third-level",
  "mixed",
  "horizontal",
  "horizontal-subnav",
  "icons",
];

test.describe("Navigation", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/navigation");
  });

  test("page loads with all example sections in ToC", async ({ page }) => {
    await expect(page.locator("#vertical")).toBeVisible();
    await expect(page.locator("#vertical")).toHaveText("Vertical");
    await expect(page.locator("#grouped")).toBeVisible();
    await expect(page.locator("#grouped")).toHaveText("Grouped");
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-navigation")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Vertical variant", () => {
    const card = '[data-rendered-href="/components/navigation/vertical"]';

    test("vertical nav has pf-v6-c-nav class", async ({ page }) => {
      await expect(page.locator(`${card} #nav-vertical`)).toHaveClass(/pf-v6-c-nav/);
    });

    test("vertical nav has 5 nav items", async ({ page }) => {
      await expect(page.locator(`${card} #nav-vertical .pf-v6-c-nav__item`)).toHaveCount(5);
    });

    test("vertical nav has one current item with aria-current=page", async ({ page }) => {
      const current = page.locator(`${card} #nav-vertical .pf-v6-c-nav__link.pf-m-current`);
      await expect(current).toHaveCount(1);
      await expect(current).toHaveAttribute("aria-current", "page");
    });
  });

  test.describe("Grouped variant", () => {
    const card = '[data-rendered-href="/components/navigation/grouped"]';

    test("grouped nav has pf-v6-c-nav class", async ({ page }) => {
      await expect(page.locator(`${card} #nav-grouped`)).toHaveClass(/pf-v6-c-nav/);
    });

    test("grouped nav has 2 sections with titles", async ({ page }) => {
      await expect(
        page.locator(`${card} #nav-grouped .pf-v6-c-nav__section`)
      ).toHaveCount(2);
      await expect(
        page.locator(`${card} #nav-grouped .pf-v6-c-nav__section-title`)
      ).toHaveCount(2);
    });

    test("grouped nav has one current item", async ({ page }) => {
      await expect(
        page.locator(`${card} #nav-grouped .pf-v6-c-nav__link.pf-m-current`)
      ).toHaveCount(1);
    });
  });

  test.describe("Expandable variant", () => {
    test("first item starts expanded with visible subnav, second collapsed", async ({ page }) => {
      const first = page.locator("#nav-expandable > ul > li").first();
      await expect(first).toHaveClass(/pf-m-expanded/);
      await expect(first.locator(".pf-v6-c-nav__subnav")).toBeVisible();
      const second = page.locator("#nav-expandable > ul > li").nth(1);
      await expect(second).not.toHaveClass(/pf-m-expanded/);
      await expect(second.locator(".pf-v6-c-nav__subnav")).toBeHidden();
    });

    test("clicking the toggle expands and collapses the subnav", async ({ page }) => {
      const second = page.locator("#nav-expandable > ul > li").nth(1);
      const toggle = second.locator("button.pf-v6-c-nav__link");
      await toggle.click();
      await expect(second).toHaveClass(/pf-m-expanded/);
      await expect(toggle).toHaveAttribute("aria-expanded", "true");
      await expect(second.locator(".pf-v6-c-nav__subnav")).toBeVisible();
      await toggle.click();
      await expect(second).not.toHaveClass(/pf-m-expanded/);
      await expect(second.locator(".pf-v6-c-nav__subnav")).toBeHidden();
    });
  });

  test.describe("Expandable third level", () => {
    test("nested expandable opens a third-level subnav", async ({ page }) => {
      const nested = page.locator("#nav-expandable-third-level .pf-v6-c-nav__subnav .pf-v6-c-nav__item", {
        has: page.locator("button.pf-v6-c-nav__link"),
      });
      await expect(nested.locator(".pf-v6-c-nav__subnav")).toBeHidden();
      await nested.locator("button.pf-v6-c-nav__link").click();
      await expect(nested.locator(".pf-v6-c-nav__subnav")).toBeVisible();
      await expect(nested.locator(".pf-v6-c-nav__subnav .pf-v6-c-nav__link")).toHaveCount(2);
    });
  });

  test.describe("Mixed variant", () => {
    test("plain links and one expandable item share the list", async ({ page }) => {
      await expect(page.locator("#nav-mixed > ul > li > a.pf-v6-c-nav__link")).toHaveCount(2);
      await expect(page.locator("#nav-mixed > ul > li > button.pf-v6-c-nav__link")).toHaveCount(1);
    });
  });

  test.describe("Horizontal variants", () => {
    test("horizontal nav has pf-m-horizontal", async ({ page }) => {
      await expect(page.locator("#nav-horizontal")).toHaveClass(/pf-m-horizontal/);
    });

    test("horizontal subnav has pf-m-horizontal and pf-m-subnav", async ({ page }) => {
      const nav = page.locator("#nav-horizontal-subnav");
      await expect(nav).toHaveClass(/pf-m-horizontal/);
      await expect(nav).toHaveClass(/pf-m-subnav/);
    });
  });

  test.describe("With item icons", () => {
    test("every link carries an icon before its text", async ({ page }) => {
      await expect(page.locator("#nav-icons .pf-v6-c-nav__link-icon")).toHaveCount(4);
      await expect(page.locator("#nav-icons .pf-v6-c-nav__link-text")).toHaveCount(4);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/navigation/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/navigation/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-nav").first()).toBeAttached();
      });
    }
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/navigation/vertical"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/navigation/grouped"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/navigation/grouped");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
