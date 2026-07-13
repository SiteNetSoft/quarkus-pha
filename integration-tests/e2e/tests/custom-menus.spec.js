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

  test("Basic example-card section id is visible", async ({ page }) => {
    await expect(page.locator("h3#basic")).toBeVisible();
    await expect(page.locator("h3#basic")).toHaveText("Basic");
  });

  test("Documentation sub-section ids are visible", async ({ page }) => {
    await expect(page.locator("h3#props-custom-menus")).toBeVisible();
    await expect(page.locator("h3#usage")).toBeVisible();
  });

  test.describe("Basic example", () => {
    test("toggle is visible with default text 'Project'", async ({ page }) => {
      const toggle = page.locator("#cm-basic-toggle");
      await expect(toggle).toBeVisible();
      await expect(toggle.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Project");
    });

    test("menu is hidden by default", async ({ page }) => {
      const menu = page.locator("#cm-basic-toggle").locator("..").locator(".pf-v6-c-menu");
      await expect(menu).not.toBeVisible();
    });

    test("clicking toggle opens menu with header, items, and footer", async ({ page }) => {
      await page.locator("#cm-basic-toggle").click();
      const wrapper = page.locator("#cm-basic-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu")).toBeVisible();
      await expect(wrapper.locator(".pf-v6-c-menu__header")).toBeVisible();
      await expect(wrapper.locator(".pf-v6-c-menu__header strong")).toHaveText("Recent projects");
      await expect(wrapper.locator(".pf-v6-c-menu__list-item")).toHaveCount(2);
      await expect(wrapper.locator(".pf-v6-c-menu__footer")).toBeVisible();
    });

    test("items have description text", async ({ page }) => {
      await page.locator("#cm-basic-toggle").click();
      const wrapper = page.locator("#cm-basic-toggle").locator("..");
      const descriptions = wrapper.locator(".pf-v6-c-menu__item-description");
      await expect(descriptions).toHaveCount(2);
      await expect(descriptions.nth(0)).toHaveText("Backend service");
      await expect(descriptions.nth(1)).toHaveText("Web frontend");
    });

    test("toggle gets expanded modifier when open", async ({ page }) => {
      const toggle = page.locator("#cm-basic-toggle");
      await toggle.click();
      await expect(toggle).toHaveClass(/pf-m-expanded/);
    });

    test("footer contains 'Browse all projects' link", async ({ page }) => {
      await page.locator("#cm-basic-toggle").click();
      const wrapper = page.locator("#cm-basic-toggle").locator("..");
      await expect(wrapper.locator(".pf-v6-c-menu__footer .pf-v6-c-button__text")).toHaveText("Browse all projects");
    });
  });

  test.describe("With search", () => {
    test("typing in the search slot filters the list", async ({ page }) => {
      await page.locator("#cm-search-toggle").click();
      const wrapper = page.locator("#cm-search-toggle").locator("..");
      const items = wrapper.locator(".pf-v6-c-menu__list-item");
      await expect(items.filter({ hasText: "Apollo" })).toBeVisible();
      await wrapper.locator(".pf-v6-c-menu__search input").fill("mer");
      await expect(items.filter({ hasText: "Mercury" })).toBeVisible();
      await expect(items.filter({ hasText: "Apollo" })).toBeHidden();
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
    for (const example of ["basic", "with-search", "flyout", "tree-view-menu", "date-select"]) {
      test(`/components/custom-menus/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/custom-menus/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-menu-toggle").first()).toBeAttached();
      });
    }
  });
});
