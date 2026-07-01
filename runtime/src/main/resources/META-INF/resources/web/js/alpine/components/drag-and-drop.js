/*
 * Drag and drop — Alpine.js factory for PatternFly v6 reorderable lists.
 *
 * Pointer-based, matching PF's DragDropSort (which wraps dnd-kit + its DragOverlay). Two pieces:
 *   - a floating OVERLAY: a clone of the dragged row, appended to <body>, lifted (.pha-dd-grabbed)
 *     and following the pointer freely in 2D — this is the thing under your cursor;
 *   - the original row stays IN the list as a faded PLACEHOLDER (.pha-dd-placeholder) that
 *     live-reorders to the insertion point, so you see where it will land (the greyed "reflection").
 * The insertion slot is read from a frozen snapshot of the slot midpoints taken at drag start, so the
 * rows reflowing around the placeholder can never feed back into the hit-test (the cause of the
 * oscillation a naive live-reorder shows). Displaced rows slide via FLIP; on release the overlay
 * animates into the committed slot and is removed, revealing the (un-faded) original.
 *
 * Multi-zone capable — state is `lists` keyed by zone id, so one factory drives a single sortable
 * list, multiple drop zones, a sortable DataList, and a drag-enabled dual list selector. Per-instance
 * data is read from data-* attributes in init() (never closed over — see the code-example.js leak).
 *
 * Keyboard: each row carries a [data-dd-handle] button; ArrowUp / ArrowDown reorder within the zone
 *           and announce the move via an aria-live region — the accessible alternative PF ships.
 * Persist:  when the root has data-persist-url, every committed change POSTs the new order (zone +
 *           comma-joined ids) via htmx.ajax, swapping #dd-persist-status — the server owns the order.
 *
 * Data shape (data-lists, JSON): { "main": [ { "id": "a", "label": "Plan release" }, ... ] }
 * Each draggable row must expose: data-dd-item, data-zone="<zone>", :data-flip-id="item.id".
 * Each zone container must expose: data-dd-zone="<zone>".
 *
 * License: Apache 2.0
 */
phaAlpine("phaDragDrop", () => ({
  lists: {},
  selected: {}, // dual-list pane selection: { available: id|null, chosen: id|null }
  persistUrl: null,
  announce: "",
  drag: null, // active pointer drag (see onPointerDown for shape)
  touched: new Set(),
  _move: null,
  _up: null,

  root: null, // captured from $root in init — $root/$nextTick are Alpine magics that only exist
  // during directive evaluation, not when our window pointer listeners call back in

  init() {
    this.root = this.$root;
    try {
      this.lists = JSON.parse(this.root.dataset.lists || "{}");
    } catch (e) {
      this.lists = {};
    }
    this.persistUrl = this.root.dataset.persistUrl || null;
  },

  // --- pointer drag -------------------------------------------------------
  onPointerDown(zone, i, e) {
    if (e.button != null && e.button !== 0) return; // primary button / touch only
    const row = e.target.closest("[data-dd-item]");
    if (!row) return;
    const rect = row.getBoundingClientRect();
    this.drag = {
      id: row.dataset.flipId,
      el: row,
      ghost: null, // the floating overlay clone
      grabX: e.clientX - rect.left,
      grabY: e.clientY - rect.top,
      w: rect.width,
      h: rect.height,
      startX: e.clientX,
      startY: e.clientY,
      lastX: e.clientX,
      lastY: e.clientY,
      slots: {}, // zone -> [mid, ...] frozen at drag start (the slot the pointer falls into)
      active: false,
    };
    this._move = (ev) => this.onPointerMove(ev);
    this._up = (ev) => this.onPointerUp(ev);
    window.addEventListener("pointermove", this._move);
    window.addEventListener("pointerup", this._up);
    window.addEventListener("pointercancel", this._up);
  },

  onPointerMove(e) {
    const d = this.drag;
    if (!d) return;
    d.lastX = e.clientX;
    d.lastY = e.clientY;
    if (!d.active) {
      // small threshold so a plain click (e.g. to focus the grip for keyboard use) is not a drag
      if (Math.abs(e.clientX - d.startX) < 4 && Math.abs(e.clientY - d.startY) < 4) return;
      this.activateDrag();
    }
    e.preventDefault();
    this.positionGhost(e.clientX, e.clientY);
    const zone = this.zoneAt(e.clientX, e.clientY) || this.zoneOf(d.id);
    this.moveItemTo(zone, this.slotIndex(zone, e.clientY));
  },

  // freeze the slot midpoints, fade the original into a placeholder, and spawn the overlay clone
  activateDrag() {
    const d = this.drag;
    d.active = true;
    this.touched = new Set();
    document.body.classList.add("pha-dd-dragging");
    this.captureSlots();
    const g = d.el.cloneNode(true); // 2D-cursor-following overlay (dnd-kit's DragOverlay)
    // x-ignore so Alpine's observer leaves the clone alone — otherwise it re-evaluates the cloned
    // x-text="item.label" with no item in scope and wipes the overlay's text
    g.setAttribute("x-ignore", "");
    g.classList.add("pha-dd-grabbed");
    g.classList.remove("pha-dd-placeholder");
    // strip the hooks our DOM queries match on, so the clone is invisible to captureSlots/flip
    g.removeAttribute("data-flip-id");
    g.removeAttribute("data-dd-item");
    g.style.position = "fixed";
    g.style.margin = "0";
    g.style.boxSizing = "border-box";
    g.style.width = d.w + "px";
    g.style.height = d.h + "px";
    g.style.zIndex = "1000";
    g.style.pointerEvents = "none";
    // append INSIDE the original's parent (not <body>) so PatternFly's context-scoped CSS still
    // applies — a data-list/dual-list item cloned to <body> loses its list-ancestor styling
    d.el.parentNode.appendChild(g);
    d.ghost = g;
    this.positionGhost(d.lastX, d.lastY);
    d.el.classList.add("pha-dd-placeholder"); // the faded original left in the list
  },

  positionGhost(x, y) {
    const g = this.drag.ghost;
    if (!g) return;
    g.style.left = x - this.drag.grabX + "px";
    g.style.top = y - this.drag.grabY + "px";
  },

  captureSlots() {
    const d = this.drag;
    d.slots = {};
    this.root.querySelectorAll("[data-dd-item]").forEach((el) => {
      const zone = el.dataset.zone;
      const r = el.getBoundingClientRect();
      (d.slots[zone] = d.slots[zone] || []).push(r.top + r.height / 2);
    });
  },

  zoneOf(id) {
    for (const zone of Object.keys(this.lists)) {
      if (this.lists[zone].some((x) => x.id === id)) return zone;
    }
    return null;
  },

  // which zone container is the pointer over (live rects — zone identity only)
  zoneAt(x, y) {
    let zone = null;
    this.root.querySelectorAll("[data-dd-zone]").forEach((c) => {
      const r = c.getBoundingClientRect();
      if (x >= r.left && x <= r.right && y >= r.top && y <= r.bottom) zone = c.dataset.ddZone;
    });
    return zone;
  },

  // slot under the pointer, from the FROZEN midpoints — immune to the rows currently reflowing
  slotIndex(zone, y) {
    const mids = this.drag.slots[zone] || [];
    let k = 0;
    for (let n = 0; n < mids.length; n++) {
      if (y > mids[n]) k++;
      else break;
    }
    return k;
  },

  // move the placeholder to slot k of `zone` (live array reorder). Every row — including the faded
  // placeholder — slides to its new position via FLIP.
  moveItemTo(zone, k) {
    const d = this.drag;
    if (!this.lists[zone]) return;
    const fromZone = this.zoneOf(d.id);
    const fromIdx = this.lists[fromZone].findIndex((x) => x.id === d.id);
    const to = fromZone === zone ? Math.min(k, this.lists[zone].length - 1) : Math.min(k, this.lists[zone].length);
    if (fromZone === zone && to === fromIdx) return;
    this.flip(null, () => {
      const item = this.lists[fromZone].splice(fromIdx, 1)[0];
      this.lists[zone].splice(to, 0, item);
      this.touched.add(fromZone);
      this.touched.add(zone);
    });
    // Zones are SEPARATE x-for loops, so a cross-zone move makes Alpine destroy the original node
    // and create a fresh one in the new zone — our d.el reference goes stale. Re-acquire the live
    // node by id and re-apply the placeholder class (otherwise the new zone shows no border, and
    // drop reads a detached rect of (0,0) and flies the overlay to the corner).
    if (fromZone !== zone) {
      window.Alpine.nextTick(() => {
        if (!this.drag) return;
        const fresh = this.row(d.id);
        if (fresh) {
          this.drag.el = fresh;
          fresh.classList.add("pha-dd-placeholder");
        }
      });
    }
  },

  // the live DOM node for an item id (re-resolved because cross-zone moves recreate the node)
  row(id) {
    return this.root.querySelector('[data-dd-item][data-flip-id="' + id + '"]');
  },

  onPointerUp() {
    const d = this.drag;
    window.removeEventListener("pointermove", this._move);
    window.removeEventListener("pointerup", this._up);
    window.removeEventListener("pointercancel", this._up);
    this._move = this._up = null;
    if (!d) return;
    if (d.active) {
      this.dropGhost();
      this.touched.forEach((z) => this.persist(z));
      this.swallowNextClick();
      document.body.classList.remove("pha-dd-dragging");
    }
    this.touched.clear();
    this.drag = null;
  },

  // reveal the (un-faded) original at its committed slot and fly the overlay clone into it, then drop
  dropGhost() {
    const d = this.drag;
    // re-resolve the live node (a cross-zone drop recreated it; d.el may be detached → rect (0,0),
    // which would fling the overlay to the top-left corner)
    const el = this.row(d.id) || d.el;
    el.classList.remove("pha-dd-placeholder");
    const g = d.ghost;
    if (!g) return;
    const target = el.getBoundingClientRect();
    const gr = g.getBoundingClientRect();
    g.style.transition = "transform 200ms ease";
    g.style.transform = "translate(" + (target.left - gr.left) + "px, " + (target.top - gr.top) + "px)";
    const done = () => {
      if (g.parentNode) g.parentNode.removeChild(g);
    };
    g.addEventListener("transitionend", done);
    setTimeout(done, 200);
  },

  // a pointer drag is followed by a synthetic click; swallow it so item @click (dual-list select)
  // does not fire after a drag
  swallowNextClick() {
    const swallow = (ev) => {
      ev.stopPropagation();
      ev.preventDefault();
      window.removeEventListener("click", swallow, true);
    };
    window.addEventListener("click", swallow, true);
    requestAnimationFrame(() => window.removeEventListener("click", swallow, true));
  },

  // --- FLIP: slide every row (except the dragged one) from its old box to its new one -----
  flip(skipId, mutate) {
    const before = {};
    this.root.querySelectorAll("[data-flip-id]").forEach((el) => {
      const r = el.getBoundingClientRect();
      before[el.dataset.flipId] = { top: r.top, left: r.left };
    });
    mutate();
    window.Alpine.nextTick(() => {
      this.root.querySelectorAll("[data-flip-id]").forEach((el) => {
        const id = el.dataset.flipId;
        if (id === skipId) return;
        const prev = before[id];
        if (!prev) return;
        el.style.transition = "none";
        el.style.transform = "";
        const now = el.getBoundingClientRect();
        const dx = prev.left - now.left;
        const dy = prev.top - now.top;
        if (Math.abs(dx) < 1 && Math.abs(dy) < 1) {
          el.style.transition = "";
          return;
        }
        el.style.transform = "translate(" + dx + "px, " + dy + "px)";
        el.getBoundingClientRect();
        requestAnimationFrame(() => {
          el.style.transition = "transform 200ms ease";
          el.style.transform = "";
        });
      });
    });
  },

  // --- dual list selector pane moves (the keyboard / button accessible path) --------------
  select(zone, id) {
    this.selected[zone] = this.selected[zone] === id ? null : id;
  },

  isSelected(zone, id) {
    return this.selected[zone] === id;
  },

  moveSelected(fromZone, toZone) {
    const id = this.selected[fromZone];
    if (id == null) return;
    const a = this.lists[fromZone];
    const idx = a.findIndex((x) => x.id === id);
    if (idx < 0) return;
    const item = a.splice(idx, 1)[0];
    this.lists[toZone].push(item);
    this.selected[fromZone] = null;
    this.announce = item.label + " moved to " + toZone + ".";
    this.persist(fromZone);
    this.persist(toZone);
  },

  // --- keyboard reorder ---------------------------------------------------
  moveUp(zone, i) {
    const a = this.lists[zone];
    if (!a || i <= 0) return;
    this.flip(null, () => {
      [a[i - 1], a[i]] = [a[i], a[i - 1]];
    });
    this.afterKeyMove(zone, i - 1);
  },

  moveDown(zone, i) {
    const a = this.lists[zone];
    if (!a || i >= a.length - 1) return;
    this.flip(null, () => {
      [a[i + 1], a[i]] = [a[i], a[i + 1]];
    });
    this.afterKeyMove(zone, i + 1);
  },

  afterKeyMove(zone, newIdx) {
    const a = this.lists[zone];
    this.persist(zone);
    this.announce = a[newIdx].label + " moved to position " + (newIdx + 1) + " of " + a.length + ".";
    window.Alpine.nextTick(() => {
      const sel = '[data-dd-handle][data-zone="' + zone + '"][data-id="' + a[newIdx].id + '"]';
      const el = this.root.querySelector(sel);
      if (el) el.focus();
    });
  },

  // --- persistence --------------------------------------------------------
  persist(zone) {
    if (!this.persistUrl || !window.htmx) return;
    const order = (this.lists[zone] || []).map((x) => x.id).join(",");
    window.htmx.ajax("POST", this.persistUrl, {
      values: { zone: zone, order: order },
      target: "#dd-persist-status",
      swap: "innerHTML",
    });
  },
}));
