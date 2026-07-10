import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "with-clear",
  "no-match",
  "result-count",
  "navigable-options",
  "autocomplete",
  "autocomplete-hint",
  "with-submit",
  "expandable",
  "advanced",
  "advanced-expanded",
];

test.describe("Search Input", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/search-input");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic and clear behave", async ({ page }) => {
    await expect(page.locator("#si-basic")).toHaveClass(/pf-v6-c-text-input-group/);
    const clear = page.locator("#si-clear .pf-v6-c-text-input-group__utilities button");
    await expect(clear).toBeVisible();
    await clear.click();
    await expect(page.locator("#si-clear input")).toHaveValue("");
  });

  test("result count badge shows while a value is present", async ({ page }) => {
    await expect(page.locator("#si-result-count .pf-v6-c-badge")).toHaveText("3");
    await page.locator("#si-result-count button[aria-label='Clear search']").click();
    await expect(page.locator("#si-result-count .pf-v6-c-badge")).toBeHidden();
  });

  test("navigable options step through matches", async ({ page }) => {
    const root = page.locator("#si-navigable");
    const prev = root.locator("button[aria-label='Previous match']");
    const next = root.locator("button[aria-label='Next match']");
    await expect(prev).toBeDisabled();
    await next.click();
    await next.click();
    await expect(root.locator(".pf-v6-c-badge")).toContainText("3 / 3");
    await expect(next).toBeDisabled();
    await expect(prev).toBeEnabled();
  });

  test("submit button reports the query", async ({ page }) => {
    await page.locator("#si-submit-input").fill("quarkus");
    await page.locator("#si-submit button[aria-label='Search']").click();
    await expect(page.locator("#si-submit p strong")).toHaveText("quarkus");
  });

  test("expandable opens into a focused input and closes again", async ({ page }) => {
    const root = page.locator("#si-expandable");
    await expect(root.locator("input")).toBeHidden();
    await root.locator("button[aria-label='Open search']").click();
    await expect(root.locator("input")).toBeVisible();
    await expect(root.locator("input")).toBeFocused();
    await root.locator("button[aria-label='Close search']").click();
    await expect(root.locator("input")).toBeHidden();
  });

  test("advanced form composes the query", async ({ page }) => {
    const root = page.locator("#si-advanced");
    await root.locator("button[aria-label='Advanced search']").click();
    await expect(root.locator(".pf-v6-c-panel")).toBeVisible();
    await page.locator("#si-advanced-username").fill("ned");
    await root.locator("button[type='submit']").click();
    await expect(root.locator("#si-advanced-input")).toHaveValue("username:ned ");
    await expect(root.locator(".pf-v6-c-panel")).toBeHidden();
  });

  test("no-match shows only the clear utility", async ({ page }) => {
    const root = page.locator("#si-no-match");
    await expect(root.locator(".pf-v6-c-badge")).toHaveCount(0);
    await root.locator(".pf-v6-c-text-input-group__utilities button").click();
    await expect(root.locator("input")).toHaveValue("");
  });

  test("autocomplete menu filters and fills the input", async ({ page }) => {
    const root = page.locator("#si-autocomplete");
    const items = root.locator(".pf-v6-c-menu__list-item");
    await expect(items).toHaveCount(3);
    await root.locator("input:not(.pf-m-hint)").fill("apples");
    await expect(items).toHaveCount(1);
    await items.first().locator("button").click();
    await expect(root.locator("input:not(.pf-m-hint)")).toHaveValue("appleseed");
    await expect(root.locator(".pf-v6-c-menu")).toBeHidden();
  });

  test("autocomplete hint ghost previews and Tab accepts it", async ({ page }) => {
    const root = page.locator("#si-autocomplete-hint");
    await expect(root.locator("input.pf-m-hint")).toHaveValue("appleseed");
    await root.locator("input:not(.pf-m-hint)").press("Tab");
    await expect(root.locator("input:not(.pf-m-hint)")).toHaveValue("appleseed");
  });

  test("advanced expanded starts open and still toggles", async ({ page }) => {
    const root = page.locator("#si-advanced-expanded");
    await expect(root.locator(".pf-v6-c-panel")).toBeVisible();
    await root.locator("button[aria-label='Advanced search']").click();
    await expect(root.locator(".pf-v6-c-panel")).toBeHidden();
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/search-input/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/search-input/${example}`);
        expect(res.status()).toBe(200);
      });
    }
  });
});
