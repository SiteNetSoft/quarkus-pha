import { test, expect } from "@playwright/test";

test.describe("Smoke tests", () => {
  test("homepage loads with correct title", async ({ page }) => {
    await page.goto("/");
    await expect(page).toHaveTitle("Component Showcase");
  });

  test("homepage displays All components heading", async ({ page }) => {
    await page.goto("/");
    const heading = page.locator("h1");
    await expect(heading).toHaveText("All components");
  });

  test("gallery shows 92 component cards", async ({ page }) => {
    await page.goto("/");
    const cards = page.locator(".pf-v6-l-gallery .pf-v6-c-card");
    await expect(cards).toHaveCount(92);
  });

  test("implemented components link to demo pages", async ({ page }) => {
    await page.goto("/");
    const aboutModalLink = page.locator('a[href="/components/about-modal"]');
    await expect(aboutModalLink).toBeVisible();

    const accordionLink = page.locator('a[href="/components/accordion"]');
    await expect(accordionLink).toBeVisible();
  });

  test("search input filters component cards", async ({ page }) => {
    await page.goto("/");
    const searchInput = page.locator('input[placeholder="Search components by name"]');
    await expect(searchInput).toBeVisible();

    await searchInput.fill("accordion");
    const visibleCards = page.locator('.pf-v6-l-gallery > div:visible');
    await expect(visibleCards).toHaveCount(1);
    await expect(page.locator('#accordion')).toBeVisible();
  });

  test("/hello REST endpoint returns 200", async ({ request }) => {
    const response = await request.get("/hello");
    expect(response.status()).toBe(200);
    expect(await response.text()).toBe("Hello from Quarkus REST");
  });
});
