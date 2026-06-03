import { test, expect } from "@playwright/test";

test.describe("Empty state", () => {
  const EXAMPLES = [
    "basic",
    "extra-small",
    "small",
    "large",
    "extra-large",
    "success",
    "spinner",
    "no-match",
    "no-icon",
    "with-actions",
  ];

  test("page loads with every example section heading", async ({ page }) => {
    await page.goto("/components/empty-state");
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeVisible();
    }
  });

  test("page anchors are present", async ({ page }) => {
    await page.goto("/components/empty-state");
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-empty-state")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic has h4 title, body, icon, and two action groups", async ({ page }) => {
    await page.goto("/components/empty-state/basic");
    await expect(page.locator("h4.pf-v6-c-empty-state__title-text")).toHaveText("Empty state");
    await expect(page.locator(".pf-v6-c-empty-state__body")).toBeVisible();
    await expect(page.locator(".pf-v6-c-empty-state__icon")).toBeVisible();
    // PF's default example: a primary action group + a row of link buttons
    await expect(page.locator(".pf-v6-c-empty-state__footer .pf-v6-c-empty-state__actions")).toHaveCount(2);
    await expect(page.locator(".pf-v6-c-empty-state__actions .pf-v6-c-button")).toHaveCount(7);
  });

  test("no-icon variant has no icon element", async ({ page }) => {
    await page.goto("/components/empty-state/no-icon");
    await expect(page.locator(".pf-v6-c-empty-state__icon")).toHaveCount(0);
  });

  for (const [example, modifier] of [
    ["extra-small", "pf-m-xs"],
    ["small", "pf-m-sm"],
    ["large", "pf-m-lg"],
    ["extra-large", "pf-m-xl"],
    ["success", "pf-m-success"],
  ]) {
    test(`${example} carries ${modifier} on the root`, async ({ page }) => {
      await page.goto(`/components/empty-state/${example}`);
      await expect(page.locator(".pf-v6-c-empty-state").first()).toHaveClass(new RegExp(modifier));
    });
  }

  test("spinner example renders a spinner in the icon area, no footer", async ({ page }) => {
    await page.goto("/components/empty-state/spinner");
    await expect(page.locator(".pf-v6-c-empty-state__icon .pf-v6-c-spinner")).toBeVisible();
    await expect(page.locator("h4.pf-v6-c-empty-state__title-text")).toHaveText("Loading");
    await expect(page.locator(".pf-v6-c-empty-state__footer")).toHaveCount(0);
  });

  test("no-match example has a search icon, headline, and clear-filters link", async ({ page }) => {
    await page.goto("/components/empty-state/no-match");
    await expect(page.locator(".pf-v6-c-empty-state__icon")).toBeVisible();
    await expect(page.locator("h4.pf-v6-c-empty-state__title-text")).toHaveText("No results found");
    await expect(page.locator(".pf-v6-c-empty-state__actions .pf-v6-c-button.pf-m-link")).toHaveText(
      "Clear all filters",
    );
  });

  test("extra-small uses small link buttons", async ({ page }) => {
    await page.goto("/components/empty-state/extra-small");
    await expect(page.locator(".pf-v6-c-empty-state__actions")).toHaveCount(1);
    await expect(page.locator(".pf-v6-c-empty-state__actions .pf-v6-c-button.pf-m-small")).toHaveCount(6);
  });

  test("all example standalone routes render", async ({ page }) => {
    for (const example of EXAMPLES) {
      const res = await page.goto(`/components/empty-state/${example}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pf-v6-c-empty-state").first()).toBeVisible();
    }
  });

  test("actions slot composes buttons via the button component", async ({ page }) => {
    await page.goto("/components/empty-state/with-actions");
    const actions = page.locator(".pf-v6-c-empty-state__actions");
    // every action is a real button component (carries the __text wrapper), not hand-rolled markup
    await expect(actions.locator(".pf-v6-c-button")).toHaveCount(4);
    await expect(actions.locator(".pf-v6-c-button .pf-v6-c-button__text")).toHaveCount(4);
    await expect(actions.locator(".pf-v6-c-button.pf-m-primary")).toHaveText("Create document");
    await expect(actions.locator(".pf-v6-c-button.pf-m-secondary")).toHaveText("Import existing");
    await expect(actions.locator(".pf-v6-c-button.pf-m-link")).toHaveText(["Browse templates", "Learn more"]);
  });
});
