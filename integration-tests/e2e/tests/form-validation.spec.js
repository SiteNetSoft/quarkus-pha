import { test, expect } from "@playwright/test";

test.describe("Form validation (HTMX)", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/form-validation");
  });

  test("renders the signup form posting to the server", async ({ page }) => {
    const form = page.locator("#htmx-signup-form");
    await expect(form).toBeVisible();
    await expect(form).toHaveAttribute("hx-post", "/api/htmx/form-validate");
  });

  test("submitting empty returns server-side required errors", async ({ page }) => {
    await page.locator("#htmx-signup-form button[type=submit]").click();
    await expect(page.locator("#htmx-signup-form .pf-v6-c-helper-text__item.pf-m-error")).toHaveCount(2);
    await expect(page.locator(".pf-v6-c-form-control.pf-m-error")).toHaveCount(2);
  });

  test("invalid values return field-specific messages", async ({ page }) => {
    await page.locator("#htmx-signup-username").fill("admin");
    await page.locator("#htmx-signup-email").fill("not-an-email");
    await page.locator("#htmx-signup-form button[type=submit]").click();
    await expect(page.locator("#htmx-signup-form")).toContainText("already taken");
    await expect(page.locator("#htmx-signup-form")).toContainText("valid email address");
  });

  test("valid submission returns a success alert", async ({ page }) => {
    await page.locator("#htmx-signup-username").fill("alice");
    await page.locator("#htmx-signup-email").fill("alice@example.com");
    await page.locator("#htmx-signup-form button[type=submit]").click();
    await expect(page.locator("#htmx-signup-form .pf-v6-c-alert.pf-m-success")).toBeVisible();
    await expect(page.locator("#htmx-signup-form .pf-v6-c-alert")).toContainText("Account created for alice");
  });
});
