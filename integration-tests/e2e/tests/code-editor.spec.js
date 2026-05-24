import { test, expect } from "@playwright/test";

test.describe("Code editor", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/code-editor");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#read-only")).toBeVisible();
    await expect(page.locator("#without-actions")).toBeVisible();
    await expect(page.locator("#minimal")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has code-editor class", async ({ page }) => {
      await expect(page.locator("#ce-basic")).toHaveClass(/pf-v6-c-code-editor/);
    });

    test("has header with controls", async ({ page }) => {
      await expect(page.locator("#ce-basic .pf-v6-c-code-editor__header")).toBeVisible();
      await expect(page.locator("#ce-basic .pf-v6-c-code-editor__controls")).toBeVisible();
    });

    test("has copy, download, and upload buttons", async ({ page }) => {
      const controls = page.locator("#ce-basic .pf-v6-c-code-editor__controls .pf-v6-c-button");
      await expect(controls).toHaveCount(3);
      await expect(controls.nth(0)).toHaveAttribute("aria-label", "Copy to clipboard");
      await expect(controls.nth(1)).toHaveAttribute("aria-label", "Download code");
      await expect(controls.nth(2)).toHaveAttribute("aria-label", "Upload code");
    });

    test("has language tab with text", async ({ page }) => {
      const tab = page.locator("#ce-basic .pf-v6-c-code-editor__tab");
      await expect(tab).toBeVisible();
      await expect(tab.locator(".pf-v6-c-code-editor__tab-text")).toHaveText("YAML");
    });

    test("displays code in pre element", async ({ page }) => {
      await expect(page.locator("#ce-basic .pf-v6-c-code-editor__code-pre")).toContainText(
        "HelmChartRepository"
      );
    });

    test("code area has ltr direction", async ({ page }) => {
      await expect(page.locator("#ce-basic .pf-v6-c-code-editor__code")).toHaveAttribute(
        "dir",
        "ltr"
      );
    });
  });

  test.describe("Read-only", () => {
    test("has read-only modifier", async ({ page }) => {
      await expect(page.locator("#ce-readonly")).toHaveClass(/pf-m-read-only/);
    });

    test("has language tab showing YAML", async ({ page }) => {
      await expect(
        page.locator("#ce-readonly .pf-v6-c-code-editor__tab-text")
      ).toHaveText("YAML");
    });

    test("has only copy button in controls", async ({ page }) => {
      const controls = page.locator(
        "#ce-readonly .pf-v6-c-code-editor__controls .pf-v6-c-button"
      );
      await expect(controls).toHaveCount(1);
      await expect(controls.first()).toHaveAttribute("aria-label", "Copy to clipboard");
    });
  });

  test.describe("Without actions", () => {
    test("has no controls section", async ({ page }) => {
      await expect(
        page.locator("#ce-no-actions .pf-v6-c-code-editor__controls")
      ).toHaveCount(0);
    });

    test("has language tab showing JSON", async ({ page }) => {
      await expect(
        page.locator("#ce-no-actions .pf-v6-c-code-editor__tab-text")
      ).toHaveText("JSON");
    });

    test("displays code content", async ({ page }) => {
      await expect(
        page.locator("#ce-no-actions .pf-v6-c-code-editor__code-pre")
      ).toContainText("example");
    });
  });

  test.describe("Minimal", () => {
    test("has no language tab", async ({ page }) => {
      await expect(
        page.locator("#ce-minimal .pf-v6-c-code-editor__tab")
      ).toHaveCount(0);
    });

    test("has no controls", async ({ page }) => {
      await expect(
        page.locator("#ce-minimal .pf-v6-c-code-editor__controls")
      ).toHaveCount(0);
    });

    test("displays code content", async ({ page }) => {
      await expect(
        page.locator("#ce-minimal .pf-v6-c-code-editor__code-pre")
      ).toContainText("Hello, world!");
    });
  });
});
