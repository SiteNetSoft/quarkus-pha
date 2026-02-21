import { test, expect } from "@playwright/test";

test.describe("Chip", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/chip");
  });

  test("page loads with all 4 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#badge-heading")).toBeVisible();
    await expect(page.locator("#removable-heading")).toBeVisible();
    await expect(page.locator("#overflow-heading")).toBeVisible();
  });

  test("deprecation notice is visible", async ({ page }) => {
    const notice = page.locator("#deprecation-notice");
    await expect(notice).toBeVisible();
    await expect(notice).toHaveClass(/pf-m-warning/);
    await expect(notice.locator(".pf-v6-c-alert__title")).toContainText("Deprecated");
  });

  test.describe("Basic", () => {
    test("renders as a label element with filled modifier", async ({ page }) => {
      const chip = page.locator("#chip-basic");
      await expect(chip).toHaveClass(/pf-v6-c-label/);
      await expect(chip).toHaveClass(/pf-m-filled/);
    });

    test("displays chip text", async ({ page }) => {
      await expect(page.locator("#chip-basic .pf-v6-c-label__text")).toHaveText("Chip");
    });

    test("has no close button", async ({ page }) => {
      const actions = page.locator("#chip-basic .pf-v6-c-label__actions");
      await expect(actions).toHaveCount(0);
    });
  });

  test.describe("With badge", () => {
    test("displays badge with count", async ({ page }) => {
      const badge = page.locator("#chip-badge .pf-v6-c-badge");
      await expect(badge).toBeVisible();
      await expect(badge).toHaveText("7");
    });

    test("badge has read modifier", async ({ page }) => {
      await expect(page.locator("#chip-badge .pf-v6-c-badge")).toHaveClass(/pf-m-read/);
    });
  });

  test.describe("Removable", () => {
    test("static removable chip has close button", async ({ page }) => {
      const closeBtn = page.locator("#chip-removable-static-remove");
      await expect(closeBtn).toBeVisible();
      await expect(closeBtn).toHaveAttribute("aria-label", "Remove");
    });

    test("close button has aria-labelledby linking to text", async ({ page }) => {
      await expect(page.locator("#chip-removable-static-remove")).toHaveAttribute(
        "aria-labelledby",
        "chip-removable-static-remove chip-removable-static-text"
      );
    });

    test("interactive chips can be removed by clicking close", async ({ page }) => {
      const container = page.locator("#removable-heading + p + div");
      await expect(container.locator(".pf-v6-c-label")).toHaveCount(3);
      // Click close on the first chip
      await container.locator(".pf-v6-c-button.pf-m-plain").first().click();
      await expect(container.locator(".pf-v6-c-label")).toHaveCount(2);
    });
  });

  test.describe("Overflow", () => {
    test("renders as a button element", async ({ page }) => {
      const overflow = page.locator("#chip-overflow");
      await expect(overflow).toHaveAttribute("type", "button");
    });

    test("has overflow modifier", async ({ page }) => {
      await expect(page.locator("#chip-overflow")).toHaveClass(/pf-m-overflow/);
    });

    test("displays overflow text", async ({ page }) => {
      await expect(page.locator("#chip-overflow .pf-v6-c-label__text")).toHaveText("3 more");
    });
  });
});
