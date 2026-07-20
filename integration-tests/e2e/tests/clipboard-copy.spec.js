import { test, expect } from "@playwright/test";

test.describe("Clipboard copy", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/clipboard-copy");
  });

  const EXAMPLES = [
    "basic",
    "readonly",
    "expandable",
    "read-only-expanded",
    "read-only-expanded-by-default",
    "expanded-with-array",
    "json-pre",
    "inline-compact",
    "inline",
    "inline-compact-with-additional-action",
    "inline-compact-truncation",
  ];

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("New variants", () => {
    test("read-only expanded combines both modifiers", async ({ page }) => {
      await expect(page.locator("#cc-ro-expanded .pf-v6-c-form-control")).toHaveClass(/pf-m-readonly/);
      await expect(page.locator("#cc-ro-expanded .pf-v6-c-clipboard-copy__toggle-icon")).toBeAttached();
    });

    test("expanded-by-default opens its panel on load", async ({ page }) => {
      await expect(page.locator("#cc-ro-expanded-default")).toHaveClass(/pf-m-expanded/);
      await expect(page.locator("#cc-ro-expanded-default .pf-v6-c-clipboard-copy__expandable-content")).toBeVisible();
    });

    test("array panel holds multiple lines and json panel wraps a pre", async ({ page }) => {
      await expect(page.locator("#cc-expanded-array .pf-v6-c-clipboard-copy__expandable-content")).toContainText(
        "key-three",
      );
      await expect(page.locator("#cc-json-pre .pf-v6-c-clipboard-copy__expandable-content pre")).toBeVisible();
    });

    test("inline compact renders plain text with a copy action", async ({ page }) => {
      await expect(page.locator("#cc-inline-compact .pf-v6-c-clipboard-copy__text")).toHaveText("2.3.4-2-redhat");
    });

    test("additional action button renders after the copy action", async ({ page }) => {
      await expect(page.locator("#cc-inline-action .pf-v6-c-clipboard-copy__actions-item button")).toHaveCount(2);
    });

    test("truncated value still exposes the full text for copying", async ({ page }) => {
      const text = page.locator("#cc-inline-truncate .pf-v6-c-clipboard-copy__text");
      await expect(text).toContainText("3f9c2a17");
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/clipboard-copy/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/clipboard-copy/${example}`);
        expect(res.status()).toBe(200);
      });
    }
  });

  test.describe("Basic", () => {
    test("has clipboard-copy class", async ({ page }) => {
      await expect(page.locator("#cc-basic")).toHaveClass(/pf-v6-c-clipboard-copy/);
    });

    test("input has correct value", async ({ page }) => {
      await expect(page.locator("#cc-basic-input")).toHaveValue("Copy this string to your clipboard");
    });

    test("copy button is visible with correct aria-label", async ({ page }) => {
      const copyBtn = page.locator("#cc-basic .pf-v6-c-button.pf-m-control").last();
      await expect(copyBtn).toBeVisible();
      await expect(copyBtn).toHaveAttribute("aria-label", "Copy to clipboard");
    });
  });

  test.describe("Read only", () => {
    test("input is readonly", async ({ page }) => {
      await expect(page.locator("#cc-readonly-input")).toHaveAttribute("readonly", "");
    });

    test("input has correct value", async ({ page }) => {
      await expect(page.locator("#cc-readonly-input")).toHaveValue("ghp_a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7");
    });

    test("form control has readonly modifier", async ({ page }) => {
      await expect(page.locator("#cc-readonly .pf-v6-c-form-control")).toHaveClass(/pf-m-readonly/);
    });
  });

  test.describe("Expandable", () => {
    test("toggle button is visible", async ({ page }) => {
      const toggle = page.locator("#cc-expandable .pf-v6-c-clipboard-copy__toggle-icon");
      await expect(toggle).toBeVisible();
    });

    test("expandable content is hidden by default", async ({ page }) => {
      const content = page.locator("#cc-expandable-content");
      await expect(content).not.toBeVisible();
    });

    test("toggle button has aria-expanded false by default", async ({ page }) => {
      const toggleBtn = page.locator("#cc-expandable .pf-v6-c-clipboard-copy__group > button.pf-m-control").first();
      await expect(toggleBtn).toHaveAttribute("aria-expanded", "false");
    });

    test("clicking toggle reveals expandable content", async ({ page }) => {
      const toggleBtn = page.locator("#cc-expandable .pf-v6-c-clipboard-copy__group > button.pf-m-control").first();
      await toggleBtn.click();
      const content = page.locator("#cc-expandable-content");
      await expect(content).toBeVisible();
      await expect(toggleBtn).toHaveAttribute("aria-expanded", "true");
    });

    test("expanded content contains full text", async ({ page }) => {
      const toggleBtn = page.locator("#cc-expandable .pf-v6-c-clipboard-copy__group > button.pf-m-control").first();
      await toggleBtn.click();
      await expect(page.locator("#cc-expandable-content")).toContainText("Full multi-line content");
    });
  });

  test.describe("Inline", () => {
    test("has inline modifier", async ({ page }) => {
      await expect(page.locator("#cc-inline")).toHaveClass(/pf-m-inline/);
    });

    test("text element is a code tag with code modifier", async ({ page }) => {
      const text = page.locator("#cc-inline .pf-v6-c-clipboard-copy__text");
      const tag = await text.evaluate((el) => el.tagName.toLowerCase());
      expect(tag).toBe("code");
      await expect(text).toHaveClass(/pf-m-code/);
    });

    test("displays correct text", async ({ page }) => {
      await expect(page.locator("#cc-inline .pf-v6-c-clipboard-copy__text")).toContainText("npm install");
    });

    test("copy button uses plain style", async ({ page }) => {
      const btn = page.locator("#cc-inline .pf-v6-c-button");
      await expect(btn).toHaveClass(/pf-m-plain/);
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/clipboard-copy");
      for (const ex of ["basic", "expanded-with-array", "inline-compact-with-additional-action"]) {
        const card = page.locator(`[data-rendered-href="/components/clipboard-copy/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/clipboard-copy/source-java/expanded-with-array");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".expandedHtml(");
    });

    test("model expandable panel still toggles", async ({ page }) => {
      await page.goto("/components/clipboard-copy/expandable");
      const panel = page.locator("#cc-expandable-content");
      await expect(panel).toBeHidden();
      await page.locator('#cc-expandable button[aria-label="Toggle content"]').click();
      await expect(panel).toBeVisible();
    });
  });
});
