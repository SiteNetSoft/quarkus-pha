import { test, expect } from "@playwright/test";

test.describe("Label", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/label");
  });

  test("page loads with example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#filled-labels")).toHaveText("Filled labels");
    await expect(page.locator("h3#outlined-labels")).toHaveText("Outlined labels");
    await expect(page.locator("h3#compact-labels")).toHaveText("Compact labels");
  });

  test("filled labels have color modifiers", async ({ page }) => {
    await expect(page.locator("#lbl-blue")).toHaveClass(/pf-m-blue/);
    await expect(page.locator("#lbl-green")).toHaveClass(/pf-m-green/);
    await expect(page.locator("#lbl-red")).toHaveClass(/pf-m-red/);
    await expect(page.locator("#lbl-purple")).toHaveClass(/pf-m-purple/);
  });

  test("outline labels have outline modifier", async ({ page }) => {
    await expect(page.locator("#lbl-outline-grey")).toHaveClass(/pf-m-outline/);
    await expect(page.locator("#lbl-outline-blue")).toHaveClass(/pf-m-outline/);
  });

  test("outline label with icon renders the icon span", async ({ page }) => {
    await expect(page.locator("#lbl-outline-icon .pf-v6-c-label__icon")).toBeVisible();
  });

  test("removable label has close button", async ({ page }) => {
    await expect(page.locator("#lbl-removable .pf-v6-c-label__actions .pf-v6-c-button")).toBeVisible();
  });

  test("label text from the content slot renders inside __text", async ({ page }) => {
    await expect(page.locator("#lbl-blue .pf-v6-c-label__text")).toHaveText("Blue");
  });

  test("labels keep PF's intrinsic height (no stretch, no padded close button)", async ({ page }) => {
    // Regression guard: a plain close button (missing pf-m-no-padding) or a flex wrapper
    // without align-items once stretched every label from 26px to 36px.
    for (const id of ["lbl-grey", "lbl-blue", "lbl-removable", "lbl-info"]) {
      const h = await page.locator(`#${id}`).evaluate((el) => el.getBoundingClientRect().height);
      expect(h, `#${id} height`).toBeLessThanOrEqual(27);
      expect(h, `#${id} height`).toBeGreaterThanOrEqual(18);
    }
  });

  test("overflow and add labels render as buttons", async ({ page }) => {
    for (const id of ["lbl-overflow", "lbl-add"]) {
      const tag = await page.locator(`#${id}`).evaluate((el) => el.tagName);
      expect(tag).toBe("BUTTON");
    }
    await expect(page.locator("#lbl-overflow")).toHaveClass(/pf-m-overflow/);
    await expect(page.locator("#lbl-add")).toHaveClass(/pf-m-add/);
  });

  test("vertical group variants carry pf-m-vertical", async ({ page }) => {
    for (const id of ["lbg-vertical-overflow", "lbg-vertical-category", "lbg-vertical-compact"]) {
      await expect(page.locator(`#${id}`)).toHaveClass(/pf-m-vertical/);
    }
  });

  test("compact group labels carry pf-m-compact", async ({ page }) => {
    await expect(page.locator("#lbg-compact .pf-v6-c-label.pf-m-compact")).toHaveCount(3);
  });

  test("truncated label caps its text width", async ({ page }) => {
    const w = await page
      .locator("#lbl-truncated .pf-v6-c-label__text")
      .evaluate((el) => el.getBoundingClientRect().width);
    expect(w).toBeLessThan(400);
  });

  test("overflow toggle actually hides and reveals labels", async ({ page }) => {
    // Regression guard: PF sets display:inline-flex on __list-item, which beats the UA
    // [hidden] rule — the Alpine factory must also set inline display:none.
    const group = page.locator("#lbg-overflow");
    const items = group.locator(".pf-v6-c-label-group__list > li:not(.pha-label-group__overflow)");
    const visible = async () => {
      let n = 0;
      for (let i = 0; i < (await items.count()); i++) if (await items.nth(i).isVisible()) n++;
      return n;
    };
    const btn = group.locator(".pha-label-group__overflow button");
    expect(await visible()).toBe(3);
    await expect(btn).toHaveText("3 more");
    await btn.click();
    expect(await visible()).toBe(6);
    await expect(btn).toHaveText("Show Less");
    await btn.click();
    expect(await visible()).toBe(3);
    await expect(btn).toHaveText("3 more");
  });

  test("expanded overflow variants start expanded and collapse live", async ({ page }) => {
    for (const id of ["lbg-overflow-expanded", "lbg-vertical-overflow-expanded"]) {
      const btn = page.locator(`#${id} .pha-label-group__overflow button`);
      await expect(btn).toHaveText("Show Less");
      await btn.click();
      await expect(btn).toHaveText("3 more");
    }
  });

  test("removable label close buttons remove their label", async ({ page }) => {
    const group = page.locator("#lbg-static-dynamic");
    const items = group.locator(".pf-v6-c-label-group__list > li:not(.pha-label-group__overflow)");
    await expect(items).toHaveCount(3);
    await group.locator(".pf-v6-c-label__actions button").first().click();
    await expect(items).toHaveCount(2);
  });

  test.describe("Standalone routes", () => {
    for (const example of [
      "overflow-label",
      "add-label",
      "label-group-with-overflow-expanded",
      "vertical-label-group-with-overflow",
      "vertical-label-group-with-overflow-expanded",
      "vertical-label-group-with-category",
      "vertical-label-group-with-removable-category",
      "static-labels-dynamic-group",
      "mixed-labels-dynamic-group",
      "label-group-with-compact-labels",
      "label-group-with-compact-labels-and-overflow",
      "vertical-label-group-with-compact-labels",
      "labels-with-truncation",
    ]) {
      test(`/components/label/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/label/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-label").first()).toBeAttached();
      });
    }
  });

  test.describe("Java source tab", () => {
    // All examples except the two hand-written compositions are model-driven (LabelDemoData).
    const HAND_WRITTEN = ["label-with-custom-render", "editable-label-group-with-add-button"];

    test("model-driven cards get a leading Java tab; hand-written ones do not", async ({ page }) => {
      await page.goto("/components/label");
      for (const ex of ["filled-labels", "basic-label-group", "editable-label-group", "labels-with-truncation"]) {
        const card = page.locator(`[data-rendered-href="/components/label/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of HAND_WRITTEN) {
        const card = page.locator(`[data-rendered-href="/components/label/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("Java tab opens Monaco with the builder code", async ({ page }) => {
      await page.goto("/components/label");
      const card = page.locator('[data-rendered-href="/components/label/basic-label-group"]');
      await card.locator('button[aria-label*="Toggle Java"]').click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
      await expect(card.locator(".monaco-editor .view-lines")).toContainText("LabelGroup.builder()", {
        timeout: 10000,
      });
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/label/source-java/labels-with-truncation");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.maxWidth("38ch")');
    });
  });
});
