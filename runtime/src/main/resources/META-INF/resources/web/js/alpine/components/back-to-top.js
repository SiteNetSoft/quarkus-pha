/*
 * Back to top — Alpine.js component for PatternFly v6 back-to-top.
 *
 * Spies on scroll (window by default, or a specific element if
 * scrollableSelector is set) and toggles visibility once scrollY > 400.
 * Clicking the button smooth-scrolls the spied target back to 0.
 *
 * Usage (window scroll):
 *   <div class="pf-v6-c-back-to-top" x-data="phaBackToTop()" x-show="visible">
 *     <button @click="scrollToTop()">…</button>
 *   </div>
 *
 * Usage (scoped to a scrollable container):
 *   <div class="pf-v6-c-back-to-top"
 *        x-data="phaBackToTop('#my-scrollable-panel')"
 *        x-show="visible">…</div>
 *
 * `alwaysVisible` (set on x-data via phaBackToTop(selector, true)) skips the
 * scroll threshold and keeps the button visible.
 *
 * License: Apache 2.0
 */
phaAlpine("phaBackToTop", (scrollableSelector = null, alwaysVisible = false) => ({
  visible: false,
  scrollableSelector: scrollableSelector,
  alwaysVisible: alwaysVisible,

  init() {
    if (this.alwaysVisible) {
      this.visible = true;
      return;
    }
    const target = this._scrollTarget();
    if (!target) return;
    this._handler = () => {
      const y = target === window ? window.scrollY : target.scrollTop;
      this.visible = y > 400;
    };
    target.addEventListener("scroll", this._handler, { passive: true });
    this._handler();
  },

  destroy() {
    const target = this._scrollTarget();
    if (target && this._handler) {
      target.removeEventListener("scroll", this._handler);
    }
  },

  scrollToTop() {
    const target = this._scrollTarget();
    if (!target) return;
    if (target === window) {
      window.scrollTo({ top: 0, behavior: "smooth" });
    } else {
      target.scrollTo({ top: 0, behavior: "smooth" });
    }
  },

  _scrollTarget() {
    if (!this.scrollableSelector) return window;
    return document.querySelector(this.scrollableSelector);
  },
}));
