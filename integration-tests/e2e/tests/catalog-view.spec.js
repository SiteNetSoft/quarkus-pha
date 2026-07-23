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

    test("vendor-description variant links the provider in the subtitle", async ({ page }) => {
      await page.goto("/extensions/catalog-view/catalog-item-header");
      await expect(page.locator("#cih-vendor .catalog-item-header-pf-subtitle a")).toContainText("SiteNetSoft");
    });

    test("standalone example routes return 200", async ({ page }) => {
      for (const example of ["basic", "vendor-description"]) {
        const resp = await page.goto(`/extensions/catalog-view/catalog-item-header/${example}`);
        expect(resp.status()).toBe(200);
      }
    });
  });

  test.describe("Catalog tile", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/extensions/catalog-view/catalog-tile");
    });

    test("basic example renders a tile grid with a badged featured tile", async ({ page }) => {
      await expect(page.locator(".catalog-tile-pf")).toHaveCount(8);
      await expect(page.locator("#ct-quarkus .catalog-tile-pf-title")).toContainText("Quarkus");
      await expect(page.locator("#ct-htmx")).toHaveClass(/catalog-tile-pf-featured/);
      await expect(page.locator("#ct-htmx .catalog-tile-pf-badge")).toHaveCount(1);
    });

    test("link variant renders as an anchor", async ({ page }) => {
      await expect(page.locator("a#ct-openshift.catalog-tile-pf")).toHaveAttribute("href", "#");
    });

    test("footer variant renders a card footer", async ({ page }) => {
      await expect(page.locator("#ct-kubernetes .catalog-tile-pf-footer")).toContainText("Operator catalog");
    });

    test("badges variant renders multiple icon badges", async ({ page }) => {
      await expect(page.locator("#ct-istio .catalog-tile-pf-badge-container .catalog-tile-pf-badge")).toHaveCount(2);
    });

    test("text-badge variant renders a plain-text badge", async ({ page }) => {
      await expect(page.locator("#ct-community .catalog-tile-pf-badge")).toContainText("Community");
    });

    test("children variant has an unclamped body instead of a description", async ({ page }) => {
      const body = page.locator("#ct-children .catalog-tile-pf-body");
      await expect(body).toContainText("not truncated");
      await expect(body.locator(".catalog-tile-pf-description")).toHaveCount(0);
    });

    test("standalone example routes return 200", async ({ page }) => {
      for (const example of ["basic", "footer", "link", "icon-badges", "text-badge", "children"]) {
        const resp = await page.goto(`/extensions/catalog-view/catalog-tile/${example}`);
        expect(resp.status()).toBe(200);
      }
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

  test.describe("Java source tabs (model-driven examples)", () => {
    const EXAMPLES = [
      ["catalog-item-header", ["basic", "vendor-description"]],
      ["catalog-tile", ["basic", "footer", "link", "icon-badges", "text-badge", "children"]],
      ["filter-side-panel", ["basic"]],
      ["properties-side-panel", ["basic"]],
      ["vertical-tabs", ["basic"]],
    ];

    for (const [name, examples] of EXAMPLES) {
      test(`every ${name} card gets a leading Java tab`, async ({ page }) => {
        await page.goto(`/extensions/catalog-view/${name}`);
        for (const ex of examples) {
          const card = page.locator(`[data-rendered-href="/extensions/catalog-view/${name}/${ex}"]`);
          await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
        }
      });
    }

    test("source-java routes serve the builder snippets as plain text", async ({ page }) => {
      for (const [name, examples] of EXAMPLES) {
        for (const ex of examples) {
          const res = await page.request.get(`/extensions/catalog-view/${name}/source-java/${ex}`);
          expect(res.status()).toBe(200);
          expect(await res.text()).toContain("org.sitenetsoft.quarkus.pha.model");
        }
      }
    });

    test("vertical-tabs nested children come from the model (no hand-rendered branch)", async ({ page }) => {
      const res = await page.request.get("/extensions/catalog-view/vertical-tabs/source-java/basic");
      expect(await res.text()).toContain('.child(VerticalTabs.Tab.of("PostgreSQL")');
    });
  });
});
