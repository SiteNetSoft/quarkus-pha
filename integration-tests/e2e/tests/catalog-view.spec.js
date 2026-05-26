import { test, expect } from "@playwright/test";

test.describe("Catalog view extensions", () => {
  test.describe("Catalog item header", () => {
    test("renders header with title and vendor subtitle", async ({ page }) => {
      await page.goto("/extensions/catalog-view/catalog-item-header");
      const header = page.locator(".catalog-item-header-pf").first();
      await expect(header).toBeVisible();
      await expect(header.locator(".catalog-item-header-pf-title")).toContainText("Quarkus pha");
      await expect(header.locator(".catalog-item-header-pf-subtitle")).toContainText("SiteNetSoft");
    });
  });

  test.describe("Catalog tile", () => {
    test("renders 3 tiles with titles and descriptions", async ({ page }) => {
      await page.goto("/extensions/catalog-view/catalog-tile");
      const tiles = page.locator(".catalog-tile-pf");
      await expect(tiles).toHaveCount(3);
      await expect(page.locator("#ct-quarkus .catalog-tile-pf-title")).toContainText("Quarkus");
      await expect(page.locator("#ct-htmx")).toHaveClass(/catalog-tile-pf-featured/);
    });
  });

  test.describe("Filter side panel", () => {
    test("renders two categories with checkbox items + counts", async ({ page }) => {
      await page.goto("/extensions/catalog-view/filter-side-panel");
      const panel = page.locator("#fsp-demo");
      await expect(panel).toBeVisible();
      await expect(panel.locator(".filter-panel-pf-category")).toHaveCount(2);
      await expect(panel.locator(".filter-panel-pf-category-item")).toHaveCount(7);
      await expect(page.locator("#fsp-cat-runtimes")).toBeChecked();
    });
  });

  test.describe("Properties side panel", () => {
    test("renders 4 label/value rows", async ({ page }) => {
      await page.goto("/extensions/catalog-view/properties-side-panel");
      const panel = page.locator("#psp-demo");
      await expect(panel).toBeVisible();
      await expect(panel.locator(".properties-side-panel-pf-property")).toHaveCount(4);
      await expect(panel.locator(".properties-side-panel-pf-property-label").first()).toContainText("Version");
    });
  });

  test.describe("Vertical tabs", () => {
    test("renders tabs with one active and nested sub-tabs under Databases", async ({ page }) => {
      await page.goto("/extensions/catalog-view/vertical-tabs");
      // Root <nav id> wraps a <ul.vertical-tabs-pf> that holds the top-level <li> tabs.
      const tabs = page.locator("#vt-demo > ul.vertical-tabs-pf > .vertical-tabs-pf-tab");
      await expect(tabs).toHaveCount(4);
      await expect(page.locator("#vt-databases")).toHaveClass(/active/);
      // Nested sub-tabs live inside the Databases <li>'s child ul.
      await expect(page.locator("#vt-databases > ul.vertical-tabs-pf > .vertical-tabs-pf-tab")).toHaveCount(3);
    });
  });
});
