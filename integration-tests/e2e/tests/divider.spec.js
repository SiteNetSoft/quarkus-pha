import { test, expect } from "@playwright/test";

test.describe("Divider", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/divider");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of [
      "examples",
      "using-hr",
      "using-li",
      "using-div",
      "inset-medium",
      "inset-at-various-breakpoints",
      "vertical-in-flex-layout",
      "vertical-in-flex-layout-inset-small",
      "vertical-in-flex-layout-inset-at-various-breakpoints",
      "switch-orientation-at-various-breakpoints",
      "documentation",
      "props-divider",
      "usage",
    ]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("hr mode renders <hr class=pf-v6-c-divider>", async ({ page }) => {
    const card = page.locator('[data-source-href="/components/divider/source/using-hr"]');
    await expect(card.locator("hr.pf-v6-c-divider")).toBeVisible();
  });

  test("li mode renders <li class=pf-v6-c-divider> as aria-hidden separator", async ({ page }) => {
    const card = page.locator('[data-source-href="/components/divider/source/using-li"]');
    const items = card.locator("li.pf-v6-c-divider");
    // Demo uses aria-hidden="true" instead of role="separator" so the surrounding
    // <ul> doesn't become a mixed-role list (axe rule list/listitem). The visual
    // divider styling is unchanged.
    await expect(items.first()).toHaveAttribute("aria-hidden", "true");
    await expect(items).toHaveCount(3);
  });

  test("div mode renders <div class=pf-v6-c-divider role=separator>", async ({ page }) => {
    const card = page.locator('[data-source-href="/components/divider/source/using-div"]');
    await expect(card.locator("div.pf-v6-c-divider[role='separator']")).toBeVisible();
  });

  test("inset-medium emits pf-m-inset-md", async ({ page }) => {
    const card = page.locator('[data-source-href="/components/divider/source/inset-medium"]');
    await expect(card.locator(".pf-v6-c-divider.pf-m-inset-md")).toBeVisible();
  });

  test("responsive inset emits per-breakpoint classes", async ({ page }) => {
    const divider = page
      .locator('[data-source-href="/components/divider/source/inset-at-various-breakpoints"] .pf-v6-c-divider')
      .first();
    await expect(divider).toHaveClass(/pf-m-inset-sm-on-md/);
    await expect(divider).toHaveClass(/pf-m-inset-md-on-lg/);
    await expect(divider).toHaveClass(/pf-m-inset-lg-on-xl/);
    await expect(divider).toHaveClass(/pf-m-inset-xl-on-2xl/);
  });

  test("vertical mode emits pf-m-vertical on a div", async ({ page }) => {
    const card = page.locator('[data-source-href="/components/divider/source/vertical-in-flex-layout"]');
    const verticals = card.locator("div.pf-v6-c-divider.pf-m-vertical");
    await expect(verticals).toHaveCount(2);
  });

  test("orientation-switch emits pf-m-vertical-on-md", async ({ page }) => {
    const card = page.locator(
      '[data-source-href="/components/divider/source/switch-orientation-at-various-breakpoints"]',
    );
    const divider = card.locator(".pf-v6-c-divider").first();
    await expect(divider).toHaveClass(/pf-m-vertical-on-md/);
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of [
      "using-hr",
      "using-li",
      "using-div",
      "inset-medium",
      "inset-at-various-breakpoints",
      "vertical-in-flex-layout",
      "vertical-in-flex-layout-inset-small",
      "vertical-in-flex-layout-inset-at-various-breakpoints",
      "switch-orientation-at-various-breakpoints",
    ]) {
      const res = await page.goto(`/components/divider/${slug}`);
      expect(res.status()).toBe(200);
    }
  });
});
