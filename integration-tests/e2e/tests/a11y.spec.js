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

function writeReport(testPath, results, blockingCount, suffix = "") {
  if (!REPORT_DIR) return;
  fs.mkdirSync(REPORT_DIR, { recursive: true });
  const file = path.join(REPORT_DIR, `${pathToSlug(testPath)}${suffix}.json`);
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

// Page inventory shared with every sweeping spec (console-errors carries the
// drift guard that keeps it in sync with the live grid).
import { ALL_PATHS } from "./showcase-paths.js";

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

// The same sweep repeated per themed rendering mode. Dark rides
// prefers-color-scheme emulation — the head boot script defaults its stored
// preference to "system" and applies the theme class from first paint,
// exactly like a real visit. High-contrast and glass seed the stored
// preference before the page loads instead (glass has no media query, and
// prefers-contrast emulation is unavailable here). Each sweep guards that the
// theme class actually applied, otherwise it would silently degrade into a
// duplicate light-theme scan.
const THEME_SWEEPS = [
  {
    name: "dark theme",
    suffix: "__dark",
    themeClass: "pf-v6-theme-dark",
    use: { colorScheme: "dark" },
  },
  {
    // prefers-contrast emulation is not available as a context option in this
    // Playwright version, so seed the stored preference instead — the boot
    // script honors "high" ahead of the media query.
    name: "high-contrast theme",
    suffix: "__high-contrast",
    themeClass: "pf-v6-theme-high-contrast",
    initScript: () => localStorage.setItem("pha-contrast", "high"),
  },
  {
    name: "glass theme",
    suffix: "__glass",
    themeClass: "pf-v6-theme-glass",
    initScript: () => localStorage.setItem("pha-contrast", "glass"),
  },
];

for (const theme of THEME_SWEEPS) {
  test.describe(`Accessibility (axe, ${theme.name})`, () => {
    if (theme.use) test.use(theme.use);
    if (theme.initScript) {
      test.beforeEach(async ({ page }) => {
        await page.addInitScript(theme.initScript);
      });
    }

    for (const p of ALL_PATHS) {
      test(`no critical/serious axe violations on ${p} in ${theme.name}`, async ({ page }) => {
        await page.goto(p);
        await page.waitForLoadState("networkidle").catch(() => {});
        await page.waitForFunction(
          (cls) => document.documentElement.classList.contains(cls),
          theme.themeClass
        );

        const results = await new AxeBuilder({ page }).analyze();
        const blocking = results.violations.filter((v) => FAIL_IMPACTS.has(v.impact));

        writeReport(p, results, blocking.length, theme.suffix);

        if (blocking.length > 0) {
          const lines = blocking.map(
            (v) => `[${v.impact}] ${v.id}: ${v.help} (${v.nodes.length} node(s))`
          );
          throw new Error(
            `${blocking.length} critical/serious axe violation(s) on ${p} in ${theme.name}:\n  - ${lines.join("\n  - ")}`
          );
        }
        expect(blocking).toEqual([]);
      });
    }
  });
}
