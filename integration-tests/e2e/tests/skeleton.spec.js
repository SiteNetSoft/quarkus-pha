import { test, expect } from "@playwright/test";

test.describe("Skeleton", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/skeleton");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of [
      "examples",
      "default",
      "percentage-widths",
      "percentage-heights",
      "text-modifiers",
      "shapes",
      "documentation",
      "props-skeleton",
      "usage",
    ]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("default skeleton has pf-v6-c-skeleton + screenreader text", async ({ page }) => {
    const sk = page.locator("#sk-default");
    await expect(sk).toHaveClass(/pf-v6-c-skeleton/);
    await expect(sk.locator(".pf-v6-screen-reader")).toContainText("Loading");
  });

  test("percentage widths apply pf-m-width-NN", async ({ page }) => {
    for (const pct of ["25", "33", "50", "66", "75"]) {
      await expect(page.locator(`#sk-w${pct}`)).toHaveClass(new RegExp(`pf-m-width-${pct}`));
    }
  });

  test("percentage heights apply pf-m-height-NN", async ({ page }) => {
    for (const pct of ["25", "50", "75", "100"]) {
      await expect(page.locator(`#sk-h${pct}`)).toHaveClass(new RegExp(`pf-m-height-${pct}`));
    }
  });

  test("font-size modifiers apply pf-m-text-XX", async ({ page }) => {
    for (const size of ["sm", "md", "lg", "xl", "2xl", "3xl", "4xl"]) {
      await expect(page.locator(`#sk-t-${size}`)).toHaveClass(new RegExp(`pf-m-text-${size}`));
    }
  });

  test("shape modifiers apply pf-m-circle / pf-m-square", async ({ page }) => {
    await expect(page.locator("#sk-shape-circle-sm")).toHaveClass(/pf-m-circle/);
    await expect(page.locator("#sk-shape-circle-md")).toHaveClass(/pf-m-circle/);
    await expect(page.locator("#sk-shape-square-md")).toHaveClass(/pf-m-square/);
  });

  test("widthValue + heightValue emit inline style", async ({ page }) => {
    const sk = page.locator("#sk-shape-circle-px");
    await expect(sk).toHaveAttribute("style", /width:\s*80px/);
    await expect(sk).toHaveAttribute("style", /height:\s*80px/);
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of ["default", "percentage-widths", "percentage-heights", "text-modifiers", "shapes"]) {
      const res = await page.goto(`/components/skeleton/${slug}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-skeleton").first()).toBeVisible();
    }
  });
});
