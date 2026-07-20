import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "expanded",
  "small",
  "disabled",
  "badge",
  "settings",
  "custom-icon",
  "avatar-text",
  "variant-styles",
  "plain",
  "primary",
  "plain-circle",
  "plain-text-label",
  "split",
  "split-checkbox",
  "split-checkbox-icon-text",
  "split-checkbox-text",
  "split-checkbox-toggle-text",
  "split-action",
  "full-height",
  "full-width",
  "in-form",
  "typeahead",
  "status",
  "placeholder",
];

const MODIFIERS = {
  "mt-small": [/pf-m-small/],
  "mt-settings": [/pf-m-settings/],
  "mt-variant-primary": [/pf-m-primary/],
  "mt-variant-secondary": [/pf-m-secondary/],
  "mt-variant-danger": [/pf-m-danger/],
  "mt-circle": [/pf-m-plain/, /pf-m-circle/],
  "mt-plain-text": [/pf-m-plain/, /pf-m-text/],
  "mt-split-check": [/pf-m-split-button/],
  "mt-split-check-disabled": [/pf-m-split-button/, /pf-m-disabled/],
  "mt-full-height": [/pf-m-full-height/],
  "mt-full-width": [/pf-m-full-width/],
  "mt-typeahead": [/pf-m-typeahead/],
  "mt-status-success": [/pf-m-success/],
  "mt-status-warning": [/pf-m-warning/],
  "mt-status-danger": [/pf-m-danger/],
  "mt-placeholder": [/pf-m-placeholder/],
};

test.describe("Menu Toggle", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/menu-toggle");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("modifier classes are applied", async ({ page }) => {
    for (const [id, patterns] of Object.entries(MODIFIERS)) {
      for (const pattern of patterns) {
        await expect(page.locator(`#${id}`)).toHaveClass(pattern);
      }
    }
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-menu-toggle class and text", async ({ page }) => {
      await expect(page.locator("#mt-basic")).toHaveClass(/pf-v6-c-menu-toggle/);
      await expect(page.locator("#mt-basic .pf-v6-c-menu-toggle__text")).toBeVisible();
    });
  });

  test.describe("Expanded", () => {
    test("has pf-m-expanded and aria-expanded", async ({ page }) => {
      await expect(page.locator("#mt-expanded")).toHaveClass(/pf-m-expanded/);
      await expect(page.locator("#mt-expanded")).toHaveAttribute("aria-expanded", "true");
    });
  });

  test.describe("Badge", () => {
    test("count badge renders inside the toggle", async ({ page }) => {
      await expect(page.locator("#mt-badge .pf-v6-c-menu-toggle__count .pf-v6-c-badge")).toHaveText("4 selected");
    });
  });

  test.describe("Avatar and text", () => {
    test("avatar image renders in the icon slot", async ({ page }) => {
      await expect(page.locator("#mt-avatar .pf-v6-c-menu-toggle__icon img.pf-v6-c-avatar")).toBeVisible();
      await expect(page.locator("#mt-avatar-disabled")).toBeDisabled();
    });
  });

  test.describe("Split toggles", () => {
    test("split checkbox toggle has a check and a caret button", async ({ page }) => {
      await expect(page.locator("#mt-split-check .pf-v6-c-check__input")).toBeVisible();
      await expect(page.locator("#mt-split-check .pf-v6-c-menu-toggle__button")).toHaveCount(1);
      await expect(page.locator("#mt-split-check-disabled .pf-v6-c-check__input")).toBeDisabled();
    });

    test("split action toggle has action + caret buttons", async ({ page }) => {
      await expect(page.locator("#mt-split-action .pf-v6-c-menu-toggle__button")).toHaveCount(2);
    });

    test("labeled checkbox shows its label", async ({ page }) => {
      await expect(page.locator("#mt-split-check-text .pf-v6-c-check__label")).toHaveText("10 selected");
    });

    test("plain split toggle has action + caret buttons in three stylings", async ({ page }) => {
      await expect(page.locator("#mt-split .pf-v6-c-menu-toggle__button")).toHaveCount(2);
      await expect(page.locator("#mt-split-primary")).toHaveClass(/pf-m-primary/);
      await expect(page.locator("#mt-split-secondary")).toHaveClass(/pf-m-secondary/);
    });

    test("checkbox-icon-text toggle carries icon, text, and caret inside a pf-m-text button", async ({ page }) => {
      const toggle = page.locator("#mt-split-check-icon-text");
      await expect(toggle.locator(".pf-v6-c-check__input")).toBeVisible();
      const button = toggle.locator(".pf-v6-c-menu-toggle__button");
      await expect(button).toHaveClass(/pf-m-text/);
      await expect(button.locator(".pf-v6-c-menu-toggle__icon")).toBeVisible();
      await expect(button.locator(".pf-v6-c-menu-toggle__text")).toHaveText("Icon");
      await expect(button.locator(".pf-v6-c-menu-toggle__toggle-icon")).toBeVisible();
    });
  });

  test.describe("Status", () => {
    test("status toggles carry a status icon", async ({ page }) => {
      for (const id of ["mt-status-success", "mt-status-warning", "mt-status-danger"]) {
        await expect(page.locator(`#${id} .pf-v6-c-menu-toggle__status-icon`)).toBeVisible();
      }
    });
  });

  test.describe("Typeahead", () => {
    test("contains a plain text-input-group and a caret button", async ({ page }) => {
      await expect(page.locator("#mt-typeahead .pf-v6-c-text-input-group.pf-m-plain input")).toBeVisible();
      await expect(page.locator("#mt-typeahead .pf-v6-c-menu-toggle__button")).toBeVisible();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/menu-toggle/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/menu-toggle/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-menu-toggle").first()).toBeAttached();
      });
    }
  });

  test.describe("Java source tab", () => {
    // All 25 examples are model-driven (MenuToggleDemoData).
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/menu-toggle");
      for (const ex of EXAMPLES) {
        const card = page.locator(`[data-rendered-href="/components/menu-toggle/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("Java tab opens Monaco with the builder code", async ({ page }) => {
      await page.goto("/components/menu-toggle");
      const card = page.locator('[data-rendered-href="/components/menu-toggle/basic"]');
      await card.locator('button[aria-label*="Toggle Java"]').click();
      await expect(card.locator(".monaco-editor").first()).toBeVisible({ timeout: 10000 });
      await expect(card.locator(".monaco-editor .view-lines")).toContainText("MenuToggle.of(", {
        timeout: 10000,
      });
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/menu-toggle/source-java/split-checkbox");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('MenuToggle.split("mt-split-check")');
    });
  });
});
