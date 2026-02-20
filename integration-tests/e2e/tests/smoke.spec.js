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

  test("component cards link to demo pages", async ({ page }) => {
    await page.goto("/");
    const aboutModalCard = page.locator('a[href="/components/about-modal"] .pf-v6-c-card__title-text');
    await expect(aboutModalCard).toBeVisible();
    await expect(aboutModalCard).toHaveText("About modal");

    const accordionCard = page.locator('a[href="/components/accordion"] .pf-v6-c-card__title-text');
    await expect(accordionCard).toBeVisible();
    await expect(accordionCard).toHaveText("Accordion");
  });

  test("search input filters component cards", async ({ page }) => {
    await page.goto("/");
    const searchInput = page.locator('input[placeholder="Search components by name"]');
    await expect(searchInput).toBeVisible();

    await searchInput.fill("accordion");
    await expect(page.locator('#about-modal')).toBeHidden();
    await expect(page.locator('#accordion')).toBeVisible();
  });

  test("/hello REST endpoint returns 200", async ({ request }) => {
    const response = await request.get("/hello");
    expect(response.status()).toBe(200);
    expect(await response.text()).toBe("Hello from Quarkus REST");
  });
});
