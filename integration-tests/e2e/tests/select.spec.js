import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "single",
  "option-variants",
  "grouped",
  "validation",
  "checkboxes",
  "typeahead",
  "typeahead-creatable",
  "multi-typeahead",
  "multi-typeahead-creatable",
  "multi-typeahead-checkbox",
  "view-more",
  "footer",
];

test.describe("Select", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/select");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Single", () => {
    const toggle = "#sl-single-toggle";
    const menu = "#sl-single-toggle + .pf-v6-c-menu";

    test("opens, lists options as listbox, selecting updates the toggle", async ({ page }) => {
      await expect(page.locator(menu)).toBeHidden();
      await page.locator(toggle).click();
      await expect(page.locator(menu)).toBeVisible();
      await expect(page.locator(`${menu} .pf-v6-c-menu__list`)).toHaveAttribute("role", "listbox");
      await page.locator(`${menu} .pf-v6-c-menu__item`).first().click();
      await expect(page.locator(menu)).toBeHidden();
      await expect(page.locator(`${toggle} .pf-v6-c-menu-toggle__text`)).toHaveText("Mr");
    });
  });

  test.describe("Option variants", () => {
    test("description, icon, disabled, and select-icon options render", async ({ page }) => {
      await page.locator("#sl-option-variants-toggle").click();
      const menu = page.locator("#sl-option-variants .pf-v6-c-menu");
      await expect(menu.locator(".pf-v6-c-menu__item-description")).toBeVisible();
      await expect(menu.locator(".pf-v6-c-menu__item-icon")).toBeVisible();
      await expect(menu.locator(".pf-v6-c-menu__item[disabled]")).toBeAttached();
      await expect(menu.locator(".pf-v6-c-menu__item-select-icon:visible")).toHaveCount(1);
    });
  });

  test.describe("Grouped", () => {
    test("two titled groups render", async ({ page }) => {
      await page.locator("#sl-grouped-toggle").click();
      await expect(page.locator("#sl-grouped .pf-v6-c-menu__group-title")).toHaveCount(2);
    });
  });

  test.describe("Validation", () => {
    test("danger until selection, then success", async ({ page }) => {
      const toggle = page.locator("#sl-validation-toggle");
      await expect(toggle).toHaveClass(/pf-m-danger/);
      await expect(page.locator("#sl-validation-helper")).toBeVisible();
      await toggle.click();
      await page.locator("#sl-validation .pf-v6-c-menu__item").first().click();
      await expect(toggle).toHaveClass(/pf-m-success/);
      await expect(page.locator("#sl-validation-helper")).toBeHidden();
    });
  });

  test.describe("Typeahead", () => {
    test("typing filters; selecting fills the input", async ({ page }) => {
      const input = page.locator("#sl-typeahead-input");
      await input.fill("new");
      const items = page.locator("#sl-typeahead .pf-v6-c-menu__list-item:visible");
      await expect(items).toHaveCount(3);
      await items.first().locator("button").click();
      await expect(input).toHaveValue("New Jersey");
    });

    test("creatable offers Create when no match", async ({ page }) => {
      const input = page.locator("#sl-typeahead-creatable-input");
      await input.fill("Zanzibar");
      const create = page.locator("#sl-typeahead-creatable .pf-v6-c-menu__item:visible", { hasText: 'Create "' });
      await expect(create).toBeVisible();
      await create.click();
      await expect(input).toHaveValue("Zanzibar");
    });
  });

  test.describe("Multi typeahead", () => {
    test("selections become removable labels", async ({ page }) => {
      await page.locator("#sl-multi-typeahead-input").click();
      const menu = page.locator("#sl-multi-typeahead .pf-v6-c-menu");
      await menu.locator(".pf-v6-c-menu__item").nth(0).click();
      await menu.locator(".pf-v6-c-menu__item").nth(1).click();
      const labels = page.locator("#sl-multi-typeahead .pf-v6-c-label-group__list-item");
      await expect(labels).toHaveCount(2);
      await labels.first().locator(".pf-v6-c-label__actions button").click();
      await expect(labels).toHaveCount(1);
    });

    test("checkbox variant shows a selected-count badge", async ({ page }) => {
      await page.locator("#sl-multi-typeahead-checkbox-input").click();
      const menu = page.locator("#sl-multi-typeahead-checkbox .pf-v6-c-menu");
      await menu.locator(".pf-v6-c-check__input").nth(0).check();
      await menu.locator(".pf-v6-c-check__input").nth(2).check();
      await expect(page.locator("#sl-multi-typeahead-checkbox .pf-v6-c-badge")).toHaveText("2");
    });
  });

  test.describe("View more / footer", () => {
    test("view more reveals the remaining options", async ({ page }) => {
      await page.locator("#sl-view-more-toggle").click();
      const items = page.locator("#sl-view-more .pf-v6-c-menu__list-item:visible");
      await expect(items).toHaveCount(4);
      await page.locator("#sl-view-more .pf-m-load button").click();
      await expect(items).toHaveCount(6);
    });

    test("footer renders a link action", async ({ page }) => {
      await page.locator("#sl-footer-toggle").click();
      await expect(page.locator("#sl-footer .pf-v6-c-menu__footer button")).toBeVisible();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/select/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/select/${example}`);
        expect(res.status()).toBe(200);
      });
    }
  });
});
