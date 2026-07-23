import { test, expect } from "@playwright/test";

// The about-modal backdrop teleports to <body> (Alpine x-teleport) so it can
// paint above the masthead — the PF page main-container is a stacking context
// that would otherwise trap it. Modal assertions are therefore scoped by the
// backdrop id (#<id>-backdrop), not by the example card that hosts the trigger.
test.describe("About modal", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/about-modal");
  });

  test("all three example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#without-product-name")).toBeVisible();
    await expect(page.locator("#complex-content")).toBeVisible();
  });

  test.describe("Basic variant", () => {
    const card = '[data-rendered-href="/components/about-modal/basic"]';
    const backdrop = "#basic-backdrop";

    test("modal hidden by default", async ({ page }) => {
      await expect(page.locator(backdrop)).toBeHidden();
    });

    test("opens via id-scoped event with product name title", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      await expect(page.locator(`${backdrop} .pf-v6-c-about-modal-box`)).toBeVisible();
      await expect(page.locator("#basic-title")).toHaveText("quarkus-pha");
    });

    test("PF structure: backdrop > bullseye > modal-box pf-m-lg > about-modal-box", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const box = page.locator(
        `${backdrop} > .pf-v6-l-bullseye > .pf-v6-c-modal-box.pf-m-lg > .pf-v6-c-about-modal-box`,
      );
      await expect(box).toBeVisible();
    });

    test("dialog aria attributes on the modal-box link to the basic title", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const dialog = page.locator(`${backdrop} .pf-v6-c-modal-box`);
      await expect(dialog).toHaveAttribute("role", "dialog");
      await expect(dialog).toHaveAttribute("aria-modal", "true");
      await expect(dialog).toHaveAttribute("aria-labelledby", "basic-title");
    });

    test("paints above the masthead (regression: stacking-context trap)", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      await expect(page.locator(`${backdrop} .pf-v6-c-about-modal-box`)).toBeVisible();
      // Hit-test the center of the masthead: with the modal open, the top
      // paint target there must belong to the teleported backdrop, not the
      // masthead. Before the x-teleport fix the masthead won and intercepted
      // clicks aimed at the modal's close button.
      const hitBackdrop = await page.evaluate((backdropSel) => {
        const masthead = document.querySelector(".pf-v6-c-masthead");
        if (!masthead) return "no-masthead";
        const r = masthead.getBoundingClientRect();
        const el = document.elementFromPoint(r.left + r.width / 2, r.top + r.height / 2);
        return el && el.closest(backdropSel) ? "backdrop" : (el?.className ?? "none");
      }, backdrop);
      expect(hitBackdrop).toBe("backdrop");
    });

    test("modal is not full-width (regression: missing modal-box wrapper)", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const dialog = page.locator(`${backdrop} .pf-v6-c-modal-box`);
      await expect(dialog).toBeVisible();
      const [box, viewport] = await Promise.all([dialog.boundingBox(), page.viewportSize()]);
      expect(box.width).toBeGreaterThan(300);
      expect(box.width).toBeLessThan(viewport.width - 80);
    });

    test("brand image, description-list body, trademark, strapline render", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      await expect(page.locator(`${backdrop} .pf-v6-c-about-modal-box__brand-image`)).toBeVisible();
      await expect(page.locator(`${backdrop} .pf-v6-c-about-modal-box__body`)).toContainText("1.0.0-SNAPSHOT");
      await expect(page.locator(`${backdrop} .pf-v6-c-about-modal-box__strapline`).last()).toContainText("Copyright");
    });

    test("Escape closes", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const modal = page.locator(`${backdrop} .pf-v6-c-about-modal-box`);
      await expect(modal).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(modal).toBeHidden();
    });

    test("close button closes with a real click", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const modal = page.locator(`${backdrop} .pf-v6-c-about-modal-box`);
      await expect(modal).toBeVisible();
      // A genuine mouse click — the pre-teleport masthead-interception
      // workaround (dispatching click via the DOM) is intentionally gone.
      await page.locator(`${backdrop} .pf-v6-c-about-modal-box__close button`).click();
      await expect(modal).toBeHidden();
    });

    test("clicking outside the dialog closes", async ({ page }) => {
      await page.locator(`${card} button`, { hasText: "About" }).first().click();
      const modal = page.locator(`${backdrop} .pf-v6-c-about-modal-box`);
      await expect(modal).toBeVisible();
      await page.locator(`${backdrop} .pf-v6-l-bullseye`).click({ position: { x: 5, y: 5 } });
      await expect(modal).toBeHidden();
    });
  });

  test.describe("Without product name variant", () => {
    const card = '[data-rendered-href="/components/about-modal/without-product-name"]';
    const backdrop = "#noname-backdrop";

    test("dialog uses aria-label instead of aria-labelledby", async ({ page }) => {
      await page.locator(`${card} button`).first().click();
      const dialog = page.locator(`${backdrop} .pf-v6-c-modal-box`);
      await expect(dialog).toHaveAttribute("aria-label", "About this product");
      await expect(dialog).not.toHaveAttribute("aria-labelledby", /.+/);
    });

    test("no h1 title element", async ({ page }) => {
      await page.locator(`${card} button`).first().click();
      await expect(page.locator("#noname-title")).toHaveCount(0);
    });
  });

  test.describe("Complex content variant", () => {
    const card = '[data-rendered-href="/components/about-modal/complex-content"]';
    const backdrop = "#complex-backdrop";

    test("default __content wrapper is skipped (only the example's own wrapper is present)", async ({ page }) => {
      await page.locator(`${card} button`).first().click();
      // noContentContainer=true → the runtime template does NOT emit a default __content wrapper;
      // the only __content div is the one the example fragment renders itself
      await expect(page.locator(`${backdrop} .pf-v6-c-about-modal-box__content`)).toHaveCount(1);
    });
  });

  test.describe("Multiple instances coexist", () => {
    test("opening basic does not open complex", async ({ page }) => {
      await page
        .locator('[data-rendered-href="/components/about-modal/basic"] button', { hasText: "About" })
        .first()
        .click();
      await expect(page.locator("#basic-backdrop .pf-v6-c-about-modal-box")).toBeVisible();
      await expect(page.locator("#complex-backdrop .pf-v6-c-about-modal-box")).toBeHidden();
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/about-modal/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/about-modal/complex-content"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/about-modal/complex-content");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/about-modal");
      for (const ex of ["basic", "complex-content", "without-product-name"]) {
        const card = page.locator(`[data-rendered-href="/components/about-modal/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/about-modal/source-java/without-product-name");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.ariaLabel("About this product")');
    });

    test("model trigger still opens and closes the dialog", async ({ page }) => {
      await page.goto("/components/about-modal/basic");
      await page.getByRole("button", { name: "About", exact: true }).click();
      const dialog = page.locator(".pf-v6-c-about-modal-box");
      await expect(dialog).toBeVisible();
      await page.keyboard.press("Escape");
      await expect(dialog).toBeHidden();
    });
  });
});
