// Small-screen regression sweep. Everything else in the suite runs at
// Playwright's default 1280×720, so small-viewport layout failures were
// invisible until a phone user hit one (the masthead search menu opened
// 121px past the viewport's left edge — showcase-shell.spec.js pins that
// fix). Three layers here:
//
// 1. Viewport-fit sweep (phone + tablet): every showcase page must produce
//    no page-level horizontal overflow. Wide tables/code blocks scrolling
//    inside their own containers are fine — the document itself must not
//    grow a horizontal scrollbar. The shell masthead must sit fully inside
//    the viewport.
// 2. Overlay probes (phone): open representative dropdown-style panels and
//    assert they never land past the viewport's left edge — the unreachable
//    direction, since leftward overflow never produces a scrollbar. Right
//    overflow scrolls within the page main, so it stays reachable.
// 3. Shell probe (phone): the hamburger must open a usable, fully-visible
//    sidebar nav.
import { test, expect } from "@playwright/test";
import { ALL_PATHS } from "./showcase-paths.js";

const PHONE = { width: 360, height: 740 };
const TABLET = { width: 768, height: 1024 };

for (const vp of [PHONE, TABLET]) {
  test.describe(`viewport fit at ${vp.width}px`, () => {
    test.use({ viewport: vp });

    for (const path of ALL_PATHS) {
      test(`${path} has no page-level horizontal overflow`, async ({ page }) => {
        await page.goto(path);
        await page.waitForLoadState("networkidle").catch(() => {});

        const doc = await page.evaluate(() => ({
          scrollWidth: document.documentElement.scrollWidth,
          innerWidth: window.innerWidth,
        }));
        expect(
          doc.scrollWidth,
          `document scrollWidth ${doc.scrollWidth}px exceeds the ${doc.innerWidth}px viewport on ${path}`,
        ).toBeLessThanOrEqual(doc.innerWidth + 1);

        const masthead = page.locator(".pf-v6-c-masthead").first();
        if (await masthead.count()) {
          const box = await masthead.boundingBox();
          if (box) {
            expect(box.x, `masthead starts left of the viewport on ${path}`).toBeGreaterThanOrEqual(-1);
            expect(box.x + box.width, `masthead ends past the viewport on ${path}`).toBeLessThanOrEqual(vp.width + 1);
          }
        }
      });
    }
  });
}

// Representative dropdown-style overlays: pages whose first example opens an
// absolute-positioned panel. Opener/panel selectors are scoped to the example
// area so the masthead search/theme menus (covered by showcase-shell.spec.js)
// never shadow them.
const OVERLAY_PROBES = [
  { path: "/components/dropdown", panel: ".pf-v6-c-menu" },
  { path: "/components/select", panel: ".pf-v6-c-menu" },
  { path: "/components/options-menu", panel: ".pf-v6-c-menu" },
  { path: "/components/custom-menus", panel: ".pf-v6-c-menu" },
  // pagination is NOT probed: PF hides its per-page menu toggle at phone widths.
  // application-launcher pins the max-width:90vw clamp its menu already carries.
  { path: "/components/application-launcher", panel: ".pf-v6-c-menu" },
  {
    path: "/components/date-picker",
    opener: 'button[aria-label="Toggle date picker"]',
    panel: ".pf-v6-c-date-picker__calendar",
  },
];

test.describe("mobile overlay probes", () => {
  test.use({ viewport: PHONE });

  for (const probe of OVERLAY_PROBES) {
    test(`${probe.path} first example panel opens within reach`, async ({ page }) => {
      await page.goto(probe.path);
      const scope = page.locator(".ws-example-page-wrapper");
      const opener = scope.locator(probe.opener ?? ".pf-v6-c-menu-toggle:not([disabled])").first();
      await opener.scrollIntoViewIfNeeded();
      await opener.click();
      const panel = scope.locator(`${probe.panel}:visible`).first();
      await expect(panel).toBeVisible();
      const box = await panel.boundingBox();
      expect(box.x, `panel on ${probe.path} starts past the viewport's left edge (unreachable)`).toBeGreaterThanOrEqual(
        -1,
      );
      expect(box.width).toBeGreaterThan(0);
    });
  }
});

test.describe("mobile shell", () => {
  test.use({ viewport: PHONE });

  test("hamburger opens a usable, fully-visible sidebar nav", async ({ page }) => {
    await page.goto("/components/backdrop");
    const sidebar = page.locator(".pf-v6-c-page__sidebar");
    await expect(sidebar).toBeHidden();
    await page.locator('button[aria-label="Global navigation"]').click();
    await expect(sidebar).toBeVisible();
    const box = await sidebar.boundingBox();
    expect(box.x).toBeGreaterThanOrEqual(0);
    expect(box.x + box.width).toBeLessThanOrEqual(PHONE.width);
    const nav = page.locator("#showcase-nav");
    await nav.getByRole("button", { name: "All components" }).click();
    await expect(nav.getByRole("link", { name: "Overview", exact: true })).toBeVisible();
  });
});
