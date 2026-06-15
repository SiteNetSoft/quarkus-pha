import { test, expect } from "@playwright/test";

test.describe("Application Launcher", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/application-launcher");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Application launcher");
  });

  test("page-level anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-application-launcher")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("all example section anchors are present", async ({ page }) => {
    for (const id of ["basic", "with-sections", "text-items", "with-external", "align-right"]) {
      await expect(page.locator(`#${id}`)).toBeVisible();
    }
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/application-launcher/basic"]';

    test("toggle button is rendered", async ({ page }) => {
      await expect(page.locator(`${card} #al-basic-toggle`)).toBeVisible();
    });

    test("menu toggle has pf-m-plain class", async ({ page }) => {
      await expect(page.locator(`${card} #al-basic-toggle`)).toHaveClass(/pf-m-plain/);
    });

    test("menu is not visible by default", async ({ page }) => {
      await expect(page.locator(`${card} .pf-v6-c-menu`)).not.toBeVisible();
    });

    test("clicking toggle opens menu", async ({ page }) => {
      await page.locator(`${card} #al-basic-toggle`).click();
      await expect(page.locator(`${card} .pf-v6-c-menu`)).toBeVisible();
    });

    test("menu has 6 application items", async ({ page }) => {
      await page.locator(`${card} #al-basic-toggle`).click();
      await expect(page.locator(`${card} .pf-v6-c-menu__item`)).toHaveCount(6);
    });

    test("opened menu is within the viewport (regression: was anchored off-screen)", async ({ page }) => {
      await page.setViewportSize({ width: 1200, height: 900 });
      await page.locator(`${card} #al-basic-toggle`).click();
      const menu = page.locator(`${card} .pf-v6-c-menu`);
      await expect(menu).toBeVisible();
      const box = await menu.boundingBox();
      expect(box.x).toBeGreaterThanOrEqual(0);
      expect(box.x + box.width).toBeLessThanOrEqual(1200);
    });
  });

  test.describe("Additional variants", () => {
    const toggles = {
      "with-sections": "#al-sections-toggle",
      "text-items": "#al-text-toggle",
      "with-external": "#al-external-toggle",
      "align-right": "#al-right-toggle",
    };

    for (const [slug, toggle] of Object.entries(toggles)) {
      test(`${slug}: toggle opens its menu`, async ({ page }) => {
        const card = `[data-rendered-href="/components/application-launcher/${slug}"]`;
        await expect(page.locator(`${card} ${toggle}`)).toBeVisible();
        await expect(page.locator(`${card} .pf-v6-c-menu`)).not.toBeVisible();
        await page.locator(`${card} ${toggle}`).click();
        await expect(page.locator(`${card} .pf-v6-c-menu`)).toBeVisible();
      });
    }

    test("with-sections has two group titles (Favorites + All applications)", async ({ page }) => {
      const card = '[data-rendered-href="/components/application-launcher/with-sections"]';
      await page.locator(`${card} #al-sections-toggle`).click();
      await expect(page.locator(`${card} .pf-v6-c-menu__group-title`)).toHaveCount(2);
    });

    test("with-external items open in a new tab", async ({ page }) => {
      const card = '[data-rendered-href="/components/application-launcher/with-external"]';
      await page.locator(`${card} #al-external-toggle`).click();
      const links = page.locator(`${card} .pf-v6-c-menu__item`);
      await expect(links.first()).toHaveAttribute("target", "_blank");
    });
  });

  test("all standalone example routes return 200", async ({ page }) => {
    for (const slug of ["basic", "with-sections", "text-items", "with-external", "align-right"]) {
      const res = await page.goto(`/components/application-launcher/${slug}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-menu-toggle").first()).toBeVisible();
    }
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/application-launcher/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/application-launcher/basic"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/application-launcher/basic");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
});
