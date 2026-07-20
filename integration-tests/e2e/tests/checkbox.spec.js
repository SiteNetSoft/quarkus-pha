import { test, expect } from "@playwright/test";

test.describe("Checkbox", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/checkbox");
  });

  test("page loads with the 8 example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#checked")).toBeVisible();
    await expect(page.locator("#disabled")).toBeVisible();
    await expect(page.locator("#reversed")).toBeVisible();
    await expect(page.locator("#with-description")).toBeVisible();
    await expect(page.locator("#with-body")).toBeVisible();
    await expect(page.locator("#standalone")).toBeVisible();
    await expect(page.locator("#required")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has check class and label text", async ({ page }) => {
      const wrap = page.locator("#cb-basic-wrap");
      await expect(wrap).toHaveClass(/pf-v6-c-check/);
      await expect(wrap.locator(".pf-v6-c-check__label")).toContainText("Accept terms");
    });

    test("input is unchecked by default", async ({ page }) => {
      await expect(page.locator("#cb-basic")).not.toBeChecked();
    });
  });

  test.describe("Checked", () => {
    test("input is pre-checked", async ({ page }) => {
      await expect(page.locator("#cb-checked")).toBeChecked();
    });
  });

  test.describe("Disabled", () => {
    test("disabled unchecked input is disabled", async ({ page }) => {
      await expect(page.locator("#cb-disabled")).toBeDisabled();
    });

    test("disabled label has pf-m-disabled class", async ({ page }) => {
      const label = page.locator("#cb-disabled-wrap .pf-v6-c-check__label");
      await expect(label).toHaveClass(/pf-m-disabled/);
    });

    test("disabled checked input is both disabled and checked", async ({ page }) => {
      const input = page.locator("#cb-disabled-checked");
      await expect(input).toBeDisabled();
      await expect(input).toBeChecked();
    });
  });

  test.describe("With description", () => {
    test("description text is visible", async ({ page }) => {
      const desc = page.locator("#cb-desc-wrap .pf-v6-c-check__description");
      await expect(desc).toBeVisible();
    });

    test("input has aria-describedby pointing to description", async ({ page }) => {
      await expect(page.locator("#cb-desc")).toHaveAttribute("aria-describedby", "cb-desc-description");
    });
  });

  test.describe("With body", () => {
    test("body content is visible", async ({ page }) => {
      const body = page.locator("#cb-body-wrap .pf-v6-c-check__body");
      await expect(body).toBeVisible();
    });
  });

  test.describe("Standalone", () => {
    test("has pf-m-standalone modifier", async ({ page }) => {
      await expect(page.locator("#cb-standalone-wrap")).toHaveClass(/pf-m-standalone/);
    });

    test("has no visible label element", async ({ page }) => {
      const label = page.locator("#cb-standalone-wrap .pf-v6-c-check__label");
      await expect(label).toHaveCount(0);
    });

    test("input has aria-label for accessibility", async ({ page }) => {
      await expect(page.locator("#cb-standalone")).toHaveAttribute("aria-label", "Select all rows");
    });
  });

  test.describe("Required", () => {
    test("input has required attribute", async ({ page }) => {
      await expect(page.locator("#cb-required")).toHaveAttribute("required", "");
    });

    test("required asterisk is visible", async ({ page }) => {
      const asterisk = page.locator("#cb-required-wrap .pf-v6-c-check__label-required");
      await expect(asterisk).toBeVisible();
      await expect(asterisk).toHaveText("*");
    });
  });

  test.describe("Parity additions", () => {
    test("controlled pair reports the checked set", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/checkbox/controlled"]');
      await expect(card.locator("p strong")).toHaveText("none");
      await page.locator("#cb-controlled-1-field").check();
      await page.locator("#cb-controlled-2-field").check();
      await expect(card.locator("p strong")).toHaveText("alpha, beta");
    });

    test("description and body render together", async ({ page }) => {
      await expect(page.locator("#cb-desc-body .pf-v6-c-check__description")).toBeVisible();
      await expect(page.locator("#cb-desc-body .pf-v6-c-check__body")).toBeVisible();
    });

    test("/components/checkbox/controlled returns 200", async ({ page }) => {
      const res = await page.goto("/components/checkbox/controlled");
      expect(res.status()).toBe(200);
    });

    test("/components/checkbox/label-wraps returns 200", async ({ page }) => {
      const res = await page.goto("/components/checkbox/label-wraps");
      expect(res.status()).toBe(200);
    });

    test("/components/checkbox/description-and-body returns 200", async ({ page }) => {
      const res = await page.goto("/components/checkbox/description-and-body");
      expect(res.status()).toBe(200);
    });
  });
  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab; anatomy/Alpine variants do not", async ({ page }) => {
      await page.goto("/components/checkbox");
      for (const ex of ["basic", "standalone", "with-description"]) {
        const card = page.locator(`[data-rendered-href="/components/checkbox/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of ["controlled", "label-wraps", "description-and-body"]) {
        const card = page.locator(`[data-rendered-href="/components/checkbox/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/checkbox/source-java/standalone");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('Check.standalone("cb-standalone", "Select all rows")');
    });
  });
});
