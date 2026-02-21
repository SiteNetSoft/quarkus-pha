import { test, expect } from "@playwright/test";

test.describe("Badge", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/badge");
  });

  test("page loads with all 3 section headings", async ({ page }) => {
    await expect(page.locator("#read-heading")).toBeVisible();
    await expect(page.locator("#unread-heading")).toBeVisible();
    await expect(page.locator("#disabled-heading")).toBeVisible();
  });

  test.describe("Read", () => {
    test("read badges have correct modifier class", async ({ page }) => {
      await expect(page.locator("#badge-read-7")).toHaveClass(/pf-m-read/);
      await expect(page.locator("#badge-read-24")).toHaveClass(/pf-m-read/);
      await expect(page.locator("#badge-read-999")).toHaveClass(/pf-m-read/);
    });
  });

  test.describe("Unread", () => {
    test("unread badges have correct modifier class", async ({ page }) => {
      await expect(page.locator("#badge-unread-7")).toHaveClass(/pf-m-unread/);
      await expect(page.locator("#badge-unread-24")).toHaveClass(/pf-m-unread/);
      await expect(page.locator("#badge-unread-999")).toHaveClass(/pf-m-unread/);
    });
  });

  test.describe("Disabled", () => {
    test("disabled badges have correct modifier class", async ({ page }) => {
      await expect(page.locator("#badge-disabled-7")).toHaveClass(/pf-m-disabled/);
      await expect(page.locator("#badge-disabled-24")).toHaveClass(/pf-m-disabled/);
      await expect(page.locator("#badge-disabled-999")).toHaveClass(/pf-m-disabled/);
    });
  });

  test("badges display numeric content", async ({ page }) => {
    await expect(page.locator("#badge-read-7")).toContainText("7");
    await expect(page.locator("#badge-unread-24")).toContainText("24");
    await expect(page.locator("#badge-disabled-999")).toContainText("999+");
  });

  test("all 9 badges are span elements with base class", async ({ page }) => {
    const badges = page.locator(".pf-v6-c-badge");
    await expect(badges).toHaveCount(9);
    for (let i = 0; i < 9; i++) {
      const tagName = await badges.nth(i).evaluate((el) => el.tagName);
      expect(tagName).toBe("SPAN");
    }
  });
});
