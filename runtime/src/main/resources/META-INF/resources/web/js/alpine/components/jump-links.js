/*
 * Jump links scrollspy — Alpine.js component for PatternFly v6 jump-links.
 *
 * Ports the react-core JumpLinks behavior for server-rendered lists: listens
 * to scroll on the scrollable container, marks the section furthest down the
 * page whose top has passed scrollTop + offset as current (pf-m-current +
 * aria-current="location" on its <li>), and turns link clicks into
 * offset-aware scrolls with the URL hash pushed manually. The offset steps
 * match patternfly.org's: below 1450px the collapsed toggle bar sticks over
 * the content, so more headroom is needed.
 *
 * Usage (the jump-links template emits this when the model sets scrollspy):
 *   <nav class="pf-v6-c-jump-links ..."
 *        x-data="phaJumpLinks({ scrollable: '#ws-page-main' })">
 *     ... server-rendered __list with #anchor links ...
 *   </nav>
 *
 * `open` seeds the toggle state the expandable shapes bind to; clicking a
 * link while the collapsed panel is open closes it, like patternfly.org.
 *
 * License: Apache 2.0
 */
phaAlpine("phaJumpLinks", (opts = {}) => ({
  open: opts.open ?? false,

  init() {
    this._scrollable = document.querySelector(opts.scrollable || "#ws-page-main");
    if (!this._scrollable) return;
    this._links = Array.from(this.$root.querySelectorAll('.pf-v6-c-jump-links__link a[href^="#"]'));
    this._skipSpy = false;
    for (const link of this._links) {
      link.addEventListener("click", (ev) => this._jump(ev, link));
    }
    this._handler = () => this._spy();
    this._scrollable.addEventListener("scroll", this._handler, { passive: true });
    this._spy();
  },

  destroy() {
    if (this._scrollable && this._handler) {
      this._scrollable.removeEventListener("scroll", this._handler);
    }
  },

  /* Headroom between the scrollport top and the line a section must cross
   * to count as current; patternfly.org's viewport steps. */
  _offset() {
    const width = window.innerWidth;
    if (width >= 1450) return 88;
    if (width >= 768) return 142;
    return 190;
  },

  _spy() {
    if (this._skipSpy) {
      this._skipSpy = false;
      return;
    }
    const position = Math.ceil(this._scrollable.scrollTop + this._offset());
    window.requestAnimationFrame(() => {
      let best = null;
      let bestTop = -1;
      for (const link of this._links) {
        const target = this._targetOf(link);
        if (!target) continue;
        if (target.offsetTop <= position && target.offsetTop >= bestTop) {
          best = link;
          bestTop = target.offsetTop;
        }
      }
      this._mark(best ?? this._links[0]);
    });
  },

  _jump(ev, link) {
    const target = this._targetOf(link);
    if (!target) return;
    ev.preventDefault();
    if (this.open) {
      this.open = false;
    }
    this._skipSpy = true;
    this._scrollable.scrollTo(0, target.offsetTop - this._offset());
    target.focus();
    window.history.pushState("", "", link.href);
    this._mark(link);
  },

  _mark(current) {
    for (const link of this._links) {
      const item = link.closest("li");
      if (!item) continue;
      if (link === current) {
        item.classList.add("pf-m-current");
        item.setAttribute("aria-current", "location");
      } else {
        item.classList.remove("pf-m-current");
        item.removeAttribute("aria-current");
      }
    }
  },

  _targetOf(link) {
    return document.getElementById(decodeURIComponent(link.getAttribute("href").slice(1)));
  },
}));
