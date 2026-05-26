import { test, expect } from "@playwright/test";

test.describe("User feedback", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/extensions/user-feedback");
  });

  test("page loads with all 3 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#full")).toBeVisible();
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#with-prefilled-email")).toBeVisible();
  });

  test.describe("Full variant (home menu)", () => {
    test("trigger button opens modal on home page", async ({ page }) => {
      const root = page.locator("#uf-full");
      const modal = root.locator(".pf-v6-c-modal-box");
      await expect(modal).toBeHidden();
      await root.getByRole("button", { name: "Share your feedback" }).click();
      await expect(modal).toBeVisible();
      await expect(modal.locator("#uf-full-title-home")).toBeVisible();
      await expect(modal.locator(".chr-c-feedback-cards .pf-v6-c-card")).toHaveCount(3);
    });

    test("clicking Share feedback card navigates to share form", async ({ page }) => {
      const root = page.locator("#uf-full");
      await root.getByRole("button", { name: "Share your feedback" }).click();
      const modal = root.locator(".pf-v6-c-modal-box");
      await modal.getByRole("button", { name: "Share feedback" }).click();
      await expect(modal.locator("#uf-full-title-share")).toBeVisible();
      await expect(modal.locator("#uf-full-feedback")).toBeVisible();
    });

    test("submit transitions to success page", async ({ page }) => {
      const root = page.locator("#uf-full");
      await root.getByRole("button", { name: "Share your feedback" }).click();
      const modal = root.locator(".pf-v6-c-modal-box");
      await modal.getByRole("button", { name: "Share feedback" }).click();
      await modal.locator("#uf-full-feedback").fill("Looks great so far!");
      await modal.getByRole("button", { name: "Submit feedback" }).click();
      await expect(modal.locator("#uf-full-title-success")).toBeVisible({ timeout: 1500 });
    });

    test("Back from share form returns to home", async ({ page }) => {
      const root = page.locator("#uf-full");
      await root.getByRole("button", { name: "Share your feedback" }).click();
      const modal = root.locator(".pf-v6-c-modal-box");
      await modal.getByRole("button", { name: "Share feedback" }).click();
      // Multiple forms each have a Back button; only the share form's is visible.
      const shareForm = modal.locator(".chr-c-feedback-content").filter({ has: page.locator("#uf-full-title-share") });
      await shareForm.getByRole("button", { name: "Back", exact: true }).click();
      await expect(modal.locator("#uf-full-title-home")).toBeVisible();
    });

    test("Escape closes the modal", async ({ page }) => {
      const root = page.locator("#uf-full");
      await root.getByRole("button", { name: "Share your feedback" }).click();
      const modal = root.locator(".pf-v6-c-modal-box");
      await expect(modal).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(modal).toBeHidden();
    });

    test("opt-in to research reveals email field", async ({ page }) => {
      const root = page.locator("#uf-full");
      await root.getByRole("button", { name: "Share your feedback" }).click();
      const modal = root.locator(".pf-v6-c-modal-box");
      await modal.getByRole("button", { name: "Share feedback" }).click();
      await expect(modal.locator("#uf-full-email-share")).toBeHidden();
      await modal.locator("#uf-full-research").check();
      await expect(modal.locator("#uf-full-email-share")).toBeVisible();
    });

    test("invalid email shows validation error", async ({ page }) => {
      const root = page.locator("#uf-full");
      await root.getByRole("button", { name: "Share your feedback" }).click();
      const modal = root.locator(".pf-v6-c-modal-box");
      await modal.getByRole("button", { name: "Share feedback" }).click();
      await modal.locator("#uf-full-research").check();
      const email = modal.locator("#uf-full-email-share");
      await email.fill("not-an-email");
      await email.blur();
      const shareForm = modal.locator(".chr-c-feedback-content").filter({ has: page.locator("#uf-full-title-share") });
      await expect(shareForm.locator(".pf-v6-c-helper-text__item.pf-m-error")).toBeVisible();
    });
  });

  test.describe("Basic variant (no home menu)", () => {
    test("trigger opens straight to share form with no Back button", async ({ page }) => {
      const root = page.locator("#uf-basic");
      await root.getByRole("button", { name: "Send feedback" }).click();
      const modal = root.locator(".pf-v6-c-modal-box");
      await expect(modal.locator("#uf-basic-title-share")).toBeVisible();
      // The share form's Back button should be x-show'd out in basic mode.
      const shareForm = modal.locator(".chr-c-feedback-content").filter({ has: page.locator("#uf-basic-title-share") });
      await expect(shareForm.getByRole("button", { name: "Back", exact: true })).toBeHidden();
    });
  });

  test.describe("Pre-filled email variant", () => {
    test("email field pre-populated from data-email", async ({ page }) => {
      const root = page.locator("#uf-email");
      await root.getByRole("button", { name: "Share your feedback" }).click();
      const modal = root.locator(".pf-v6-c-modal-box");
      await modal.getByRole("button", { name: "Share feedback" }).click();
      await modal.locator("#uf-email-research").check();
      await expect(modal.locator("#uf-email-email-share")).toHaveValue("user@example.com");
    });
  });
});
