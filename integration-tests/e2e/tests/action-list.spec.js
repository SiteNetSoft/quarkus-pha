import { test, expect } from "@playwright/test";

test.describe("Action list", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/action-list");
  });

  test("page loads with all 7 variant sections", async ({ page }) => {
    await expect(page.locator("#single-group-heading")).toBeVisible();
    await expect(page.locator("#with-icons-heading")).toBeVisible();
    await expect(page.locator("#group-icons-heading")).toBeVisible();
    await expect(page.locator("#multiple-groups-heading")).toBeVisible();
    await expect(page.locator("#cancel-form-heading")).toBeVisible();
    await expect(page.locator("#cancel-wizard-heading")).toBeVisible();
    await expect(page.locator("#vertical-heading")).toBeVisible();
  });

  test.describe("Single group", () => {
    test("renders primary and secondary buttons", async ({ page }) => {
      const actionList = page.locator("#action-list-single");
      await expect(actionList.locator(".pf-v6-c-button.pf-m-primary")).toHaveCount(1);
      await expect(actionList.locator(".pf-v6-c-button.pf-m-secondary")).toHaveCount(1);
    });
  });

  test.describe("Icons variant", () => {
    test("root has pf-m-icons class", async ({ page }) => {
      const actionList = page.locator("#action-list-icons");
      await expect(actionList).toHaveClass(/pf-m-icons/);
    });

    test("icon-only buttons have aria-label attributes", async ({ page }) => {
      const actionList = page.locator("#action-list-icons");
      const buttons = actionList.locator(".pf-v6-c-button");
      const count = await buttons.count();
      for (let i = 0; i < count; i++) {
        await expect(buttons.nth(i)).toHaveAttribute("aria-label");
      }
    });
  });

  test.describe("Group icons", () => {
    test("pf-m-icons on group elements, not on root", async ({ page }) => {
      const actionList = page.locator("#action-list-group-icons");
      await expect(actionList).not.toHaveClass(/pf-m-icons/);
      const iconGroups = actionList.locator(".pf-v6-c-action-list__group.pf-m-icons");
      await expect(iconGroups).toHaveCount(1);
    });
  });

  test.describe("Multiple groups", () => {
    test("renders 2 group elements", async ({ page }) => {
      const actionList = page.locator("#action-list-multi");
      const groups = actionList.locator(".pf-v6-c-action-list__group");
      await expect(groups).toHaveCount(2);
    });
  });

  test.describe("Cancel button (forms/modals)", () => {
    test("cancel button uses pf-m-link class", async ({ page }) => {
      const actionList = page.locator("#action-list-cancel-form");
      await expect(actionList.locator(".pf-v6-c-button.pf-m-link")).toHaveCount(1);
    });
  });

  test.describe("Vertical variant", () => {
    test("root has pf-m-vertical class", async ({ page }) => {
      const actionList = page.locator("#action-list-vertical");
      await expect(actionList).toHaveClass(/pf-m-vertical/);
    });
  });
});
