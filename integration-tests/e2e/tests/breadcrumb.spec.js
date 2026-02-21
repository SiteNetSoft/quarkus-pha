import { test, expect } from "@playwright/test";

test.describe("Breadcrumb", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/breadcrumb");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#no-home-heading")).toBeVisible();
    await expect(page.locator("#heading-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("renders as a nav element with breadcrumb class and aria-label", async ({ page }) => {
      const nav = page.locator("#breadcrumb-basic");
      await expect(nav).toBeVisible();
      await expect(nav).toHaveClass(/pf-v6-c-breadcrumb/);
      await expect(nav).toHaveAttribute("aria-label", "Breadcrumb");
    });

    test("contains 4 breadcrumb items with dividers", async ({ page }) => {
      const items = page.locator("#breadcrumb-basic .pf-v6-c-breadcrumb__item");
      await expect(items).toHaveCount(4);

      const dividers = page.locator("#breadcrumb-basic .pf-v6-c-breadcrumb__item-divider");
      await expect(dividers).toHaveCount(4);
    });

    test("last item has pf-m-current class and aria-current", async ({ page }) => {
      const currentLink = page.locator("#breadcrumb-basic .pf-v6-c-breadcrumb__link.pf-m-current");
      await expect(currentLink).toHaveCount(1);
      await expect(currentLink).toHaveAttribute("aria-current", "page");
      await expect(currentLink).toHaveText("Section landing");
    });
  });

  test.describe("Without home link", () => {
    test("first item has no link element", async ({ page }) => {
      const firstItem = page.locator("#breadcrumb-no-home .pf-v6-c-breadcrumb__item").first();
      const link = firstItem.locator(".pf-v6-c-breadcrumb__link");
      await expect(link).toHaveCount(0);
    });

    test("last item has pf-m-current class", async ({ page }) => {
      const currentLink = page.locator("#breadcrumb-no-home .pf-v6-c-breadcrumb__link.pf-m-current");
      await expect(currentLink).toHaveCount(1);
    });
  });

  test.describe("With heading", () => {
    test("last item contains an h1 heading element", async ({ page }) => {
      const heading = page.locator("#breadcrumb-heading .pf-v6-c-breadcrumb__heading");
      await expect(heading).toHaveCount(1);
    });

    test("heading wraps the current link", async ({ page }) => {
      const headingLink = page.locator("#breadcrumb-heading .pf-v6-c-breadcrumb__heading .pf-v6-c-breadcrumb__link.pf-m-current");
      await expect(headingLink).toHaveCount(1);
      await expect(headingLink).toHaveAttribute("aria-current", "page");
    });
  });
});
