import { test, expect } from "@playwright/test";

test.describe("Smoke tests", () => {
  test("homepage loads with correct title", async ({ page }) => {
    await page.goto("/");
    await expect(page).toHaveTitle("Component Showcase");
  });

  test("homepage displays component navigation", async ({ page }) => {
    await page.goto("/");
    const heading = page.locator("h1");
    await expect(heading).toHaveText("Component Showcase");

    const nav = page.locator("nav.pf-v6-c-nav");
    await expect(nav).toBeVisible();
  });

  test("navigation contains links to component pages", async ({ page }) => {
    await page.goto("/");
    const aboutModalLink = page.locator('a.pf-v6-c-nav__link[href="/components/about-modal"]');
    await expect(aboutModalLink).toBeVisible();
    await expect(aboutModalLink).toHaveText("About Modal");

    const accordionLink = page.locator('a.pf-v6-c-nav__link[href="/components/accordion"]');
    await expect(accordionLink).toBeVisible();
    await expect(accordionLink).toHaveText("Accordion");
  });

  test("/hello REST endpoint returns 200", async ({ request }) => {
    const response = await request.get("/hello");
    expect(response.status()).toBe(200);
    expect(await response.text()).toBe("Hello from Quarkus REST");
  });
});
