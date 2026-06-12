import { test, expect } from "@playwright/test";

test.describe("Dropdown", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/dropdown");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#plain-kebab")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-dropdown")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Basic", () => {
    const card = '[data-rendered-href="/components/dropdown/basic"]';

    test("menu toggle is visible", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu-toggle`)).toBeVisible();
    });

    test("menu is not visible by default", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu`)).not.toBeVisible();
    });

    test("clicking toggle opens menu and toggle gets pf-m-expanded", async ({ page }) => {
      await page.locator(`${card} .pf-v6-c-menu-toggle`).click();
      await expect(page.locator(`${card} .pf-v6-c-menu`)).toBeVisible();
      await expect(page.locator(`${card} .pf-v6-c-menu-toggle`)).toHaveClass(/pf-m-expanded/);
    });
  });

  test.describe("Plain kebab", () => {
    const card = '[data-rendered-href="/components/dropdown/plain-kebab"]';

    test("menu toggle has class pf-m-plain", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu-toggle`)).toHaveClass(/pf-m-plain/);
    });
  });

  test.describe("Parity additions", () => {
    test("groups and descriptions render in their menus", async ({ page }) => {
      await page.locator("#dd-groups .pf-v6-c-menu-toggle").evaluate((el) => el.click());
      await expect(page.locator("#dd-groups .pf-v6-c-menu__group-title")).toHaveText("Group 1");
      await page.locator("#dd-descriptions .pf-v6-c-menu-toggle").evaluate((el) => el.click());
      await expect(page.locator("#dd-descriptions .pf-v6-c-menu__item-description").first()).toBeVisible();
    });

    test("split checkbox toggle selects via menu actions", async ({ page }) => {
      const root = page.locator("#dd-split-checkbox");
      await root.locator(".pf-v6-c-menu-toggle__button").click();
      await root.locator(".pf-v6-c-menu__item", { hasText: "Select all" }).click();
      await expect(page.locator("#dd-split-checkbox-check")).toBeChecked();
    });

    test("/components/dropdown/with-groups returns 200", async ({ page }) => {
      const res = await page.goto("/components/dropdown/with-groups");
      expect(res.status()).toBe(200);
    });

    test("/components/dropdown/with-descriptions returns 200", async ({ page }) => {
      const res = await page.goto("/components/dropdown/with-descriptions");
      expect(res.status()).toBe(200);
    });

    test("/components/dropdown/split-checkbox returns 200", async ({ page }) => {
      const res = await page.goto("/components/dropdown/split-checkbox");
      expect(res.status()).toBe(200);
    });
  });
});
