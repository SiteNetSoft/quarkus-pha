import { test, expect } from "@playwright/test";

test.describe("Drawer", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/drawer");
  });

  test("page loads with both example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#static")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-drawer class", async ({ page }) => {
      await expect(page.locator("#dr-basic")).toHaveClass(/pf-v6-c-drawer/);
    });

    test("panel is hidden by default", async ({ page }) => {
      await expect(page.locator("#dr-basic")).not.toHaveClass(/pf-m-expanded/);
      await expect(page.locator("#dr-basic .pf-v6-c-drawer__panel")).toBeHidden();
    });

    test("clicking the toggle opens the panel", async ({ page }) => {
      await page.locator("#dr-basic button", { hasText: "Open drawer" }).first().click();
      await expect(page.locator("#dr-basic")).toHaveClass(/pf-m-expanded/);
      await expect(page.locator("#dr-basic .pf-v6-c-drawer__panel")).toBeVisible();
    });

    test("close button collapses the panel again", async ({ page }) => {
      await page.locator("#dr-basic button", { hasText: "Open drawer" }).first().click();
      await expect(page.locator("#dr-basic .pf-v6-c-drawer__panel")).toBeVisible();
      await page.locator("#dr-basic button[aria-label='Close drawer panel']").click();
      await expect(page.locator("#dr-basic")).not.toHaveClass(/pf-m-expanded/);
      await expect(page.locator("#dr-basic .pf-v6-c-drawer__panel")).toBeHidden();
    });
  });

  test.describe("Static", () => {
    test("has pf-m-static and pf-m-expanded classes", async ({ page }) => {
      const dr = page.locator("#dr-static");
      await expect(dr).toHaveClass(/pf-m-static/);
      await expect(dr).toHaveClass(/pf-m-expanded/);
    });

    test("panel is visible without any toggle", async ({ page }) => {
      await expect(page.locator("#dr-static .pf-v6-c-drawer__panel")).toBeVisible();
    });
  });
});
