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
      await expect(page.locator("#jl-with-label-center")).toHaveClass(/pf-m-center/);
      await expect(page.locator("#jl-with-label-center .pf-v6-c-jump-links__label")).toBeVisible();
      const exp = page.locator("#jl-expandable");
      await expect(exp).toHaveClass(/pf-m-expanded/);
      await exp.locator(".pf-v6-c-jump-links__toggle").click();
      await expect(exp).not.toHaveClass(/pf-m-expanded/);
      await expect(exp.locator(".pf-v6-c-jump-links__main")).toBeHidden();
    });

    test("subsection nests a second list", async ({ page }) => {
      await expect(page.locator("#jl-expandable .pf-v6-c-jump-links__list .pf-v6-c-jump-links__list li")).toHaveCount(
        2,
      );
    });

    test("inactive and active subsection variants place pf-m-current correctly", async ({ page }) => {
      const inactive = page.locator("#jl-subsections-inactive");
      await expect(inactive.locator("> .pf-v6-c-jump-links__list > .pf-m-current")).toHaveCount(1);
      await expect(inactive.locator(".pf-v6-c-jump-links__list .pf-v6-c-jump-links__list .pf-m-current")).toHaveCount(
        0,
      );
      const active = page.locator("#jl-subsections-active");
      await expect(active.locator(".pf-v6-c-jump-links__list .pf-v6-c-jump-links__list .pf-m-current")).toHaveCount(1);
      await expect(active.locator(".pf-m-current").first()).toHaveAttribute("aria-current", "location");
    });

    test("responsive expandable variants carry breakpoint modifiers and toggle", async ({ page }) => {
      const resp = page.locator("#jl-expandable-responsive");
      await expect(resp).toHaveClass(/pf-m-non-expandable-on-md/);
      await expect(resp).toHaveClass(/pf-m-expandable-on-lg/);
      await expect(resp).toHaveClass(/pf-m-non-expandable-on-xl/);
      await expect(resp.locator(".pf-v6-c-jump-links__label")).toHaveText("Jump to section");
      // The default 1280px viewport sits in the non-expandable-on-xl range (toggle
      // hidden by CSS); drop into the expandable lg range to exercise the toggle.
      await page.setViewportSize({ width: 1100, height: 800 });
      await expect(resp).not.toHaveClass(/pf-m-expanded/);
      await resp.locator(".pf-v6-c-jump-links__toggle button").click();
      await expect(resp).toHaveClass(/pf-m-expanded/);
      const noLabel = page.locator("#jl-expandable-responsive-no-label");
      await expect(noLabel.locator(".pf-v6-c-jump-links__label")).toHaveCount(0);
      await expect(noLabel.locator(".pf-v6-c-jump-links__toggle button")).toBeVisible();
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

  test.describe("Standalone routes", () => {
    for (const example of [
      "horizontal",
      "vertical",
      "centered",
      "subsections-inactive",
      "subsections-active",
      "expandable-responsive",
      "expandable-responsive-no-label",
    ]) {
      test(`/components/jump-links/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/jump-links/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-jump-links").first()).toBeAttached();
      });
    }
  });
});
