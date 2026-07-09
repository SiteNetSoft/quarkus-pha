import { test, expect } from "@playwright/test";

const EXAMPLES = ["basic", "bordered", "sizes", "bordered-sizes", "initials", "colors"];

test.describe("Avatar", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/avatar");
  });

  test("TOC anchors render", async ({ page }) => {
    const anchors = ["examples", ...EXAMPLES, "documentation", "props-avatar", "usage"];
    for (const id of anchors) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test.describe("Basic", () => {
    test("has the base avatar class + img tag + alt", async ({ page }) => {
      const avatar = page.locator("#avatar-basic");
      await expect(avatar).toHaveClass(/pf-v6-c-avatar/);
      await expect(avatar).toHaveAttribute("alt");
      await expect(avatar).toHaveAttribute("src");
      const tag = await avatar.evaluate((el) => el.tagName);
      expect(tag).toBe("IMG");
    });
  });

  test.describe("Bordered", () => {
    test("carries pf-m-bordered", async ({ page }) => {
      await expect(page.locator("#avatar-bordered")).toHaveClass(/pf-m-bordered/);
    });
  });

  test.describe("Sizes", () => {
    test("renders all four size modifiers", async ({ page }) => {
      for (const size of ["sm", "md", "lg", "xl"]) {
        await expect(page.locator(`#avatar-${size}`)).toHaveClass(new RegExp(`pf-m-${size}`));
      }
    });
  });

  test.describe("Bordered sizes", () => {
    test("each size carries both pf-m-bordered + pf-m-{size}", async ({ page }) => {
      for (const size of ["sm", "md", "lg", "xl"]) {
        const a = page.locator(`#avatar-bordered-${size}`);
        await expect(a).toHaveClass(new RegExp(`pf-m-${size}`));
        await expect(a).toHaveClass(/pf-m-bordered/);
      }
    });
  });

  test.describe("Initials", () => {
    test("renders as a regular avatar with alt containing the name", async ({ page }) => {
      const a = page.locator("#avatar-initials");
      await expect(a).toHaveClass(/pf-v6-c-avatar/);
      await expect(a).toHaveAttribute("alt", /\w+/);
    });
  });

  test.describe("Colors", () => {
    test("renders all nine colorful div avatars with role=img", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/avatar/colors"]');
      await expect(card.locator(".pf-v6-c-avatar.pf-m-colorful")).toHaveCount(9);
      for (const color of ["red", "orangered", "orange", "yellow", "green", "teal", "blue", "purple", "gray"]) {
        const a = card.locator(`.pf-v6-c-avatar.pf-m-${color}`);
        await expect(a).toHaveAttribute("role", "img");
        await expect(a).toHaveAttribute("aria-label", /avatar/);
        const tag = await a.evaluate((el) => el.tagName);
        expect(tag).toBe("DIV");
      }
    });
  });

  test("every avatar is an <img> with src, or a colorful <div> with role=img", async ({ page }) => {
    const avatars = page.locator(".pf-v6-c-avatar");
    const count = await avatars.count();
    expect(count).toBeGreaterThan(0);
    for (let i = 0; i < count; i++) {
      const a = avatars.nth(i);
      const colorful = await a.evaluate((el) => el.classList.contains("pf-m-colorful"));
      const tag = await a.evaluate((el) => el.tagName);
      if (colorful) {
        expect(tag).toBe("DIV");
        await expect(a).toHaveAttribute("role", "img");
      } else {
        expect(tag).toBe("IMG");
        await expect(a).toHaveAttribute("src");
      }
    }
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/avatar/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/avatar/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-avatar").first()).toBeAttached();
      });
    }
  });

  test("source endpoint returns the raw Qute markup", async ({ request }) => {
    const res = await request.get("/components/avatar/source/basic");
    expect(res.status()).toBe(200);
    const body = await res.text();
    expect(body).toContain("pf-v6-c-avatar");
  });
});
