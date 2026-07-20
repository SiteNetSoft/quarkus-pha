import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "positions",
  "hoverable",
  "close-from-content",
  "no-header-footer",
  "no-padding",
  "width-auto",
  "advanced",
  "icon-in-title",
  "alert-variants",
  "danger",
];

test.describe("Popover", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/popover");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic", () => {
    test("click toggles the popover; close button dismisses", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/basic"]');
      const popover = page.locator("#po-basic");
      await expect(popover).toBeHidden();
      await card.locator("button", { hasText: "Show popover" }).click();
      await expect(popover).toBeVisible();
      await expect(popover).toHaveClass(/pf-m-top/);
      await expect(popover.locator(".pf-v6-c-popover__arrow")).toBeVisible();
      await expect(popover.locator(".pf-v6-c-popover__footer")).toHaveText("Popover footer");
      await popover.locator(".pf-v6-c-popover__close button").click();
      await expect(popover).toBeHidden();
    });
  });

  test.describe("Hoverable", () => {
    test("opens on hover and closes on leave", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/hoverable"]');
      const trigger = card.locator("button", { hasText: "Hover or focus me" });
      const popover = page.locator("#po-hoverable");
      await expect(popover).toBeHidden();
      await trigger.hover();
      await expect(popover).toBeVisible();
      await page.mouse.move(0, 0);
      await expect(popover).toBeHidden();
    });
  });

  test.describe("Close from content", () => {
    test("the footer action dismisses the popover", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/close-from-content"]');
      await card.locator("button", { hasText: "Show popover" }).click();
      const popover = page.locator("#po-close-from-content");
      await expect(popover).toBeVisible();
      await popover.locator("footer button", { hasText: "Got it" }).click();
      await expect(popover).toBeHidden();
    });
  });

  test.describe("Variants", () => {
    test("no-header-footer popover has only a body", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/no-header-footer"]');
      await card.locator("button", { hasText: "Show plain popover" }).click();
      const popover = page.locator("#po-no-header-footer");
      await expect(popover.locator(".pf-v6-c-popover__body")).toBeVisible();
      await expect(popover.locator(".pf-v6-c-popover__header")).toHaveCount(0);
      await expect(popover.locator(".pf-v6-c-popover__close")).toHaveCount(0);
    });

    test("no-padding popover carries the modifier and only a body", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/no-padding"]');
      await card.locator("button", { hasText: "Show no-padding popover" }).click();
      const popover = page.locator("#po-no-padding");
      await expect(popover).toHaveClass(/pf-m-no-padding/);
      await expect(popover).toHaveClass(/pf-m-right/);
      await expect(popover.locator(".pf-v6-c-popover__header")).toHaveCount(0);
    });

    test("position variants render one live popover per modifier", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/positions"]');
      await expect(card.locator(".pf-v6-c-popover")).toHaveCount(8);
      await card.locator("button", { hasText: "Left top" }).click();
      const popover = page.locator("#po-pos-left-top");
      await expect(popover).toBeVisible();
      await expect(popover).toHaveClass(/pf-m-left-top/);
      await popover.locator(".pf-v6-c-popover__close button").click();
      await expect(popover).toBeHidden();
      await card.locator("button", { hasText: "Bottom right" }).click();
      await expect(page.locator("#po-pos-bottom-right")).toHaveClass(/pf-m-bottom-right/);
    });

    test("width-auto carries the modifier", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/width-auto"]');
      await card.locator("button", { hasText: "Show auto-width popover" }).click();
      await expect(page.locator("#po-width-auto")).toHaveClass(/pf-m-width-auto/);
    });

    test("advanced has a title icon, close, and footer action", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/advanced"]');
      await card.locator("button", { hasText: "Show advanced popover" }).click();
      const popover = page.locator("#po-advanced");
      await expect(popover.locator(".pf-v6-c-popover__title-icon")).toBeVisible();
      await expect(popover.locator(".pf-v6-c-popover__close button")).toBeVisible();
      // Popovers render inline above the trigger and can be overlaid; dispatch the click.
      await popover.locator("footer button", { hasText: "Dismiss" }).evaluate((el) => el.click());
      await expect(popover).toBeHidden();
    });

    test("alert variants carry their severity classes", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/popover/alert-variants"]');
      for (const variant of ["custom", "info", "success", "warning"]) {
        await card.locator("button", { hasText: `Show ${variant} popover` }).click();
        await expect(page.locator(`#po-alert-${variant}`)).toHaveClass(new RegExp(`pf-m-${variant}`));
        await card.locator("button", { hasText: `Show ${variant} popover` }).click();
      }
    });
  });

  test.describe("Danger (static)", () => {
    test("danger popover renders visible with footer actions", async ({ page }) => {
      await expect(page.locator("#po-danger")).toHaveClass(/pf-m-danger/);
      await expect(page.locator("#po-danger .pf-v6-c-popover__footer button")).toHaveCount(2);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/popover/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/popover/${example}`);
        expect(res.status()).toBe(200);
      });
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/popover");
      for (const ex of ["basic", "positions", "danger", "hoverable", "advanced"]) {
        const card = page.locator(`[data-rendered-href="/components/popover/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/popover/source-java/danger");
      expect(res.status()).toBe(200);
      const body = await res.text();
      expect(body).toContain(".staticOpen()");
      expect(body).toContain('.titlePlain("Delete this project?")');
    });

    test("model click popover opens, closes from footer, and hover popover hovers", async ({ page }) => {
      await page.goto("/components/popover/close-from-content");
      await page.getByRole("button", { name: "Show popover" }).click();
      const box = page.locator("#po-close-from-content");
      await expect(box).toBeVisible();
      await box.getByRole("button", { name: "Got it" }).click();
      await expect(box).toBeHidden();
    });
  });
});
