import { test, expect } from "@playwright/test";

test.describe("Title", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/title");
  });

  test("page loads with all 2 section headings", async ({ page }) => {
    await expect(page.locator("#sizes-heading")).toBeVisible();
    await expect(page.locator("#heading-levels-heading")).toBeVisible();
  });

  test("has titles at all size modifiers", async ({ page }) => {
    await expect(page.locator("#title-4xl")).toHaveClass(/pf-m-4xl/);
    await expect(page.locator("#title-3xl")).toHaveClass(/pf-m-3xl/);
    await expect(page.locator("#title-2xl")).toHaveClass(/pf-m-2xl/);
    await expect(page.locator("#title-xl")).toHaveClass(/pf-m-xl/);
    await expect(page.locator("#title-lg")).toHaveClass(/pf-m-lg/);
    await expect(page.locator("#title-md")).toHaveClass(/pf-m-md/);
  });

  test("titles have pf-v6-c-title class", async ({ page }) => {
    await expect(page.locator("#title-4xl")).toHaveClass(/pf-v6-c-title/);
  });

  test("heading levels render correct elements", async ({ page }) => {
    // h1 is default
    const h1 = page.locator("h1#title-h1");
    await expect(h1).toBeVisible();
    const h2 = page.locator("h2#title-h2");
    await expect(h2).toBeVisible();
    const h3 = page.locator("h3#title-h3");
    await expect(h3).toBeVisible();
  });
});
