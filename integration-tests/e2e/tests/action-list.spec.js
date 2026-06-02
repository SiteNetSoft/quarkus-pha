import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "single-group",
  "with-kebab",
  "icons-list",
  "icons-group",
  "multiple-groups",
  "cancel-form",
  "cancel-wizard",
  "vertical",
];

test.describe("Action list", () => {
  test("TOC anchors render", async ({ page }) => {
    await page.goto("/components/action-list");
    const anchors = ["examples", ...EXAMPLES, "documentation", "props-action-list", "usage"];
    for (const id of anchors) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("single group composes primary + secondary via the button component", async ({ page }) => {
    await page.goto("/components/action-list/single-group");
    const list = page.locator(".pf-v6-c-action-list");
    await expect(list.locator(".pf-v6-c-button.pf-m-primary .pf-v6-c-button__text")).toHaveText("Next");
    await expect(list.locator(".pf-v6-c-button.pf-m-secondary .pf-v6-c-button__text")).toHaveText("Back");
  });

  test("with kebab renders Next + Back + a kebab menu toggle", async ({ page }) => {
    await page.goto("/components/action-list/with-kebab");
    const list = page.locator(".pf-v6-c-action-list");
    await expect(list.locator(".pf-v6-c-button.pf-m-primary")).toHaveCount(1);
    await expect(list.locator(".pf-v6-c-button.pf-m-secondary")).toHaveCount(1);
    await expect(list.locator(".pf-v6-c-menu-toggle")).toHaveCount(1);
  });

  test("icons-list: root carries pf-m-icons and every icon button has an aria-label", async ({ page }) => {
    await page.goto("/components/action-list/icons-list");
    const list = page.locator(".pf-v6-c-action-list");
    await expect(list).toHaveClass(/pf-m-icons/);
    const buttons = list.locator(".pf-v6-c-button");
    const count = await buttons.count();
    expect(count).toBeGreaterThan(0);
    for (let i = 0; i < count; i++) {
      await expect(buttons.nth(i)).toHaveAttribute("aria-label");
    }
  });

  test("icons-group: pf-m-icons sits on the group, not the root", async ({ page }) => {
    await page.goto("/components/action-list/icons-group");
    const list = page.locator(".pf-v6-c-action-list");
    await expect(list).not.toHaveClass(/pf-m-icons/);
    await expect(list.locator(".pf-v6-c-action-list__group.pf-m-icons")).toHaveCount(1);
  });

  test("multiple-groups renders 2 groups", async ({ page }) => {
    await page.goto("/components/action-list/multiple-groups");
    await expect(page.locator(".pf-v6-c-action-list .pf-v6-c-action-list__group")).toHaveCount(2);
  });

  test("cancel-form uses a link-styled cancel", async ({ page }) => {
    await page.goto("/components/action-list/cancel-form");
    await expect(page.locator(".pf-v6-c-action-list .pf-v6-c-button.pf-m-link")).toHaveText("Cancel");
  });

  test("cancel-wizard puts cancel in its own group", async ({ page }) => {
    await page.goto("/components/action-list/cancel-wizard");
    await expect(page.locator(".pf-v6-c-action-list .pf-v6-c-action-list__group")).toHaveCount(2);
  });

  test("vertical: root carries pf-m-vertical", async ({ page }) => {
    await page.goto("/components/action-list/vertical");
    await expect(page.locator(".pf-v6-c-action-list")).toHaveClass(/pf-m-vertical/);
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/action-list/${example} returns 200 with rendered list`, async ({ page }) => {
        const res = await page.goto(`/components/action-list/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-action-list")).toBeAttached();
      });
    }
  });

  test("source endpoint returns Qute source", async ({ request }) => {
    const res = await request.get("/components/action-list/source/single-group");
    expect(res.status()).toBe(200);
    const body = await res.text();
    expect(body).toContain("components/actions/action-list");
  });
});
