import { test, expect } from "@playwright/test";

/** Scroll the page past the back-to-top threshold and wait for Alpine to react. */
async function scrollPastThreshold(page) {
  await page.evaluate(() => {
    window.scrollTo(0, 600);
    window.dispatchEvent(new Event("scroll"));
  });
  await page.waitForFunction(() => window.scrollY > 400);
}

test.describe("Back to top", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/back-to-top");
  });

  test("page loads with example and documentation TOC anchors", async ({ page }) => {
    for (const id of ["examples", "basic", "documentation", "props-back-to-top", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("back-to-top is hidden at top of page", async ({ page }) => {
    await expect(page.locator("#back-to-top-basic")).toBeHidden();
  });

  test("appears after scrolling past 400px", async ({ page }) => {
    await scrollPastThreshold(page);
    await expect(page.locator("#back-to-top-basic")).toBeVisible();
    await expect(page.locator("#back-to-top-basic")).toHaveClass(/pf-v6-c-back-to-top/);
  });

  test("click scrolls back to top", async ({ page }) => {
    await scrollPastThreshold(page);
    await expect(page.locator("#back-to-top-basic")).toBeVisible();
    await page.locator("#back-to-top-basic button").click();
    await page.waitForFunction(() => window.scrollY < 50);
    expect(await page.evaluate(() => window.scrollY)).toBeLessThan(50);
  });

  test("button renders title + icon", async ({ page }) => {
    await scrollPastThreshold(page);
    const btn = page.locator("#back-to-top-basic button");
    await expect(btn.locator(".pf-v6-c-button__text")).toHaveText("Back to top");
    await expect(btn.locator(".pf-v6-c-button__icon svg")).toBeVisible();
  });

  test("standalone example route renders", async ({ page }) => {
    const res = await page.goto("/components/back-to-top/basic");
    expect(res.status()).toBe(200);
    await expect(page.locator("#back-to-top-basic")).toBeAttached();
  });
});
