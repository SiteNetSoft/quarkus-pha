// The JSON view-model contract page (/json-models): docs sections + the live
// try-it panel that POSTs the payload to /api/pha/render and swaps in the
// server-rendered HTML.
import { test, expect } from "@playwright/test";

test.describe("JSON view models page", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/json-models");
  });

  test("page loads with all sections in the ToC", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("JSON view models");
    for (const id of ["quick-start", "shape-rules", "template-uris", "try-it"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
    await expect(page.locator('.ws-toc a[href="#try-it"]')).toBeAttached();
  });

  test("try-it renders the payload server-side into the preview", async ({ page }) => {
    const panel = page.locator("#json-try-it");
    await panel.getByRole("button", { name: "Render on the server" }).click();
    const menu = panel.locator(".pf-v6-c-menu");
    await expect(menu).toBeVisible();
    await expect(menu).toHaveAttribute("id", "mn-try");
    await expect(menu.locator(".pf-v6-c-menu__list-item.pf-m-danger")).toContainText("Delete");
    await expect(menu.locator(".pf-v6-c-divider")).toBeAttached();
  });

  test("a payload violating the model invariants shows the server error", async ({ page }) => {
    const panel = page.locator("#json-try-it");
    await panel.locator("textarea").fill('{"id": "mn-empty"}');
    await panel.getByRole("button", { name: "Render on the server" }).click();
    await expect(panel.locator('[role="alert"]')).toBeVisible();
    await expect(panel.locator(".pf-v6-c-menu")).toHaveCount(0);
  });

  test("the sidebar nav and search reach the page", async ({ page }) => {
    await expect(page.locator('#showcase-nav a[href="/json-models"]')).toBeVisible();
    await page.locator('.pha-global-search button[aria-label="Search the showcase"]').click();
    const menu = page.locator("#pha-global-search-menu");
    await menu.locator('input[type="search"], .pf-v6-c-menu__search input').fill("JSON");
    await expect(menu.locator('.pf-v6-c-menu__list-item:visible a[href="/json-models"]')).toBeVisible();
  });
});
