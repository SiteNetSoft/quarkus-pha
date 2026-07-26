import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "variants",
  "variations",
  "with-description",
  "closable",
  "inline",
  "with-actions",
  "timeout",
  "expandable",
  "truncated",
  "custom-icons",
  "plain-inline",
  "static-live-region",
  "alert-group-static",
  "alert-group-toast",
  "alert-group-toast-overflow",
  "dynamic-groups",
  "dynamic-live-region",
  "async-live-region",
  "dynamic-group-overflow",
  "multiple-dynamic-groups",
  "async-groups",
];

test.describe("Alert", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/alert");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Variants", () => {
    test("correct modifier classes for all 5 variants", async ({ page }) => {
      await expect(page.locator("#al-custom")).toHaveClass(/pf-m-custom/);
      await expect(page.locator("#al-info")).toHaveClass(/pf-m-info/);
      await expect(page.locator("#al-success")).toHaveClass(/pf-m-success/);
      await expect(page.locator("#al-warning")).toHaveClass(/pf-m-warning/);
      await expect(page.locator("#al-danger")).toHaveClass(/pf-m-danger/);
    });
  });

  test.describe("Screen-reader prefix", () => {
    const VARIANTS = [
      ["#al-custom", "custom alert:"],
      ["#al-info", "info alert:"],
      ["#al-success", "success alert:"],
      ["#al-warning", "warning alert:"],
      ["#al-danger", "danger alert:"],
    ];

    for (const [selector, prefix] of VARIANTS) {
      test(`${selector} carries the "${prefix}" prefix`, async ({ page }) => {
        await expect(page.locator(`${selector} .pf-v6-c-alert__title .pf-v6-screen-reader`)).toHaveText(prefix);
      });
    }

    // A class that matches no CSS rule still passes markup and a11y checks, so
    // assert the computed clip rather than the element's presence or text.
    test("the prefix is clipped rather than visibly rendered", async ({ page }) => {
      const clipPath = await page
        .locator("#al-info .pf-v6-c-alert__title .pf-v6-screen-reader")
        .evaluate((el) => getComputedStyle(el).clipPath);
      expect(clipPath).not.toBe("none");
    });
  });

  test.describe("Variations", () => {
    test("full variation has description, action links, and close", async ({ page }) => {
      const alert = page.locator("#al-variation-full");
      await expect(alert.locator(".pf-v6-c-alert__description")).toBeVisible();
      await expect(alert.locator(".pf-v6-c-alert__action-group .pf-v6-c-button")).toHaveCount(2);
      await expect(alert.locator(".pf-v6-c-alert__action button")).toBeVisible();
    });
  });

  test.describe("Closable", () => {
    test("clicking close hides the alert", async ({ page }) => {
      const alert = page.locator("#al-close");
      await expect(alert).toBeVisible();
      await alert.locator(".pf-v6-c-alert__action button").click();
      await expect(alert).toBeHidden();
    });
  });

  test.describe("Inline / plain", () => {
    test("inline alert has pf-m-inline class", async ({ page }) => {
      await expect(page.locator("#al-inline")).toHaveClass(/pf-m-inline/);
    });

    test("plain inline alerts carry both modifiers", async ({ page }) => {
      await expect(page.locator("#al-plain-info")).toHaveClass(/pf-m-inline/);
      await expect(page.locator("#al-plain-info")).toHaveClass(/pf-m-plain/);
    });
  });

  test.describe("Expandable", () => {
    test("description reveals on toggle", async ({ page }) => {
      const alert = page.locator("#al-expandable");
      await expect(alert).toHaveClass(/pf-m-expandable/);
      await expect(alert.locator(".pf-v6-c-alert__description")).toBeHidden();
      await alert.locator(".pf-v6-c-alert__toggle button").click();
      await expect(alert).toHaveClass(/pf-m-expanded/);
      await expect(alert.locator(".pf-v6-c-alert__description")).toBeVisible();
    });
  });

  test.describe("Truncated", () => {
    test("title carries pf-m-truncate", async ({ page }) => {
      await expect(page.locator("#al-truncated .pf-v6-c-alert__title")).toHaveClass(/pf-m-truncate/);
    });
  });

  test.describe("Custom icons", () => {
    test("custom icon renders in the icon slot", async ({ page }) => {
      await expect(page.locator("#al-custom-icon-1 .pf-v6-c-alert__icon svg")).toBeVisible();
    });
  });

  test.describe("Timeout", () => {
    test("added alert disappears after its timeout", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/alert/timeout"]');
      await card.locator("button", { hasText: "Add alert" }).click();
      const item = page.locator("#al-timeout-group .pf-v6-c-alert-group__item");
      await expect(item).toHaveCount(1);
      await expect(item).toHaveCount(0, { timeout: 10000 });
    });
  });

  test.describe("Alert groups", () => {
    test("static group stacks three inline alerts", async ({ page }) => {
      await expect(page.locator("#al-group-static .pf-v6-c-alert-group__item")).toHaveCount(3);
    });

    test("toast group appends and dismisses toasts", async ({ page }) => {
      const demo = page.locator("#al-group-toast-demo");
      await demo.locator("button", { hasText: "Add success toast" }).click();
      await demo.locator("button", { hasText: "Add info toast" }).click();
      const items = demo.locator(".pf-v6-c-alert-group__item");
      await expect(items).toHaveCount(2);
      await items.first().locator(".pf-v6-c-alert__action button").click();
      await expect(items).toHaveCount(1);
    });

    test("toast overflow caps visible toasts and shows the overflow button", async ({ page }) => {
      const demo = page.locator("#al-group-toast-overflow-demo");
      const add = demo.locator("button", { hasText: "Add toast" });
      for (let i = 0; i < 6; i++) await add.click();
      await expect(demo.locator(".pf-v6-c-alert-group__item .pf-v6-c-alert")).toHaveCount(4);
      const overflow = demo.locator(".pf-v6-c-alert-group__overflow-button");
      await expect(overflow).toBeVisible();
      await expect(overflow).toContainText("View 2 more");
    });

    test("dynamic group appends and clears alerts", async ({ page }) => {
      const demo = page.locator("#al-dynamic-groups-demo");
      await demo.locator("button", { hasText: "Add alert" }).click();
      await demo.locator("button", { hasText: "Add alert" }).click();
      await expect(demo.locator(".pf-v6-c-alert-group__item")).toHaveCount(2);
      await demo.locator("button", { hasText: "Remove all" }).click();
      await expect(demo.locator(".pf-v6-c-alert-group__item")).toHaveCount(0);
    });

    test("dynamic live region prepends variant alerts", async ({ page }) => {
      const demo = page.locator("#al-dynamic-live-region-demo");
      await demo.locator("button", { hasText: "Add single success alert" }).click();
      await demo.locator("button", { hasText: "Add single danger alert" }).click();
      const alerts = demo.locator(".pf-v6-c-alert-group__item .pf-v6-c-alert");
      await expect(alerts).toHaveCount(2);
      await expect(alerts.first()).toHaveClass(/pf-m-danger/);
      await expect(alerts.first()).toContainText("Single danger alert");
    });

    test("overflow group caps visible alerts at four", async ({ page }) => {
      const demo = page.locator("#al-dynamic-group-overflow-demo");
      const add = demo.locator("button", { hasText: "Add single success alert" });
      for (let i = 0; i < 5; i++) await add.click();
      await expect(demo.locator(".pf-v6-c-alert-group__item .pf-v6-c-alert")).toHaveCount(4);
      const overflow = demo.locator(".pf-v6-c-alert-group__overflow-button");
      await expect(overflow).toBeVisible();
      await expect(overflow).toContainText("View 1 more");
    });

    test("multiple dynamic group prepends a three-alert collection", async ({ page }) => {
      const demo = page.locator("#al-multiple-dynamic-groups-demo");
      await demo.locator("button", { hasText: "Add alert collection" }).click();
      const alerts = demo.locator(".pf-v6-c-alert-group__item .pf-v6-c-alert");
      await expect(alerts).toHaveCount(3);
      await expect(alerts.nth(0)).toHaveClass(/pf-m-danger/);
      await expect(alerts.nth(2)).toHaveClass(/pf-m-success/);
      await alerts.nth(0).locator(".pf-v6-c-alert__action button").click();
      await expect(alerts).toHaveCount(2);
    });

    test("async group starts and stops the toast interval", async ({ page }) => {
      const demo = page.locator("#al-async-groups-demo");
      await demo.locator("button", { hasText: "Start async alerts" }).click();
      const alerts = demo.locator(".pf-v6-c-alert-group__item .pf-v6-c-alert");
      await expect(alerts.first()).toBeVisible({ timeout: 10000 });
      await expect(alerts.first()).toHaveClass(/pf-m-danger/);
      await demo.locator("button", { hasText: "Stop async alerts" }).click();
    });

    test("async live region toggle feeds info alerts", async ({ page }) => {
      const demo = page.locator("#al-async-live-region-demo");
      const toggle = demo.locator(".pf-v6-c-toggle-group__button");
      await toggle.click();
      await expect(toggle).toHaveClass(/pf-m-selected/);
      const alerts = demo.locator(".pf-v6-c-alert-group__item .pf-v6-c-alert");
      await expect(alerts.first()).toBeVisible({ timeout: 10000 });
      await expect(alerts.first()).toHaveClass(/pf-m-info/);
      await toggle.click();
      await expect(toggle).not.toHaveClass(/pf-m-selected/);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/alert/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/alert/${example}`);
        expect(res.status()).toBe(200);
      });
    }
  });
  test.describe("Java source tab", () => {
    // Timeout, the dynamic/async demos and the toast demos stay hand-written (live list state).
    const HAND_WRITTEN = [
      "timeout",
      "dynamic-groups",
      "alert-group-toast",
      "alert-group-toast-overflow",
      "dynamic-live-region",
      "async-live-region",
      "dynamic-group-overflow",
      "multiple-dynamic-groups",
      "async-groups",
    ];

    test("model-driven cards get a leading Java tab; hand-written ones do not", async ({ page }) => {
      await page.goto("/components/alert");
      for (const ex of ["variants", "expandable", "alert-group-static"]) {
        const card = page.locator(`[data-rendered-href="/components/alert/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
      for (const ex of HAND_WRITTEN) {
        const card = page.locator(`[data-rendered-href="/components/alert/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(0);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/alert/source-java/expandable");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain(".asExpandable()");
    });
  });
});
