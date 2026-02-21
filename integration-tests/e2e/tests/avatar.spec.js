import { test, expect } from "@playwright/test";

test.describe("Avatar", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/avatar");
  });

  test("page loads with all 5 section headings", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#sizes-heading")).toBeVisible();
    await expect(page.locator("#bordered-heading")).toBeVisible();
    await expect(page.locator("#bordered-sizes-heading")).toBeVisible();
    await expect(page.locator("#custom-image-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has base avatar class", async ({ page }) => {
      await expect(page.locator("#avatar-basic")).toHaveClass(/pf-v6-c-avatar/);
    });

    test("has alt attribute", async ({ page }) => {
      await expect(page.locator("#avatar-basic")).toHaveAttribute("alt");
    });
  });

  test.describe("Sizes", () => {
    test("correct modifier classes for all 4 sizes", async ({ page }) => {
      await expect(page.locator("#avatar-sm")).toHaveClass(/pf-m-sm/);
      await expect(page.locator("#avatar-md")).toHaveClass(/pf-m-md/);
      await expect(page.locator("#avatar-lg")).toHaveClass(/pf-m-lg/);
      await expect(page.locator("#avatar-xl")).toHaveClass(/pf-m-xl/);
    });
  });

  test.describe("Bordered", () => {
    test("has bordered modifier class", async ({ page }) => {
      await expect(page.locator("#avatar-bordered")).toHaveClass(/pf-m-bordered/);
    });
  });

  test.describe("Bordered sizes", () => {
    test("has both size and bordered modifiers", async ({ page }) => {
      const sizes = ["sm", "md", "lg", "xl"];
      for (const size of sizes) {
        const avatar = page.locator(`#avatar-bordered-${size}`);
        await expect(avatar).toHaveClass(new RegExp(`pf-m-${size}`));
        await expect(avatar).toHaveClass(/pf-m-bordered/);
      }
    });
  });

  test("all avatars are img elements", async ({ page }) => {
    const avatars = page.locator(".pf-v6-c-avatar");
    const count = await avatars.count();
    expect(count).toBeGreaterThan(0);
    for (let i = 0; i < count; i++) {
      await expect(avatars.nth(i)).toHaveAttribute("src");
      const tagName = await avatars.nth(i).evaluate((el) => el.tagName);
      expect(tagName).toBe("IMG");
    }
  });
});
