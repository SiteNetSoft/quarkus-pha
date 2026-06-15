import { test, expect } from "@playwright/test";
import AxeBuilder from "@axe-core/playwright";
import fs from "node:fs";
import path from "node:path";

// Run axe-core against every demo page. We assert on critical/serious
// violations only — moderate/minor findings are recorded in the per-page
// report so the team can triage them without failing the whole run on
// borderline issues.
//
// When A11Y_REPORT_DIR is set (e2e.sh wires this through Podman), each
// test writes a per-path JSON file with the full axe results. e2e.sh
// builds a summary.txt from those files after the run.

const REPORT_DIR = process.env.A11Y_REPORT_DIR || "";
const FAIL_IMPACTS = new Set(["critical", "serious"]);

function pathToSlug(p) {
  if (p === "/") return "index";
  return p.replace(/^\//, "").replace(/\//g, "__");
}

function writeReport(testPath, results, blockingCount) {
  if (!REPORT_DIR) return;
  fs.mkdirSync(REPORT_DIR, { recursive: true });
  const file = path.join(REPORT_DIR, `${pathToSlug(testPath)}.json`);
  // Trim noise: only keep what's useful for triage.
  const trimmed = {
    path: testPath,
    blocking: blockingCount,
    violations: results.violations.map((v) => ({
      id: v.id,
      impact: v.impact,
      description: v.description,
      help: v.help,
      helpUrl: v.helpUrl,
      nodes: v.nodes.map((n) => ({
        target: n.target,
        failureSummary: n.failureSummary,
      })),
    })),
  };
  fs.writeFileSync(file, JSON.stringify(trimmed, null, 2));
}

const COMPONENT_PATHS = [
  "/components/about-modal", "/components/accordion", "/components/action-list",
  "/components/alert", "/components/application-launcher", "/components/avatar",
  "/components/backdrop", "/components/background-image", "/components/back-to-top",
  "/components/badge", "/components/banner", "/components/brand",
  "/components/breadcrumb", "/components/button", "/components/calendar-month",
  "/components/card", "/components/chart", "/components/checkbox",
  "/components/chip", "/components/click-to-load", "/components/clipboard-copy",
  "/components/code-block", "/components/code-editor", "/components/content",
  "/components/context-selector", "/components/custom-menus", "/components/data-list",
  "/components/date-and-time-picker", "/components/date-picker", "/components/description-list",
  "/components/divider", "/components/document-editor", "/components/drag-and-drop",
  "/components/drawer", "/components/dropdown", "/components/dual-list-selector",
  "/components/empty-state", "/components/expandable-section", "/components/form",
  "/components/form-control", "/components/form-select", "/components/helper-text",
  "/components/hint", "/components/icon", "/components/infinite-scroll",
  "/components/inline-edit", "/components/input-group", "/components/jump-links",
  "/components/label", "/components/lazy-modal", "/components/list",
  "/components/form-validation", "/components/sortable-table", "/components/click-to-edit", "/components/toast-confirm", "/components/skeleton-loading", "/components/i18n", "/components/live-search", "/components/login-page", "/components/map",
  "/components/masthead", "/components/menu", "/components/menu-toggle",
  "/components/modal", "/components/multiple-file-upload", "/components/navigation",
  "/components/notification-badge", "/components/notification-drawer", "/components/number-input",
  "/components/options-menu", "/components/overflow-menu", "/components/page",
  "/components/pagination", "/components/panel", "/components/password-generator",
  "/components/password-strength", "/components/popover", "/components/progress",
  "/components/progress-stepper", "/components/radio", "/components/rectangle-selection",
  "/components/search-input", "/components/select", "/components/sidebar",
  "/components/simple-file-upload", "/components/simple-list", "/components/skeleton",
  "/components/skip-to-content", "/components/slider", "/components/spinner",
  "/components/switch", "/components/table", "/components/tabs",
  "/components/text-area", "/components/text-input", "/components/text-input-group",
  "/components/tile", "/components/hero", "/components/compass", "/components/time-picker", "/components/timestamp",
  "/components/title", "/components/toggle-group", "/components/toolbar",
  "/components/tooltip", "/components/tree-view", "/components/truncate",
  "/components/wizard"
];

const DEMO_PATHS = [
  "/demos/dashboard", "/demos/data-management", "/demos/settings", "/demos/landing"
];

const ALL_PATHS = ["/", ...COMPONENT_PATHS, ...DEMO_PATHS];

test.describe("Accessibility (axe)", () => {
  for (const p of ALL_PATHS) {
    test(`no critical/serious axe violations on ${p}`, async ({ page }) => {
      await page.goto(p);
      await page.waitForLoadState("networkidle").catch(() => {});

      const results = await new AxeBuilder({ page }).analyze();
      const blocking = results.violations.filter((v) => FAIL_IMPACTS.has(v.impact));

      writeReport(p, results, blocking.length);

      if (blocking.length > 0) {
        const lines = blocking.map(
          (v) => `[${v.impact}] ${v.id}: ${v.help} (${v.nodes.length} node(s))`
        );
        throw new Error(
          `${blocking.length} critical/serious axe violation(s) on ${p}:\n  - ${lines.join("\n  - ")}`
        );
      }
      expect(blocking).toEqual([]);
    });
  }
});
