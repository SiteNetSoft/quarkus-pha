import { test, expect } from "@playwright/test";

const card = '[data-rendered-href="/components/application-launcher/application-launcher-menu"]';
const toggle = `${card} #al-menu-toggle`;
const searchInput = `${card} input[aria-label="Filter menu items"]`;

test.describe("Application Launcher", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/application-launcher");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Application launcher");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#application-launcher-menu")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-application-launcher")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Toggle", () => {
    test("is rendered as a plain menu toggle", async ({ page }) => {
      await expect(page.locator(toggle)).toBeVisible();
      await expect(page.locator(toggle)).toHaveClass(/pf-m-plain/);
    });

    test("uses the PF waffle icon, not a FontAwesome stand-in", async ({ page }) => {
      const icon = page.locator(`${toggle} .pf-v6-c-menu-toggle__icon svg`);
      await expect(icon).toHaveAttribute("viewBox", "0 0 32 32");
    });

    test("menu is not visible by default", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu`)).not.toBeVisible();
    });

    test("clicking toggle opens the menu and focuses the search input", async ({ page }) => {
      await page.locator(toggle).click();
      await expect(page.locator(`${card} .pf-v6-c-menu`)).toBeVisible();
      await expect(page.locator(searchInput)).toBeFocused();
    });

    test("Escape closes the menu", async ({ page }) => {
      await page.locator(toggle).click();
      await expect(page.locator(`${card} .pf-v6-c-menu`)).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(page.locator(`${card} .pf-v6-c-menu`)).not.toBeVisible();
    });

    test("opened menu is within the viewport", async ({ page }) => {
      await page.setViewportSize({ width: 1200, height: 900 });
      await page.locator(toggle).click();
      const menu = page.locator(`${card} .pf-v6-c-menu`);
      await expect(menu).toBeVisible();
      const box = await menu.boundingBox();
      expect(box.x).toBeGreaterThanOrEqual(0);
      expect(box.x + box.width).toBeLessThanOrEqual(1200);
    });
  });

  test.describe("Menu content", () => {
    test.beforeEach(async ({ page }) => {
      await page.locator(toggle).click();
    });

    test("shows Group 1 and Group 2 with a divider between them", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`, { hasText: "Group 1" })).toBeVisible();
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`, { hasText: "Group 2" })).toBeVisible();
      await expect(page.locator(`${card} .pf-v6-c-divider`).first()).toBeVisible();
    });

    test("external item carries a custom icon and the external-link icon", async ({ page }) => {
      // scope to Group 2 — a favorited clone of the same item can exist in the Favorites group
      const group2 = page.locator(`${card} .pf-v6-c-menu__group`).nth(2);
      const item = group2.locator(".pf-v6-c-menu__item", { hasText: "Custom component with icon" });
      await expect(item.locator(".pf-v6-c-menu__item-icon img")).toBeVisible();
      await expect(item.locator(".pf-v6-c-menu__item-external-icon svg")).toBeVisible();
    });

    test("Unavailable Application is disabled", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu__item`, { hasText: "Unavailable Application" })).toBeDisabled();
    });

    test("hovering the tooltip item shows the tooltip on the right", async ({ page }) => {
      // the ungrouped list is a direct child of menu__content; the Favorites clone lives inside a section
      const tipItem = page.locator(`${card} .pf-v6-c-menu__content > ul > li`, {
        hasText: "Application 3 with tooltip",
      });
      await tipItem.locator("button.pf-v6-c-menu__item").hover();
      const tooltip = tipItem.locator(".pf-v6-c-tooltip");
      await expect(tooltip).toBeVisible();
      await expect(tooltip).toHaveClass(/pf-m-right/);
      await expect(tooltip).toContainText("Launch Application 3");
    });
  });

  test.describe("Search filter", () => {
    test.beforeEach(async ({ page }) => {
      await page.locator(toggle).click();
    });

    test("filtering hides non-matching items and empty groups", async ({ page }) => {
      await page.locator(searchInput).fill("Application 2");
      const group1 = page.locator(`${card} .pf-v6-c-menu__group`).nth(1);
      await expect(group1.locator(".pf-v6-c-menu__item", { hasText: "Application 2" })).toBeVisible();
      await expect(group1.locator(".pf-v6-c-menu__item", { hasText: /^Application 1$/ })).not.toBeVisible();
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`, { hasText: "Group 2" })).not.toBeVisible();
    });

    test("no match shows the No results found item", async ({ page }) => {
      await page.locator(searchInput).fill("does-not-exist");
      await expect(page.locator(`${card} .pf-v6-c-menu__item`, { hasText: "No results found" })).toBeVisible();
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`)).toHaveCount(3);
      for (const title of await page.locator(`${card} .pf-v6-c-menu__group-title`).all()) {
        await expect(title).not.toBeVisible();
      }
    });

    test("the reset utility clears the filter", async ({ page }) => {
      await page.locator(searchInput).fill("does-not-exist");
      await page.locator(`${card} button[aria-label="Reset"]`).click();
      await expect(page.locator(searchInput)).toHaveValue("");
      await expect(page.locator(`${card} .pf-v6-c-menu__item`, { hasText: "No results found" })).not.toBeVisible();
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`, { hasText: "Group 1" })).toBeVisible();
    });
  });

  test.describe("Favorites", () => {
    test.beforeEach(async ({ page }) => {
      await page.locator(toggle).click();
    });

    test("no Favorites group until an item is starred", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`, { hasText: "Favorites" })).not.toBeVisible();
    });

    test("starring an item pins a clone into the Favorites group", async ({ page }) => {
      await page.locator(`${card} button[aria-label="Favorite Application 1"]`).click();
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`, { hasText: "Favorites" })).toBeVisible();
      // the original's star flips to favorited too, so both carry the Unfavorite label
      await expect(page.locator(`${card} button[aria-label="Unfavorite Application 1"]:visible`)).toHaveCount(2);
    });

    test("unstarring from the Favorites group removes the group again", async ({ page }) => {
      await page.locator(`${card} button[aria-label="Favorite Application 1"]`).click();
      await page.locator(`${card} button[aria-label="Unfavorite Application 1"]`).first().click();
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`, { hasText: "Favorites" })).not.toBeVisible();
      await expect(page.locator(`${card} button[aria-label="Favorite Application 1"]`)).toBeVisible();
    });

    test("favorites respect the search filter", async ({ page }) => {
      await page.locator(`${card} button[aria-label="Favorite Application 1"]`).click();
      await page.locator(searchInput).fill("Application 2");
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`, { hasText: "Favorites" })).not.toBeVisible();
    });
  });

  test("standalone example route returns 200", async ({ page }) => {
    const res = await page.goto("/components/application-launcher/application-launcher-menu");
    expect(res.status()).toBe(200);
    await expect(page.locator(".pf-v6-c-menu-toggle").first()).toBeVisible();
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const exampleCard = page.locator(card);
      await exampleCard.locator('button[aria-label*="Toggle Qute"]').click();
      await expect(exampleCard.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const exampleCard = page.locator(card);
      const link = exampleCard.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/application-launcher/application-launcher-menu");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });

  test.describe("Java source tab", () => {
    test("the example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/application-launcher");
      const card = page.locator('[data-rendered-href="/components/application-launcher/application-launcher-menu"]');
      await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
    });

    test("source-java route serves the composed builders as plain text", async ({ page }) => {
      const res = await page.request.get("/components/application-launcher/source-java/application-launcher-menu");
      expect(res.status()).toBe(200);
      const body = await res.text();
      expect(body).toContain("Menu.builder()");
      expect(body).toContain(".searchFilter(");
    });
  });
});
