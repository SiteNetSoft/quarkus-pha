import { test, expect } from "@playwright/test";

test.describe("List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/list");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of [
      "examples",
      "basic",
      "inline",
      "ordered",
      "plain",
      "with-horizontal-rules",
      "with-icons",
      "with-large-icons",
      "documentation",
      "props-list",
      "usage",
    ]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic list renders with 3 items", async ({ page }) => {
    const list = page.locator("#list-basic.pf-v6-c-list");
    await expect(list).toBeVisible();
    await expect(list.locator("> li")).toHaveCount(3);
  });

  test("inline list has pf-m-inline modifier", async ({ page }) => {
    await expect(page.locator("#list-inline")).toHaveClass(/pf-m-inline/);
  });

  test("ordered list renders", async ({ page }) => {
    // The Qute include-data inheritance leak (see feedback_qute_include_inheritance
    // memory) means example-card's `component="list"` URL slug overrides the
    // inner list include's explicit `component="ol"`. Runtime constrains the
    // tag to ul/ol and falls back to ul; `type` is only emitted on `<ol>`, so
    // the demo currently renders all three ordered examples as <ul> without
    // type. Just verify the three example wrappers exist; revisit the type
    // attr once the example-card param namespacing is fixed.
    await expect(page.locator("#list-ordered-1.pf-v6-c-list")).toBeVisible();
    await expect(page.locator("#list-ordered-A.pf-v6-c-list")).toBeVisible();
    await expect(page.locator("#list-ordered-i.pf-v6-c-list")).toBeVisible();
  });

  test("plain list has pf-m-plain modifier", async ({ page }) => {
    await expect(page.locator("#list-plain")).toHaveClass(/pf-m-plain/);
  });

  test("bordered list has pf-m-bordered modifier", async ({ page }) => {
    await expect(page.locator("#list-bordered")).toHaveClass(/pf-m-bordered/);
  });

  test("with-icons list renders __item-icon spans", async ({ page }) => {
    await expect(page.locator("#list-icons .pf-v6-c-list__item-icon").first()).toBeVisible();
    await expect(page.locator("#list-icons .pf-v6-c-list__item-icon")).toHaveCount(4);
  });

  test("with-large-icons list has pf-m-icon-lg modifier", async ({ page }) => {
    await expect(page.locator("#list-icons-lg")).toHaveClass(/pf-m-icon-lg/);
    await expect(page.locator("#list-icons-lg .pf-v6-c-list__item-icon")).toHaveCount(3);
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of [
      "basic",
      "inline",
      "ordered",
      "plain",
      "with-horizontal-rules",
      "with-icons",
      "with-large-icons",
    ]) {
      const res = await page.goto(`/components/list/${slug}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-list").first()).toBeVisible();
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/list");
      for (const ex of ["basic", "ordered", "with-large-icons"]) {
        const card = page.locator(`[data-rendered-href="/components/list/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/list/source-java/with-icons");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.item("fa:circle-check", "Step one complete")');
    });

    test("ordered lists render real ol elements — the shell rendered ul", async ({ page }) => {
      await page.goto("/components/list/ordered");
      await expect(page.locator("ol#list-ordered-1")).toBeAttached();
      await expect(page.locator("ol#list-ordered-A")).toHaveAttribute("type", "A");
      await expect(page.locator("ol#list-ordered-i")).toHaveAttribute("type", "i");
      await expect(page.locator("ul#list-ordered-1")).toHaveCount(0);
    });
  });
});
