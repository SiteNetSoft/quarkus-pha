// Docs tabs on component demo pages (patternfly.org-style tab row under the title):
// "Java Builder" = the existing demo page, "Qute template" = /components/{name}/docs/qute —
// a reference generated from the runtime template sources (include line + header doc comment
// per template), with a composition fallback for components that have no runtime template.
// Demos tab arrives when per-component demo pages exist as separate content.
import { test, expect } from "@playwright/test";

test.describe("docs tabs", () => {
  test("demo page shows the tab row with Java Builder current", async ({ page }) => {
    await page.goto("/components/backdrop");
    const tabs = page.locator(".ws-docs-tabs");
    await expect(tabs).toBeVisible();
    await expect(tabs.locator(".pf-v6-c-tabs__item.pf-m-current .pf-v6-c-tabs__item-text")).toHaveText("Java Builder");
    await expect(tabs.locator('a[aria-current="page"] .pf-v6-c-tabs__item-text')).toHaveText("Java Builder");
    await expect(tabs.locator('a[href="/components/backdrop/docs/qute"]')).toBeVisible();
  });

  test("tab row is present on model-driven, composition, and misc-routed pages", async ({ page }) => {
    for (const slug of ["table", "application-launcher", "wizard", "icon"]) {
      await page.goto(`/components/${slug}`);
      const tabs = page.locator(".ws-docs-tabs");
      await expect(tabs).toBeVisible();
      await expect(tabs.locator(`a[href="/components/${slug}/docs/qute"]`)).toBeVisible();
    }
  });

  test("Qute template tab navigates to the reference page and back", async ({ page }) => {
    await page.goto("/components/backdrop");
    await page.locator('.ws-docs-tabs a[href="/components/backdrop/docs/qute"]').click();
    await expect(page).toHaveURL(/\/components\/backdrop\/docs\/qute$/);
    await expect(page.locator("h1#ws-page-title")).toHaveText("Backdrop");
    const tabs = page.locator(".ws-docs-tabs");
    await expect(tabs.locator(".pf-v6-c-tabs__item.pf-m-current .pf-v6-c-tabs__item-text")).toHaveText("Qute template");
    await expect(page.locator("h2#templates")).toBeVisible();
    await expect(page.locator(".pf-v6-c-code-block__code").first()).toContainText(
      "{#include components/feedback/backdrop /}",
    );

    await tabs.locator('a[href="/components/backdrop"]').click();
    await expect(page).toHaveURL(/\/components\/backdrop$/);
    await expect(page.locator("h1#ws-page-title")).toHaveText("Backdrop");
  });

  test("Qute reference page keeps the showcase shell chrome and gets a ToC", async ({ page }) => {
    await page.goto("/components/backdrop/docs/qute");
    await expect(page.locator("#showcase-nav")).toBeVisible();
    await expect(page.locator(".pha-theme-selector")).toHaveCount(1);
    await expect(page.locator(".pf-v6-c-back-to-top")).toBeAttached();
    await expect(page.locator('.ws-toc a[href="#templates"]')).toBeAttached();
  });

  test("brick families and slug aliases list every runtime template", async ({ page }) => {
    await page.goto("/components/navigation/docs/qute");
    for (const name of ["nav", "nav-item", "nav-item-expandable", "nav-section"]) {
      await expect(page.locator(`h3#${name}`)).toBeAttached();
    }
    // model-mode internals stay out of the param reference
    await expect(page.locator("h3#nav-items")).toHaveCount(0);

    await page.goto("/components/table/docs/qute");
    for (const name of ["table", "table-sort-th", "table-tree-tr", "table-expandable-tr"]) {
      await expect(page.locator(`h3#${name}`)).toBeAttached();
    }
    await expect(page.locator("h3#table-model-content")).toHaveCount(0);
  });

  test("composition components get the composition fallback", async ({ page }) => {
    await page.goto("/components/application-launcher/docs/qute");
    await expect(page.locator("#ws-page-content-router")).toContainText("is a composition");
    await expect(page.locator('.ws-p a[href="/components/application-launcher"]')).toBeVisible();
    await expect(page.locator("h2#templates")).toHaveCount(0);
  });

  test("tab links keep the small corner radius, not pf-m-nav's pill", async ({ page }) => {
    await page.goto("/components/backdrop");
    const radius = await page
      .locator(".ws-docs-tabs .pf-v6-c-tabs__link")
      .first()
      .evaluate((el) => getComputedStyle(el).borderRadius);
    // default theme: --pf-t--global--border--radius--small (6px); pf-m-nav's pill is 999px
    expect(radius).toBe("6px");
  });

  test("qute stub 404s for unknown and non-demo components", async ({ page }) => {
    expect((await page.request.get("/components/definitely-not-real/docs/qute")).status()).toBe(404);
    // chip is in the registry but has no {slug}-demo.html page (HTMX-era template) — no stub either.
    expect((await page.request.get("/components/chip/docs/qute")).status()).toBe(404);
  });
});
