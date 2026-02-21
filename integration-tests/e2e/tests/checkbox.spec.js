import { test, expect } from "@playwright/test";

test.describe("Checkbox", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/checkbox");
  });

  test("page loads with all 8 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#checked-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
    await expect(page.locator("#description-heading")).toBeVisible();
    await expect(page.locator("#body-heading")).toBeVisible();
    await expect(page.locator("#desc-body-heading")).toBeVisible();
    await expect(page.locator("#standalone-heading")).toBeVisible();
    await expect(page.locator("#required-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has check class and label text", async ({ page }) => {
      const wrap = page.locator("#check-basic-wrap");
      await expect(wrap).toHaveClass(/pf-v6-c-check/);
      await expect(wrap.locator(".pf-v6-c-check__label")).toHaveText("Checkbox");
    });

    test("input is unchecked by default", async ({ page }) => {
      await expect(page.locator("#check-basic")).not.toBeChecked();
    });
  });

  test.describe("Checked", () => {
    test("input is pre-checked", async ({ page }) => {
      await expect(page.locator("#check-checked")).toBeChecked();
    });
  });

  test.describe("Disabled", () => {
    test("disabled unchecked input is disabled", async ({ page }) => {
      await expect(page.locator("#check-disabled")).toBeDisabled();
    });

    test("disabled label has pf-m-disabled class", async ({ page }) => {
      const label = page.locator("#check-disabled-wrap .pf-v6-c-check__label");
      await expect(label).toHaveClass(/pf-m-disabled/);
    });

    test("disabled checked input is both disabled and checked", async ({ page }) => {
      const input = page.locator("#check-disabled-checked");
      await expect(input).toBeDisabled();
      await expect(input).toBeChecked();
    });
  });

  test.describe("With description", () => {
    test("description text is visible", async ({ page }) => {
      const desc = page.locator("#check-description-wrap .pf-v6-c-check__description");
      await expect(desc).toBeVisible();
      await expect(desc).toContainText("Single-tenant cloud service");
    });

    test("input has aria-describedby pointing to description", async ({ page }) => {
      await expect(page.locator("#check-description")).toHaveAttribute(
        "aria-describedby",
        "check-description-description"
      );
    });
  });

  test.describe("With body", () => {
    test("body content is visible", async ({ page }) => {
      const body = page.locator("#check-body-wrap .pf-v6-c-check__body");
      await expect(body).toBeVisible();
      await expect(body).toHaveText("This is where custom content goes.");
    });
  });

  test.describe("With description and body", () => {
    test("both description and body are visible", async ({ page }) => {
      const wrap = page.locator("#check-desc-body-wrap");
      await expect(wrap.locator(".pf-v6-c-check__description")).toBeVisible();
      await expect(wrap.locator(".pf-v6-c-check__body")).toBeVisible();
    });
  });

  test.describe("Standalone", () => {
    test("has pf-m-standalone modifier", async ({ page }) => {
      await expect(page.locator("#check-standalone-wrap")).toHaveClass(/pf-m-standalone/);
    });

    test("has no visible label element", async ({ page }) => {
      const label = page.locator("#check-standalone-wrap .pf-v6-c-check__label");
      await expect(label).toHaveCount(0);
    });

    test("input has aria-label for accessibility", async ({ page }) => {
      await expect(page.locator("#check-standalone")).toHaveAttribute(
        "aria-label",
        "Standalone checkbox"
      );
    });
  });

  test.describe("Required", () => {
    test("input has required attribute", async ({ page }) => {
      await expect(page.locator("#check-required")).toHaveAttribute("required", "");
    });

    test("required asterisk is visible", async ({ page }) => {
      const asterisk = page.locator("#check-required-wrap .pf-v6-c-check__label-required");
      await expect(asterisk).toBeVisible();
      await expect(asterisk).toHaveText("*");
    });
  });
});
