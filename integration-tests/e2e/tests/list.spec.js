import { test, expect } from "@playwright/test";

test.describe("List", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/list");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of [
      "examples",
      "basic",
      "inline",
      "ordered",
      "plain",
      "with-horizontal-rules",
      "with-icons",
      "with-large-icons",
      "documentation",
      "props-list",
      "usage",
    ]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic list renders with 3 items", async ({ page }) => {
    const list = page.locator("#list-basic.pf-v6-c-list");
    await expect(list).toBeVisible();
    await expect(list.locator("> li")).toHaveCount(3);
  });

  test("inline list has pf-m-inline modifier", async ({ page }) => {
    await expect(page.locator("#list-inline")).toHaveClass(/pf-m-inline/);
  });

  test("ordered list renders an ol with type attr", async ({ page }) => {
    await expect(page.locator("ol#list-ordered-1.pf-v6-c-list")).toBeVisible();
    await expect(page.locator("ol#list-ordered-A")).toHaveAttribute("type", "A");
    await expect(page.locator("ol#list-ordered-i")).toHaveAttribute("type", "i");
  });

  test("plain list has pf-m-plain modifier", async ({ page }) => {
    await expect(page.locator("#list-plain")).toHaveClass(/pf-m-plain/);
  });

  test("bordered list has pf-m-bordered modifier", async ({ page }) => {
    await expect(page.locator("#list-bordered")).toHaveClass(/pf-m-bordered/);
  });

  test("with-icons list renders __item-icon spans", async ({ page }) => {
    await expect(page.locator("#list-icons .pf-v6-c-list__item-icon").first()).toBeVisible();
    await expect(page.locator("#list-icons .pf-v6-c-list__item-icon")).toHaveCount(4);
  });

  test("with-large-icons list has pf-m-icon-lg modifier", async ({ page }) => {
    await expect(page.locator("#list-icons-lg")).toHaveClass(/pf-m-icon-lg/);
    await expect(page.locator("#list-icons-lg .pf-v6-c-list__item-icon")).toHaveCount(3);
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of [
      "basic",
      "inline",
      "ordered",
      "plain",
      "with-horizontal-rules",
      "with-icons",
      "with-large-icons",
    ]) {
      const res = await page.goto(`/components/list/${slug}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-list").first()).toBeVisible();
    }
  });
});
