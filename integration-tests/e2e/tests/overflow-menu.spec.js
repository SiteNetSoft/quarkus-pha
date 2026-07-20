import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "vertical",
  "group-types",
  "multiple-groups",
  "persistent",
  "breakpoint-container-width",
  "breakpoint-container-height",
];

test.describe("Overflow Menu", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/overflow-menu");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic has content, control, and a button group", async ({ page }) => {
    await expect(page.locator("#om-basic .pf-v6-c-overflow-menu__content")).toBeVisible();
    await expect(page.locator("#om-basic .pf-v6-c-overflow-menu__control")).toBeVisible();
    await expect(page.locator("#om-basic .pf-v6-c-overflow-menu__group.pf-m-button-group")).toBeVisible();
  });

  test("vertical carries pf-m-vertical", async ({ page }) => {
    await expect(page.locator("#om-vertical")).toHaveClass(/pf-m-vertical/);
  });

  test("group types include an icon-button group", async ({ page }) => {
    await expect(page.locator("#om-group-types .pf-v6-c-overflow-menu__group.pf-m-icon-button-group")).toBeVisible();
  });

  test("multiple groups render three groups", async ({ page }) => {
    await expect(page.locator("#om-multiple-groups .pf-v6-c-overflow-menu__group")).toHaveCount(3);
  });

  test("persistent control opens a working menu", async ({ page }) => {
    const control = page.locator("#om-persistent .pf-v6-c-overflow-menu__control");
    await control.locator("> button").click();
    await expect(control.locator(".pf-v6-c-menu")).toBeVisible();
    await expect(control.locator(".pf-v6-c-menu__item")).toHaveCount(3);
  });

  test("container width breakpoint collapses the buttons into the kebab", async ({ page }) => {
    const card = page.locator('[data-rendered-href="/components/overflow-menu/breakpoint-container-width"]');
    const container = card.locator("div[x-init]").first();
    const content = page.locator("#om-breakpoint-width .pf-v6-c-overflow-menu__content");
    const control = page.locator("#om-breakpoint-width .pf-v6-c-overflow-menu__control");
    await expect(content).toBeVisible();
    await expect(control).toBeHidden();
    await container.evaluate((el) => {
      el.style.width = "240px";
    });
    await expect(control).toBeVisible();
    await expect(content).toBeHidden();
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/overflow-menu/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/overflow-menu/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-overflow-menu").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/overflow-menu");
      for (const ex of ["basic", "persistent", "breakpoint-container-width"]) {
        const card = page.locator(`[data-rendered-href="/components/overflow-menu/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/overflow-menu/source-java/persistent");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.persistentMenu("Edit", "Duplicate", "Archive")');
    });

    test("generated persistent kebab opens a working menu", async ({ page }) => {
      await page.goto("/components/overflow-menu/persistent");
      await page.locator('#om-persistent button[aria-label="More actions"]').click();
      const menu = page.locator("#om-persistent .pf-v6-c-menu");
      await expect(menu).toBeVisible();
      await menu.getByRole("menuitem", { name: "Duplicate" }).click();
      await expect(menu).toBeHidden();
    });
  });
});
