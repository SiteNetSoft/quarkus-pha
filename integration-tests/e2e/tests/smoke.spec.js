import { test, expect } from "@playwright/test";

test.describe("Smoke tests", () => {
  test("homepage loads with correct title", async ({ page }) => {
    await page.goto("/");
    await expect(page).toHaveTitle("Hello World");
  });

  test("PatternFly button renders with correct classes", async ({ page }) => {
    await page.goto("/");
    const button = page.locator("button.pf-v6-c-button.pf-m-primary");
    await expect(button).toBeVisible();
    await expect(button).toHaveText("About");
  });

  test("Alpine.js backdrop is hidden by default", async ({ page }) => {
    await page.goto("/");
    const backdrop = page.locator(".pf-v6-c-backdrop");
    await expect(backdrop).toBeHidden();
  });

  test("/hello REST endpoint returns 200", async ({ request }) => {
    const response = await request.get("/hello");
    expect(response.status()).toBe(200);
    expect(await response.text()).toBe("Hello from Quarkus REST");
  });
});
