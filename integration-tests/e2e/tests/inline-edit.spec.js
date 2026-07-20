import { test, expect } from "@playwright/test";

test.describe("Inline Edit", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/inline-edit");
  });

  test("page loads with example section headings", async ({ page }) => {
    await expect(page.locator("#basic")).toBeVisible();
  });

  test.describe("Basic", () => {
    test("has pf-v6-c-inline-edit class", async ({ page }) => {
      await expect(page.locator("#ie-basic")).toHaveClass(/pf-v6-c-inline-edit/);
    });

    test("shows value text by default", async ({ page }) => {
      await expect(page.locator("#ie-basic .pf-v6-c-inline-edit__value")).toBeVisible();
    });

    test("clicking edit button shows input with pf-m-inline-editable class", async ({ page }) => {
      await page
        .locator("#ie-basic button[aria-label*='edit'], #ie-basic .pf-v6-c-inline-edit__action button")
        .first()
        .click();
      await expect(page.locator("#ie-basic")).toHaveClass(/pf-m-inline-editable/);
      await expect(page.locator("#ie-basic input")).toBeVisible();
    });

    test("clicking save button returns to view mode", async ({ page }) => {
      await page
        .locator("#ie-basic button[aria-label*='edit'], #ie-basic .pf-v6-c-inline-edit__action button")
        .first()
        .click();
      await page
        .locator("#ie-basic button[aria-label*='save'], #ie-basic .pf-v6-c-inline-edit__action button")
        .last()
        .click();
      await expect(page.locator("#ie-basic")).not.toHaveClass(/pf-m-inline-editable/);
    });
  });

  test.describe("With label", () => {
    test("has a bold label and edits like basic", async ({ page }) => {
      await expect(page.locator("#ie-label .pf-v6-c-inline-edit__label.pf-m-bold").first()).toBeVisible();
      await page.locator('#ie-label button[aria-label="Edit title"]').click();
      await expect(page.locator("#ie-label")).toHaveClass(/pf-m-inline-editable/);
      await expect(page.locator("#ie-label input")).toBeVisible();
    });
  });

  test.describe("Multiple fields", () => {
    test("one Edit toggle reveals all field inputs and a footer", async ({ page }) => {
      await page.locator('#ie-multiple button[aria-label="Edit all fields"]').click();
      await expect(page.locator("#ie-multiple input")).toHaveCount(3);
      await expect(page.locator("#ie-multiple .pf-v6-c-inline-edit__group.pf-m-footer")).toBeVisible();
    });
  });

  test.describe("Validated", () => {
    test("empty is invalid (Save disabled); typing makes it valid", async ({ page }) => {
      const control = page.locator("#ie-validated .pf-v6-c-form-control");
      const save = page.locator('#ie-validated button[aria-label="Save"]');
      await expect(control).toHaveClass(/pf-m-error/);
      await expect(save).toBeDisabled();
      await page.locator("#ie-validated input").fill("Ada Lovelace");
      await expect(control).toHaveClass(/pf-m-success/);
      await expect(save).toBeEnabled();
    });
  });

  test.describe("Free form edit", () => {
    test("the editable-text block is contenteditable and accepts typing", async ({ page }) => {
      const text = page.locator("#ie-free-form-text");
      await expect(text).toHaveAttribute("contenteditable", "true");
      await text.click();
      await page.keyboard.press("End");
      await page.keyboard.type(" Edited.");
      await expect(text).toContainText("Edited.");
    });

    test("the table-row cross-link points at the table editable-rows example", async ({ page }) => {
      await expect(page.locator("#table-row")).toBeAttached();
      await expect(page.locator('a[href="/components/table#editable-rows"]')).toBeAttached();
    });
  });

  test.describe("Standalone routes", () => {
    for (const example of ["basic", "with-label", "multiple", "validated", "free-form"]) {
      test(`/components/inline-edit/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/inline-edit/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-inline-edit").first()).toBeAttached();
      });
    }
  });
  test.describe("Java source tab", () => {
    test("every example card gets a leading Java tab", async ({ page }) => {
      await page.goto("/components/inline-edit");
      for (const ex of ["basic", "multiple", "validated", "with-label", "free-form"]) {
        const card = page.locator(`[data-rendered-href="/components/inline-edit/${ex}"]`);
        await expect(card.locator('button[aria-label*="Toggle Java"]')).toHaveCount(1);
      }
    });

    test("source-java route serves the snippet as plain text", async ({ page }) => {
      const res = await page.request.get("/components/inline-edit/source-java/multiple");
      expect(res.status()).toBe(200);
      expect(await res.text()).toContain('.field("Name", "Jane Smith")');
    });

    test("generated multiple shape edits all fields at once", async ({ page }) => {
      await page.goto("/components/inline-edit/multiple");
      await page.locator('#ie-multiple button[aria-label="Edit all fields"]').click();
      const nameInput = page.locator('#ie-multiple input[aria-label="Name"]');
      await expect(nameInput).toBeVisible();
      await expect(nameInput).toHaveValue("Jane Smith");
      await page.locator("#ie-multiple").getByRole("button", { name: "Save" }).click();
      await expect(nameInput).toBeHidden();
    });
  });
});
