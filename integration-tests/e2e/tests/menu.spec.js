import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "with-icons",
  "with-actions",
  "with-links",
  "with-descriptions",
  "item-checkbox",
  "footer",
  "separated-items",
  "titled-groups",
  "favorites",
  "filtering-search",
  "option-single-select",
  "option-multi-select",
  "scrollable",
  "scrollable-custom-height",
  "view-more",
  "with-drilldown",
];

test.describe("Menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/menu");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-menu class with items, divider, danger item", async ({ page }) => {
      await expect(page.locator("#mn-basic")).toHaveClass(/pf-v6-c-menu/);
      await expect(page.locator("#mn-basic .pf-v6-c-divider")).toBeAttached();
      await expect(page.locator("#mn-basic .pf-v6-c-menu__list-item.pf-m-danger")).toBeAttached();
    });
  });

  test.describe("With icons / actions / links", () => {
    test("icon items carry item icons", async ({ page }) => {
      await expect(page.locator("#mn-icons .pf-v6-c-menu__item-icon")).toHaveCount(3);
    });

    test("action star toggles favorited styling", async ({ page }) => {
      const star = page.locator("#mn-actions .pf-v6-c-menu__item-action.pf-m-favorite").first();
      await expect(star).not.toHaveClass(/pf-m-favorited/);
      await star.click();
      await expect(star).toHaveClass(/pf-m-favorited/);
    });

    test("link items render anchors with external icons", async ({ page }) => {
      await expect(page.locator("#mn-links a.pf-v6-c-menu__item")).toHaveCount(3);
      await expect(page.locator("#mn-links .pf-v6-c-menu__item-external-icon")).toHaveCount(2);
    });
  });

  test.describe("Item checkbox", () => {
    test("checkboxes toggle within menu items", async ({ page }) => {
      const first = page.locator("#mn-check-1-input");
      await expect(first).not.toBeChecked();
      await first.check();
      await expect(first).toBeChecked();
      await expect(page.locator("#mn-check-2-input")).toBeChecked();
    });
  });

  test.describe("Structure variants", () => {
    test("footer renders below the content", async ({ page }) => {
      await expect(page.locator("#mn-footer .pf-v6-c-menu__footer button")).toBeVisible();
    });

    test("titled groups have group titles", async ({ page }) => {
      await expect(page.locator("#mn-titled-groups .pf-v6-c-menu__group-title")).toHaveCount(2);
    });

    test("separated items include a divider", async ({ page }) => {
      await expect(page.locator("#mn-separated .pf-v6-c-divider")).toBeAttached();
    });
  });

  test.describe("Favorites", () => {
    test("starring an item adds it to the favorites group", async ({ page }) => {
      const favGroup = page.locator("#mn-favorites .pf-v6-c-menu__group").first();
      await expect(favGroup.locator(".pf-v6-c-menu__list-item")).toHaveCount(1);
      const allGroup = page.locator("#mn-favorites .pf-v6-c-menu__group").nth(1);
      await allGroup.locator(".pf-v6-c-menu__item-action").nth(1).click();
      await expect(favGroup.locator(".pf-v6-c-menu__list-item")).toHaveCount(2);
    });
  });

  test.describe("Filtering with search input", () => {
    test("typing filters the items", async ({ page }) => {
      const items = page.locator("#mn-filtering-search .pf-v6-c-menu__list-item:visible");
      await expect(items).toHaveCount(3);
      await page.locator("#mn-filtering-search input").fill("Action 2");
      await expect(items).toHaveCount(1);
    });
  });

  test.describe("Option select", () => {
    test("single select moves the check icon", async ({ page }) => {
      const menu = page.locator("#mn-option-single-select");
      await expect(menu.locator(".pf-v6-c-menu__item.pf-m-selected")).toHaveCount(1);
      await menu.locator(".pf-v6-c-menu__item").nth(2).click();
      await expect(menu.locator(".pf-v6-c-menu__item").nth(2)).toHaveClass(/pf-m-selected/);
      await expect(menu.locator(".pf-v6-c-menu__item.pf-m-selected")).toHaveCount(1);
    });

    test("multi select accumulates selections", async ({ page }) => {
      const menu = page.locator("#mn-option-multi-select");
      await menu.locator(".pf-v6-c-menu__item").nth(1).click();
      await expect(menu.locator(".pf-v6-c-menu__item.pf-m-selected")).toHaveCount(2);
    });
  });

  test.describe("Scrollable", () => {
    test("scrollable menus carry the modifier and custom height variant sets the var", async ({ page }) => {
      await expect(page.locator("#mn-scrollable")).toHaveClass(/pf-m-scrollable/);
      await expect(page.locator("#mn-scrollable-custom-height .pf-v6-c-menu__content")).toHaveAttribute(
        "style",
        /--pf-v6-c-menu__content--MaxHeight:\s*200px/,
      );
    });
  });

  test.describe("View more", () => {
    test("clicking view more appends items", async ({ page }) => {
      const items = page.locator("#mn-view-more .pf-v6-c-menu__list-item:visible");
      await expect(items).toHaveCount(4);
      await page.locator("#mn-view-more .pf-m-load button").click();
      await expect(items).toHaveCount(6);
    });
  });

  test.describe("Drilldown", () => {
    test("drilling in and out toggles the classes", async ({ page }) => {
      const menu = page.locator("#mn-drilldown");
      await expect(menu).toHaveClass(/pf-m-drilldown/);
      await expect(menu).not.toHaveClass(/pf-m-drilled-in/);
      await menu.locator("button", { hasText: "Start rollout" }).first().click();
      await expect(menu).toHaveClass(/pf-m-drilled-in/);
      await expect(menu.locator(".pf-v6-c-menu__list-item.pf-m-current-path")).toHaveCount(1);
      const nested = menu.locator(".pf-v6-c-menu .pf-v6-c-menu__item", { hasText: "Application grouping" });
      await expect(nested).toBeVisible();
      await menu.locator(".pf-v6-c-menu button", { hasText: "Start rollout" }).first().click();
      await expect(menu).not.toHaveClass(/pf-m-drilled-in/);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/menu/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/menu/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-menu").first()).toBeAttached();
      });
    }
  });
});
