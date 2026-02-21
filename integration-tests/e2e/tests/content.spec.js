import { test, expect } from "@playwright/test";

test.describe("Content", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/content");
  });

  test("page loads with all 8 section headings", async ({ page }) => {
    await expect(page.locator("#headings-heading")).toBeVisible();
    await expect(page.locator("#body-heading")).toBeVisible();
    await expect(page.locator("#links-heading")).toBeVisible();
    await expect(page.locator("#ul-heading")).toBeVisible();
    await expect(page.locator("#ol-heading")).toBeVisible();
    await expect(page.locator("#plain-heading")).toBeVisible();
    await expect(page.locator("#dl-heading")).toBeVisible();
    await expect(page.locator("#editorial-heading")).toBeVisible();
  });

  test.describe("Headings", () => {
    test("has content class", async ({ page }) => {
      await expect(page.locator("#content-headings")).toHaveClass(/pf-v6-c-content/);
    });

    test("contains all six heading levels", async ({ page }) => {
      const wrapper = page.locator("#content-headings");
      await expect(wrapper.locator("h1")).toHaveText("First level heading");
      await expect(wrapper.locator("h2")).toHaveText("Second level heading");
      await expect(wrapper.locator("h3")).toHaveText("Third level heading");
      await expect(wrapper.locator("h4")).toHaveText("Fourth level heading");
      await expect(wrapper.locator("h5")).toHaveText("Fifth level heading");
      await expect(wrapper.locator("h6")).toHaveText("Sixth level heading");
    });
  });

  test.describe("Body text", () => {
    test("contains a paragraph", async ({ page }) => {
      await expect(page.locator("#content-body p").first()).toContainText(
        "paragraph of body text"
      );
    });

    test("contains small text", async ({ page }) => {
      await expect(page.locator("#content-body small")).toContainText(
        "small text"
      );
    });

    test("contains a blockquote", async ({ page }) => {
      await expect(page.locator("#content-body blockquote")).toContainText(
        "blockquote"
      );
    });

    test("contains preformatted text", async ({ page }) => {
      await expect(page.locator("#content-body pre")).toContainText(
        "preformatted text"
      );
    });
  });

  test.describe("Links", () => {
    test("has a default link", async ({ page }) => {
      await expect(page.locator("#content-links a")).toBeVisible();
    });

    test("visited variant has visited modifier", async ({ page }) => {
      await expect(page.locator("#content-links-visited")).toHaveClass(
        /pf-m-visited/
      );
    });
  });

  test.describe("Unordered list", () => {
    test("contains an unordered list with 3 items", async ({ page }) => {
      const items = page.locator("#content-ul ul > li");
      await expect(items).toHaveCount(3);
    });
  });

  test.describe("Ordered list", () => {
    test("contains an ordered list with 3 items", async ({ page }) => {
      const items = page.locator("#content-ol ol > li");
      await expect(items).toHaveCount(3);
    });
  });

  test.describe("Plain list", () => {
    test("has plain modifier on list", async ({ page }) => {
      await expect(page.locator("#content-plain ul")).toHaveClass(/pf-m-plain/);
    });

    test("contains 3 items", async ({ page }) => {
      const items = page.locator("#content-plain ul > li");
      await expect(items).toHaveCount(3);
    });
  });

  test.describe("Definition list", () => {
    test("contains dt and dd pairs", async ({ page }) => {
      const terms = page.locator("#content-dl dt");
      const defs = page.locator("#content-dl dd");
      await expect(terms).toHaveCount(3);
      await expect(defs).toHaveCount(3);
    });

    test("first term is Name", async ({ page }) => {
      await expect(page.locator("#content-dl dt").first()).toHaveText("Name");
    });
  });

  test.describe("Editorial", () => {
    test("has editorial modifier", async ({ page }) => {
      await expect(page.locator("#content-editorial")).toHaveClass(
        /pf-m-editorial/
      );
    });

    test("contains editorial heading and paragraph", async ({ page }) => {
      await expect(page.locator("#content-editorial h2")).toHaveText(
        "Editorial heading"
      );
      await expect(page.locator("#content-editorial p").first()).toContainText(
        "editorial styling"
      );
    });
  });
});
