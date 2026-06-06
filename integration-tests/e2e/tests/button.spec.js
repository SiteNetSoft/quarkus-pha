import { test, expect } from "@playwright/test";

test.describe("Button", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/button");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#variant-examples")).toBeVisible();
    await expect(page.locator("#disabled-buttons")).toBeVisible();
    await expect(page.locator("#small-buttons")).toBeVisible();
    await expect(page.locator("#block-level")).toBeVisible();
    await expect(page.locator("#links-as-buttons")).toBeVisible();
    await expect(page.locator("#call-to-action")).toBeVisible();
  });

  test("page anchors are present", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-button")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test.describe("Variations", () => {
    test("all 8 variants render with correct modifier classes", async ({ page }) => {
      const variants = ["primary", "secondary", "tertiary", "danger", "warning", "link", "plain", "control"];
      for (const variant of variants) {
        const btn = page.locator(`#btn-${variant}`);
        await expect(btn).toHaveClass(/pf-v6-c-button/);
        await expect(btn).toHaveClass(new RegExp(`pf-m-${variant}`));
      }
    });

    test("buttons are button elements with type=button", async ({ page }) => {
      const btn = page.locator("#btn-primary");
      await expect(btn).toHaveAttribute("type", "button");
    });
  });

  test.describe("Disabled", () => {
    test("disabled buttons have disabled attribute", async ({ page }) => {
      await expect(page.locator("#btn-disabled-primary")).toBeDisabled();
      await expect(page.locator("#btn-disabled-secondary")).toBeDisabled();
    });
  });

  test.describe("Small", () => {
    test("has pf-m-sm modifier", async ({ page }) => {
      await expect(page.locator("#btn-sm-primary")).toHaveClass(/pf-m-sm/);
      await expect(page.locator("#btn-sm-secondary")).toHaveClass(/pf-m-sm/);
    });
  });

  test.describe("Block level", () => {
    test("has pf-m-block modifier", async ({ page }) => {
      await expect(page.locator("#btn-block")).toHaveClass(/pf-m-block/);
    });
  });

  test.describe("Links as buttons", () => {
    test("renders as anchor elements with href", async ({ page }) => {
      const primaryLink = page.locator("#btn-link-primary");
      await expect(primaryLink).toHaveAttribute("href", "#links-as-buttons");

      const tagName = await primaryLink.evaluate((el) => el.tagName.toLowerCase());
      expect(tagName).toBe("a");
    });
  });

  test.describe("Call to action", () => {
    test("has pf-m-lg modifier", async ({ page }) => {
      await expect(page.locator("#btn-cta-primary")).toHaveClass(/pf-m-lg/);
      await expect(page.locator("#btn-cta-secondary")).toHaveClass(/pf-m-lg/);
      await expect(page.locator("#btn-cta-tertiary")).toHaveClass(/pf-m-lg/);
    });
  });

  test.describe("Circle buttons", () => {
    test("section heading and pf-m-circle modifier render", async ({ page }) => {
      await expect(page.locator("#circle-buttons")).toBeVisible();
      await expect(page.locator("#btn-circle-primary")).toHaveClass(/pf-m-circle/);
      await expect(page.locator("#btn-circle-plain")).toHaveClass(/pf-m-circle/);
      // icon-only — must carry an accessible name
      await expect(page.locator("#btn-circle-primary")).toHaveAttribute("aria-label", /circle/);
    });
  });

  test.describe("Button types", () => {
    test("submit / reset / default types render", async ({ page }) => {
      await expect(page.locator("#button-types")).toBeVisible();
      await expect(page.locator("#btn-type-submit")).toHaveAttribute("type", "submit");
      await expect(page.locator("#btn-type-reset")).toHaveAttribute("type", "reset");
      await expect(page.locator("#btn-type-default")).toHaveAttribute("type", "button");
    });
  });

  test.describe("Progress button (login demo)", () => {
    test("clicking logs in via HTMX stub, then resets", async ({ page }) => {
      await expect(page.locator("#progress-login")).toBeVisible();
      const loginBtn = page.locator("#login-btn");
      await expect(loginBtn).toHaveText(/Link account and log in/);

      await loginBtn.click();
      // server stub returns the success state (with a Reset button)
      const reset = page.locator('#login-action button:has-text("Reset demo")');
      await expect(reset).toBeVisible({ timeout: 5000 });
      await expect(page.locator("#login-action")).toContainText(/Logged in as/);

      // reset swaps the progress button back in
      await reset.click();
      await expect(page.locator("#login-btn")).toBeVisible({ timeout: 5000 });
    });
  });
});
