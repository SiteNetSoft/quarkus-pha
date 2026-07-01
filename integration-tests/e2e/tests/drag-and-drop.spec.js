import { test, expect } from "@playwright/test";

test.describe("Drag and Drop", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/drag-and-drop");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1#ws-page-title")).toHaveText("Drag and drop");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#examples")).toBeVisible();
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#multiple-zones")).toBeVisible();
    await expect(page.locator("#data-list")).toBeVisible();
    await expect(page.locator("#dual-list")).toBeVisible();
    await expect(page.locator("#documentation")).toBeVisible();
    await expect(page.locator("#props-drag-and-drop")).toBeVisible();
    await expect(page.locator("#usage")).toBeVisible();
  });

  test("basic wrapper exists with 4 draggable items + handles", async ({ page }) => {
    await expect(page.locator("#dd-basic")).toBeVisible();
    const items = page.locator("#dd-basic .pf-v6-c-draggable");
    await expect(items).toHaveCount(4);
    // pointer-draggable rows carry data-dd-item (native HTML5 draggable was removed)
    await expect(page.locator("#dd-basic [data-dd-item]")).toHaveCount(4);
    await expect(page.locator("#dd-basic [data-dd-handle]")).toHaveCount(4);
  });

  test("basic: keyboard ArrowDown reorders the focused item", async ({ page }) => {
    const items = page.locator("#dd-basic .pf-v6-c-draggable");
    await expect(items.first()).toContainText("Plan release");

    await page.locator("#dd-basic [data-dd-handle]").first().focus();
    await page.keyboard.press("ArrowDown");

    // "Plan release" should now be in the second slot, "Cut branch" first.
    await expect(items.nth(0)).toContainText("Cut branch");
    await expect(items.nth(1)).toContainText("Plan release");
  });

  test("basic: pointer drag moves the first item to the bottom", async ({ page }) => {
    const items = page.locator("#dd-basic .pf-v6-c-draggable");
    await expect(items.nth(0)).toContainText("Plan release");

    const grip = await page.locator("#dd-basic .pha-dd-grip").first().boundingBox();
    const last = await items.nth(3).boundingBox();
    // grab the GRIP (drag only starts from the grip handle) and drag it past the last row's midpoint
    await page.mouse.move(grip.x + grip.width / 2, grip.y + grip.height / 2);
    await page.mouse.down();
    await page.mouse.move(grip.x + grip.width / 2, grip.y + grip.height / 2 + 6, { steps: 3 });
    await page.mouse.move(last.x + 20, last.y + last.height * 0.75, { steps: 12 });
    await page.mouse.up();

    await expect(items.nth(0)).toContainText("Cut branch");
    await expect(items.nth(3)).toContainText("Plan release");
  });

  test("multiple zones: two lists with the expected item counts", async ({ page }) => {
    await expect(page.locator("#dd-multiple-zones")).toBeVisible();
    await expect(page.locator('#dd-multiple-zones .pf-v6-c-draggable[data-zone="a"]')).toHaveCount(3);
    await expect(page.locator('#dd-multiple-zones .pf-v6-c-draggable[data-zone="b"]')).toHaveCount(2);
  });

  test("data list: reorder persists via HTMX and updates the status", async ({ page }) => {
    await expect(page.locator("#dd-data-list")).toBeVisible();
    const status = page.locator("#dd-persist-status");
    await expect(status).toHaveText("Primary nav, Masthead, Sidebar, Footer");

    await page.locator("#dd-data-list [data-dd-handle]").first().focus();
    await page.keyboard.press("ArrowDown");

    // The server echoes the saved order back into the status node.
    await expect(status).toHaveText("Masthead, Primary nav, Sidebar, Footer");
  });

  test("dual list: move-right button moves the selected option across panes", async ({ page }) => {
    await expect(page.locator("#dd-dual-list")).toBeVisible();
    const available = page.locator("#dd-dual-list .pf-m-available .pf-v6-c-dual-list-selector__list-item");
    const chosen = page.locator("#dd-dual-list .pf-m-chosen .pf-v6-c-dual-list-selector__list-item");
    await expect(available).toHaveCount(5);
    await expect(chosen).toHaveCount(0);

    await available.first().click(); // select
    await page.locator('#dd-dual-list button[aria-label="Move selected to chosen"]').click();

    await expect(available).toHaveCount(4);
    await expect(chosen).toHaveCount(1);
  });
});
