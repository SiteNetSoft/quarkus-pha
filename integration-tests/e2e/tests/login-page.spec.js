import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "show-hide-password", "header-utilities"];

test.describe("Login Page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/login-page");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic", () => {
    test("login page wrapper has pf-v6-c-login class", async ({ page }) => {
      await expect(page.locator("#lp-basic")).toHaveClass(/pf-v6-c-login/);
    });

    test("brand header renders the logo", async ({ page }) => {
      await expect(page.locator("#lp-basic .pf-v6-c-login__header img.pf-v6-c-brand")).toBeVisible();
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

    test("helper text appears on empty submit and fields are marked invalid", async ({ page }) => {
      await expect(page.locator("#lp-basic .pf-v6-c-helper-text")).toBeHidden();
      await page.locator("#lp-basic button[type='submit']").click();
      await expect(page.locator("#lp-basic .pf-v6-c-helper-text")).toBeVisible();
      await expect(page.locator("#lp-basic .pf-v6-c-helper-text__item")).toHaveClass(/pf-m-error/);
      await expect(page.locator("#lp-basic-username-field")).toHaveAttribute("aria-invalid", "true");
    });

    test("helper text stays hidden when credentials are filled", async ({ page }) => {
      await page.locator("#lp-basic-username-field").fill("admin");
      await page.locator("#lp-basic-password-field").fill("hunter2");
      await page.locator("#lp-basic button[type='submit']").click();
      await expect(page.locator("#lp-basic .pf-v6-c-helper-text")).toBeHidden();
    });

    test("remember-me checkbox toggles", async ({ page }) => {
      const check = page.locator("#lp-basic-remember");
      await expect(check).not.toBeChecked();
      await check.check();
      await expect(check).toBeChecked();
    });

    test("social login links and footer band render", async ({ page }) => {
      await expect(page.locator("#lp-basic .pf-v6-c-login__main-footer-links-item")).toHaveCount(5);
      await expect(page.locator("#lp-basic .pf-v6-c-login__main-footer-band-item")).toHaveCount(2);
      await expect(page.locator("#lp-basic .pf-v6-c-login__footer .pf-v6-c-list.pf-m-inline li")).toHaveCount(3);
    });
  });

  test.describe("Show/hide password", () => {
    test("eye button flips the input type", async ({ page }) => {
      const input = page.locator("#lp-show-hide-password-field");
      await expect(input).toHaveAttribute("type", "password");
      const toggle = page.locator("#lp-show-hide .pf-v6-c-input-group button");
      await toggle.click();
      await expect(input).toHaveAttribute("type", "text");
      await expect(toggle).toHaveAttribute("aria-label", "Hide password");
      await toggle.click();
      await expect(input).toHaveAttribute("type", "password");
      await expect(toggle).toHaveAttribute("aria-label", "Show password");
    });
  });

  test.describe("With header utilities", () => {
    test("language dropdown opens and selecting updates the toggle text", async ({ page }) => {
      const toggle = page.locator("#lp-header-utilities-lang-toggle");
      await expect(toggle).toContainText("English");
      const menu = page.locator("#lp-header-utilities .pf-v6-c-menu");
      await expect(menu).toBeHidden();
      await toggle.click();
      await expect(menu).toBeVisible();
      await menu.locator("button.pf-v6-c-menu__item", { hasText: "Español" }).click();
      await expect(menu).toBeHidden();
      await expect(toggle).toContainText("Español");
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/login-page/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/login-page/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-login").first()).toBeAttached();
      });
    }
  });
});
