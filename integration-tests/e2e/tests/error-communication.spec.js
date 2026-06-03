import { test, expect } from "@playwright/test";

const COMPONENTS = [
  ["error-state", "Something went wrong"],
  ["missing-page", "We lost that page"],
  ["maintenance", "Maintenance in progress"],
  ["unauthorized-access", "You do not have access"],
  ["unavailable-content", "This page is temporarily unavailable"],
  ["error-boundary", "Something went wrong"],
];

test.describe("Error communication", () => {
  test("landing lists all 7 components", async ({ page }) => {
    await page.goto("/error-communication");
    await expect(page.locator(".pf-v6-l-gallery .pf-v6-c-card")).toHaveCount(7);
  });

  for (const [slug, title] of COMPONENTS) {
    test(`${slug} composes an empty-state with the expected title`, async ({ page }) => {
      const res = await page.goto(`/error-communication/${slug}/standalone`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-empty-state__title-text").first()).toHaveText(title);
    });

    test(`${slug} demo page renders`, async ({ page }) => {
      const res = await page.goto(`/error-communication/${slug}`);
      expect(res.status()).toBe(200);
    });
  }

  test("action buttons are composed, not leaking the empty-state size/id", async ({ page }) => {
    await page.goto("/error-communication/error-state/standalone");
    const btn = page.locator(".pf-v6-c-empty-state__actions .pf-v6-c-button");
    await expect(btn).toHaveText("Go to home page");
    await expect(btn).not.toHaveClass(/pf-m-lg/);
    await expect(btn).not.toHaveAttribute("id");
  });

  test("unauthorized-access has two action groups (primary + return link)", async ({ page }) => {
    await page.goto("/error-communication/unauthorized-access/standalone");
    await expect(page.locator(".pf-v6-c-empty-state__actions")).toHaveCount(2);
  });

  test("warning-modal opens on trigger and composes the modal", async ({ page }) => {
    await page.goto("/error-communication/warning-modal/standalone");
    const modalBox = page.locator(".pf-v6-c-modal-box");
    // hidden until the trigger fires the open-modal event
    await expect(modalBox).toBeHidden();
    await page.getByRole("button", { name: "Show warning modal" }).click();
    await expect(modalBox).toBeVisible();
    await expect(modalBox.locator(".pf-v6-c-modal-box__title-text")).toHaveText("Confirm");
    await expect(modalBox.locator("#warning-modal-modal-confirm")).toBeVisible();
    await expect(modalBox.locator("#warning-modal-modal-cancel")).toBeVisible();
  });

  test("error-boundary shows expandable diagnostics (demo passes errorDetails)", async ({ page }) => {
    await page.goto("/error-communication/error-boundary");
    await expect(page.locator("details summary")).toBeVisible();
  });

  test("source endpoint returns the runtime template", async ({ request }) => {
    const res = await request.get("/error-communication/error-state/source");
    expect(res.status()).toBe(200);
    expect(await res.text()).toContain("components/feedback/empty-state");
  });
});
