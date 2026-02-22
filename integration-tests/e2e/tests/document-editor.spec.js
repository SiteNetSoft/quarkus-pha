import { test, expect } from "@playwright/test";

test.describe("Document Editor", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/document-editor");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Document Editor");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#toolbar-heading")).toBeVisible();
    await expect(page.locator("#readonly-heading")).toBeVisible();
    await expect(page.locator("#status-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("container has pha-c-document-editor class", async ({ page }) => {
      await expect(page.locator("#editor-basic")).toHaveClass(/pha-c-document-editor/);
    });

    test("has editor body", async ({ page }) => {
      await expect(page.locator("#editor-basic .pha-c-document-editor__body")).toBeVisible();
    });

    test("has iframe with sandbox attribute", async ({ page }) => {
      const iframe = page.locator("#editor-basic .pha-c-document-editor__frame");
      await expect(iframe).toHaveCount(1);
      const sandbox = await iframe.getAttribute("sandbox");
      expect(sandbox).toContain("allow-scripts");
      expect(sandbox).toContain("allow-same-origin");
    });

    test("shows empty state when no server configured", async ({ page }) => {
      const empty = page.locator("#editor-basic .pha-c-document-editor__empty");
      await expect(empty).toBeVisible();
      await expect(empty.locator(".pf-v6-c-empty-state__title-text")).toHaveText("No document loaded");
    });
  });

  test.describe("With Toolbar", () => {
    test("has toolbar", async ({ page }) => {
      await expect(page.locator("#editor-toolbar .pha-c-document-editor__toolbar")).toBeVisible();
    });

    test("toolbar shows file name", async ({ page }) => {
      const title = page.locator("#editor-toolbar .pha-c-document-editor__toolbar-title");
      await expect(title).toHaveText("Quarterly Report.odt");
    });

    test("toolbar has save button", async ({ page }) => {
      await expect(page.locator("#editor-toolbar button[aria-label='Save']")).toBeVisible();
    });

    test("toolbar has print button", async ({ page }) => {
      await expect(page.locator("#editor-toolbar button[aria-label='Print']")).toBeVisible();
    });

    test("toolbar has download button", async ({ page }) => {
      await expect(page.locator("#editor-toolbar button[aria-label='Download']")).toBeVisible();
    });

    test("toolbar has fullscreen button", async ({ page }) => {
      await expect(page.locator("#editor-toolbar button[aria-label='Fullscreen']")).toBeVisible();
    });
  });

  test.describe("Read-only Viewer", () => {
    test("has read-only label", async ({ page }) => {
      const label = page.locator("#editor-readonly .pf-v6-c-label__text");
      await expect(label).toHaveText("Read-only");
    });

    test("has custom height", async ({ page }) => {
      const body = page.locator("#editor-readonly .pha-c-document-editor__body");
      await expect(body).toHaveAttribute("style", /min-height:\s*400px/);
    });
  });

  test.describe("With Status Bar", () => {
    test("has status bar", async ({ page }) => {
      await expect(page.locator("#editor-status-bar")).toBeVisible();
    });

    test("status shows not connected", async ({ page }) => {
      await expect(page.locator("#editor-status-bar")).toContainText("Not connected");
    });

    test("has custom height", async ({ page }) => {
      const body = page.locator("#editor-status .pha-c-document-editor__body");
      await expect(body).toHaveAttribute("style", /min-height:\s*350px/);
    });
  });
});
