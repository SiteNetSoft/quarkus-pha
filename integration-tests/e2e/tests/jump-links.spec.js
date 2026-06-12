import { test, expect } from "@playwright/test";

test.describe("Jump Links", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/jump-links");
  });

  test("page loads with all example section headings", async ({ page }) => {
    await expect(page.locator("#vertical")).toBeVisible();
    await expect(page.locator("#horizontal")).toBeVisible();
  });

  test.describe("Vertical", () => {
    test("has pf-v6-c-jump-links and pf-m-vertical classes", async ({ page }) => {
      await expect(page.locator("#jl-vertical")).toHaveClass(/pf-v6-c-jump-links/);
      await expect(page.locator("#jl-vertical")).toHaveClass(/pf-m-vertical/);
    });

    test("has pf-v6-c-jump-links__item elements", async ({ page }) => {
      await expect(page.locator("#jl-vertical .pf-v6-c-jump-links__item").first()).toBeVisible();
    });

    test("one item has pf-m-current", async ({ page }) => {
      await expect(page.locator("#jl-vertical .pf-v6-c-jump-links__item.pf-m-current")).toBeVisible();
    });
  });

  test.describe("Horizontal", () => {
    test("does not have class pf-m-vertical", async ({ page }) => {
      await expect(page.locator("#jl-horizontal")).not.toHaveClass(/pf-m-vertical/);
    });
  });

  test.describe("Parity additions", () => {
    test("labels render and the expandable rail collapses", async ({ page }) => {
      await expect(page.locator("#jl-with-label .pf-v6-c-jump-links__label")).toHaveText("Jump to section");
      const exp = page.locator("#jl-expandable");
      await expect(exp).toHaveClass(/pf-m-expanded/);
      await exp.locator(".pf-v6-c-jump-links__toggle").click();
      await expect(exp).not.toHaveClass(/pf-m-expanded/);
      await expect(exp.locator(".pf-v6-c-jump-links__main")).toBeHidden();
    });

    test("subsection nests a second list", async ({ page }) => {
      await expect(page.locator("#jl-expandable .pf-v6-c-jump-links__list .pf-v6-c-jump-links__list li")).toHaveCount(2);
    });

    test("/components/jump-links/with-label returns 200", async ({ page }) => {
      const res = await page.goto("/components/jump-links/with-label");
      expect(res.status()).toBe(200);
    });

    test("/components/jump-links/vertical-with-label returns 200", async ({ page }) => {
      const res = await page.goto("/components/jump-links/vertical-with-label");
      expect(res.status()).toBe(200);
    });

    test("/components/jump-links/expandable-vertical-subsection returns 200", async ({ page }) => {
      const res = await page.goto("/components/jump-links/expandable-vertical-subsection");
      expect(res.status()).toBe(200);
    });
  });
});
