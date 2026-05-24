import { test, expect } from "@playwright/test";

test.describe("Clipboard copy", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/clipboard-copy");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#readonly")).toBeVisible();
    await expect(page.locator("#expandable")).toBeVisible();
    await expect(page.locator("#inline")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has clipboard-copy class", async ({ page }) => {
      await expect(page.locator("#cc-basic")).toHaveClass(/pf-v6-c-clipboard-copy/);
    });

    test("input has correct value", async ({ page }) => {
      await expect(page.locator("#cc-basic-input")).toHaveValue(
        "Copy this string to your clipboard"
      );
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
      await expect(page.locator("#cc-readonly-input")).toHaveValue(
        "ghp_a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7"
      );
    });

    test("form control has readonly modifier", async ({ page }) => {
      await expect(page.locator("#cc-readonly .pf-v6-c-form-control")).toHaveClass(
        /pf-m-readonly/
      );
    });
  });

  test.describe("Expandable", () => {
    test("toggle button is visible", async ({ page }) => {
      const toggle = page.locator(
        "#cc-expandable .pf-v6-c-clipboard-copy__toggle-icon"
      );
      await expect(toggle).toBeVisible();
    });

    test("expandable content is hidden by default", async ({ page }) => {
      const content = page.locator("#cc-expandable-content");
      await expect(content).not.toBeVisible();
    });

    test("toggle button has aria-expanded false by default", async ({ page }) => {
      const toggleBtn = page.locator(
        "#cc-expandable .pf-v6-c-clipboard-copy__group > button.pf-m-control"
      ).first();
      await expect(toggleBtn).toHaveAttribute("aria-expanded", "false");
    });

    test("clicking toggle reveals expandable content", async ({ page }) => {
      const toggleBtn = page.locator(
        "#cc-expandable .pf-v6-c-clipboard-copy__group > button.pf-m-control"
      ).first();
      await toggleBtn.click();
      const content = page.locator("#cc-expandable-content");
      await expect(content).toBeVisible();
      await expect(toggleBtn).toHaveAttribute("aria-expanded", "true");
    });

    test("expanded content contains full text", async ({ page }) => {
      const toggleBtn = page.locator(
        "#cc-expandable .pf-v6-c-clipboard-copy__group > button.pf-m-control"
      ).first();
      await toggleBtn.click();
      await expect(page.locator("#cc-expandable-content")).toContainText(
        "Full multi-line content"
      );
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
      await expect(
        page.locator("#cc-inline .pf-v6-c-clipboard-copy__text")
      ).toContainText("npm install");
    });

    test("copy button uses plain style", async ({ page }) => {
      const btn = page.locator("#cc-inline .pf-v6-c-button");
      await expect(btn).toHaveClass(/pf-m-plain/);
    });
  });
});
