import { test, expect } from "@playwright/test";

test.describe("Login Page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/login-page");
  });

  test("page loads with all 1 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#basic-heading")).toHaveText("Basic");
  });

  test.describe("Basic", () => {
    test("login page has pf-v6-c-login class", async ({ page }) => {
      await expect(page.locator("#lp-basic")).toHaveClass(/pf-v6-c-login/);
    });

    test("login page has a form element", async ({ page }) => {
      await expect(page.locator("#lp-basic form")).toBeVisible();
    });

    test("login page has a username input", async ({ page }) => {
      await expect(page.locator("#lp-user")).toBeVisible();
      await expect(page.locator("#lp-user")).toHaveAttribute("type", "text");
    });

    test("login page has a password input", async ({ page }) => {
      await expect(page.locator("#lp-pass")).toBeVisible();
      await expect(page.locator("#lp-pass")).toHaveAttribute("type", "password");
    });

    test("login page has a submit button", async ({ page }) => {
      await expect(page.locator("#lp-basic button[type='submit']")).toBeVisible();
    });
  });
});
