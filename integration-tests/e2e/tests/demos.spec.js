import { test, expect } from "@playwright/test";

test.describe("Demo Pages", () => {

  // ── Dashboard ──

  test.describe("Dashboard", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/demos/dashboard");
    });

    test("page loads with sidebar layout", async ({ page }) => {
      await expect(page.locator("#layout-sidebar-page")).toBeVisible();
    });

    test("sidebar nav is visible", async ({ page }) => {
      await expect(page.locator(".pf-v6-c-page__sidebar")).toBeVisible();
    });

    test("stat cards are displayed", async ({ page }) => {
      await expect(page.locator("#stat-card-1")).toBeVisible();
      await expect(page.locator("#stat-card-2")).toBeVisible();
      await expect(page.locator("#stat-card-3")).toBeVisible();
      await expect(page.locator("#stat-card-4")).toBeVisible();
    });

    test("activity table is displayed", async ({ page }) => {
      await expect(page.locator("#activity-table")).toBeVisible();
    });

    test("alert banner is displayed", async ({ page }) => {
      await expect(page.locator("#dashboard-alert")).toBeVisible();
    });

    test("chart section exists", async ({ page }) => {
      await expect(page.locator("#dashboard-chart-section")).toBeVisible();
    });

    test("sidebar toggle button works", async ({ page }) => {
      const sidebar = page.locator(".pf-v6-c-page__sidebar");
      await expect(sidebar).toBeVisible();
      await page.locator('button[aria-label="Toggle sidebar"]').click();
      await expect(sidebar).not.toBeVisible();
      await page.locator('button[aria-label="Toggle sidebar"]').click();
      await expect(sidebar).toBeVisible();
    });

    test("skip to content link exists", async ({ page }) => {
      await expect(page.locator('a[href="#main-content"]')).toBeAttached();
    });

    test("dashboard nav link is active", async ({ page }) => {
      const activeLink = page.locator('a.pf-v6-c-nav__link.pf-m-current');
      await expect(activeLink).toContainText("Dashboard");
    });
  });

  // ── Data Management ──

  test.describe("Data Management", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/demos/data-management");
    });

    test("page loads with heading", async ({ page }) => {
      await expect(page.locator("#data-management-heading")).toBeVisible();
    });

    test("live search input is present", async ({ page }) => {
      await expect(page.locator("#data-search-field")).toBeVisible();
    });

    test("click-to-load button is present", async ({ page }) => {
      await expect(page.locator("#data-load-more-0 button")).toBeVisible();
    });

    test("search works via HTMX", async ({ page }) => {
      await page.locator("#data-search-field").pressSequentially("Gateway", { delay: 50 });
      await expect(page.locator("#data-search-results")).toContainText("Gateway Zeta", { timeout: 5000 });
    });

    test("click-to-load works via HTMX", async ({ page }) => {
      await page.locator("#data-load-more-0 button").click();
      await expect(page.locator("#load-item-0")).toBeVisible({ timeout: 5000 });
    });

    test("data management nav link is active", async ({ page }) => {
      const activeLink = page.locator('a.pf-v6-c-nav__link.pf-m-current');
      await expect(activeLink).toContainText("Data Management");
    });
  });

  // ── Settings ──

  test.describe("Settings", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/demos/settings");
    });

    test("page loads with horizontal nav layout", async ({ page }) => {
      await expect(page.locator("#layout-horizontal-page")).toBeVisible();
    });

    test("heading is visible", async ({ page }) => {
      await expect(page.locator("#settings-heading")).toBeVisible();
    });

    test("tabs are visible", async ({ page }) => {
      await expect(page.locator("#settings-tabs")).toBeVisible();
    });

    test("General tab is active by default", async ({ page }) => {
      await expect(page.locator("#panel-general")).toBeVisible();
      await expect(page.locator("#settings-display-name")).toBeVisible();
    });

    test("switching to Notifications tab shows notification settings", async ({ page }) => {
      await page.locator("#tab-notifications").click();
      await expect(page.locator("#panel-notifications")).toBeVisible();
      await expect(page.locator("#settings-email-notif")).toBeVisible();
    });

    test("switching to Security tab shows security settings", async ({ page }) => {
      await page.locator("#tab-security").click();
      await expect(page.locator("#panel-security")).toBeVisible();
      await expect(page.locator("#settings-current-password")).toBeVisible();
    });

    test("tabs have correct ARIA attributes", async ({ page }) => {
      await expect(page.locator("#tab-general")).toHaveAttribute("role", "tab");
      await expect(page.locator("#tab-general")).toHaveAttribute("aria-selected", "true");
      await expect(page.locator("#tab-notifications")).toHaveAttribute("aria-selected", "false");
    });

    test("settings nav link is active", async ({ page }) => {
      const activeLink = page.locator('a.pf-v6-c-nav__link.pf-m-current');
      await expect(activeLink).toContainText("Settings");
    });
  });

  // ── Landing Page ──

  test.describe("Landing Page", () => {
    test.beforeEach(async ({ page }) => {
      await page.goto("/demos/landing");
    });

    test("page loads with stacked layout", async ({ page }) => {
      await expect(page.locator("#layout-stacked-page")).toBeVisible();
    });

    test("hero section is visible", async ({ page }) => {
      await expect(page.locator("#hero-heading")).toBeVisible();
      await expect(page.locator("#hero-heading")).toContainText("quarkus-pha");
    });

    test("CTA buttons are visible", async ({ page }) => {
      await expect(page.locator("#cta-dashboard")).toBeVisible();
      await expect(page.locator("#cta-components")).toBeVisible();
    });

    test("features section is visible", async ({ page }) => {
      await expect(page.locator("#features-heading")).toBeVisible();
    });

    test("feature cards are displayed", async ({ page }) => {
      await expect(page.locator("#feature-card-1")).toBeVisible();
      await expect(page.locator("#feature-card-2")).toBeVisible();
      await expect(page.locator("#feature-card-3")).toBeVisible();
    });

    test("about section is visible", async ({ page }) => {
      await expect(page.locator("#about-heading")).toBeVisible();
    });

    test("description list in about section", async ({ page }) => {
      await expect(page.locator("#landing-about .pf-v6-c-description-list")).toBeVisible();
    });

    test("landing page nav link is active", async ({ page }) => {
      const activeLink = page.locator('.pf-v6-c-masthead a.pf-v6-c-nav__link.pf-m-current');
      await expect(activeLink).toContainText("Landing Page");
    });

    test("secondary nav exists", async ({ page }) => {
      const subnav = page.locator(".pf-v6-c-page__main-subnav");
      await expect(subnav).toBeVisible();
    });
  });

  // ── Index Page Demos Section ──

  test.describe("Index page demos section", () => {
    test("demos heading is visible", async ({ page }) => {
      await page.goto("/");
      await expect(page.locator("#demos-heading")).toBeVisible();
    });

    test("all 4 demo cards are present", async ({ page }) => {
      await page.goto("/");
      await expect(page.locator("#demo-dashboard")).toBeVisible();
      await expect(page.locator("#demo-data-management")).toBeVisible();
      await expect(page.locator("#demo-settings")).toBeVisible();
      await expect(page.locator("#demo-landing")).toBeVisible();
    });

    test("demo cards link to correct pages", async ({ page }) => {
      await page.goto("/");
      await expect(page.locator("#demo-dashboard")).toHaveAttribute("href", "/demos/dashboard");
      await expect(page.locator("#demo-data-management")).toHaveAttribute("href", "/demos/data-management");
      await expect(page.locator("#demo-settings")).toHaveAttribute("href", "/demos/settings");
      await expect(page.locator("#demo-landing")).toHaveAttribute("href", "/demos/landing");
    });
  });
});
