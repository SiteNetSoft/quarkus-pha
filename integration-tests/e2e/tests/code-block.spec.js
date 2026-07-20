import { test, expect } from "@playwright/test";

test.describe("Code block", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/code-block");
  });

  test("page loads with both section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#with-copy")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has code-block class", async ({ page }) => {
      await expect(page.locator("#cb-basic")).toHaveClass(/pf-v6-c-code-block/);
    });

    test("contains pre and code elements", async ({ page }) => {
      await expect(page.locator("#cb-basic .pf-v6-c-code-block__pre")).toBeVisible();
      await expect(page.locator("#cb-basic .pf-v6-c-code-block__code")).toBeVisible();
    });

    test("displays code text", async ({ page }) => {
      await expect(page.locator("#cb-basic .pf-v6-c-code-block__code")).toContainText("apiVersion: v1");
    });

    test("has no header actions", async ({ page }) => {
      await expect(page.locator("#cb-basic .pf-v6-c-code-block__header")).toHaveCount(0);
    });
  });

  test.describe("With copy", () => {
    test("has header with actions", async ({ page }) => {
      await expect(page.locator("#cb-copy .pf-v6-c-code-block__header")).toBeVisible();
      await expect(page.locator("#cb-copy .pf-v6-c-code-block__actions")).toBeVisible();
    });

    test("copy button is visible with correct aria-label", async ({ page }) => {
      const copyBtn = page.locator("#cb-copy .pf-v6-c-code-block__actions-item .pf-v6-c-button");
      await expect(copyBtn).toBeVisible();
      await expect(copyBtn).toHaveAttribute("aria-label", "Copy to clipboard");
    });

    test("code content is in pre/code structure", async ({ page }) => {
      const pre = page.locator("#cb-copy pre.pf-v6-c-code-block__pre");
      await expect(pre).toBeVisible();
      const code = pre.locator("code.pf-v6-c-code-block__code");
      await expect(code).toContainText("curl -X POST");
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/code-block");
      for (const ex of ["basic", "with-copy"]) {
        const card = page.locator(`[data-rendered-href="/components/code-block/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/code-block/source-java/with-copy");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".withCopy()");
    });

    test("model with-copy block still copies via the header button", async ({ page }) => {
      await page.goto("/components/code-block/with-copy");
      const btn = page.locator("#cb-copy button");
      await expect(btn).toHaveAttribute("aria-label", "Copy to clipboard");
      await btn.click();
      await expect(btn).toHaveAttribute("aria-label", "Successfully copied to clipboard!");
    });
  });
});
