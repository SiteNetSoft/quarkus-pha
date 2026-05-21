import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "single-group",
  "with-kebab",
  "icons-list",
  "icons-group",
  "multiple-groups",
  "cancel-form",
  "cancel-wizard",
  "vertical",
];

test.describe("Action list", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/action-list");
  });

  test("TOC anchors render", async ({ page }) => {
    const anchors = [
      "examples",
      ...EXAMPLES,
      "documentation",
      "props-action-list",
      "props-group",
      "props-item",
      "usage",
    ];
    for (const id of anchors) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Single group", () => {
    test("renders primary + secondary buttons", async ({ page }) => {
      const list = page.locator("#action-list-single-group");
      await expect(list.locator(".pf-v6-c-button.pf-m-primary")).toHaveCount(1);
      await expect(list.locator(".pf-v6-c-button.pf-m-secondary")).toHaveCount(1);
    });
  });

  test.describe("With kebab", () => {
    test("renders Next + Back + a kebab toggle", async ({ page }) => {
      const list = page.locator("#action-list-with-kebab");
      await expect(list.locator(".pf-v6-c-button.pf-m-primary")).toHaveCount(1);
      await expect(list.locator(".pf-v6-c-button.pf-m-secondary")).toHaveCount(1);
      await expect(list.locator(".pf-v6-c-menu-toggle")).toHaveCount(1);
    });
  });

  test.describe("Icons on list", () => {
    test("root carries pf-m-icons", async ({ page }) => {
      const list = page.locator("#action-list-icons-list");
      await expect(list).toHaveClass(/pf-m-icons/);
    });

    test("every icon button has an aria-label", async ({ page }) => {
      const buttons = page.locator("#action-list-icons-list .pf-v6-c-button");
      const count = await buttons.count();
      expect(count).toBeGreaterThan(0);
      for (let i = 0; i < count; i++) {
        await expect(buttons.nth(i)).toHaveAttribute("aria-label");
      }
    });
  });

  test.describe("Icons on group", () => {
    test("pf-m-icons sits on the group, not the root", async ({ page }) => {
      const list = page.locator("#action-list-icons-group");
      await expect(list).not.toHaveClass(/pf-m-icons/);
      await expect(list.locator(".pf-v6-c-action-list__group.pf-m-icons")).toHaveCount(1);
    });
  });

  test.describe("Multiple groups", () => {
    test("renders 2 groups", async ({ page }) => {
      const groups = page.locator("#action-list-multiple-groups .pf-v6-c-action-list__group");
      await expect(groups).toHaveCount(2);
    });
  });

  test.describe("Cancel — form", () => {
    test("uses a link-styled cancel", async ({ page }) => {
      await expect(page.locator("#action-list-cancel-form .pf-v6-c-button.pf-m-link")).toHaveCount(1);
    });
  });

  test.describe("Cancel — wizard", () => {
    test("cancel sits in its own group", async ({ page }) => {
      const list = page.locator("#action-list-cancel-wizard");
      await expect(list.locator(".pf-v6-c-action-list__group")).toHaveCount(2);
    });
  });

  test.describe("Vertical", () => {
    test("root carries pf-m-vertical", async ({ page }) => {
      await expect(page.locator("#action-list-vertical")).toHaveClass(/pf-m-vertical/);
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/action-list/${example} returns 200 with rendered list`, async ({ page }) => {
        const res = await page.goto(`/components/action-list/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-action-list")).toBeAttached();
      });
    }
  });

  test.describe("Source endpoint", () => {
    test("/components/action-list/source/single-group returns Qute source", async ({ request }) => {
      const res = await request.get("/components/action-list/source/single-group");
      expect(res.status()).toBe(200);
      const body = await res.text();
      expect(body).toContain("components/actions/action-list");
    });
  });
});
