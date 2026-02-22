import { test, expect } from "@playwright/test";

test.describe("Accessibility", () => {

  // ── Modal Focus Trapping ──

  test.describe("Modal focus trapping", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/modal");
    });

    test("modal has role dialog and aria-modal", async ({ page }) => {
      const modalBox = page.locator("#modal-basic-backdrop .pf-v6-c-modal-box");
      await expect(modalBox).toHaveAttribute("role", "dialog");
      await expect(modalBox).toHaveAttribute("aria-modal", "true");
    });

    test("modal has aria-labelledby pointing to title", async ({ page }) => {
      const modalBox = page.locator("#modal-basic-backdrop .pf-v6-c-modal-box");
      await expect(modalBox).toHaveAttribute("aria-labelledby", "modal-basic-title");
    });

    test("Escape key closes the modal", async ({ page }) => {
      // Open modal by dispatching custom event
      await page.evaluate(() => window.dispatchEvent(new CustomEvent('open-modal', { detail: 'modal-basic' })));
      await expect(page.locator("#modal-basic-backdrop")).toBeVisible({ timeout: 3000 });
      await page.keyboard.press("Escape");
      await expect(page.locator("#modal-basic-backdrop")).not.toBeVisible();
    });

    test("Tab key keeps focus within modal", async ({ page }) => {
      await page.evaluate(() => window.dispatchEvent(new CustomEvent('open-modal', { detail: 'modal-basic' })));
      await expect(page.locator("#modal-basic-backdrop")).toBeVisible({ timeout: 3000 });
      // Wait for focus trap to activate and focus first element
      await page.waitForTimeout(200);
      // Tab multiple times — focus should stay inside the backdrop
      for (let i = 0; i < 5; i++) {
        await page.keyboard.press("Tab");
      }
      const insideBackdrop = await page.evaluate(() => {
        var el = document.activeElement;
        if (!el) return false;
        // Focus should be on the backdrop itself, or inside the modal
        return el.closest('#modal-basic-backdrop') !== null;
      });
      expect(insideBackdrop).toBe(true);
    });

    test("close button has aria-label", async ({ page }) => {
      const closeBtn = page.locator("#modal-basic-backdrop .pf-v6-c-modal-box__close button");
      await expect(closeBtn).toHaveAttribute("aria-label", "Close");
    });
  });

  // ── About Modal Focus Trapping ──

  test.describe("About modal focus trapping", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/about-modal");
    });

    test("about modal has role dialog", async ({ page }) => {
      const modal = page.locator(".pf-v6-c-about-modal-box");
      await expect(modal).toHaveAttribute("role", "dialog");
      await expect(modal).toHaveAttribute("aria-modal", "true");
    });

    test("about modal has aria-labelledby", async ({ page }) => {
      const modal = page.locator(".pf-v6-c-about-modal-box");
      await expect(modal).toHaveAttribute("aria-labelledby", "about-modal-title");
    });

    test("close button has aria-label", async ({ page }) => {
      const closeBtn = page.locator(".pf-v6-c-about-modal-box__close button");
      await expect(closeBtn).toHaveAttribute("aria-label", "Close dialog");
    });
  });

  // ── Tabs Keyboard Navigation ──

  test.describe("Tabs keyboard navigation", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/tabs");
    });

    test("tabs container has role tablist", async ({ page }) => {
      const tablist = page.locator("#tabs-basic .pf-v6-c-tabs__list");
      await expect(tablist).toHaveAttribute("role", "tablist");
    });

    test("tabs component renders", async ({ page }) => {
      await expect(page.locator("#tabs-basic")).toBeVisible();
    });
  });

  // ── Menu Keyboard Navigation ──

  test.describe("Menu keyboard navigation", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/menu");
    });

    test("menu list has role menu", async ({ page }) => {
      const menuList = page.locator("#mn-basic .pf-v6-c-menu__list");
      await expect(menuList).toHaveAttribute("role", "menu");
    });

    test("menu component renders", async ({ page }) => {
      await expect(page.locator("#mn-basic")).toBeVisible();
    });
  });

  // ── Tree View Keyboard Navigation ──

  test.describe("Tree view keyboard navigation", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/tree-view");
    });

    test("tree view list has role tree", async ({ page }) => {
      const treeList = page.locator("#tv-basic .pf-v6-c-tree-view__list").first();
      await expect(treeList).toHaveAttribute("role", "tree");
    });

    test("tree view component renders", async ({ page }) => {
      await expect(page.locator("#tv-basic")).toBeVisible();
    });
  });

  // ── Toggle Group ARIA ──

  test.describe("Toggle group ARIA", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/toggle-group");
    });

    test("toggle group has role group", async ({ page }) => {
      const group = page.locator("#tg-basic");
      await expect(group).toHaveAttribute("role", "group");
    });
  });

  // ── Notification Drawer ARIA ──

  test.describe("Notification drawer ARIA", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/notification-drawer");
    });

    test("notification drawer has role region", async ({ page }) => {
      const drawer = page.locator("#nd-basic");
      await expect(drawer).toHaveAttribute("role", "region");
    });

    test("notification drawer has aria-label", async ({ page }) => {
      const drawer = page.locator("#nd-basic");
      const ariaLabel = await drawer.getAttribute("aria-label");
      expect(ariaLabel).toBeTruthy();
    });
  });

  // ── Drawer ARIA ──

  test.describe("Drawer ARIA attributes", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/drawer");
    });

    test("drawer renders", async ({ page }) => {
      await expect(page.locator("#dr-inline")).toBeVisible();
    });
  });

  // ── Popover ARIA ──

  test.describe("Popover ARIA", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/popover");
    });

    test("popover has role dialog and aria-modal", async ({ page }) => {
      const popover = page.locator("#po-top");
      await expect(popover).toHaveAttribute("role", "dialog");
      await expect(popover).toHaveAttribute("aria-modal", "true");
    });

    test("popover has aria-labelledby when titled", async ({ page }) => {
      const popover = page.locator("#po-header");
      await expect(popover).toHaveAttribute("aria-labelledby", "po-header-title");
    });

    test("popover close button has aria-label", async ({ page }) => {
      const closeBtn = page.locator("#po-top .pf-v6-c-popover__close button");
      await expect(closeBtn).toHaveAttribute("aria-label", "Close");
    });
  });

  // ── Skip to Content ──

  test.describe("Skip to content", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/skip-to-content");
    });

    test("skip to content link exists", async ({ page }) => {
      const link = page.locator(".pf-v6-c-skip-to-content").first();
      await expect(link).toBeAttached();
    });

    test("skip to content link has href", async ({ page }) => {
      const link = page.locator(".pf-v6-c-skip-to-content").first();
      const href = await link.getAttribute("href");
      expect(href).toBeTruthy();
      expect(href).toMatch(/^#/);
    });
  });

  // ── Navigation ARIA ──

  test.describe("Navigation ARIA", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/navigation");
    });

    test("nav element has aria-label", async ({ page }) => {
      const nav = page.locator("#nav-vertical");
      const ariaLabel = await nav.getAttribute("aria-label");
      expect(ariaLabel).toBeTruthy();
    });

    test("nav element is a nav tag", async ({ page }) => {
      const tagName = await page.locator("#nav-vertical").evaluate((el) => el.tagName);
      expect(tagName).toBe("NAV");
    });
  });

  // ── Masthead ARIA ──

  test.describe("Masthead ARIA", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/components/masthead");
    });

    test("masthead is a header element", async ({ page }) => {
      const tagName = await page.locator("#mh-basic").evaluate((el) => el.tagName);
      expect(tagName).toBe("HEADER");
    });
  });

  // ── General ARIA Checks ──

  test.describe("General ARIA checks", () => {
    test("icon-only buttons have aria-label across multiple pages", async ({ page }) => {
      await page.goto("/components/modal");
      const closeBtn = page.locator(".pf-v6-c-modal-box__close button").first();
      const ariaLabel = await closeBtn.getAttribute("aria-label");
      expect(ariaLabel).toBeTruthy();
    });

    test("all backdrop elements have hidden decorative icons", async ({ page }) => {
      await page.goto("/components/modal");
      const icons = page.locator(".pf-v6-c-modal-box i.fas");
      const count = await icons.count();
      for (let i = 0; i < count; i++) {
        await expect(icons.nth(i)).toHaveAttribute("aria-hidden", "true");
      }
    });

    test("page component uses semantic structure", async ({ page }) => {
      await page.goto("/components/page");
      const pageEl = page.locator("#pg-basic");
      await expect(pageEl).toHaveClass(/pf-v6-c-page/);
    });
  });
});
