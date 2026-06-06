import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "positions", "on-icon", "long-content"];

test.describe("Tooltip", () => {
  test("page loads with all example section headings", async ({ page }) => {
    await page.goto("/components/tooltip");
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("hidden until hover, shows after the delay, hides on mouse-out", async ({ page }) => {
    await page.goto("/components/tooltip/basic");
    const tip = page.locator("#tt-basic");
    await expect(tip).toBeHidden();
    await page.getByRole("button", { name: "Hover or focus me" }).hover();
    await expect(tip).toBeVisible(); // auto-waits past PF's 300ms entry delay
    await expect(tip).toHaveText("I'm a tooltip");
    await page.mouse.move(0, 0);
    await expect(tip).toBeHidden();
  });

  test("shows on keyboard focus", async ({ page }) => {
    await page.goto("/components/tooltip/basic");
    await page.getByRole("button", { name: "Hover or focus me" }).focus();
    await expect(page.locator("#tt-basic")).toBeVisible();
  });

  test("trigger references the tip via aria-describedby", async ({ page }) => {
    await page.goto("/components/tooltip/basic");
    await expect(page.locator(".pha-tooltip > [aria-describedby='tt-basic']")).toHaveCount(1);
  });

  test("positions render the right pf-m-{position} class", async ({ page }) => {
    await page.goto("/components/tooltip/positions");
    await expect(page.locator("#tt-top")).toHaveClass(/pf-m-top/);
    await expect(page.locator("#tt-bottom")).toHaveClass(/pf-m-bottom/);
    await expect(page.locator("#tt-left")).toHaveClass(/pf-m-left/);
    await expect(page.locator("#tt-right")).toHaveClass(/pf-m-right/);
  });

  test("on-icon: a plain icon button with an aria-label triggers it", async ({ page }) => {
    await page.goto("/components/tooltip/on-icon");
    const iconBtn = page.getByRole("button", { name: "More information" });
    await expect(iconBtn).toBeVisible();
    await iconBtn.hover();
    await expect(page.locator("#tt-icon")).toBeVisible();
  });

  test("all example standalone routes render", async ({ page }) => {
    for (const ex of EXAMPLES) {
      const res = await page.goto(`/components/tooltip/${ex}`);
      expect(res.status()).toBe(200);
      await expect(page.locator(".pha-tooltip").first()).toBeAttached();
    }
  });
});
