import { test, expect } from "@playwright/test";
import fs from "node:fs";
import path from "node:path";

// HTMX target-contract check.
//
// For every element on a page with an hx-{get,post,put,delete,patch}
// attribute, an hx-target selector (when present) must resolve to at
// least one element in the rendered DOM. Otherwise the swap silently
// goes nowhere — a class of bug no validator/linter sees.
//
// Special target values are handled:
//   (no attr)   — implicit self target, always PASS
//   "this"      — always PASS
//   "body"      — always PASS (every page has a body)
//   "closest X" — PASS if X resolves anywhere on the page (best effort:
//                 we can't statically know which trigger fires)
//   "find X"    — same as closest
//   "next X"    — same
//   "previous X"— same
//   selector    — PASS iff document.querySelector(selector) matches

const REPORT_DIR = process.env.HTMX_TARGETS_REPORT_DIR || "";

function pathToSlug(p) {
  if (p === "/") return "index";
  return p.replace(/^\//, "").replace(/\//g, "__");
}

function writeReport(testPath, results) {
  if (!REPORT_DIR) return;
  fs.mkdirSync(REPORT_DIR, { recursive: true });
  const file = path.join(REPORT_DIR, `${pathToSlug(testPath)}.json`);
  fs.writeFileSync(file, JSON.stringify({ path: testPath, ...results }, null, 2));
}

// Page inventory shared with every sweeping spec (console-errors carries the
// drift guard that keeps it in sync with the live grid).
import { ALL_PATHS } from "./showcase-paths.js";

test.describe("HTMX swap target contract", () => {
  for (const p of ALL_PATHS) {
    test(`hx-target selectors resolve on ${p}`, async ({ page }) => {
      await page.goto(p);
      await page.waitForLoadState("networkidle").catch(() => {});

      const findings = await page.evaluate(() => {
        const HTMX_VERB_ATTRS = ["hx-get", "hx-post", "hx-put", "hx-delete", "hx-patch"];
        const RELATIVE_PREFIXES = ["closest ", "find ", "next ", "previous "];

        function describeTrigger(el) {
          const tag = el.tagName.toLowerCase();
          const id = el.id ? `#${el.id}` : "";
          const cls =
            el.className && typeof el.className === "string"
              ? "." + el.className.trim().split(/\s+/).slice(0, 2).join(".")
              : "";
          return `<${tag}${id}${cls}>`;
        }

        function checkSelector(sel) {
          try {
            return document.querySelector(sel) !== null;
          } catch (_e) {
            return false;
          }
        }

        const triggers = [];
        for (const attr of HTMX_VERB_ATTRS) {
          for (const el of document.querySelectorAll(`[${attr}]`)) {
            triggers.push(el);
          }
        }

        const broken = [];
        const passed = [];
        for (const el of triggers) {
          const target = el.getAttribute("hx-target");
          const desc = describeTrigger(el);
          if (target == null || target === "this" || target === "body") {
            passed.push({ trigger: desc, target: target ?? "(implicit self)" });
            continue;
          }
          let selectorToCheck = target;
          for (const prefix of RELATIVE_PREFIXES) {
            if (target.startsWith(prefix)) {
              selectorToCheck = target.slice(prefix.length).trim();
              break;
            }
          }
          if (checkSelector(selectorToCheck)) {
            passed.push({ trigger: desc, target });
          } else {
            broken.push({ trigger: desc, target });
          }
        }

        return {
          triggers: triggers.length,
          passed: passed.length,
          broken,
        };
      });

      writeReport(p, findings);

      if (findings.broken.length > 0) {
        const lines = findings.broken.map((b) => `${b.trigger} hx-target="${b.target}" — selector matches no element`);
        throw new Error(`${findings.broken.length} unresolved hx-target(s) on ${p}:\n  - ${lines.join("\n  - ")}`);
      }
      expect(findings.broken).toEqual([]);
    });
  }
});
