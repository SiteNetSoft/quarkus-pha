import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "vertical-nav",
  "multiple-sidebar-body",
  "horizontal-nav",
  "uncontrolled-nav",
  "filled-sections",
  "main-section-padding",
  "main-section-variations",
  "group-section",
  "centered-section",
  "plain-sections",
  "dynamic-sticky-section",
];

test.describe("Page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/page");
  });

  test("page loads with the basic example section in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#basic")).toHaveText("Basic");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("every example card renders a page root", async ({ page }) => {
    for (const slug of EXAMPLES) {
      const card = page.locator(`[data-rendered-href="/components/page/${slug}"]`);
      await expect(card.locator(".pf-v6-c-page").first()).toBeAttached();
    }
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-page")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Vertical navigation", () => {
    test("burger toggle collapses and expands the sidebar", async ({ page }) => {
      const sidebar = page.locator("#pg-vertical-nav .pf-v6-c-page__sidebar");
      await expect(sidebar).toBeVisible();
      await expect(sidebar).toHaveClass(/pf-m-expanded/);
      await page.locator("#pg-vertical-nav .pf-v6-c-masthead__toggle button").click();
      await expect(sidebar).toBeHidden();
      await page.locator("#pg-vertical-nav .pf-v6-c-masthead__toggle button").click();
      await expect(sidebar).toBeVisible();
    });

    test("renders three sections, one secondary", async ({ page }) => {
      await expect(page.locator("#pg-vertical-nav .pf-v6-c-page__main-section")).toHaveCount(3);
      await expect(page.locator("#pg-vertical-nav .pf-v6-c-page__main-section.pf-m-secondary")).toHaveCount(1);
    });
  });

  test.describe("Multiple sidebar body", () => {
    test("sidebar stacks a context-selector body and a fill body", async ({ page }) => {
      const root = page.locator("#pg-multiple-sidebar-body");
      await expect(root.locator(".pf-v6-c-page__sidebar-body")).toHaveCount(2);
      await expect(root.locator(".pf-v6-c-page__sidebar-body.pf-m-context-selector")).toHaveCount(1);
      await expect(root.locator(".pf-v6-c-page__sidebar-body.pf-m-fill")).toHaveCount(1);
    });
  });

  test.describe("Horizontal navigation", () => {
    test("masthead content carries a horizontal nav, no sidebar", async ({ page }) => {
      const root = page.locator("#pg-horizontal-nav");
      await expect(root.locator(".pf-v6-c-masthead__content .pf-v6-c-nav.pf-m-horizontal")).toBeVisible();
      await expect(root.locator(".pf-v6-c-page__sidebar")).toHaveCount(0);
    });
  });

  test.describe("Filled page sections", () => {
    test("fill and no-fill modifiers are applied", async ({ page }) => {
      const root = page.locator("#pg-filled-sections");
      await expect(root.locator(".pf-v6-c-page__main-section.pf-m-fill")).toHaveCount(1);
      await expect(root.locator(".pf-v6-c-page__main-section.pf-m-no-fill")).toHaveCount(1);
    });
  });

  test.describe("Main section padding", () => {
    test("no-padding and responsive padding modifiers are applied", async ({ page }) => {
      const root = page.locator("#pg-main-section-padding");
      await expect(root.locator(".pf-v6-c-page__main-section.pf-m-no-padding")).toHaveCount(2);
      await expect(root.locator(".pf-v6-c-page__main-section.pf-m-padding-on-md")).toHaveCount(1);
    });
  });

  test.describe("Main section variations", () => {
    test("typed main children render", async ({ page }) => {
      const root = page.locator("#pg-main-section-variations");
      for (const cls of ["main-subnav", "main-tabs", "main-breadcrumb", "main-section", "main-wizard"]) {
        await expect(root.locator(`.pf-v6-c-page__${cls}`).first()).toBeAttached();
      }
    });
  });

  test.describe("Group section", () => {
    test("group bundles a breadcrumb and a section", async ({ page }) => {
      const group = page.locator("#pg-group-section .pf-v6-c-page__main-group");
      await expect(group.locator(".pf-v6-c-page__main-breadcrumb")).toBeAttached();
      await expect(group.locator(".pf-v6-c-page__main-section")).toBeAttached();
    });
  });

  test.describe("Centered section", () => {
    test("section is width-limited and center-aligned", async ({ page }) => {
      const section = page.locator("#pg-centered-section .pf-v6-c-page__main-section");
      await expect(section).toHaveClass(/pf-m-limit-width/);
      await expect(section).toHaveClass(/pf-m-align-center/);
    });
  });

  test.describe("Plain sections and groups", () => {
    test("plain section, plain group, and secondary section render", async ({ page }) => {
      const root = page.locator("#pg-plain-sections");
      await expect(root.locator(".pf-v6-c-page__main-section.pf-m-plain")).toHaveCount(1);
      await expect(root.locator(".pf-v6-c-page__main-group.pf-m-plain")).toHaveCount(1);
      await expect(root.locator(".pf-v6-c-page__main-section.pf-m-secondary")).toHaveCount(1);
    });
  });

  test.describe("Dynamic sticky section", () => {
    test("first section is sticky-top", async ({ page }) => {
      await expect(
        page.locator("#pg-dynamic-sticky-section .pf-v6-c-page__main-section.pf-m-sticky-top"),
      ).toBeAttached();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/page/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/page/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-page").first()).toBeAttached();
      });
    }
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/page/basic"]';

    test("page component has pf-v6-c-page class", async ({ page }) => {
      await expect(page.locator(`${card} #pg-basic`)).toHaveClass(/pf-v6-c-page/);
    });

    test("page has a masthead", async ({ page }) => {
      await expect(page.locator(`${card} #pg-basic .pf-v6-c-masthead`)).toBeVisible();
    });

    test("page has a main content area", async ({ page }) => {
      await expect(page.locator(`${card} #pg-basic .pf-v6-c-page__main`)).toBeVisible();
    });

    test("page main area has content", async ({ page }) => {
      await expect(
        page.locator(`${card} #pg-basic .pf-v6-c-page__main-body`)
      ).toContainText("Page content");
    });

    test("page masthead shows brand", async ({ page }) => {
      await expect(
        page.locator(`${card} #pg-basic .pf-v6-c-masthead__brand`)
      ).toContainText("App name");
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/page/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/page/basic"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/page/basic");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
