import { test, expect } from "@playwright/test";

test.describe("Password generator", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/password-generator");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Password generator");
  });

  test("Basic section heading is visible", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test("input is present and pre-populated on init", async ({ page }) => {
    const input = page.locator("#pg-basic-input");
    await expect(input).toBeVisible();
    // x-init="generate()" fills the field immediately.
    await expect(input).not.toHaveValue("");
  });

  test("input is rendered inside a text-input-group", async ({ page }) => {
    // Match the outer wrapper only — the __main inner wrapper would also match a
    // plain contains() lookup, so pin to the exact root class via class-token syntax.
    const group = page
      .locator("#pg-basic-input")
      .locator(
        "xpath=ancestor::div[contains(concat(' ', normalize-space(@class), ' '), ' pf-v6-c-text-input-group ')]",
      );
    await expect(group).toBeVisible();
  });

  test.describe("Pattern page: in-modal example", () => {
    test("open modal paints above the masthead (regression: stacking-context trap)", async ({ page }) => {
      await page.goto("/patterns/password-generator");
      // The in-modal example starts open — its backdrop must paint above the
      // masthead. An inline backdrop is trapped in the page main-container's
      // stacking context and paints under it.
      await expect(page.locator("#pwd-modal-backdrop .pf-v6-c-modal-box")).toBeVisible();
      const hitBackdrop = await page.evaluate(() => {
        const masthead = document.querySelector(".pf-v6-c-masthead");
        if (!masthead) return "no-masthead";
        const r = masthead.getBoundingClientRect();
        const el = document.elementFromPoint(r.left + r.width / 2, r.top + r.height / 2);
        return el && el.closest("#pwd-modal-backdrop") ? "backdrop" : (el?.className ?? "none");
      });
      expect(hitBackdrop).toBe("backdrop");
    });

    test("Cancel closes the modal and the reopen button appears", async ({ page }) => {
      await page.goto("/patterns/password-generator");
      const modal = page.locator("#pwd-modal-backdrop .pf-v6-c-modal-box");
      await expect(modal).toBeVisible();
      await modal.getByRole("button", { name: "Cancel" }).click();
      await expect(modal).toBeHidden();
      await expect(page.getByRole("button", { name: 'Open "Create user" modal' })).toBeVisible();
    });
  });

  test("regenerate produces a different value", async ({ page }) => {
    const input = page.locator("#pg-basic-input");
    const initial = await input.inputValue();
    await page.locator("button[aria-label='Regenerate']").click();
    const next = await input.inputValue();
    expect(next).not.toBe("");
    // 16 chars from a 70-char alphabet — collision is astronomically unlikely.
    expect(next).not.toBe(initial);
  });
});
