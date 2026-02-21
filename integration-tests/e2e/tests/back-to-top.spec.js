import { test, expect } from "@playwright/test";

/** Scroll the page and wait for Alpine.js to process the scroll event. */
async function scrollDown(page) {
  await page.evaluate(() => {
    window.scrollTo(0, 500);
    window.dispatchEvent(new Event("scroll"));
  });
  await page.waitForFunction(() => window.scrollY > 400);
}

test.describe("Back to top", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/back-to-top");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#always-visible-heading")).toBeVisible();
    await expect(page.locator("#scroll-content-heading")).toBeVisible();
  });

  test("hidden at top of page", async ({ page }) => {
    await expect(page.locator("#back-to-top-basic")).toBeHidden();
  });

  test("visible after scrolling", async ({ page }) => {
    await scrollDown(page);
    await expect(page.locator("#back-to-top-basic")).toBeVisible();
  });

  test("click scrolls to top", async ({ page }) => {
    await scrollDown(page);
    await expect(page.locator("#back-to-top-basic")).toBeVisible();

    await page.locator("#back-to-top-basic button").click();
    await page.waitForFunction(() => window.scrollY < 50);

    const scrollY = await page.evaluate(() => window.scrollY);
    expect(scrollY).toBeLessThan(50);
  });

  test("always visible variant is visible without scrolling", async ({ page }) => {
    await expect(page.locator("#back-to-top-visible")).toBeVisible();
  });

  test("correct CSS classes", async ({ page }) => {
    await expect(page.locator("#back-to-top-basic")).toHaveClass(/pf-v6-c-back-to-top/);
    await expect(page.locator("#back-to-top-basic button")).toHaveClass(/pf-v6-c-button/);
    await expect(page.locator("#back-to-top-basic button")).toHaveClass(/pf-m-primary/);
  });

  test("button has icon", async ({ page }) => {
    await scrollDown(page);
    await expect(page.locator("#back-to-top-basic")).toBeVisible();

    const icon = page.locator("#back-to-top-basic .pf-v6-c-button__icon");
    await expect(icon).toBeVisible();
    await expect(icon.locator("svg")).toBeVisible();
  });
});
