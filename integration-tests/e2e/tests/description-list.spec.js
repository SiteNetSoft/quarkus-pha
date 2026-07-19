import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "term-help-text",
  "default-2-col",
  "default-3-col-on-lg",
  "horizontal",
  "horizontal-custom-term-width",
  "horizontal-2-col",
  "horizontal-3-col-on-lg",
  "compact",
  "compact-horizontal",
  "fluid-horizontal",
  "fill-columns",
  "large-display-size",
  "default-responsive-columns",
  "horizontal-responsive-columns",
  "responsive-hori-vert-group",
  "default-auto-column-width",
  "horizontal-auto-column-width",
  "default-inline-grid",
  "with-card",
  "large-display-size-and-card",
  "display-size-card-3-col-lg",
  "display-size-card-horizontal-term-width",
  "auto-fit",
  "auto-fit-min-width",
  "auto-fit-min-width-responsive",
  "icons-on-terms",
];

// id -> expected modifier classes on the <dl>
const MODIFIERS = {
  "dl-default-2-col": [/pf-m-2-col/],
  "dl-default-3-col-on-lg": [/pf-m-3-col-on-lg/],
  "dl-horizontal": [/pf-m-horizontal/],
  "dl-horizontal-2-col": [/pf-m-horizontal/, /pf-m-2-col/],
  "dl-compact": [/pf-m-compact/],
  "dl-compact-horizontal": [/pf-m-compact/, /pf-m-horizontal/],
  "dl-fluid-horizontal": [/pf-m-horizontal/, /pf-m-fluid/],
  "dl-large-display-size": [/pf-m-display-lg/],
  "dl-default-responsive-columns": [/pf-m-2-col-on-lg/, /pf-m-3-col-on-xl/],
  "dl-responsive-hori-vert-group": [/pf-m-vertical-on-md/, /pf-m-horizontal-on-lg/],
  "dl-default-auto-column-width": [/pf-m-auto-column-widths/],
  "dl-default-inline-grid": [/pf-m-inline-grid/],
  "dl-auto-fit": [/pf-m-auto-fit/],
};

test.describe("Description List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/description-list");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of [...EXAMPLES, "documentation", "props-description-list", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("every example card renders a description list", async ({ page }) => {
    for (const slug of EXAMPLES) {
      const card = page.locator(`[data-rendered-href="/components/description-list/${slug}"]`);
      await expect(card.locator("dl.pf-v6-c-description-list").first()).toBeAttached();
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
    test("has pf-v6-c-description-list class and groups", async ({ page }) => {
      await expect(page.locator("#dl-basic")).toHaveClass(/pf-v6-c-description-list/);
      await expect(page.locator("#dl-basic .pf-v6-c-description-list__group").first()).toBeVisible();
    });
  });

  test.describe("Term help text", () => {
    test("terms use pf-m-help-text and the first opens a popover", async ({ page }) => {
      const terms = page.locator("#dl-term-help-text .pf-v6-c-description-list__text.pf-m-help-text");
      await expect(terms).toHaveCount(5);
      const popover = page.locator("#dl-term-help-text .pf-v6-c-popover");
      await expect(popover).toBeHidden();
      await terms.first().click();
      await expect(popover).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(popover).toBeHidden();
    });
  });

  test.describe("Custom widths", () => {
    test("horizontal custom term width sets the custom properties", async ({ page }) => {
      await expect(page.locator("#dl-horizontal-custom-term-width")).toHaveAttribute(
        "style",
        /--pf-v6-c-description-list--m-horizontal__term--width:\s*12ch/,
      );
    });

    test("auto-fit min width sets the grid min", async ({ page }) => {
      await expect(page.locator("#dl-auto-fit-min-width")).toHaveAttribute(
        "style",
        /--pf-v6-c-description-list--GridTemplateColumns--min:\s*200px/,
      );
    });
  });

  test.describe("Card groups", () => {
    test("with-card renders groups as cards", async ({ page }) => {
      await expect(page.locator("#dl-with-card > .pf-v6-c-card")).toHaveCount(5);
      await expect(page.locator("#dl-with-card .pf-v6-c-description-list__group")).toHaveCount(0);
    });
  });

  test.describe("Icons on terms", () => {
    test("every term carries a term-icon", async ({ page }) => {
      await expect(page.locator("#dl-icons-on-terms .pf-v6-c-description-list__term-icon")).toHaveCount(5);
    });
  });

  test.describe("Fill columns", () => {
    test("has pf-m-fill-columns modifier", async ({ page }) => {
      await expect(page.locator("#dl-fill")).toHaveClass(/pf-m-fill-columns/);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/description-list/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/description-list/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-description-list").first()).toBeAttached();
      });
    }
  });

  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab, the popover composition does not", async ({ page }) => {
      await page.goto("/components/description-list");
      for (const ex of EXAMPLES.filter((e) => e !== "term-help-text")) {
        const card = page.locator(`[data-rendered-href="/components/description-list/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      const compositionCard = page.locator('[data-rendered-href="/components/description-list/term-help-text"]');
      await expect(compositionCard.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
    });

    test("Java tab opens Monaco with the builder code", async ({ page }) => {
      await page.goto("/components/description-list");
      const card = page.locator('[data-rendered-href="/components/description-list/basic"]');
      await card.locator('button[aria-label*="Toggle Java"]').click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
      await expect(card.locator(".monaco-editor .view-lines")).toContainText("DescriptionList.builder()", {
        timeout: 10000,
      });
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/description-list/source-java/icons-on-terms");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.termIcon("fa:cube")');
    });
  });
});
