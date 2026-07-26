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
