import { test, expect } from "@playwright/test";

test.describe("Helper text", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/helper-text");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    await expect(page.locator("#examples")).toBeAttached();
    await expect(page.locator("#basic")).toBeAttached();
    await expect(page.locator("#with-custom-icons")).toBeAttached();
    await expect(page.locator("#multiple-items")).toBeAttached();
    await expect(page.locator("#documentation")).toBeAttached();
    await expect(page.locator("#props-container")).toBeAttached();
    await expect(page.locator("#props-item")).toBeAttached();
    await expect(page.locator("#usage")).toBeAttached();
  });

  test("basic card renders all status variants with auto-icons", async ({ page }) => {
    await expect(page.locator("#ht-basic-static .pf-v6-c-helper-text__item-text")).toHaveText(
      "This is default helper text",
    );
    await expect(page.locator("#ht-basic-success .pf-v6-c-helper-text__item")).toHaveClass(/pf-m-success/);
    await expect(page.locator("#ht-basic-warning .pf-v6-c-helper-text__item")).toHaveClass(/pf-m-warning/);
    await expect(page.locator("#ht-basic-error .pf-v6-c-helper-text__item")).toHaveClass(/pf-m-error/);
    await expect(page.locator("#ht-basic-indeterminate .pf-v6-c-helper-text__item")).toHaveClass(/pf-m-indeterminate/);
    await expect(page.locator("#ht-basic-success .pf-v6-c-helper-text__item-icon")).toBeVisible();
    await expect(page.locator("#ht-basic-warning .pf-v6-c-helper-text__item-icon")).toBeVisible();
    await expect(page.locator("#ht-basic-error .pf-v6-c-helper-text__item-icon")).toBeVisible();
    await expect(page.locator("#ht-basic-indeterminate .pf-v6-c-helper-text__item-icon")).toBeVisible();
  });

  test("custom-icons card overrides the default icon", async ({ page }) => {
    await expect(page.locator("#ht-icon-info .pf-v6-c-helper-text__item-icon svg")).toBeVisible();
    await expect(page.locator("#ht-icon-warning .pf-v6-c-helper-text__item-icon svg")).toBeVisible();
    await expect(page.locator("#ht-icon-error .pf-v6-c-helper-text__item-icon svg")).toBeVisible();
  });

  test("multiple-items card renders 4 items in one container", async ({ page }) => {
    const container = page.locator("#ht-multi-pwd");
    await expect(container).toBeVisible();
    await expect(container.locator(".pf-v6-c-helper-text__item")).toHaveCount(4);
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of ["basic", "with-custom-icons", "multiple-items"]) {
      const res = await page.goto(`/components/helper-text/${slug}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-helper-text").first()).toBeVisible();
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/helper-text");
      for (const ex of ["basic", "multiple-items", "with-custom-icons"]) {
        const card = page.locator(`[data-rendered-href="/components/helper-text/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/helper-text/source-java/multiple-items");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('Item.of("At least 8 characters").variant("success")');
    });
  });
});
