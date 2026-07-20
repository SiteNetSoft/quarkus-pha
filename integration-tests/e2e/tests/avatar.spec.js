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

    test("custom-content twin is a div with role=img holding an svg", async ({ page }) => {
      const avatar = page.locator("#avatar-basic-custom");
      await expect(avatar).toHaveClass(/pf-v6-c-avatar/);
      await expect(avatar).toHaveAttribute("role", "img");
      await expect(avatar).toHaveAttribute("aria-label", /custom content/);
      await expect(avatar.locator("svg")).toBeAttached();
      const tag = await avatar.evaluate((el) => el.tagName);
      expect(tag).toBe("DIV");
    });
  });

  test.describe("Bordered", () => {
    test("is a bordered div avatar holding the chatbot icon", async ({ page }) => {
      const avatar = page.locator("#avatar-bordered");
      await expect(avatar).toHaveClass(/pf-m-bordered/);
      await expect(avatar).toHaveAttribute("role", "img");
      await expect(avatar).toHaveAttribute("aria-label", /chatbot icon/);
      await expect(avatar.locator("svg.pf-v6-svg")).toBeAttached();
      const tag = await avatar.evaluate((el) => el.tagName);
      expect(tag).toBe("DIV");
    });
  });

  test.describe("Sizes", () => {
    test("renders all four sizes as colorful red avatars with initial C", async ({ page }) => {
      for (const size of ["sm", "md", "lg", "xl"]) {
        const a = page.locator(`#avatar-${size}`);
        await expect(a).toHaveClass(new RegExp(`pf-m-${size}`));
        await expect(a).toHaveClass(/pf-m-colorful/);
        await expect(a).toHaveClass(/pf-m-red/);
        await expect(a).toHaveAttribute("role", "img");
        await expect(a).toHaveAttribute("aria-label", /avatar with initial C/);
        await expect(a.locator(".pf-v6-c-avatar__initials")).toHaveText("C");
      }
    });

    test("sizes render at increasing pixel widths", async ({ page }) => {
      let prev = 0;
      for (const size of ["sm", "md", "lg", "xl"]) {
        const width = await page.locator(`#avatar-${size}`).evaluate((el) => el.getBoundingClientRect().width);
        expect(width).toBeGreaterThan(prev);
        prev = width;
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
    test("bordered avatar renders initial C in an __initials span", async ({ page }) => {
      const a = page.locator("#avatar-initials");
      await expect(a).toHaveClass(/pf-m-bordered/);
      await expect(a).toHaveAttribute("role", "img");
      await expect(a).toHaveAttribute("aria-label", /initial C/);
      await expect(a.locator(".pf-v6-c-avatar__initials")).toHaveText("C");
    });

    test("all nine colorful initials avatars render", async ({ page }) => {
      const card = page.locator('[data-rendered-href="/components/avatar/initials"]');
      const colorful = card.locator(".pf-v6-c-avatar.pf-m-colorful");
      await expect(colorful).toHaveCount(9);
      await expect(card.locator(".pf-v6-c-avatar__initials")).toHaveCount(10);
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

  test("every avatar is an <img> with src+alt, or a <div> with role=img + aria-label", async ({ page }) => {
    const avatars = page.locator(".pf-v6-c-avatar");
    const count = await avatars.count();
    expect(count).toBeGreaterThan(0);
    for (let i = 0; i < count; i++) {
      const a = avatars.nth(i);
      const tag = await a.evaluate((el) => el.tagName);
      if (tag === "IMG") {
        await expect(a).toHaveAttribute("src");
        await expect(a).toHaveAttribute("alt");
      } else {
        expect(tag).toBe("DIV");
        await expect(a).toHaveAttribute("role", "img");
        await expect(a).toHaveAttribute("aria-label", /\w+/);
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
    expect(body).toContain("components/data-display/avatar");
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/avatar");
      for (const ex of ["basic", "colors", "sizes"]) {
        const card = page.locator(`[data-rendered-href="/components/avatar/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/avatar/source-java/colors");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('Avatar.silhouette("Red avatar").color("red")');
    });
  });
});
