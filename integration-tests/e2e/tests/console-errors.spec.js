import { test, expect } from "@playwright/test";
import fs from "node:fs";
import path from "node:path";

// When JS_REPORT_DIR is set (e2e.sh wires this through Podman), write a
// per-path report file mirroring scripts/validate.sh and validate-css.sh.
// e2e.sh also assembles a summary.txt from these files after the run.
const REPORT_DIR = process.env.JS_REPORT_DIR || "";

function pathToSlug(p) {
  if (p === "/") return "index";
  return p.replace(/^\//, "").replace(/\//g, "__");
}

function writeReport(testPath, errors) {
  if (!REPORT_DIR) return;
  fs.mkdirSync(REPORT_DIR, { recursive: true });
  const file = path.join(REPORT_DIR, `${pathToSlug(testPath)}.txt`);
  const status = errors.length === 0 ? "PASS" : "FAIL";
  const body =
    `Path: ${testPath}\n` +
    `Status: ${status}\n` +
    `Errors: ${errors.length}\n` +
    (errors.length > 0 ? `\n${errors.join("\n")}\n` : "");
  fs.writeFileSync(file, body);
}

// Visit each demo page and assert the browser produced no JS console errors
// or uncaught exceptions. This is the JS-runtime equivalent of the HTML/CSS
// validators: it catches Alpine init failures, undefined globals, HTMX swap
// crashes, etc. — things ESLint cannot see because they only happen in a
// real browser executing the page.

const COMPONENT_PATHS = [
  "/components/about-modal",
  "/components/accordion",
  "/components/action-list",
  "/components/alert",
  "/components/application-launcher",
  "/components/avatar",
  "/components/backdrop",
  "/components/background-image",
  "/components/back-to-top",
  "/components/badge",
  "/components/banner",
  "/components/brand",
  "/components/breadcrumb",
  "/components/button",
  "/components/calendar-month",
  "/components/card",
  "/components/chart",
  "/components/checkbox",
  "/components/chip",
  "/components/click-to-load",
  "/components/clipboard-copy",
  "/components/code-block",
  "/components/code-editor",
  "/components/content",
  "/components/context-selector",
  "/components/custom-menus",
  "/components/data-list",
  "/components/date-and-time-picker",
  "/components/date-picker",
  "/components/description-list",
  "/components/divider",
  "/components/document-editor",
  "/components/drag-and-drop",
  "/components/drawer",
  "/components/dropdown",
  "/components/dual-list-selector",
  "/components/empty-state",
  "/components/expandable-section",
  "/components/form",
  "/components/form-control",
  "/components/form-select",
  "/components/helper-text",
  "/components/hint",
  "/components/icon",
  "/components/infinite-scroll",
  "/components/inline-edit",
  "/components/input-group",
  "/components/jump-links",
  "/components/label",
  "/components/lazy-modal",
  "/components/list",
  "/components/live-search",
  "/components/login-page",
  "/components/map",
  "/components/masthead",
  "/components/menu",
  "/components/menu-toggle",
  "/components/modal",
  "/components/multiple-file-upload",
  "/components/navigation",
  "/components/notification-badge",
  "/components/notification-drawer",
  "/components/number-input",
  "/components/options-menu",
  "/components/overflow-menu",
  "/components/page",
  "/components/pagination",
  "/components/panel",
  "/components/password-generator",
  "/components/password-strength",
  "/components/popover",
  "/components/progress",
  "/components/progress-stepper",
  "/components/radio",
  "/components/rectangle-selection",
  "/components/search-input",
  "/components/select",
  "/components/sidebar",
  "/components/simple-file-upload",
  "/components/simple-list",
  "/components/skeleton",
  "/components/skip-to-content",
  "/components/slider",
  "/components/spinner",
  "/components/switch",
  "/components/table",
  "/components/tabs",
  "/components/text-area",
  "/components/text-input",
  "/components/text-input-group",
  "/components/tile",
  "/components/time-picker",
  "/components/timestamp",
  "/components/title",
  "/components/toggle-group",
  "/components/toolbar",
  "/components/tooltip",
  "/components/tree-view",
  "/components/truncate",
  "/components/wizard"
];

const DEMO_PATHS = [
  "/demos/dashboard",
  "/demos/data-management",
  "/demos/settings",
  "/demos/landing"
];

const ALL_PATHS = ["/", ...COMPONENT_PATHS, ...DEMO_PATHS];

test.describe("Console errors", () => {
  for (const path of ALL_PATHS) {
    test(`no JS errors on ${path}`, async ({ page }) => {
      const errors = [];

      page.on("console", (msg) => {
        if (msg.type() === "error") {
          errors.push(`console.error: ${msg.text()}`);
        }
      });
      page.on("pageerror", (err) => {
        errors.push(`pageerror: ${err.message}`);
      });
      page.on("response", (response) => {
        const status = response.status();
        if (status >= 400) {
          errors.push(`HTTP ${status}: ${response.url()}`);
        }
      });

      await page.goto(path);
      // Give Alpine init and any HTMX boot a moment to settle
      await page.waitForLoadState("networkidle").catch(() => {});

      writeReport(path, errors);

      if (errors.length > 0) {
        throw new Error(
          `${errors.length} JS error(s) on ${path}:\n  - ${errors.join("\n  - ")}`
        );
      }
      expect(errors).toEqual([]);
    });
  }
});
