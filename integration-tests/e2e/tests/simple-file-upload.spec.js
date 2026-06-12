import { test, expect } from "@playwright/test";

const EXAMPLES = [
  "basic",
  "simple-text-file",
  "with-helper-text",
  "text-with-edits",
  "text-with-restrictions",
  "custom-file-preview",
  "custom-upload",
  "disabled",
];

const setFile = (page, idp, name, content) =>
  page.locator(`#${idp} input[type='file']`).setInputFiles({
    name,
    mimeType: "text/plain",
    buffer: Buffer.from(content),
  });

test.describe("Simple File Upload", () => {
  test.beforeEach(async ({ page }) => {
    await page.goto("/components/simple-file-upload");
  });

  test("ToC anchors render for every example", async ({ page }) => {
    for (const id of EXAMPLES) {
      await expect(page.locator(`#${id}`)).toBeAttached();
    }
  });

  test("basic has the file-select row; disabled disables the buttons", async ({ page }) => {
    await expect(page.locator("#sfu-basic .pf-v6-c-file-upload__file-select")).toBeVisible();
    await expect(page.locator("#sfu-disabled button").first()).toBeDisabled();
  });

  test("selecting a text file fills the name and preview; Clear resets", async ({ page }) => {
    await setFile(page, "sfu-text", "notes.txt", "hello from playwright");
    await expect(page.locator("#sfu-text-filename")).toHaveValue("notes.txt");
    await expect(page.locator("#sfu-text-details")).toHaveValue("hello from playwright");
    await page.locator("#sfu-text button", { hasText: "Clear" }).click();
    await expect(page.locator("#sfu-text-filename")).toHaveValue("");
    await expect(page.locator("#sfu-text-details")).toHaveValue("");
  });

  test("edits-allowed preview is editable", async ({ page }) => {
    await setFile(page, "sfu-edits", "draft.txt", "original");
    const details = page.locator("#sfu-edits-details");
    await expect(details).toHaveValue("original");
    await details.fill("edited content");
    await expect(details).toHaveValue("edited content");
  });

  test("restrictions reject oversized files with a danger helper", async ({ page }) => {
    await setFile(page, "sfu-restrict", "big.txt", "x".repeat(2048));
    await expect(page.locator("#sfu-restrict .pf-v6-c-helper-text__item.pf-m-error")).toBeVisible();
    await expect(page.locator("#sfu-restrict-filename")).toHaveValue("");
    await setFile(page, "sfu-restrict", "small.txt", "ok");
    await expect(page.locator("#sfu-restrict-filename")).toHaveValue("small.txt");
  });

  test("custom preview shows icon, name, and size", async ({ page }) => {
    await setFile(page, "sfu-preview", "archive.bin", "0123456789");
    const preview = page.locator("#sfu-preview .pf-v6-c-file-upload__file-details");
    await expect(preview).toBeVisible();
    await expect(preview.locator("strong")).toHaveText("archive.bin");
    await expect(preview).toContainText("10 bytes");
  });

  test("custom upload uses a link-button trigger", async ({ page }) => {
    await expect(page.locator("#sfu-custom .pf-v6-c-button.pf-m-link", { hasText: "Upload a file" })).toBeVisible();
  });

  test.describe("Standalone routes", () => {
    for (const example of EXAMPLES) {
      test(`/components/simple-file-upload/${example} returns 200`, async ({ page }) => {
        const res = await page.goto(`/components/simple-file-upload/${example}`);
        expect(res.status()).toBe(200);
        await expect(page.locator(".pf-v6-c-file-upload").first()).toBeAttached();
      });
    }
  });
});
