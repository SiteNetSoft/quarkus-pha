import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "subtitle",
  "subtitle-actions",
  "secondary",
  "compact",
  "flat",
  "modifiers",
  "header-images-actions",
  "title-inline-images-actions",
  "header-without-title",
  "header-wraps",
  "heading-element",
  "multiple-body-sections",
  "body-section-fills",
  "selectable",
  "single-selectable",
  "actionable",
  "actionable-selectable",
  "expandable",
  "expandable-with-icon",
  "with-dividers",
  "single-selectable-tiles",
  "multi-selectable-tiles",
];

test.describe("Card", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/card");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of [...EXAMPLES, "documentation", "props-card", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("every example card renders a pf-v6-c-card", async ({ page }) => {
    for (const slug of EXAMPLES) {
      const card = page.locator(`[data-rendered-href="/components/card/${slug}"]`);
      await expect(card.locator(".pf-v6-c-card").first()).toBeAttached();
    }
  });

  test.describe("Basic variant", () => {
    test("has card class with title, body, and footer", async ({ page }) => {
      const cd = page.locator("#cd-basic");
      await expect(cd).toHaveClass(/pf-v6-c-card/);
      await expect(cd.locator(".pf-v6-c-card__title-text")).toHaveText("Project Apollo");
      await expect(cd.locator(".pf-v6-c-card__body")).toContainText("dashboard");
      await expect(cd.locator(".pf-v6-c-card__footer")).toContainText("Last updated");
    });
  });

  test.describe("Subtitle", () => {
    test("renders a subtitle element below the title", async ({ page }) => {
      await expect(page.locator("#cd-subtitle .pf-v6-c-card__subtitle")).toHaveText("Card subtitle");
    });

    // PF pads __header/__title/__body/__footer but not __subtitle, so a subtitle placed as a
    // direct card child renders 24px outdented. The text assertion above passed throughout that
    // bug — only geometry catches it.
    for (const id of ["#cd-subtitle", "#cd-subtitle-actions"]) {
      test(`${id} subtitle aligns with its title`, async ({ page }) => {
        const offsets = await page.locator(id).evaluate((card) => {
          const inlineStart = (el) =>
            el.getBoundingClientRect().left + parseFloat(getComputedStyle(el).paddingInlineStart);
          return {
            title: inlineStart(card.querySelector(".pf-v6-c-card__title")),
            subtitle: inlineStart(card.querySelector(".pf-v6-c-card__subtitle")),
          };
        });
        expect(offsets.subtitle).toBeCloseTo(offsets.title, 0);
      });
    }
  });

  test.describe("Secondary", () => {
    test("all three cards carry pf-m-secondary", async ({ page }) => {
      await expect(page.locator("#cd-secondary .pf-v6-c-card.pf-m-secondary")).toHaveCount(3);
    });
  });

  test.describe("Compact variant", () => {
    test("has pf-m-compact modifier", async ({ page }) => {
      await expect(page.locator("#cd-compact")).toHaveClass(/pf-m-compact/);
    });
  });

  test.describe("Modifiers", () => {
    test("checkboxes toggle the card modifier classes", async ({ page }) => {
      const card = page.locator("#cd-modifiers-card");
      await expect(card).not.toHaveClass(/pf-m-compact/);
      await page.locator("#cd-mod-compact").check();
      await expect(card).toHaveClass(/pf-m-compact/);
      await page.locator("#cd-mod-large").check();
      await expect(card).toHaveClass(/pf-m-display-lg/);
      await page.locator("#cd-mod-full-height").check();
      await expect(card).toHaveClass(/pf-m-full-height/);
      await page.locator("#cd-mod-plain").check();
      await expect(card).toHaveClass(/pf-m-plain/);
    });
  });

  test.describe("Header variations", () => {
    test("header-images-actions has a brand image and actions", async ({ page }) => {
      await expect(page.locator("#cd-header-images-actions .pf-v6-c-card__header img.pf-v6-c-brand")).toBeVisible();
      await expect(page.locator("#cd-header-images-actions .pf-v6-c-card__actions button")).toBeVisible();
    });

    test("header-without-title has actions but no title", async ({ page }) => {
      await expect(page.locator("#cd-header-without-title .pf-v6-c-card__actions")).toBeAttached();
      await expect(page.locator("#cd-header-without-title .pf-v6-c-card__title")).toHaveCount(0);
    });

    test("header-wraps uses pf-m-wrap", async ({ page }) => {
      await expect(page.locator("#cd-header-wraps .pf-v6-c-card__header")).toHaveClass(/pf-m-wrap/);
    });

    test("heading-element renders the title as an h4", async ({ page }) => {
      await expect(page.locator("#cd-heading-element h4.pf-v6-c-card__title-text")).toBeAttached();
    });
  });

  test.describe("Body sections", () => {
    test("multiple-body-sections stacks three bodies", async ({ page }) => {
      await expect(page.locator("#cd-multiple-body-sections .pf-v6-c-card__body")).toHaveCount(3);
    });

    test("body-section-fills marks two bodies no-fill", async ({ page }) => {
      await expect(page.locator("#cd-body-section-fills .pf-v6-c-card__body.pf-m-no-fill")).toHaveCount(2);
    });
  });

  test.describe("Selectable", () => {
    test("checking the box selects the card", async ({ page }) => {
      const card = page.locator("#cd-selectable-1");
      await expect(card).toHaveClass(/pf-m-selectable/);
      await expect(card).not.toHaveClass(/pf-m-selected/);
      await page.locator("#cd-selectable-1-check").check();
      await expect(card).toHaveClass(/pf-m-selected/);
    });

    test("third card is disabled", async ({ page }) => {
      await expect(page.locator("#cd-selectable-3")).toHaveClass(/pf-m-disabled/);
      await expect(page.locator("#cd-selectable-3-check")).toBeDisabled();
    });
  });

  test.describe("Single selectable", () => {
    test("radio selection is exclusive", async ({ page }) => {
      await page.locator("#cd-single-selectable-1-radio").check();
      await expect(page.locator("#cd-single-selectable-1")).toHaveClass(/pf-m-selected/);
      await page.locator("#cd-single-selectable-2-radio").check();
      await expect(page.locator("#cd-single-selectable-2")).toHaveClass(/pf-m-selected/);
      await expect(page.locator("#cd-single-selectable-1")).not.toHaveClass(/pf-m-selected/);
    });
  });

  test.describe("Actionable", () => {
    test("clickable overlay button fires the action", async ({ page }) => {
      await expect(page.locator("#cd-actionable-1")).toHaveClass(/pf-m-clickable/);
      await page.locator("#cd-actionable-1 .pf-v6-c-card__clickable-action").click();
      await expect(page.locator("#cd-actionable-1 .pf-v6-c-card__body span")).toHaveText("1");
    });

    test("link and disabled variants render", async ({ page }) => {
      await expect(page.locator("#cd-actionable-2 a.pf-v6-c-card__clickable-action")).toBeAttached();
      await expect(page.locator("#cd-actionable-3")).toHaveClass(/pf-m-disabled/);
      await expect(page.locator("#cd-actionable-3 .pf-v6-c-card__clickable-action")).toBeDisabled();
    });
  });

  test.describe("Expandable", () => {
    test("toggle expands and collapses the content", async ({ page }) => {
      const card = page.locator("#cd-expandable");
      const content = card.locator(".pf-v6-c-card__expandable-content");
      await expect(content).toBeHidden();
      await card.locator(".pf-v6-c-card__header-toggle button").click();
      await expect(card).toHaveClass(/pf-m-expanded/);
      await expect(content).toBeVisible();
      await card.locator(".pf-v6-c-card__header-toggle button").click();
      await expect(content).toBeHidden();
    });
  });

  test.describe("Dividers", () => {
    test("dividers separate the card sections", async ({ page }) => {
      await expect(page.locator("#cd-with-dividers .pf-v6-c-divider")).toHaveCount(3);
    });
  });

  test.describe("Tiles", () => {
    test("multi tiles toggle independently", async ({ page }) => {
      await page.locator("#cd-mtile-1-input").check();
      await page.locator("#cd-mtile-2-input").check();
      await expect(page.locator("#cd-mtile-1")).toHaveClass(/pf-m-selected/);
      await expect(page.locator("#cd-mtile-2")).toHaveClass(/pf-m-selected/);
      await page.locator("#cd-mtile-1-input").uncheck();
      await expect(page.locator("#cd-mtile-1")).not.toHaveClass(/pf-m-selected/);
      await expect(page.locator("#cd-mtile-2")).toHaveClass(/pf-m-selected/);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/card/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/card/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-card").first()).toBeAttached();
      });
    }
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/card/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/card/flat"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/card/flat");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
