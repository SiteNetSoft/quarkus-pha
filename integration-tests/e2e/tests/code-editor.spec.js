import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "read-only",
  "without-actions",
  "minimal",
  "upload",
  "header-content",
  "container",
  "full-height",
  "custom-control",
];

test.describe("Code editor", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/code-editor");
  });

  test("page loads with all 9 section headings", async ({ page }) => {
    for (const slug of EXAMPLES) {
      await expect(page.locator("#" + slug)).toBeVisible();
    }
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

    test("has all three control buttons", async ({ page }) => {
      const controls = page.locator(
        "#ce-readonly .pf-v6-c-code-editor__controls .pf-v6-c-button"
      );
      await expect(controls).toHaveCount(3);
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

  test.describe("Upload", () => {
    test("renders empty state inside the upload border", async ({ page }) => {
      const upload = page.locator("#ce-upload .pf-v6-c-code-editor__upload");
      await expect(upload).toBeVisible();
      await expect(upload.locator(".pf-v6-c-empty-state__title-text")).toHaveText(
        "Start editing"
      );
      await expect(
        upload.getByRole("button", { name: "Browse" })
      ).toBeVisible();
    });

    test("drag-over applies pf-m-drag-hover to main, live", async ({ page }) => {
      const root = page.locator("#ce-upload");
      const main = root.locator(".pf-v6-c-code-editor__main");
      await expect(main).not.toHaveClass(/pf-m-drag-hover/);
      await root.dispatchEvent("dragover");
      await expect(main).toHaveClass(/pf-m-drag-hover/);
      await root.dispatchEvent("dragleave");
      await expect(main).not.toHaveClass(/pf-m-drag-hover/);
    });

    test("start from scratch swaps the empty state for a code view", async ({
      page,
    }) => {
      const root = page.locator("#ce-upload");
      await root.getByRole("button", { name: "Start from scratch" }).click();
      await expect(root.locator(".pf-v6-c-code-editor__upload")).toBeHidden();
      await expect(root.locator(".pf-v6-c-code-editor__code")).toBeVisible();
    });

    test("picking a file loads its text into the code view, live", async ({
      page,
    }) => {
      const root = page.locator("#ce-upload");
      await root.locator("input[type=file]").setInputFiles({
        name: "hello.yaml",
        mimeType: "text/yaml",
        buffer: Buffer.from("hello: world"),
      });
      await expect(root.locator(".pf-v6-c-code-editor__code-pre")).toHaveText(
        "hello: world"
      );
      await expect(root.locator(".pf-v6-c-code-editor__upload")).toBeHidden();
    });
  });

  test.describe("Header content and shortcuts", () => {
    test("shows header main text", async ({ page }) => {
      await expect(
        page.locator("#ce-header-content .pf-v6-c-code-editor__header-main")
      ).toHaveText("Header main content");
    });

    test("shows the shortcuts button in the keyboard-shortcuts area", async ({
      page,
    }) => {
      const shortcuts = page.locator(
        "#ce-header-content .pf-v6-c-code-editor__keyboard-shortcuts"
      );
      await expect(
        shortcuts.getByRole("button", { name: "View shortcuts" })
      ).toBeVisible();
    });
  });

  test.describe("Container", () => {
    test("wraps header and main in a presentation container", async ({ page }) => {
      const container = page.locator("#ce-container .pf-v6-c-code-editor__container");
      await expect(container).toHaveAttribute("role", "presentation");
      await expect(container).toHaveAttribute("tabindex", "0");
      await expect(container.locator(".pf-v6-c-code-editor__header")).toBeVisible();
      await expect(container.locator(".pf-v6-c-code-editor__main")).toBeVisible();
    });

    test("has all three control buttons and the upload empty state", async ({
      page,
    }) => {
      await expect(
        page.locator("#ce-container .pf-v6-c-code-editor__controls .pf-v6-c-button")
      ).toHaveCount(3);
      await expect(
        page.locator("#ce-container .pf-v6-c-code-editor__upload .pf-v6-c-empty-state")
      ).toBeVisible();
    });
  });

  test.describe("Full height", () => {
    test("carries pf-m-full-height and fills its 300px wrapper", async ({ page }) => {
      const root = page.locator("#ce-full-height");
      await expect(root).toHaveClass(/pf-m-full-height/);
      const box = await root.boundingBox();
      expect(box.height).toBeGreaterThan(280);
    });
  });

  test.describe("Custom control", () => {
    test("appends the Execute control after copy and download", async ({ page }) => {
      const controls = page.locator(
        "#ce-custom-control .pf-v6-c-code-editor__controls .pf-v6-c-button"
      );
      await expect(controls).toHaveCount(3);
      await expect(controls.nth(0)).toHaveAttribute("aria-label", "Copy to clipboard");
      await expect(controls.nth(1)).toHaveAttribute("aria-label", "Download code");
      await expect(controls.nth(2)).toHaveAttribute("aria-label", "Execute code");
    });

    test("has no language tab", async ({ page }) => {
      await expect(
        page.locator("#ce-custom-control .pf-v6-c-code-editor__tab")
      ).toHaveCount(0);
    });

    test("execute click flips the icon state for 2s, live", async ({ page }) => {
      const exec = page.locator(
        "#ce-custom-control .pf-v6-c-code-editor__controls .pf-v6-c-button"
      ).nth(2);
      await exec.click();
      await expect(exec).toHaveAttribute("aria-label", "Code executed");
      await expect(exec).toHaveAttribute("aria-label", "Execute code", {
        timeout: 5000,
      });
    });
  });

  test.describe("standalone example routes", () => {
    for (const slug of EXAMPLES) {
      test(slug + " renders standalone", async ({ page }) => {
        const response = await page.goto("/components/code-editor/" + slug);
        expect(response.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-code-editor").first()).toBeVisible();
      });
    }
  });
});
