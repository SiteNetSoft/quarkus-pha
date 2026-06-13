import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "with-textarea", "with-dropdown", "with-popover", "multiple-siblings"];

test.describe("Input group", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/input-group");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic joins boxed addons around a filling input", async ({ page }) => {
    await expect(page.locator("#ig-basic")).toHaveClass(/pf-v6-c-input-group/);
    await expect(page.locator("#ig-basic .pf-v6-c-input-group__item.pf-m-box")).toHaveCount(2);
    await expect(page.locator("#ig-basic .pf-v6-c-input-group__item.pf-m-fill input")).toBeVisible();
  });

  test("dropdown unit picker updates the toggle text", async ({ page }) => {
    const root = page.locator("#ig-dropdown");
    await root.locator(".pf-v6-c-menu-toggle").click();
    await root.locator(".pf-v6-c-menu__item", { hasText: "MB" }).click();
    await expect(root.locator(".pf-v6-c-menu-toggle__text")).toHaveText("MB");
  });

  test("popover help opens from the attached button", async ({ page }) => {
    const root = page.locator("#ig-popover");
    await expect(root.locator(".pf-v6-c-popover")).toBeHidden();
    await root.locator("button").last().click();
    await expect(root.locator(".pf-v6-c-popover")).toBeVisible();
  });

  test("multiple siblings render as separate groups", async ({ page }) => {
    await expect(page.locator("#ig-sib-1")).toHaveClass(/pf-v6-c-input-group/);
    await expect(page.locator("#ig-sib-2 .pf-v6-c-input-group__item.pf-m-disabled")).toBeAttached();
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/input-group/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/input-group/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-input-group").first()).toBeAttached();
      });
    }
  });
});
