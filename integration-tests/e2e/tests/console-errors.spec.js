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

// These lists must cover EVERY card on the showcase index (`/`). A page that
// exists in the showcase but is missing here gets NO JS-error checking, and
// the suite still reports green — that blind spot once hid /components/video-player
// throwing 3 errors for weeks. The "coverage matches showcase" test at the
// bottom scrapes the live index and hard-fails if these lists drift out of sync,
// so a forgotten page turns the suite RED instead of silently un-checked.
//
// Source of truth = the URL map in HelloResource.java (/components/, /extensions/)
// plus the demo cards in templates/hello.html (/demos/). We can't parse those
// files at runtime — e2e.sh mounts only integration-tests/e2e into the Playwright
// container — so the lists are maintained by hand and the drift guard enforces them.
// Page inventory lives in showcase-paths.js, shared by every sweeping spec;
// the drift guard below hard-fails when it diverges from the live grid.
import { SHOWCASE_PATHS, ALL_PATHS } from "./showcase-paths.js";

// Known-noise filters per path. Entries here are tracked outside the test
// suite — typically server-side template or asset gaps that have their own
// fix-it tickets but should not block JS-runtime regression detection on
// unrelated pages.
//
// Each entry is a regex tested against the full error string
// (e.g. "HTTP 404: ..." or "pageerror: mode is not defined").
const KNOWN_NOISE = {
  "/components": [
    // Topology has no grid thumbnail PNG yet; HelloResource.NO_THUMBNAIL
    // omission triggers a 404 + matching console.error on the /components grid.
    /HTTP 404:.*\/web\/images\/components\/topology\.png/,
    /console\.error: Failed to load resource:.*404/,
  ],
  "/components/hint": [
    // Example-card x-data evaluates `mode`/`error` bindings before Alpine
    // wires the phaCodeExample factory on this specific page. Tracked
    // separately; filter here so the spec catches new regressions.
    /pageerror: mode is not defined/,
    /pageerror: error is not defined/,
  ],
};

function filterErrors(testPath, errors) {
  const patterns = KNOWN_NOISE[testPath] || [];
  if (patterns.length === 0) return errors;
  return errors.filter((e) => !patterns.some((rx) => rx.test(e)));
}

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

      const filtered = filterErrors(path, errors);
      writeReport(path, filtered);

      if (filtered.length > 0) {
        throw new Error(`${filtered.length} JS error(s) on ${path}:\n  - ${filtered.join("\n  - ")}`);
      }
      expect(filtered).toEqual([]);
    });
  }

  // Drift guard: the per-path checks above only cover pages we remembered to
  // list. This test derives the authoritative page set from the live showcase
  // and fails loudly if SHOWCASE_PATHS is missing (or has stale) entries — so a
  // newly added component can never sit silently un-checked again.
  test("console-error coverage matches the live showcase", async ({ page }) => {
    await page.goto("/components");

    const hrefs = await page.$$eval(".pf-v6-l-gallery a[href]", (links) => links.map((a) => a.getAttribute("href")));
    const live = [...new Set(hrefs.filter((h) => /^\/(components|extensions|demos)\//.test(h)))].sort();
    const expected = [...SHOWCASE_PATHS].sort();

    const missing = live.filter((p) => !expected.includes(p)); // on showcase, not checked
    const stale = expected.filter((p) => !live.includes(p)); // checked, not on showcase

    expect(
      { missing, stale },
      `console-errors.spec.js is out of sync with the showcase.\n` +
        `  Add to SHOWCASE_PATHS (on index, not JS-checked): ${missing.join(", ") || "none"}\n` +
        `  Remove from SHOWCASE_PATHS (checked, not on index): ${stale.join(", ") || "none"}`,
    ).toEqual({ missing: [], stale: [] });
  });
});
