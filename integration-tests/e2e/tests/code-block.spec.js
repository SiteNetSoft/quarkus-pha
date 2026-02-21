import { test, expect } from "@playwright/test";

test.describe("Code block", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/code-block");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#actions-heading")).toBeVisible();
    await expect(page.locator("#expandable-heading")).toBeVisible();
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
      await expect(page.locator("#cb-basic .pf-v6-c-code-block__code")).toContainText(
        "apiVersion: helm.openshift.io"
      );
    });

    test("has no header actions", async ({ page }) => {
      await expect(page.locator("#cb-basic .pf-v6-c-code-block__header")).toHaveCount(0);
    });
  });

  test.describe("With actions", () => {
    test("has header with actions", async ({ page }) => {
      await expect(page.locator("#cb-actions .pf-v6-c-code-block__header")).toBeVisible();
      await expect(page.locator("#cb-actions .pf-v6-c-code-block__actions")).toBeVisible();
    });

    test("copy button is visible with correct aria-label", async ({ page }) => {
      const copyBtn = page.locator("#cb-actions .pf-v6-c-code-block__actions-item .pf-v6-c-button");
      await expect(copyBtn).toBeVisible();
      await expect(copyBtn).toHaveAttribute("aria-label", "Copy to clipboard");
    });

    test("code content is in pre/code structure", async ({ page }) => {
      const pre = page.locator("#cb-actions pre.pf-v6-c-code-block__pre");
      await expect(pre).toBeVisible();
      const code = pre.locator("code.pf-v6-c-code-block__code");
      await expect(code).toContainText("connectionConfig");
    });
  });

  test.describe("Expandable", () => {
    test("show more button is visible", async ({ page }) => {
      const toggle = page.locator("#cb-expandable-toggle");
      await expect(toggle).toBeVisible();
      await expect(toggle).toContainText("Show more");
    });

    test("toggle has aria-expanded false by default", async ({ page }) => {
      await expect(page.locator("#cb-expandable-toggle")).toHaveAttribute(
        "aria-expanded",
        "false"
      );
    });

    test("expanded content is hidden by default", async ({ page }) => {
      const hiddenSpan = page.locator("#cb-expandable .pf-v6-c-code-block__code > span");
      await expect(hiddenSpan).not.toBeVisible();
    });

    test("clicking toggle reveals expanded content", async ({ page }) => {
      await page.locator("#cb-expandable-toggle").click();
      const expandedSpan = page.locator("#cb-expandable .pf-v6-c-code-block__code > span");
      await expect(expandedSpan).toBeVisible();
      await expect(expandedSpan).toContainText("connectionConfig");
    });

    test("toggle text changes to Show less when expanded", async ({ page }) => {
      await page.locator("#cb-expandable-toggle").click();
      await expect(page.locator("#cb-expandable-toggle")).toContainText("Show less");
      await expect(page.locator("#cb-expandable-toggle")).toHaveAttribute(
        "aria-expanded",
        "true"
      );
    });

    test("expandable section has detached modifier", async ({ page }) => {
      await expect(
        page.locator("#cb-expandable .pf-v6-c-expandable-section")
      ).toHaveClass(/pf-m-detached/);
    });
  });
});
