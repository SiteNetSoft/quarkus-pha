import { test, expect } from "@playwright/test";

test.describe("Custom menus", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/custom-menus");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Custom menus");
  });

  test("Examples and Documentation section headings are visible", async ({ page }) => {
    await expect(page.locator("h2#examples")).toBeVisible();
    await expect(page.locator("h2#documentation")).toBeVisible();
  });

  test("With actions example-card section id is visible", async ({ page }) => {
    await expect(page.locator("h3#with-actions")).toBeVisible();
    await expect(page.locator("h3#with-actions")).toHaveText("With actions");
  });

  test("Documentation sub-section ids are visible", async ({ page }) => {
    await expect(page.locator("h3#props-custom-menus")).toBeVisible();
    await expect(page.locator("h3#usage")).toBeVisible();
  });

  test.describe("With actions", () => {
    test("toggle text flips between Collapsed and Expanded", async ({ page }) => {
      const toggle = page.locator("#cm-actions-toggle");
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Collapsed");
      await toggle.click();
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Expanded");
    });

    test("menu shows the Actions group with icon actions and a disabled item", async ({ page }) => {
      await page.locator("#cm-actions-toggle").click();
      const wrapper = page.locator("#cm-actions-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu__group-title")).toHaveText("Actions");
      await expect(wrapper.locator(".pf-v6-c-menu__item-action")).toHaveCount(4);
      await expect(wrapper.locator(".pf-v6-c-menu__item", { hasText: "Item 2" })).toBeDisabled();
    });

    test("selecting checks the item and keeps the menu open; reselecting unchecks", async ({ page }) => {
      await page.locator("#cm-actions-toggle").click();
      const wrapper = page.locator("#cm-actions-toggle").locator("..");
      const item1 = wrapper.locator(".pf-v6-c-menu__item", { hasText: "Item 1" });
      await item1.click();
      await expect(wrapper.locator(".pf-v6-c-menu")).toBeVisible();
      await expect(item1).toHaveAttribute("aria-selected", "true");
      await expect(item1.locator(".pf-v6-c-menu__item-select-icon")).toBeVisible();
      const item3 = wrapper.locator(".pf-v6-c-menu__item", { hasText: "Item 3" });
      await item3.click();
      await expect(item3).toHaveAttribute("aria-selected", "true");
      await item1.click();
      await expect(item1).toHaveAttribute("aria-selected", "false");
    });
  });

  test.describe("With favorites", () => {
    test("no Favorites group until an item is starred", async ({ page }) => {
      await page.locator("#cm-favorites-toggle").click();
      const wrapper = page.locator("#cm-favorites-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu__group-title", { hasText: "Favorites" })).not.toBeVisible();
      await expect(wrapper.locator(".pf-v6-c-menu__group-title", { hasText: "Group 1" })).toBeVisible();
      await expect(wrapper.locator(".pf-v6-c-menu__group-title", { hasText: "Group 2" })).toBeVisible();
    });

    test("starring pins a clone into Favorites; unstarring removes it", async ({ page }) => {
      await page.locator("#cm-favorites-toggle").click();
      const wrapper = page.locator("#cm-favorites-toggle").locator("..");
      await wrapper.locator('button[aria-label="Favorite Item 2"]').click();
      await expect(wrapper.locator(".pf-v6-c-menu__group-title", { hasText: "Favorites" })).toBeVisible();
      await expect(wrapper.locator('button[aria-label="Unfavorite Item 2"]')).toHaveCount(2);
      await wrapper.locator('button[aria-label="Unfavorite Item 2"]').first().click();
      await expect(wrapper.locator(".pf-v6-c-menu__group-title", { hasText: "Favorites" })).not.toBeVisible();
    });
  });

  test.describe("With drilldown", () => {
    test("drilling in shows the submenu and the breadcrumb drills back out", async ({ page }) => {
      await page.locator("#cm-drilldown-toggle").click();
      const wrapper = page.locator("#cm-drilldown-toggle").locator("..");
      const menu = wrapper.locator(".pf-v6-c-menu.pf-m-drilldown");
      await expect(menu).toBeVisible();
      await menu.locator(".pf-v6-c-menu__item", { hasText: "Start rollout" }).first().click();
      await expect(menu).toHaveClass(/pf-m-drilled-in/);
      const sub = menu.locator("li.pf-m-current-path > .pf-v6-c-menu").first();
      await expect(sub.locator(".pf-v6-c-menu__item", { hasText: "Application Grouping" }).first()).toBeVisible();
      // the first list item of the drilled-in submenu is the breadcrumb that drills back out
      await sub.locator("li").first().locator(".pf-v6-c-menu__item").click();
      await expect(menu).not.toHaveClass(/pf-m-drilled-in/);
    });

    test("drills three levels deep", async ({ page }) => {
      await page.locator("#cm-drilldown-toggle").click();
      const wrapper = page.locator("#cm-drilldown-toggle").locator("..");
      const menu = wrapper.locator(".pf-v6-c-menu.pf-m-drilldown");
      await menu.locator(".pf-v6-c-menu__item", { hasText: "Start rollout" }).first().click();
      const subs = menu.locator("li.pf-m-current-path > .pf-v6-c-menu");
      await subs.first().locator(".pf-v6-c-menu__item", { hasText: "Application Grouping" }).first().click();
      await expect(subs.first()).toHaveClass(/pf-m-drilled-in/);
      await expect(subs.nth(1).locator(".pf-v6-c-menu__item", { hasText: "Group A" })).toBeVisible();
    });

    test("reopening the toggle resets the drill state", async ({ page }) => {
      const toggle = page.locator("#cm-drilldown-toggle");
      await toggle.click();
      const wrapper = toggle.locator("..");
      const menu = wrapper.locator(".pf-v6-c-menu.pf-m-drilldown");
      await menu.locator(".pf-v6-c-menu__item", { hasText: "Start rollout" }).first().click();
      await toggle.click();
      await toggle.click();
      await expect(menu).not.toHaveClass(/pf-m-drilled-in/);
    });
  });

  test.describe("With inline search filter", () => {
    test("typing filters the list live", async ({ page }) => {
      await page.locator("#cm-inline-search-toggle").click();
      const wrapper = page.locator("#cm-inline-search-toggle").locator("..");
      const items = wrapper.locator(".pf-v6-c-menu__list-item");
      // 13 data items + the always-attached (hidden) "No results found" item
      await expect(items).toHaveCount(14);
      await wrapper.locator(".pf-v6-c-menu__search input").fill("azure");
      await expect(items.filter({ hasText: "Azure" })).toHaveCount(2);
      await expect(items.filter({ hasText: "AWS" })).toHaveCount(0);
    });

    test("no match shows the disabled No results found item; reset clears it", async ({ page }) => {
      await page.locator("#cm-inline-search-toggle").click();
      const wrapper = page.locator("#cm-inline-search-toggle").locator("..");
      await wrapper.locator(".pf-v6-c-menu__search input").fill("does-not-exist");
      const noResults = wrapper.locator(".pf-v6-c-menu__item", { hasText: "No results found" });
      await expect(noResults).toBeVisible();
      await expect(noResults).toBeDisabled();
      await wrapper.locator('button[aria-label="Reset"]').click();
      await expect(noResults).toBeHidden();
      await expect(wrapper.locator(".pf-v6-c-menu__list-item", { hasText: "Action 1" })).toBeVisible();
    });
  });

  test.describe("Flyout", () => {
    test("hovering the flyout item reveals its submenu", async ({ page }) => {
      await page.locator("#cm-flyout-toggle").click();
      const flyout = page.locator("#cm-flyout-toggle").locator("..").locator(".pf-m-flyout");
      const submenu = flyout.locator(".pf-v6-c-menu");
      await expect(submenu).toBeHidden();
      await flyout.hover();
      await expect(submenu).toBeVisible();
      await expect(submenu).toContainText("Email");
    });
  });

  test.describe("Tree view menu", () => {
    test("toggle opens a raised panel with the Status tree and flips its label", async ({ page }) => {
      const toggle = page.locator("#cm-tree-toggle");
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Collapsed");
      const panel = toggle.locator("..").locator(".pf-v6-c-panel");
      await expect(panel).toBeHidden();
      await toggle.click();
      await expect(panel).toBeVisible();
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Expanded");
      await expect(panel.locator(".pf-v6-c-title")).toHaveText("Status");
      await expect(panel.locator(".pf-v6-c-tree-view__list-item")).toHaveCount(5);
      await expect(panel.locator(".pf-v6-c-tree-view__node-count .pf-v6-c-badge")).toHaveCount(5);
    });

    test("expanding a parent reveals children; parent checkbox cascades", async ({ page }) => {
      await page.locator("#cm-tree-toggle").click();
      const panel = page.locator("#cm-tree-toggle").locator("..").locator(".pf-v6-c-panel");
      const ready = panel.locator(".pf-v6-c-tree-view__list > .pf-v6-c-tree-view__list-item").first();
      await ready.locator(".pf-v6-c-tree-view__node-toggle").click();
      await expect(ready).toHaveClass(/pf-m-expanded/);
      await expect(ready.locator('[role="group"] .pf-v6-c-tree-view__list-item')).toHaveCount(2);
      await ready.locator("#cm-tree-check-ready").check();
      await expect(ready.locator("#cm-tree-check-server")).toBeChecked();
      await expect(ready.locator("#cm-tree-check-worker")).toBeChecked();
    });
  });

  test.describe("Date select", () => {
    test("toggle opens a panel hosting the calendar month", async ({ page }) => {
      const toggle = page.locator("#cm-date-toggle");
      await expect(toggle.locator(".pf-v6-c-timestamp__text")).toHaveText("(May 20, 2026)");
      const panel = toggle.locator("..").locator(".pf-v6-c-panel");
      await expect(panel).toBeHidden();
      await toggle.click();
      await expect(panel).toBeVisible();
      await expect(panel.locator(".pf-v6-c-calendar-month")).toBeAttached();
    });

    test("picking a day moves the selection, updates the toggle, and closes", async ({ page }) => {
      const toggle = page.locator("#cm-date-toggle");
      await toggle.click();
      const panel = toggle.locator("..").locator(".pf-v6-c-panel");
      await panel
        .locator(".pf-v6-c-calendar-month__dates-cell:not(.pf-m-adjacent-month) .pf-v6-c-calendar-month__date", {
          hasText: /^27$/,
        })
        .click();
      await expect(panel).toBeHidden();
      await expect(toggle.locator(".pf-v6-c-timestamp__text")).toHaveText("(May 27, 2026)");
      await toggle.click();
      const selected = panel.locator(".pf-v6-c-calendar-month__dates-cell.pf-m-selected");
      await expect(selected).toHaveCount(1);
      await expect(selected.locator(".pf-v6-c-calendar-month__date")).toHaveText("27");
    });

    test("adjacent-month days are ignored", async ({ page }) => {
      const toggle = page.locator("#cm-date-toggle");
      await toggle.click();
      const panel = toggle.locator("..").locator(".pf-v6-c-panel");
      await panel.locator(".pf-m-adjacent-month .pf-v6-c-calendar-month__date").first().click();
      await expect(panel).toBeVisible();
      await expect(toggle.locator(".pf-v6-c-timestamp__text")).toHaveText("(May 20, 2026)");
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of [
      "with-actions",
      "with-favorites",
      "with-drilldown",
      "with-inline-search-filter",
      "tree-view-menu",
      "flyout",
      "date-select",
    ]) {
      test(`/components/custom-menus/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/custom-menus/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-menu-toggle").first()).toBeAttached();
      });
    }
  });

  test.describe("Java source tab", () => {
    test("builder-expressible cards get a Java tab; drilldown/flyout/tree do not", async ({ page }) => {
      await page.goto("/components/custom-menus");
      for (const ex of ["with-actions", "with-favorites", "with-inline-search-filter", "date-select"]) {
        const card = page.locator(`[data-rendered-href="/components/custom-menus/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      // drilldown, flyout and tree-view menus have no builder equivalent — hand-rolled anatomy only.
      for (const ex of ["with-drilldown", "flyout", "tree-view-menu"]) {
        const card = page.locator(`[data-rendered-href="/components/custom-menus/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("source-java route serves the composed builders as plain text", async ({ page }) => {
      const res = await page.request.get("/components/custom-menus/source-java/date-select");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain("CalendarMonth.of(");
    });
  });
});
