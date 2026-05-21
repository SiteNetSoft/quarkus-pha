import { test, expect } from "@playwright/test";

test.describe("Content", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/content");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of [
      "examples",
      "content-as-a-wrapper",
      "headings",
      "body",
      "unordered-list",
      "ordered-list",
      "plain-list",
      "description-list",
      "link-and-visited-link",
      "editorial-content",
      "documentation",
      "props-content",
      "usage",
    ]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("wrapper example renders pf-v6-c-content div with mixed children", async ({ page }) => {
    const wrapper = page.locator("#content-wrapper");
    await expect(wrapper).toBeVisible();
    await expect(wrapper).toHaveClass(/pf-v6-c-content/);
    await expect(wrapper.locator("h2")).toBeVisible();
    await expect(wrapper.locator("p").first()).toBeVisible();
    await expect(wrapper.locator("ul li")).toHaveCount(3);
    await expect(wrapper.locator("blockquote")).toBeVisible();
  });

  test("headings example renders h1..h6 each with the matching pf-v6-c-content--hN class", async ({ page }) => {
    const card = page.locator('[data-source-href="/components/content/source/headings"]');
    for (const level of [1, 2, 3, 4, 5, 6]) {
      await expect(card.locator(`h${level}.pf-v6-c-content--h${level}`)).toBeVisible();
    }
  });

  test("body example renders p, small, blockquote, pre with matching classes", async ({ page }) => {
    const card = page.locator('[data-source-href="/components/content/source/body"]');
    await expect(card.locator("p.pf-v6-c-content--p")).toBeVisible();
    await expect(card.locator("small.pf-v6-c-content--small")).toBeVisible();
    await expect(card.locator("blockquote.pf-v6-c-content--blockquote")).toBeVisible();
    await expect(card.locator("pre.pf-v6-c-content--pre")).toBeVisible();
  });

  test("unordered list renders a ul.pf-v6-c-content--ul with 3 items", async ({ page }) => {
    const ul = page.locator("ul#content-ul.pf-v6-c-content--ul");
    await expect(ul).toBeVisible();
    await expect(ul.locator("> li")).toHaveCount(3);
  });

  test("ordered list renders an ol.pf-v6-c-content--ol with 3 items", async ({ page }) => {
    const ol = page.locator("ol#content-ol.pf-v6-c-content--ol");
    await expect(ol).toBeVisible();
    await expect(ol.locator("> li")).toHaveCount(3);
  });

  test("plain list has pf-m-plain modifier", async ({ page }) => {
    await expect(page.locator("#content-plain")).toHaveClass(/pf-m-plain/);
  });

  test("description list renders dl with dt/dd pairs", async ({ page }) => {
    const dl = page.locator("dl#content-dl.pf-v6-c-content--dl");
    await expect(dl).toBeVisible();
    await expect(dl.locator("dt")).toHaveCount(3);
    await expect(dl.locator("dd")).toHaveCount(3);
  });

  test("link example renders default + visited anchors", async ({ page }) => {
    const card = page.locator('[data-source-href="/components/content/source/link-and-visited-link"]');
    await expect(card.locator('a.pf-v6-c-content--a[href="#default"]')).toBeVisible();
    await expect(card.locator('a.pf-v6-c-content--a.pf-m-visited[href="#visited"]')).toBeVisible();
  });

  test("editorial card has pf-m-editorial on the wrapper", async ({ page }) => {
    await expect(page.locator("#content-editorial")).toHaveClass(/pf-m-editorial/);
  });

  test("standalone example routes render", async ({ page }) => {
    for (const slug of [
      "content-as-a-wrapper",
      "headings",
      "body",
      "unordered-list",
      "ordered-list",
      "plain-list",
      "description-list",
      "link-and-visited-link",
      "editorial-content",
    ]) {
      const res = await page.goto(`/components/content/${slug}`);
      expect(res.status()).toBe(200);
    }
  });
});
