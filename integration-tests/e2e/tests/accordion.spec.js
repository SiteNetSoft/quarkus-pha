import { test, expect } from "@playwright/test";

test.describe("Accordion", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/accordion");
  });

  test("page loads with all 6 variant sections", async ({ page }) => {
    await expect(page.locator("#fluid-heading")).toBeVisible();
    await expect(page.locator("#fixed-heading")).toBeVisible();
    await expect(page.locator("#definition-list-heading")).toBeVisible();
    await expect(page.locator("#bordered-heading")).toBeVisible();
    await expect(page.locator("#large-bordered-heading")).toBeVisible();
    await expect(page.locator("#toggle-start-heading")).toBeVisible();
  });

  test.describe("Fluid variant", () => {
    test("click toggle expands and collapses content", async ({ page }) => {
      const accordion = page.locator("#accordion-fluid");
      const firstToggle = accordion.locator(".pf-v6-c-accordion__toggle").first();
      const firstItem = accordion.locator(".pf-v6-c-accordion__item").first();
      const firstContent = accordion.locator(".pf-v6-c-accordion__expandable-content").first();

      // Initially collapsed
      await expect(firstContent).toBeHidden();
      await expect(firstToggle).toHaveAttribute("aria-expanded", "false");

      // Click to expand
      await firstToggle.click();
      await expect(firstContent).toBeVisible();
      await expect(firstToggle).toHaveAttribute("aria-expanded", "true");
      await expect(firstItem).toHaveClass(/pf-m-expanded/);
      await expect(firstContent).toHaveClass(/pf-m-expanded/);

      // Click again to collapse
      await firstToggle.click();
      await expect(firstContent).toBeHidden();
      await expect(firstToggle).toHaveAttribute("aria-expanded", "false");
    });

    test("only clicked item expands, others stay collapsed", async ({ page }) => {
      const accordion = page.locator("#accordion-fluid");
      const toggles = accordion.locator(".pf-v6-c-accordion__toggle");
      const contents = accordion.locator(".pf-v6-c-accordion__expandable-content");

      // Expand first item
      await toggles.nth(0).click();
      await expect(contents.nth(0)).toBeVisible();
      await expect(contents.nth(1)).toBeHidden();
      await expect(contents.nth(2)).toBeHidden();

      // Expand second item — first stays open (independent)
      await toggles.nth(1).click();
      await expect(contents.nth(0)).toBeVisible();
      await expect(contents.nth(1)).toBeVisible();
      await expect(contents.nth(2)).toBeHidden();
    });

    test("toggle icon SVG is present on each item", async ({ page }) => {
      const accordion = page.locator("#accordion-fluid");
      const icons = accordion.locator(".pf-v6-c-accordion__toggle-icon svg");
      await expect(icons).toHaveCount(4);
    });
  });

  test.describe("Fixed variant", () => {
    test("expanded content has pf-m-fixed class", async ({ page }) => {
      const accordion = page.locator("#accordion-fixed");
      const contents = accordion.locator(".pf-v6-c-accordion__expandable-content");

      // All expandable content elements should have pf-m-fixed
      const count = await contents.count();
      for (let i = 0; i < count; i++) {
        await expect(contents.nth(i)).toHaveClass(/pf-m-fixed/);
      }
    });
  });

  test.describe("Definition list variant", () => {
    test("root element is a dl", async ({ page }) => {
      const accordion = page.locator("#accordion-dl");
      await expect(accordion).toBeVisible();
      const tagName = await accordion.evaluate((el) => el.tagName.toLowerCase());
      expect(tagName).toBe("dl");
    });

    test("toggle wrapper is dt and content wrapper is dd", async ({ page }) => {
      const accordion = page.locator("#accordion-dl");
      const dts = accordion.locator("dt");
      const dds = accordion.locator("dd");
      await expect(dts).toHaveCount(4);
      await expect(dds).toHaveCount(4);

      // dt contains the toggle button
      const firstToggle = dts.first().locator(".pf-v6-c-accordion__toggle");
      await expect(firstToggle).toBeVisible();

      // dd has the expandable content class
      await expect(dds.first()).toHaveClass(/pf-v6-c-accordion__expandable-content/);
    });

    test("expand and collapse works", async ({ page }) => {
      const accordion = page.locator("#accordion-dl");
      const firstToggle = accordion.locator(".pf-v6-c-accordion__toggle").first();
      const firstContent = accordion.locator("dd.pf-v6-c-accordion__expandable-content").first();

      await expect(firstContent).toBeHidden();
      await firstToggle.click();
      await expect(firstContent).toBeVisible();
      await expect(firstContent).toHaveClass(/pf-m-expanded/);
    });
  });

  test.describe("Bordered variant", () => {
    test("root has pf-m-bordered class", async ({ page }) => {
      const accordion = page.locator("#accordion-bordered");
      await expect(accordion).toHaveClass(/pf-m-bordered/);
    });
  });

  test.describe("Large bordered variant", () => {
    test("root has both pf-m-display-lg and pf-m-bordered", async ({ page }) => {
      const accordion = page.locator("#accordion-large-bordered");
      await expect(accordion).toHaveClass(/pf-m-bordered/);
      await expect(accordion).toHaveClass(/pf-m-display-lg/);
    });
  });

  test.describe("Toggle icon at start variant", () => {
    test("root has pf-m-toggle-start class", async ({ page }) => {
      const accordion = page.locator("#accordion-toggle-start");
      await expect(accordion).toHaveClass(/pf-m-toggle-start/);
    });

    test("icon span precedes text span in DOM order", async ({ page }) => {
      const accordion = page.locator("#accordion-toggle-start");
      const firstToggle = accordion.locator(".pf-v6-c-accordion__toggle").first();

      // Get the order of children — icon should come before text
      const order = await firstToggle.evaluate((el) => {
        const children = Array.from(el.children);
        const iconIndex = children.findIndex((c) =>
          c.classList.contains("pf-v6-c-accordion__toggle-icon")
        );
        const textIndex = children.findIndex((c) =>
          c.classList.contains("pf-v6-c-accordion__toggle-text")
        );
        return { iconIndex, textIndex };
      });
      expect(order.iconIndex).toBeLessThan(order.textIndex);
    });
  });
});
