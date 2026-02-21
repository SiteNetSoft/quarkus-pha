import { test, expect } from "@playwright/test";

test.describe("Map", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/map");
  });

  test("page loads with correct heading", async ({ page }) => {
    await expect(page.locator("h1")).toHaveText("Map");
  });

  test("all section headings are visible", async ({ page }) => {
    await expect(page.locator("#basic-heading")).toBeVisible();
    await expect(page.locator("#markers-heading")).toBeVisible();
    await expect(page.locator("#dark-heading")).toBeVisible();
    await expect(page.locator("#controls-heading")).toBeVisible();
    await expect(page.locator("#geojson-heading")).toBeVisible();
    await expect(page.locator("#compact-heading")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("container has pha-c-map class", async ({ page }) => {
      await expect(page.locator("#map-basic")).toHaveClass(/pha-c-map/);
    });

    test("map container element exists", async ({ page }) => {
      await expect(page.locator("#map-basic .pha-c-map__container")).toBeVisible();
    });

    test("MapLibre renders a canvas", async ({ page }) => {
      const canvas = page.locator("#map-basic canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });

    test("navigation control is present", async ({ page }) => {
      const nav = page.locator("#map-basic .maplibregl-ctrl-group");
      await expect(nav).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("With Markers", () => {
    test("container has pha-c-map class", async ({ page }) => {
      await expect(page.locator("#map-markers")).toHaveClass(/pha-c-map/);
    });

    test("has 6 markers", async ({ page }) => {
      const markers = page.locator("#map-markers .maplibregl-marker");
      await expect(markers).toHaveCount(6, { timeout: 10000 });
    });
  });

  test.describe("Dark Theme", () => {
    test("container has pha-c-map class", async ({ page }) => {
      await expect(page.locator("#map-dark")).toHaveClass(/pha-c-map/);
    });

    test("MapLibre renders a canvas", async ({ page }) => {
      const canvas = page.locator("#map-dark canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("Full Controls", () => {
    test("has scale control", async ({ page }) => {
      const scale = page.locator("#map-controls .maplibregl-ctrl-scale");
      await expect(scale).toBeVisible({ timeout: 10000 });
    });

    test("has fullscreen control", async ({ page }) => {
      const fullscreen = page.locator("#map-controls .maplibregl-ctrl-fullscreen");
      await expect(fullscreen).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("GeoJSON Layer", () => {
    test("container has pha-c-map class", async ({ page }) => {
      await expect(page.locator("#map-geojson")).toHaveClass(/pha-c-map/);
    });

    test("MapLibre renders a canvas", async ({ page }) => {
      const canvas = page.locator("#map-geojson canvas");
      await expect(canvas).toBeVisible({ timeout: 10000 });
    });
  });

  test.describe("Compact", () => {
    test("container has pha-c-map class", async ({ page }) => {
      await expect(page.locator("#map-compact")).toHaveClass(/pha-c-map/);
    });

    test("map container has custom height", async ({ page }) => {
      const container = page.locator("#map-compact .pha-c-map__container");
      await expect(container).toHaveAttribute("style", /height:\s*200px/);
    });
  });
});
