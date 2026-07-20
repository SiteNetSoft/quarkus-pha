import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "panel-end",
  "panel-start",
  "panel-bottom",
  "basic-inline",
  "inline-panel-end",
  "inline-panel-start",
  "stacked-content-body",
  "modified-content-padding",
  "modified-panel-padding",
  "additional-section",
  "static",
  "breakpoint",
  "resizable-end",
  "resizable-start",
  "resizable-bottom",
  "resizable-inline",
  "secondary-background",
  "focus-trap",
  "pill",
  "pill-inline",
];

// slug -> [container id, expected modifier classes on the drawer root]
const MODIFIERS = {
  "panel-start": ["dr-panel-start", [/pf-m-panel-left/]],
  "panel-bottom": ["dr-panel-bottom", [/pf-m-panel-bottom/]],
  "basic-inline": ["dr-basic-inline", [/pf-m-inline/]],
  "inline-panel-end": ["dr-inline-panel-end", [/pf-m-inline/]],
  "inline-panel-start": ["dr-inline-panel-start", [/pf-m-inline/, /pf-m-panel-left/]],
  pill: ["dr-pill", [/pf-m-pill/]],
  "pill-inline": ["dr-pill-inline", [/pf-m-pill/, /pf-m-inline/]],
};

test.describe("Drawer", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/drawer");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of ["examples", ...EXAMPLES, "documentation", "props-drawer", "usage"]) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("every example card renders a drawer root", async ({ page }) => {
    for (const slug of EXAMPLES) {
      const card = page.locator(`[data-rendered-href="/components/drawer/${slug}"]`);
      await expect(card.locator(".pf-v6-c-drawer").first()).toBeAttached();
    }
  });

  test("position and display modifiers are applied", async ({ page }) => {
    for (const [id, patterns] of Object.values(MODIFIERS)) {
      for (const pattern of patterns) {
        await expect(page.locator(`#${id}`)).toHaveClass(pattern);
      }
    }
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-drawer class", async ({ page }) => {
      await expect(page.locator("#dr-basic")).toHaveClass(/pf-v6-c-drawer/);
    });

    test("panel is hidden by default", async ({ page }) => {
      await expect(page.locator("#dr-basic")).not.toHaveClass(/pf-m-expanded/);
      await expect(page.locator("#dr-basic .pf-v6-c-drawer__panel")).toBeHidden();
    });

    test("clicking the toggle opens the panel", async ({ page }) => {
      await page.locator("#dr-basic button", { hasText: "Open drawer" }).first().click();
      await expect(page.locator("#dr-basic")).toHaveClass(/pf-m-expanded/);
      await expect(page.locator("#dr-basic .pf-v6-c-drawer__panel")).toBeVisible();
    });

    test("close button collapses the panel again", async ({ page }) => {
      await page.locator("#dr-basic button", { hasText: "Open drawer" }).first().click();
      await expect(page.locator("#dr-basic .pf-v6-c-drawer__panel")).toBeVisible();
      await page.locator("#dr-basic button[aria-label='Close drawer panel']").click();
      await expect(page.locator("#dr-basic")).not.toHaveClass(/pf-m-expanded/);
      await expect(page.locator("#dr-basic .pf-v6-c-drawer__panel")).toBeHidden();
    });
  });

  test.describe("Panel at end", () => {
    test("focus moves to the panel header on expand", async ({ page }) => {
      await page.locator("#dr-panel-end button", { hasText: "Toggle drawer" }).click();
      await expect(page.locator("#dr-panel-end .pf-v6-c-drawer__panel")).toBeVisible();
      await expect(page.locator("#dr-panel-end .pf-v6-c-drawer__head span[x-ref='focus']")).toBeFocused();
    });
  });

  test.describe("Static", () => {
    test("has pf-m-static and pf-m-expanded classes", async ({ page }) => {
      const dr = page.locator("#dr-static");
      await expect(dr).toHaveClass(/pf-m-static/);
      await expect(dr).toHaveClass(/pf-m-expanded/);
    });

    test("panel is visible without any toggle", async ({ page }) => {
      await expect(page.locator("#dr-static .pf-v6-c-drawer__panel")).toBeVisible();
    });
  });

  test.describe("Stacked content body elements", () => {
    test("content stacks three bodies, panel mixes padded and unpadded bodies", async ({ page }) => {
      await expect(page.locator("#dr-stacked-content-body .pf-v6-c-drawer__content .pf-v6-c-drawer__body")).toHaveCount(
        3,
      );
      await expect(
        page.locator("#dr-stacked-content-body .pf-v6-c-drawer__content .pf-v6-c-drawer__body.pf-m-padding"),
      ).toHaveCount(1);
      await expect(
        page.locator("#dr-stacked-content-body .pf-v6-c-drawer__panel .pf-v6-c-drawer__body.pf-m-no-padding"),
      ).toHaveCount(1);
    });
  });

  test.describe("Additional section", () => {
    test("drawer__section sits outside drawer__main", async ({ page }) => {
      const section = page.locator("#dr-additional-section > .pf-v6-c-drawer__section");
      await expect(section).toBeVisible();
    });
  });

  test.describe("Breakpoint", () => {
    test("panel carries pf-m-width-33", async ({ page }) => {
      await expect(page.locator("#dr-breakpoint .pf-v6-c-drawer__panel")).toHaveClass(/pf-m-width-33/);
    });
  });

  test.describe("Resizable at end", () => {
    test("panel is resizable with a vertical splitter", async ({ page }) => {
      await page.locator("#dr-resizable-end button", { hasText: "Toggle drawer" }).click();
      const panel = page.locator("#dr-resizable-end .pf-v6-c-drawer__panel");
      await expect(panel).toHaveClass(/pf-m-resizable/);
      const splitter = page.locator("#dr-resizable-end .pf-v6-c-drawer__splitter");
      await expect(splitter).toHaveClass(/pf-m-vertical/);
      await expect(splitter).toHaveAttribute("role", "separator");
      await expect(splitter).toHaveAttribute("aria-orientation", "vertical");
    });

    test("default size is applied as a flex-basis custom property", async ({ page }) => {
      await page.locator("#dr-resizable-end button", { hasText: "Toggle drawer" }).click();
      const panel = page.locator("#dr-resizable-end .pf-v6-c-drawer__panel");
      await expect(panel).toHaveAttribute("style", /--pf-v6-c-drawer__panel--md--FlexBasis:\s*500px/);
    });

    test("dragging the splitter changes the panel size", async ({ page }) => {
      await page.locator("#dr-resizable-end button", { hasText: "Toggle drawer" }).click();
      const splitter = page.locator("#dr-resizable-end .pf-v6-c-drawer__splitter");
      await expect(splitter).toBeVisible();
      const box = await splitter.boundingBox();
      const x = box.x + box.width / 2;
      const y = box.y + box.height / 2;
      await page.mouse.move(x, y);
      await page.mouse.down();
      await page.mouse.move(x + 120, y, { steps: 5 });
      await page.mouse.up();
      const panel = page.locator("#dr-resizable-end .pf-v6-c-drawer__panel");
      const style = await panel.getAttribute("style");
      const match = style.match(/--pf-v6-c-drawer__panel--md--FlexBasis:\s*(\d+)px/);
      expect(match).not.toBeNull();
      expect(parseInt(match[1], 10)).toBeLessThan(500);
    });

    test("arrow keys resize when the splitter has focus", async ({ page }) => {
      await page.locator("#dr-resizable-end button", { hasText: "Toggle drawer" }).click();
      const splitter = page.locator("#dr-resizable-end .pf-v6-c-drawer__splitter");
      await expect(splitter).toBeVisible();
      await splitter.press("ArrowLeft");
      const panel = page.locator("#dr-resizable-end .pf-v6-c-drawer__panel");
      await expect(panel).toHaveAttribute("style", /--pf-v6-c-drawer__panel--md--FlexBasis:\s*505px/);
    });
  });

  test.describe("Resizable on bottom", () => {
    test("splitter is horizontal", async ({ page }) => {
      await page.locator("#dr-resizable-bottom button", { hasText: "Toggle drawer" }).click();
      const splitter = page.locator("#dr-resizable-bottom .pf-v6-c-drawer__splitter");
      await expect(splitter).not.toHaveClass(/pf-m-vertical/);
      await expect(splitter).toHaveAttribute("aria-orientation", "horizontal");
    });
  });

  test.describe("Secondary background", () => {
    test("panel starts secondary; checkboxes toggle content and section", async ({ page }) => {
      const panel = page.locator("#dr-secondary-background .pf-v6-c-drawer__panel");
      await expect(panel).toHaveClass(/pf-m-secondary/);
      const content = page.locator("#dr-secondary-background .pf-v6-c-drawer__content");
      await expect(content).not.toHaveClass(/pf-m-secondary/);
      await page.locator("#dr-secondary-content-check").check();
      await expect(content).toHaveClass(/pf-m-secondary/);
      const section = page.locator("#dr-secondary-background .pf-v6-c-drawer__section");
      await page.locator("#dr-secondary-section-check").check();
      await expect(section).toHaveClass(/pf-m-secondary/);
    });
  });

  test.describe("With focus trap", () => {
    test("Tab cycles within the open panel", async ({ page }) => {
      await page.locator("#dr-focus-trap button", { hasText: "Toggle drawer" }).click();
      const panel = page.locator("#dr-focus-trap .pf-v6-c-drawer__panel");
      await expect(panel).toBeVisible();
      await expect(panel).toHaveAttribute("role", "dialog");
      // Tab several times — focus must stay inside the panel.
      for (let i = 0; i < 4; i++) {
        await page.keyboard.press("Tab");
        const inside = await page.evaluate(() => {
          const p = document.querySelector("#dr-focus-trap .pf-v6-c-drawer__panel");
          return p.contains(document.activeElement);
        });
        expect(inside).toBe(true);
      }
    });

    test("Escape closes the panel", async ({ page }) => {
      await page.locator("#dr-focus-trap button", { hasText: "Toggle drawer" }).click();
      await expect(page.locator("#dr-focus-trap .pf-v6-c-drawer__panel")).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(page.locator("#dr-focus-trap .pf-v6-c-drawer__panel")).toBeHidden();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/drawer/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/drawer/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-drawer").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab; scope-sharing compositions do not", async ({ page }) => {
      await page.goto("/components/drawer");
      for (const ex of ["basic", "resizable-end", "stacked-content-body", "static"]) {
        const card = page.locator(`[data-rendered-href="/components/drawer/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of ["secondary-background", "focus-trap"]) {
        const card = page.locator(`[data-rendered-href="/components/drawer/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/drawer/source-java/resizable-end");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".resizable(500, 150)");
    });

    test("generated toggle opens the panel and the head close button closes it", async ({ page }) => {
      await page.goto("/components/drawer/basic");
      const panel = page.locator("#dr-basic .pf-v6-c-drawer__panel");
      await expect(panel).toBeHidden();
      const toggle = page.locator("#dr-basic .pf-v6-c-drawer__content button.pf-m-primary");
      await expect(toggle).toHaveText("Open drawer");
      await toggle.click();
      await expect(panel).toBeVisible();
      await expect(toggle).toHaveText("Close drawer");
      await panel.locator('button[aria-label="Close drawer panel"]').click();
      await expect(panel).toBeHidden();
    });
  });
});
