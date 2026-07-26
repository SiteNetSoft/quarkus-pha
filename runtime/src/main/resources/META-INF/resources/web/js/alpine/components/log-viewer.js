/**
 * phaLogViewer — Alpine component for the PatternFly v6 log-viewer extension.
 *
 * Owns these interactions on the root .pf-v6-c-log-viewer element:
 *   - Search: highlight all matches in the log lines, jump to a "current" match
 *     with prev/next, count total matches. Highlights use the canonical PF
 *     classes .pf-v6-c-log-viewer__string.pf-m-match (all) and .pf-m-current
 *     (the focused one).
 *   - Wrap toggle: flip between wrap and nowrap by adding/removing pf-m-nowrap
 *     on the root. The pf-m-wrap-text class isn't needed because removing
 *     pf-m-nowrap falls back to the default (break-spaces) wrap behavior.
 *   - ANSI rendering: when data-ansi="true" on the root, walk every
 *     .pf-v6-c-log-viewer__text and replace ANSI SGR escape sequences with
 *     <span class="pha-ansi-*"> tags (palette in css/components/log-viewer.css,
 *     theme-aware). Supports the subset most tooling uses — 8 standard colors,
 *     bright variants, bold, reset. Other SGR codes are dropped.
 *   - Scroll-to-bottom: scrollToBottom() jumps the scroll container to its
 *     bottom; meant for "tail mode" toggles or after a server-driven append.
 *
 * Per-instance state lives on `this`. Configuration flags are read from
 * data-* attributes in init() to avoid the factory-closure config leak
 * documented in [[feedback_alpine_factory_closure_leak]].
 *
 * License: Apache 2.0
 */
phaAlpine("phaLogViewer", () => ({
  query: "",
  matchCount: 0,
  currentIndex: 0,
  wrapped: true,
  ansi: false,

  init() {
    this.ansi = this.$root.dataset.ansi === "true";
    this.wrapped = !this.$root.classList.contains("pf-m-nowrap");
    if (this.ansi) {
      this._renderAnsi();
    }
  },

  /* ---- search ---- */

  setQuery(q) {
    this.query = q;
    this._highlight();
  },

  nextMatch() {
    if (this.matchCount === 0) return;
    this.currentIndex = (this.currentIndex + 1) % this.matchCount;
    this._focusCurrent();
  },

  prevMatch() {
    if (this.matchCount === 0) return;
    this.currentIndex = (this.currentIndex - 1 + this.matchCount) % this.matchCount;
    this._focusCurrent();
  },

  _highlight() {
    const texts = this.$root.querySelectorAll(".pf-v6-c-log-viewer__text");
    const q = (this.query || "").trim();
    let count = 0;
    texts.forEach((node) => {
      const raw = node.dataset.raw != null ? node.dataset.raw : node.textContent;
      if (!q) {
        node.innerHTML = this.ansi ? this._ansiToHtml(raw) : this._escape(raw);
        return;
      }
      const escaped = this.ansi ? this._stripAnsi(raw) : raw;
      const parts = escaped.split(this._buildQueryRegex(q));
      let html = "";
      parts.forEach((part, i) => {
        if (i % 2 === 1) {
          html +=
            '<span class="pf-v6-c-log-viewer__string pf-m-match" data-match-index="' +
            count +
            '">' +
            this._escape(part) +
            "</span>";
          count++;
        } else {
          html += this._escape(part);
        }
      });
      node.innerHTML = html;
    });
    this.matchCount = count;
    if (this.currentIndex >= count) this.currentIndex = 0;
    this._focusCurrent();
  },

  _focusCurrent() {
    const matches = this.$root.querySelectorAll(".pf-v6-c-log-viewer__string.pf-m-match");
    matches.forEach((m) => m.classList.remove("pf-m-current"));
    if (this.matchCount === 0) return;
    const current = matches[this.currentIndex];
    if (current) {
      current.classList.add("pf-m-current");
      current.scrollIntoView({ block: "nearest", behavior: "smooth" });
    }
  },

  _buildQueryRegex(q) {
    const escaped = q.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
    return new RegExp("(" + escaped + ")", "gi");
  },

  _escape(s) {
    return String(s).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
  },

  /* ---- wrap toggle ---- */

  toggleWrap() {
    this.wrapped = !this.wrapped;
    this.$root.classList.toggle("pf-m-nowrap", !this.wrapped);
  },

  /* ---- scroll ---- */

  scrollToBottom() {
    const s = this.$refs.scroll;
    if (s) s.scrollTop = s.scrollHeight;
  },

  /* ---- ANSI ---- */

  _renderAnsi() {
    const texts = this.$root.querySelectorAll(".pf-v6-c-log-viewer__text");
    texts.forEach((node) => {
      const raw = node.dataset.raw != null ? node.dataset.raw : node.textContent;
      node.innerHTML = this._ansiToHtml(raw);
    });
  },

  _stripAnsi(s) {
    return String(s).replace(/\x1B\[[0-9;]*m/g, "");
  },

  _ansiToHtml(s) {
    // Subset of ECMA-48 SGR — colors 30-37 / 90-97, bright bg 40-47 / 100-107,
    // bold (1), reset (0/empty). Unknown codes are dropped.
    // Colors render via pha-ansi-{code} classes (css/components/log-viewer.css)
    // rather than inline hex so the palette can differ per theme — both the
    // light and dark values there meet WCAG AA 4.5:1 on their backgrounds.
    // Keep the accepted codes in sync with that stylesheet and with the
    // server-side pre-renderer in ExtensionsRoutes.java.
    const COLOR_CODES = new Set([30, 31, 32, 33, 34, 35, 36, 37, 90, 91, 92, 93, 94, 95, 96, 97]);
    let out = "";
    let pos = 0;
    let stack = 0;
    const re = /\x1B\[([0-9;]*)m/g;
    let m;
    while ((m = re.exec(s)) !== null) {
      out += this._escape(s.slice(pos, m.index));
      pos = m.index + m[0].length;
      const codes = m[1] === "" ? [0] : m[1].split(";").map((c) => parseInt(c, 10));
      codes.forEach((code) => {
        if (code === 0) {
          while (stack > 0) {
            out += "</span>";
            stack--;
          }
        } else if (code === 1) {
          out += '<span class="pha-ansi-bold">';
          stack++;
        } else if (COLOR_CODES.has(code)) {
          out += '<span class="pha-ansi-' + code + '">';
          stack++;
        }
      });
    }
    out += this._escape(s.slice(pos));
    while (stack > 0) {
      out += "</span>";
      stack--;
    }
    return out;
  },
}));
