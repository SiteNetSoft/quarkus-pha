import { test, expect } from "@playwright/test";

test.describe("About modal", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/");
  });

  test("modal is hidden by default", async ({ page }) => {
    const modal = page.locator(".pf-v6-c-about-modal-box");
    await expect(modal).toBeHidden();
  });

  test("opens on About button click and shows product name", async ({ page }) => {
    await page.locator("button", { hasText: "About" }).click();

    const modal = page.locator(".pf-v6-c-about-modal-box");
    await expect(modal).toBeVisible();

    const title = page.locator("#about-modal-title");
    await expect(title).toHaveText("quarkus-pha");
  });

  test("displays version info and strapline", async ({ page }) => {
    await page.locator("button", { hasText: "About" }).click();

    const body = page.locator(".pf-v6-c-about-modal-box__body");
    await expect(body).toContainText("1.0.0-SNAPSHOT");
    await expect(body).toContainText("3.31.4");

    const strapline = page.locator(".pf-v6-c-about-modal-box__strapline");
    await expect(strapline).toContainText("Copyright");
    await expect(strapline).toContainText("SiteNetSoft");
  });

  test("brand image is visible with alt text", async ({ page }) => {
    await page.locator("button", { hasText: "About" }).click();

    const img = page.locator(".pf-v6-c-about-modal-box__brand-image");
    await expect(img).toBeVisible();
    await expect(img).toHaveAttribute("alt", "quarkus-pha logo");
    await expect(img).toHaveAttribute("src", "/web/images/PF-IconLogo.svg");
  });

  test("background image is applied", async ({ page }) => {
    await page.locator("button", { hasText: "About" }).click();

    const modal = page.locator(".pf-v6-c-about-modal-box");
    const style = await modal.getAttribute("style");
    expect(style).toContain("/web/images/pf-background.svg");
  });

  test("close button closes modal", async ({ page }) => {
    await page.locator("button", { hasText: "About" }).click();
    const modal = page.locator(".pf-v6-c-about-modal-box");
    await expect(modal).toBeVisible();

    await page.locator(".pf-v6-c-about-modal-box__close button").click();
    await expect(modal).toBeHidden();
  });

  test("Escape key closes modal", async ({ page }) => {
    await page.locator("button", { hasText: "About" }).click();
    const modal = page.locator(".pf-v6-c-about-modal-box");
    await expect(modal).toBeVisible();

    await page.keyboard.press("Escape");
    await expect(modal).toBeHidden();
  });

  test("ARIA attributes are correct", async ({ page }) => {
    await page.locator("button", { hasText: "About" }).click();

    const modal = page.locator(".pf-v6-c-about-modal-box");
    await expect(modal).toHaveAttribute("role", "dialog");
    await expect(modal).toHaveAttribute("aria-modal", "true");
    await expect(modal).toHaveAttribute("aria-labelledby", "about-modal-title");
  });
});
