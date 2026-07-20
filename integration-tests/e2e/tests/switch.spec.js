import { test, expect } from "@playwright/test";

test.describe("Switch", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/switch");
  });

  test("page loads with all 4 example sections in ToC", async ({ page }) => {
    await expect(page.locator("h3#basic")).toHaveText("Basic");
    await expect(page.locator("h3#checked")).toHaveText("Checked");
    await expect(page.locator("h3#disabled")).toHaveText("Disabled");
    await expect(page.locator("h3#reversed")).toHaveText("Reversed");
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-switch class", async ({ page }) => {
      await expect(page.locator("#sw-basic")).toHaveClass(/pf-v6-c-switch/);
    });

    test("has label text", async ({ page }) => {
      await expect(page.locator("#sw-basic .pf-v6-c-switch__label")).toHaveText("Enable notifications");
    });
  });

  test.describe("Checked", () => {
    test("switch input is checked", async ({ page }) => {
      await expect(page.locator("#sw-checked-field")).toBeChecked();
    });
  });

  test.describe("Disabled", () => {
    test("disabled switches are disabled", async ({ page }) => {
      await expect(page.locator("#sw-dis-off-field")).toBeDisabled();
      await expect(page.locator("#sw-dis-on-field")).toBeDisabled();
      await expect(page.locator("#sw-dis-on-field")).toBeChecked();
    });
  });

  test.describe("Reversed", () => {
    test("has pf-m-reverse modifier", async ({ page }) => {
      await expect(page.locator("#sw-rev")).toHaveClass(/pf-m-reverse/);
    });
  });

  test.describe("Parity additions", () => {
    test("without-label switch is aria-labelled and toggles", async ({ page }) => {
      const input = page.locator("#sw-without-label-field");
      await expect(input).toHaveAttribute("aria-label", "Enable feature");
      await expect(input).toBeChecked();
      // The native input is visually hidden under the toggle — click the label.
      await page.locator("#sw-without-label").click();
      await expect(input).not.toBeChecked();
    });

    test("/components/switch/without-label returns 200", async ({ page }) => {
      const res = await page.goto("/components/switch/without-label");
      expect(res.status()).toBe(200);
    });
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/switch");
      for (const ex of ["basic", "without-label"]) {
        const card = page.locator(`[data-rendered-href="/components/switch/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/switch/source-java/without-label");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.ariaLabel("Enable feature")');
    });

    test("model-driven without-label switch keeps its aria-label wiring", async ({ page }) => {
      await page.goto("/components/switch/without-label");
      const input = page.locator("#sw-without-label-field");
      await expect(input).toHaveAttribute("aria-label", "Enable feature");
      await expect(input).toBeChecked();
    });
  });
});
