import { test, expect } from "@playwright/test";

test.describe("Switch", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/switch");
  });

  test("page loads with all 4 example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#checked")).toHaveText("Checked");
    await expect(page.locator("h3#disabled")).toHaveText("Disabled");
    await expect(page.locator("h3#reversed")).toHaveText("Reversed");
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-switch class", async ({ page }) => {
      await expect(page.locator("#sw-basic")).toHaveClass(/pf-v6-c-switch/);
    });

    test("has label text", async ({ page }) => {
      await expect(page.locator("#sw-basic .pf-v6-c-switch__label")).toHaveText("Enable notifications");
    });
  });

  test.describe("Checked", () => {
    test("switch input is checked", async ({ page }) => {
      await expect(page.locator("#sw-checked-field")).toBeChecked();
    });
  });

  test.describe("Disabled", () => {
    test("disabled switches are disabled", async ({ page }) => {
      await expect(page.locator("#sw-dis-off-field")).toBeDisabled();
      await expect(page.locator("#sw-dis-on-field")).toBeDisabled();
      await expect(page.locator("#sw-dis-on-field")).toBeChecked();
    });
  });

  test.describe("Reversed", () => {
    test("has pf-m-reverse modifier", async ({ page }) => {
      await expect(page.locator("#sw-rev")).toHaveClass(/pf-m-reverse/);
    });
  });
});
