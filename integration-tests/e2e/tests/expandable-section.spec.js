import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "collapsed",
  "expanded",
  "dynamic-toggle-text",
  "detached",
  "disclosure",
  "indented",
  "custom-toggle",
  "heading-semantics",
  "truncate-expansion",
];

test.describe("Expandable section", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/expandable-section");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("collapsed toggles open; expanded starts open", async ({ page }) => {
    await expect(page.locator("#es-collapsed")).not.toHaveClass(/pf-m-expanded/);
    await page.locator("#es-collapsed .pf-v6-c-expandable-section__toggle button").click();
    await expect(page.locator("#es-collapsed")).toHaveClass(/pf-m-expanded/);
    await expect(page.locator("#es-expanded")).toHaveClass(/pf-m-expanded/);
  });

  test("dynamic toggle text flips with the state", async ({ page }) => {
    const toggle = page.locator("#es-dynamic-toggle-text .pf-v6-c-expandable-section__toggle");
    await expect(toggle.locator("span[x-text]")).toHaveText("Show more");
    await toggle.locator("button").click();
    await expect(toggle.locator("span[x-text]")).toHaveText("Show less");
  });

  test("modifier variants carry their classes", async ({ page }) => {
    await expect(page.locator("#es-detached")).toHaveClass(/pf-m-expand-top/);
    await expect(page.locator("#es-disclosure")).toHaveClass(/pf-m-display-lg/);
    await expect(page.locator("#es-disclosure")).toHaveClass(/pf-m-limit-width/);
    await expect(page.locator("#es-indented")).toHaveClass(/pf-m-indented/);
    await expect(page.locator("#es-truncate-expansion")).toHaveClass(/pf-m-truncate/);
  });

  test("custom toggle composes an icon and a badge", async ({ page }) => {
    const toggle = page.locator("#es-custom-toggle .pf-v6-c-expandable-section__toggle");
    await expect(toggle.locator(".pf-v6-c-icon")).toBeVisible();
    await expect(toggle.locator(".pf-v6-c-badge")).toHaveText("4");
  });

  test("heading semantics nests the toggle inside an h2", async ({ page }) => {
    await expect(page.locator("#es-heading-semantics h2 > .pf-v6-c-expandable-section__toggle")).toBeAttached();
  });

  test("truncate expansion keeps content visible and toggles the clamp", async ({ page }) => {
    const section = page.locator("#es-truncate-expansion");
    await expect(section.locator(".pf-v6-c-expandable-section__content")).toBeVisible();
    const toggleText = section.locator(".pf-v6-c-expandable-section__toggle span[x-text]");
    await expect(toggleText).toHaveText("Show more");
    await section.locator(".pf-v6-c-expandable-section__toggle button").click();
    await expect(section).toHaveClass(/pf-m-expanded/);
    await expect(toggleText).toHaveText("Show less");
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/expandable-section/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/expandable-section/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-expandable-section").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab; custom-toggle does not", async ({ page }) => {
      await page.goto("/components/expandable-section");
      for (const ex of ["collapsed", "truncate-expansion", "heading-semantics"]) {
        const card = page.locator(`[data-rendered-href="/components/expandable-section/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      const handWritten = page.locator('[data-rendered-href="/components/expandable-section/custom-toggle"]');
      await expect(handWritten.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/expandable-section/source-java/truncate-expansion");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".asTruncate()");
    });
  });
});
