import { test, expect } from "@playwright/test";

test.describe("Notification Badge", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/notification-badge");
  });

  test("page loads with the example sections in ToC", async ({ page }) => {
    await expect(page.locator("#read")).toBeVisible();
    await expect(page.locator("#unread")).toBeVisible();
    await expect(page.locator("#attention")).toBeVisible();
    await expect(page.locator("#with-animation")).toBeVisible();
  });

  test.describe("With animation", () => {
    test("adding a notification bumps the count and pulses pf-m-notify", async ({ page }) => {
      const demo = page.locator("#nb-with-animation-demo");
      await expect(demo.locator("#nb-anim-read .pf-v6-c-button__count")).toBeHidden();
      await demo.locator("button", { hasText: "Add Notification" }).click();
      await expect(demo.locator("#nb-anim-read .pf-v6-c-badge")).toHaveText("1");
      await expect(demo.locator("#nb-anim-unread .pf-v6-c-badge")).toHaveText("1");
      await expect(demo.locator("#nb-anim-attention .pf-v6-c-badge")).toHaveText("1");
      await demo.locator("button", { hasText: "Add Notification" }).click();
      await expect(demo.locator("#nb-anim-read .pf-v6-c-badge")).toHaveText("2");
    });

    test("standalone route renders", async ({ page }) => {
      const res = await page.goto("/components/notification-badge/with-animation");
      expect(res.status()).toBe(200);
      await expect(page.locator("#nb-with-animation-demo")).toBeVisible();
    });
  });

  test.describe("Read", () => {
    test("is a stateful button with pf-m-read", async ({ page }) => {
      await expect(page.locator("#nb-read")).toHaveClass(/pf-v6-c-button/);
      await expect(page.locator("#nb-read")).toHaveClass(/pf-m-stateful/);
      await expect(page.locator("#nb-read")).toHaveClass(/pf-m-read/);
    });
  });

  test.describe("Unread", () => {
    test("has class pf-m-unread and a count of 3", async ({ page }) => {
      await expect(page.locator("#nb-unread")).toHaveClass(/pf-m-unread/);
      await expect(page.locator("#nb-unread .pf-v6-c-button__count .pf-v6-c-badge")).toHaveText("3");
    });
  });

  test.describe("Attention", () => {
    test("has class pf-m-attention and an exclamation count", async ({ page }) => {
      await expect(page.locator("#nb-attention")).toHaveClass(/pf-m-attention/);
      await expect(page.locator("#nb-attention .pf-v6-c-button__count .pf-v6-c-badge")).toHaveText("!");
    });
  });

  test("all badges render an inline bell SVG icon", async ({ page }) => {
    for (const id of ["nb-read", "nb-unread", "nb-attention"]) {
      await expect(page.locator(`#${id} .pf-v6-c-button__icon svg`)).toBeVisible();
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/notification-badge");
      for (const ex of ["read", "unread", "attention"]) {
        const card = page.locator(`[data-rendered-href="/components/notification-badge/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/notification-badge/source-java/attention");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.variant("attention").count("!")');
    });
  });
});
