/**
 * phaColumnResize — Alpine component for user-resizable table columns.
 *
 * Put x-data="phaColumnResize()" on a <table> laid out with table-layout: fixed
 * (so column widths hold). Each resizable <th> is position: relative and carries
 * a grip element whose @pointerdown calls start($event, <the th>). Dragging sets
 * the th's inline width; a document-level pointermove/up pair tracks the drag so
 * it keeps working even when the pointer leaves the narrow grip.
 *
 * State is per-instance on the returned object (no shared closure state), per
 * [[feedback_alpine_factory_closure_leak]].
 *
 * License: Apache 2.0
 */
phaAlpine("phaColumnResize", () => ({
  active: null, // the <th> currently being resized, or null
  startX: 0,
  startWidth: 0,
  minWidth: 80,
  _move: null,
  _up: null,

  init() {
    // Bind once so add/removeEventListener share the same references.
    this._move = (ev) => this.onMove(ev);
    this._up = () => this.onUp();
  },

  start(ev, th) {
    if (!th) return;
    this.active = th;
    this.startX = ev.clientX;
    this.startWidth = th.offsetWidth;
    document.addEventListener("pointermove", this._move);
    document.addEventListener("pointerup", this._up);
    // Stop text selection / header sort from firing during the drag.
    ev.preventDefault();
    ev.stopPropagation();
  },

  onMove(ev) {
    if (!this.active) return;
    let next = this.startWidth + (ev.clientX - this.startX);
    if (next < this.minWidth) next = this.minWidth;
    this.active.style.width = next + "px";
  },

  onUp() {
    this.active = null;
    document.removeEventListener("pointermove", this._move);
    document.removeEventListener("pointerup", this._up);
  },
}));
