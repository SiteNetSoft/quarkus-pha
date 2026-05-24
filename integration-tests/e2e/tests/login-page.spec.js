import { test, expect } from "@playwright/test";

test.describe("Login Page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/login-page");
  });

  test("page loads with the basic example section in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("login page wrapper has pf-v6-c-login class", async ({ page }) => {
      await expect(page.locator("#lp-basic")).toHaveClass(/pf-v6-c-login/);
    });

    test("login page has a form element", async ({ page }) => {
      await expect(page.locator("#lp-basic form")).toBeVisible();
    });

    test("login page has a text username input", async ({ page }) => {
      const username = page.locator("#lp-basic-username-field");
      await expect(username).toBeVisible();
      await expect(username).toHaveAttribute("type", "text");
    });

    test("login page has a password input", async ({ page }) => {
      const password = page.locator("#lp-basic-password-field");
      await expect(password).toBeVisible();
      await expect(password).toHaveAttribute("type", "password");
    });

    test("login page has a submit button", async ({ page }) => {
      await expect(page.locator("#lp-basic button[type='submit']")).toBeVisible();
    });
  });
});
