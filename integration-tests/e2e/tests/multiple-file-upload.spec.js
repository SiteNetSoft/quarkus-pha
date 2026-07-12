import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "horizontal", "status", "status-expanded", "horizontal-status-expanded"];

test.describe("Multiple File Upload", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/multiple-file-upload");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Multiple file upload");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of [...EXAMPLES, "documentation", "props-multiple-file-upload", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic", () => {
    test("renders title, separator, Upload button, and info", async ({ page }) => {
      const mfu = page.locator("#mfu-basic");
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__title-text")).toHaveText("Drag and drop files here");
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__title-text-separator")).toHaveText("or");
      await expect(mfu.locator(".pf-v6-c-button.pf-m-secondary")).toContainText("Upload");
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__info")).toContainText("Accepted file types");
    });

    test("drag-over applies pf-m-drag-over live", async ({ page }) => {
      const mfu = page.locator("#mfu-basic");
      await expect(mfu).not.toHaveClass(/pf-m-drag-over/);
      await mfu.dispatchEvent("dragover");
      await expect(mfu).toHaveClass(/pf-m-drag-over/);
      await mfu.dispatchEvent("dragleave");
      await expect(mfu).not.toHaveClass(/pf-m-drag-over/);
      await mfu.dispatchEvent("dragover");
      await mfu.dispatchEvent("drop");
      await expect(mfu).not.toHaveClass(/pf-m-drag-over/);
    });
  });

  test.describe("Horizontal", () => {
    test("carries pf-m-horizontal and live drag-over", async ({ page }) => {
      const mfu = page.locator("#mfu-horizontal");
      await expect(mfu).toHaveClass(/pf-m-horizontal/);
      await mfu.dispatchEvent("dragover");
      await expect(mfu).toHaveClass(/pf-m-drag-over/);
      await mfu.dispatchEvent("drop");
      await expect(mfu).not.toHaveClass(/pf-m-drag-over/);
    });
  });

  test.describe("File upload status", () => {
    test("starts collapsed and expands via the toggle", async ({ page }) => {
      const mfu = page.locator("#mfu-status");
      const toggle = mfu.locator(".pf-v6-c-expandable-section__toggle");
      await expect(toggle).toHaveAttribute("aria-expanded", "false");
      await expect(toggle).toContainText("0 of 3 files uploaded");
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__status-item").first()).toBeHidden();
      await toggle.click();
      await expect(toggle).toHaveAttribute("aria-expanded", "true");
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__status-item")).toHaveCount(3);
      await expect(mfu.locator("[role='progressbar']")).toHaveCount(3);
    });

    test("close buttons remove items and the count follows", async ({ page }) => {
      const mfu = page.locator("#mfu-status");
      await mfu.locator(".pf-v6-c-expandable-section__toggle").click();
      await mfu.locator(".pf-v6-c-multiple-file-upload__status-item-close button").first().click();
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__status-item")).toHaveCount(2);
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__status-progress-text")).toHaveText(
        "0 of 2 files uploaded",
      );
    });
  });

  test.describe("Expanded status variants", () => {
    test("status-expanded starts expanded with three visible items", async ({ page }) => {
      const mfu = page.locator("#mfu-status-expanded");
      await expect(mfu.locator(".pf-v6-c-expandable-section__toggle")).toHaveAttribute("aria-expanded", "true");
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__status-item")).toHaveCount(3);
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__status-item").first()).toBeVisible();
    });

    test("horizontal-status-expanded combines pf-m-horizontal with the expanded status", async ({ page }) => {
      const mfu = page.locator("#mfu-horizontal-status-expanded");
      await expect(mfu).toHaveClass(/pf-m-horizontal/);
      await expect(mfu.locator(".pf-v6-c-expandable-section__toggle")).toHaveAttribute("aria-expanded", "true");
      await expect(mfu.locator(".pf-v6-c-multiple-file-upload__status-item")).toHaveCount(3);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/multiple-file-upload/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/multiple-file-upload/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-multiple-file-upload").first()).toBeAttached();
      });
    }
  });
});
