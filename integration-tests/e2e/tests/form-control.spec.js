import { test, expect } from "@playwright/test";

test.describe("Form Control", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/form-control");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
    await expect(page.locator("#readonly-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-form-control class", async ({ page }) => {
      await expect(page.locator("#fc-basic")).toHaveClass(
        /pf-v6-c-form-control/
      );
    });
  });

  test.describe("Disabled", () => {
    test("input has disabled attribute", async ({ page }) => {
      await expect(page.locator("#fc-disabled input")).toBeDisabled();
    });
  });

  test.describe("Readonly", () => {
    test("input has readonly attribute", async ({ page }) => {
      const input = page.locator("#fc-readonly input");
      await expect(input).toHaveAttribute("readonly", "");
    });
  });
});
