/*
 * Drawer resize — Alpine.js component for PatternFly v6 resizable drawer panels.
 *
 * Mirrors PF React's DrawerPanelContent isResizable behavior: pointer drag on
 * the splitter (and arrow keys when it has focus) update the panel's
 * --pf-v6-c-drawer__panel--md--FlexBasis custom property, clamped to a
 * minimum size. aria-valuenow reflects the panel size as a percentage of the
 * drawer main area.
 *
 * Usage:
 *   <div class="pf-v6-c-drawer"
 *        x-data="phaDrawerResizable"
 *        data-position="end|start|bottom"
 *        data-default-size="500"
 *        data-min-size="150"
 *        :class="{ 'pf-m-expanded': expanded }">
 *     ...
 *     <div class="pf-v6-c-drawer__panel pf-m-resizable" x-ref="panel" x-show="expanded">
 *       <div class="pf-v6-c-drawer__splitter pf-m-vertical" x-ref="splitter"
 *            role="separator" tabindex="0" aria-orientation="vertical"
 *            @pointerdown="startResize" @keydown="keyResize">
 *         <div class="pf-v6-c-drawer__splitter-handle" aria-hidden="true"></div>
 *       </div>
 *       <div class="pf-v6-c-drawer__panel-main">...</div>
 *     </div>
 *   </div>
 *
 * Per-instance config comes from data-* attributes on the root (factory
 * arguments would be shared across instances on the same page).
 *
 * License: Apache 2.0
 */
phaAlpine("phaDrawerResizable", () => ({
  expanded: false,
  position: "end",
  minSize: 150,
  size: 500,

  init() {
    const d = this.$root.dataset;
    this.position = d.position || "end";
    this.minSize = parseInt(d.minSize || "150", 10);
    this.size = parseInt(d.defaultSize || "500", 10);
    this._applySize();
  },

  _applySize() {
    const panel = this.$refs.panel;
    if (!panel) return;
    panel.style.setProperty("--pf-v6-c-drawer__panel--md--FlexBasis", this.size + "px");
    const splitter = this.$refs.splitter;
    const main = this.$root.querySelector(".pf-v6-c-drawer__main");
    if (!splitter || !main) return;
    const total = this.position === "bottom" ? main.offsetHeight : main.offsetWidth;
    const pct = total > 0 ? Math.round((this.size / total) * 100) : 50;
    splitter.setAttribute("aria-valuenow", String(Math.min(100, Math.max(0, pct))));
  },

  startResize(e) {
    e.preventDefault();
    const splitter = e.currentTarget;
    splitter.setPointerCapture(e.pointerId);
    this.$root.classList.add("pf-m-resizing");
    const move = (ev) => {
      const rect = this.$refs.panel.getBoundingClientRect();
      let next;
      if (this.position === "start") {
        next = ev.clientX - rect.left;
      } else if (this.position === "bottom") {
        next = rect.bottom - ev.clientY;
      } else {
        next = rect.right - ev.clientX;
      }
      this.size = Math.max(this.minSize, Math.round(next));
      this._applySize();
    };
    const up = (ev) => {
      splitter.releasePointerCapture(ev.pointerId);
      splitter.removeEventListener("pointermove", move);
      splitter.removeEventListener("pointerup", up);
      this.$root.classList.remove("pf-m-resizing");
    };
    splitter.addEventListener("pointermove", move);
    splitter.addEventListener("pointerup", up);
  },

  keyResize(e) {
    const step = 5;
    let delta = 0;
    if (this.position === "bottom") {
      if (e.key === "ArrowUp") delta = step;
      else if (e.key === "ArrowDown") delta = -step;
    } else if (this.position === "start") {
      if (e.key === "ArrowRight") delta = step;
      else if (e.key === "ArrowLeft") delta = -step;
    } else {
      if (e.key === "ArrowLeft") delta = step;
      else if (e.key === "ArrowRight") delta = -step;
    }
    if (delta === 0) return;
    e.preventDefault();
    this.size = Math.max(this.minSize, this.size + delta);
    this._applySize();
  },
}));
