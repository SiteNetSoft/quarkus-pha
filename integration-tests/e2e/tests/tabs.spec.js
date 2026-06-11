import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "box",
  "box-secondary",
  "vertical",
  "vertical-expandable",
  "overflow",
  "inset",
  "page-insets",
  "icons-text",
  "subtabs",
  "filled-with-icons",
  "nav-tabs",
  "nav-subtabs",
  "site-nav",
  "content-body-padding",
  "dynamic",
  "help",
  "help-and-close",
];

const MODIFIERS = {
  "tabs-box": [/pf-m-box/],
  "tabs-box-secondary": [/pf-m-box/, /pf-m-secondary/],
  "tabs-vertical": [/pf-m-vertical/],
  "tabs-vertical-expandable": [/pf-m-vertical/, /pf-m-expandable/],
  "tabs-overflow": [/pf-m-scrollable/],
  "tabs-inset": [/pf-m-inset-lg/],
  "tabs-page-insets": [/pf-m-page-insets/],
  "tabs-filled-with-icons": [/pf-m-fill/],
  "tabs-nav": [/pf-m-nav/],
  "tabs-nav-subtab": [/pf-m-nav/, /pf-m-subtab/],
  "tabs-site-nav": [/pf-m-nav/, /pf-m-page-insets/, /pf-m-no-border-bottom/],
  "tabs-subtabs-secondary": [/pf-m-subtab/],
};

test.describe("Tabs", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/tabs");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of [...EXAMPLES, "documentation", "props-tabs", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("modifier classes are applied", async ({ page }) => {
    for (const [id, patterns] of Object.entries(MODIFIERS)) {
      for (const pattern of patterns) {
        await expect(page.locator(`#${id}`)).toHaveClass(pattern);
      }
    }
  });

  test.describe("Basic", () => {
    test("clicking a tab switches the current item", async ({ page }) => {
      const second = page.locator("#tabs-basic .pf-v6-c-tabs__item").nth(1);
      await expect(second).not.toHaveClass(/pf-m-current/);
      await second.locator("button").click();
      await expect(second).toHaveClass(/pf-m-current/);
    });

    test("arrow keys move focus through the tablist", async ({ page }) => {
      const tabs = page.locator("#tabs-basic [role='tab']");
      await tabs.first().click();
      await page.keyboard.press("ArrowRight");
      await expect(tabs.nth(1)).toBeFocused();
    });
  });

  test.describe("Vertical expandable", () => {
    test("toggle expands the rail", async ({ page }) => {
      const tabs = page.locator("#tabs-vertical-expandable");
      await expect(tabs).not.toHaveClass(/pf-m-expanded/);
      await tabs.locator(".pf-v6-c-tabs__toggle button").click();
      await expect(tabs).toHaveClass(/pf-m-expanded/);
    });
  });

  test.describe("Overflow", () => {
    test("scroll buttons enable and scroll the list", async ({ page }) => {
      const tabs = page.locator("#tabs-overflow");
      await tabs.scrollIntoViewIfNeeded();
      const left = tabs.locator(".pf-v6-c-tabs__scroll-button button").first();
      const right = tabs.locator(".pf-v6-c-tabs__scroll-button button").last();
      await expect(left).toBeDisabled();
      await expect(right).toBeEnabled();
      await right.click();
      await expect(left).toBeEnabled();
    });
  });

  test.describe("Icons and text", () => {
    test("tabs carry item icons", async ({ page }) => {
      await expect(page.locator("#tabs-icons-text .pf-v6-c-tabs__item-icon")).toHaveCount(3);
    });
  });

  test.describe("Subtabs", () => {
    test("secondary tablist switches independently", async ({ page }) => {
      const sub = page.locator("#tabs-subtabs-secondary .pf-v6-c-tabs__item").nth(1);
      await sub.locator("button").click();
      await expect(sub).toHaveClass(/pf-m-current/);
      await expect(page.locator("#tabs-subtabs-main .pf-v6-c-tabs__item").first()).toHaveClass(/pf-m-current/);
    });
  });

  test.describe("Nav tabs", () => {
    test("tab links are anchors with aria-current", async ({ page }) => {
      const first = page.locator("#tabs-nav .pf-v6-c-tabs__item").first();
      await expect(first.locator("a.pf-v6-c-tabs__link")).toHaveAttribute("aria-current", "page");
    });
  });

  test.describe("Content body padding", () => {
    test("panel body has pf-m-padding", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/tabs/content-body-padding"]');
      await expect(card.locator(".pf-v6-c-tab-content__body.pf-m-padding").first()).toBeVisible();
    });
  });

  test.describe("Dynamic", () => {
    test("add button appends a tab and close removes it", async ({ page }) => {
      const items = page.locator("#tabs-dynamic .pf-v6-c-tabs__item");
      await expect(items).toHaveCount(3);
      await page.locator("#tabs-dynamic .pf-v6-c-tabs__add button").click();
      await expect(items).toHaveCount(4);
      await expect(items.nth(3)).toHaveClass(/pf-m-current/);
      await items.nth(3).locator(".pf-v6-c-tabs__item-action button").click();
      await expect(items).toHaveCount(3);
    });
  });

  test.describe("Help action", () => {
    test("help button opens a popover", async ({ page }) => {
      const firstItem = page.locator("#tabs-help .pf-v6-c-tabs__item").first();
      const popover = firstItem.locator(".pf-v6-c-popover");
      await expect(popover).toBeHidden();
      await firstItem.locator(".pf-v6-c-tabs__item-action button").click();
      await expect(popover).toBeVisible();
    });

    test("help-and-close items carry two action buttons", async ({ page }) => {
      await expect(
        page.locator("#tabs-help-and-close .pf-v6-c-tabs__item").first().locator(".pf-v6-c-tabs__item-action button"),
      ).toHaveCount(2);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/tabs/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/tabs/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-tabs").first()).toBeAttached();
      });
    }
  });
});
