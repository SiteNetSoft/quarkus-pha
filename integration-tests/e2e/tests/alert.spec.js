import { test, expect } from "@playwright/test";

test.describe("Alert", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/alert");
  });

  test("page loads with all 8 section headings", async ({ page }) => {
    await expect(page.locator("#types-heading")).toBeVisible();
    await expect(page.locator("#descriptions-heading")).toBeVisible();
    await expect(page.locator("#action-close-heading")).toBeVisible();
    await expect(page.locator("#action-links-heading")).toBeVisible();
    await expect(page.locator("#inline-heading")).toBeVisible();
    await expect(page.locator("#inline-plain-heading")).toBeVisible();
    await expect(page.locator("#expandable-heading")).toBeVisible();
    await expect(page.locator("#truncated-heading")).toBeVisible();
  });

  test.describe("Types", () => {
    test("correct modifier classes for all 5 variants", async ({ page }) => {
      await expect(page.locator("#alert-custom")).toHaveClass(/pf-m-custom/);
      await expect(page.locator("#alert-info")).toHaveClass(/pf-m-info/);
      await expect(page.locator("#alert-success")).toHaveClass(/pf-m-success/);
      await expect(page.locator("#alert-warning")).toHaveClass(/pf-m-warning/);
      await expect(page.locator("#alert-danger")).toHaveClass(/pf-m-danger/);
    });

    test("icon element present in each alert", async ({ page }) => {
      const variants = ["custom", "info", "success", "warning", "danger"];
      for (const variant of variants) {
        const alert = page.locator(`#alert-${variant}`);
        await expect(alert.locator(".pf-v6-c-alert__icon")).toBeVisible();
      }
    });

    test("screen reader text present", async ({ page }) => {
      const variants = ["custom", "info", "success", "warning", "danger"];
      for (const variant of variants) {
        const alert = page.locator(`#alert-${variant}`);
        const srText = alert.locator(".pf-screen-reader");
        await expect(srText).toHaveText(`${variant} alert:`);
      }
    });
  });

  test.describe("Description", () => {
    test("has h4 title and description element", async ({ page }) => {
      const alert = page.locator("#alert-description");
      await expect(alert.locator("h4.pf-v6-c-alert__title")).toBeVisible();
      await expect(alert.locator(".pf-v6-c-alert__description")).toBeVisible();
    });
  });

  test.describe("Close button", () => {
    test("close button exists with aria-label", async ({ page }) => {
      const alert = page.locator("#alert-closable");
      const closeBtn = alert.locator(".pf-v6-c-alert__action button");
      await expect(closeBtn).toBeVisible();
      await expect(closeBtn).toHaveAttribute("aria-label");
    });

    test("clicking close hides the alert", async ({ page }) => {
      const alert = page.locator("#alert-closable");
      await expect(alert).toBeVisible();
      await alert.locator(".pf-v6-c-alert__action button").click();
      await expect(alert).toBeHidden();
    });
  });

  test.describe("Action links", () => {
    test("action group contains links", async ({ page }) => {
      const alert = page.locator("#alert-action-links");
      const actionGroup = alert.locator(".pf-v6-c-alert__action-group");
      await expect(actionGroup).toBeVisible();
      const links = actionGroup.locator("a");
      await expect(links).toHaveCount(2);
    });
  });

  test.describe("Inline", () => {
    test("all 5 inline alerts have pf-m-inline class", async ({ page }) => {
      const variants = ["custom", "info", "success", "warning", "danger"];
      for (const variant of variants) {
        await expect(page.locator(`#alert-inline-${variant}`)).toHaveClass(/pf-m-inline/);
      }
    });
  });

  test.describe("Inline plain", () => {
    test("has both pf-m-inline and pf-m-plain modifiers", async ({ page }) => {
      const alert = page.locator("#alert-inline-plain");
      await expect(alert).toHaveClass(/pf-m-inline/);
      await expect(alert).toHaveClass(/pf-m-plain/);
    });
  });

  test.describe("Expandable", () => {
    test("toggle click expands and shows description", async ({ page }) => {
      const alert = page.locator("#alert-expandable");
      await expect(alert).toHaveClass(/pf-m-expandable/);

      // Description should be hidden initially
      const description = alert.locator(".pf-v6-c-alert__description");
      await expect(description).toBeHidden();

      // Click toggle to expand
      await alert.locator(".pf-v6-c-alert__toggle button").click();
      await expect(alert).toHaveClass(/pf-m-expanded/);
      await expect(description).toBeVisible();
    });
  });

  test.describe("Truncated title", () => {
    test("has pf-m-truncate modifier", async ({ page }) => {
      await expect(page.locator("#alert-truncated")).toHaveClass(/pf-m-truncate/);
    });
  });
});
