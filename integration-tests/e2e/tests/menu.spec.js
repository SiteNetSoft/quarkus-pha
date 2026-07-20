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
  "danger-item",
  "flyout",
  "flyout-positions",
  "scrollable-drilldown",
  "width-modified-drilldown",
  "drilldown-initial-state",
  "drilldown-breadcrumbs",
  "drilldown-inline-filter",
  "header-footer",
  "search-footer",
  "loading",
  "plain",
  "plain-search-footer",
  "plain-scrollable-search-footer",
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
      await expect(page.locator("#mn-separated .pf-v6-c-divider").first()).toBeAttached();
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

  test.describe("Danger item", () => {
    test("the delete item carries pf-m-danger and a screen-reader prefix", async ({ page }) => {
      const item = page.locator("#mn-danger-item .pf-v6-c-menu__list-item.pf-m-danger");
      await expect(item).toBeAttached();
      await expect(item.locator(".pf-v6-screen-reader")).toHaveText("Danger item:");
    });
  });

  test.describe("Flyout", () => {
    test("hovering an expandable item reveals its flyout submenu", async ({ page }) => {
      const menu = page.locator("#mn-flyout");
      await expect(menu.locator(".pf-v6-c-menu[hidden]")).toHaveCount(2);
      await menu.locator(".pf-v6-c-menu__list-item", { hasText: "Add storage" }).first().hover();
      await expect(menu.locator(".pf-v6-c-menu[hidden]")).toHaveCount(1);
      await expect(
        menu.locator(".pf-v6-c-menu .pf-v6-c-menu__item", { hasText: "Application grouping" }).first(),
      ).toBeVisible();
    });

    test("position modifiers land on the nested menus", async ({ page }) => {
      await expect(page.locator("#mn-flyout-positions .pf-v6-c-menu.pf-m-top:not(.pf-m-left)")).toHaveCount(1);
      await expect(page.locator("#mn-flyout-positions .pf-v6-c-menu.pf-m-left:not(.pf-m-top)")).toHaveCount(1);
      await expect(page.locator("#mn-flyout-positions .pf-v6-c-menu.pf-m-left.pf-m-top")).toHaveCount(1);
    });
  });

  test.describe("Drilldown variants", () => {
    test("scrollable and width-modified drilldowns carry their modifiers", async ({ page }) => {
      await expect(page.locator("#mn-drilldown-scrollable")).toHaveClass(/pf-m-scrollable/);
      await expect(page.locator("#mn-drilldown-scrollable")).toHaveClass(/pf-m-drilldown/);
      await expect(page.locator("#mn-drilldown-width")).toHaveAttribute("style", /--pf-v6-c-menu--Width:\s*350px/);
    });

    test("initial-state drilldown starts drilled in and can drill out", async ({ page }) => {
      const menu = page.locator("#mn-drilldown-initial");
      await expect(menu).toHaveClass(/pf-m-drilled-in/);
      await menu.locator(".pf-v6-c-menu button", { hasText: "Start rollout" }).first().click();
      await expect(menu).not.toHaveClass(/pf-m-drilled-in/);
    });

    test("breadcrumbs drilldown updates the breadcrumb as you drill", async ({ page }) => {
      const menu = page.locator("#mn-drilldown-breadcrumbs");
      const breadcrumb = menu.locator(".pf-v6-c-menu__breadcrumb");
      await expect(breadcrumb).toBeHidden();
      await menu.locator(".pf-v6-c-menu__content button", { hasText: "Edit" }).first().click();
      await expect(breadcrumb).toBeVisible();
      await expect(breadcrumb.locator(".pf-m-current:visible")).toHaveText("Edit");
      await menu.locator(".pf-v6-c-menu button", { hasText: "Deployment" }).first().click();
      await expect(breadcrumb.locator(".pf-m-current:visible")).toHaveText("Deployment");
      await breadcrumb.locator("button", { hasText: "All" }).click();
      await expect(menu).not.toHaveClass(/pf-m-drilled-in/);
      await expect(breadcrumb).toBeHidden();
    });

    test("inline filter narrows the drilled submenu", async ({ page }) => {
      const menu = page.locator("#mn-drilldown-filter");
      await menu.locator("button", { hasText: "Start rollout" }).first().click();
      await expect(menu).toHaveClass(/pf-m-drilled-in/);
      await menu.locator("input").fill("count");
      await expect(menu.locator(".pf-v6-c-menu__item", { hasText: "Annotations" })).toBeHidden();
      await expect(menu.locator(".pf-v6-c-menu .pf-v6-c-menu__item", { hasText: "Count" }).first()).toBeVisible();
    });
  });

  test.describe("Header / search / footer regions", () => {
    test("header-footer frames a scrollable list", async ({ page }) => {
      const menu = page.locator("#mn-header-footer");
      await expect(menu).toHaveClass(/pf-m-scrollable/);
      await expect(menu.locator(".pf-v6-c-menu__header")).toHaveText("Header");
      await expect(menu.locator(".pf-v6-c-menu__footer .pf-v6-c-button")).toBeVisible();
    });

    test("search-footer search input filters the list", async ({ page }) => {
      const menu = page.locator("#mn-search-footer");
      await menu.locator(".pf-v6-c-menu__search-input input").fill("Action 12");
      await expect(menu.locator(".pf-v6-c-menu__list-item:visible")).toHaveCount(1);
      await expect(menu.locator(".pf-v6-c-menu__footer")).toBeAttached();
    });

    test("plain flavors carry pf-m-plain", async ({ page }) => {
      await expect(page.locator("#mn-plain")).toHaveClass(/pf-m-plain/);
      await expect(page.locator("#mn-plain-search-footer")).toHaveClass(/pf-m-plain/);
      const both = page.locator("#mn-plain-scrollable-search-footer");
      await expect(both).toHaveClass(/pf-m-plain/);
      await expect(both).toHaveClass(/pf-m-scrollable/);
    });
  });

  test.describe("Loading", () => {
    test("the loading item shows a spinner", async ({ page }) => {
      await expect(page.locator("#mn-loading .pf-m-loading .pf-v6-c-spinner")).toBeVisible();
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

  test.describe("Java source tab", () => {
    // Drilldowns, flyouts and favorites stay hand-written (live composition state).
    const HAND_WRITTEN = [
      "favorites",
      "with-drilldown",
      "scrollable-drilldown",
      "width-modified-drilldown",
      "drilldown-initial-state",
      "drilldown-breadcrumbs",
      "drilldown-inline-filter",
      "flyout",
      "flyout-positions",
    ];

    test("model-driven cards get a leading Java tab; hand-written ones do not", async ({ page }) => {
      await page.goto("/components/menu");
      for (const ex of ["basic", "titled-groups", "option-single-select", "search-footer"]) {
        const card = page.locator(`[data-rendered-href="/components/menu/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of HAND_WRITTEN) {
        const card = page.locator(`[data-rendered-href="/components/menu/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("Java tab opens Monaco with the builder code", async ({ page }) => {
      await page.goto("/components/menu");
      const card = page.locator('[data-rendered-href="/components/menu/basic"]');
      await card.locator('button[aria-label*="Toggle Java"]').click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
      await expect(card.locator(".monaco-editor .view-lines")).toContainText("Menu.builder()", {
        timeout: 10000,
      });
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/menu/source-java/option-multi-select");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".selectMulti()");
    });
  });
});
