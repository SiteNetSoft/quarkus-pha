import { test, expect } from "@playwright/test";

test.describe("Slider", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/slider");
  });

  test("page loads with all 3 example sections in ToC", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
    await expect(page.locator("#custom-range")).toBeVisible();
    await expect(page.locator("#disabled")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("basic slider has pf-v6-c-slider class", async ({ page }) => {
      await expect(page.locator("#sl-basic")).toHaveClass(/pf-v6-c-slider/);
    });

    test("basic slider has a thumb element", async ({ page }) => {
      await expect(page.locator("#sl-basic .pf-v6-c-slider__thumb")).toBeVisible();
    });
  });

  test.describe("Custom range", () => {
    test("custom-range slider has pf-v6-c-slider class", async ({ page }) => {
      await expect(page.locator("#sl-temp")).toHaveClass(/pf-v6-c-slider/);
    });

    test("custom-range thumb advertises the temperature bounds", async ({ page }) => {
      const thumb = page.locator("#sl-temp .pf-v6-c-slider__thumb");
      await expect(thumb).toBeVisible();
      await expect(thumb).toHaveAttribute("aria-valuemin", "-10");
      await expect(thumb).toHaveAttribute("aria-valuemax", "40");
    });
  });

  test.describe("Disabled", () => {
    test("disabled slider has pf-m-disabled class", async ({ page }) => {
      await expect(page.locator("#sl-disabled")).toHaveClass(/pf-m-disabled/);
    });

    test("disabled slider has a thumb element", async ({ page }) => {
      await expect(page.locator("#sl-disabled .pf-v6-c-slider__thumb")).toBeVisible();
    });
  });

  test.describe("Per-example code viewer", () => {
    test("Toggle Qute opens Monaco with the fragment source", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/slider/basic"]');
      const toggle = card.locator('button[aria-label*="Toggle Qute"]');
      await toggle.click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
    });

    test("Open-in-new-window link points to the standalone route", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/slider/disabled"]');
      const link = card.locator('a[aria-label*="Open"]');
      await expect(link).toHaveAttribute("href", "/components/slider/disabled");
      await expect(link).toHaveAttribute("target", "_blank");
    });
  });

  test.describe("Parity additions", () => {
    test("value input stays in sync with the slider", async ({ page }) => {
      const input = page.locator("#sl-value-input-wrap input[type='number']");
      await expect(input).toHaveValue("30");
      await input.fill("70");
      await expect(page.locator("#sl-value-input .pf-v6-c-slider__thumb")).toHaveAttribute("aria-valuenow", "70");
    });

    test("action buttons step the value", async ({ page }) => {
      const wrap = page.locator("#sl-actions-wrap");
      await wrap.locator("button[aria-label='Increase']").click();
      await expect(wrap.locator(".pf-v6-c-slider__thumb")).toHaveAttribute("aria-valuenow", "51");
      await wrap.locator("button[aria-label='Decrease']").click();
      await expect(wrap.locator(".pf-v6-c-slider__thumb")).toHaveAttribute("aria-valuenow", "50");
    });

    test("/components/slider/continuous returns 200", async ({ page }) => {
      const res = await page.goto("/components/slider/continuous");
      expect(res.status()).toBe(200);
    });

    test("/components/slider/value-input returns 200", async ({ page }) => {
      const res = await page.goto("/components/slider/value-input");
      expect(res.status()).toBe(200);
    });

    test("/components/slider/actions returns 200", async ({ page }) => {
      const res = await page.goto("/components/slider/actions");
      expect(res.status()).toBe(200);
    });
  });
  test.describe("Java source tab", () => {
    test("model-driven cards get a leading Java tab; composition examples do not", async ({ page }) => {
      await page.goto("/components/slider");
      for (const ex of ["basic", "custom-range", "disabled"]) {
        const card = page.locator(`[data-rendered-href="/components/slider/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of ["actions", "value-input"]) {
        const card = page.locator(`[data-rendered-href="/components/slider/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/slider/source-java/custom-range");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".min(-10).max(40)");
    });

    test("generated keyboard wiring still moves the thumb", async ({ page }) => {
      await page.goto("/components/slider/basic");
      const thumb = page.locator("#sl-basic .pf-v6-c-slider__thumb");
      await thumb.focus();
      await page.keyboard.press("ArrowRight");
      await expect(thumb).toHaveAttribute("aria-valuenow", "51");
      await page.keyboard.press("End");
      await expect(thumb).toHaveAttribute("aria-valuenow", "100");
    });
  });

  test("thumb is pointer-draggable (PF primary interaction)", async ({ page }) => {
    const slider = page.locator("#sl-basic");
    const thumb = slider.locator(".pf-v6-c-slider__thumb");
    const rail = slider.locator(".pf-v6-c-slider__rail");
    const railBox = await rail.boundingBox();
    const thumbBox = await thumb.boundingBox();
    await page.mouse.move(thumbBox.x + thumbBox.width / 2, thumbBox.y + thumbBox.height / 2);
    await page.mouse.down();
    await page.mouse.move(railBox.x + railBox.width * 0.9, thumbBox.y + thumbBox.height / 2, { steps: 5 });
    await page.mouse.up();
    const value = await thumb.getAttribute("aria-valuenow");
    expect(Number(value)).toBeGreaterThan(75);
  });
});
