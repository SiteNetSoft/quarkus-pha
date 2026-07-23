/**
 * phaDataView — Alpine component for the PatternFly data-view extension.
 *
 * Composes PF v6's existing Table + Toolbar + Pagination via client-side
 * state. The host passes the full row set in once (via data-rows JSON on the
 * root, or assignment after init); the component filters/sorts/paginates.
 *
 * Per-instance state lives on `this`; init() reads JSON config from
 * data-rows / data-columns / data-per-page on the root, sidestepping the
 * factory-closure leak documented in [[feedback_alpine_factory_closure_leak]].
 *
 * For server-driven flows (HTMX swap on filter change), skip this factory
 * and have the toolbar's input emit hx-get that swaps the table body.
 *
 * License: Apache 2.0
 */
phaAlpine("phaDataView", () => ({
  rows: [],
  columns: [],
  query: "",
  sortColumn: null,
  sortDir: "asc",
  page: 1,
  perPage: 5,
  selected: [], // row keys (row.name) selected across pages
  expanded: [], // row keys (row.name) whose detail row is open (expandable example)

  init() {
    const rawRows = this.$root.dataset.rows;
    const rawCols = this.$root.dataset.columns;
    const rawPP = this.$root.dataset.perPage;
    try {
      this.rows = rawRows ? JSON.parse(rawRows) : [];
      this.columns = rawCols ? JSON.parse(rawCols) : [];
    } catch (e) {
      // Bad JSON: leave the defaults; the rendered HTML will fall back to
      // the static <tbody> the server emitted, so the page still works.
      console.warn("phaDataView: failed to parse data-rows / data-columns", e);
    }
    if (rawPP) {
      const n = parseInt(rawPP, 10);
      if (!Number.isNaN(n) && n > 0) this.perPage = n;
    }
  },

  get filtered() {
    const q = (this.query || "").trim().toLowerCase();
    if (!q) return this.rows;
    return this.rows.filter((r) =>
      this.columns.some((col) => {
        const v = r[col.key];
        return v != null && String(v).toLowerCase().includes(q);
      }),
    );
  },

  get sorted() {
    if (this.sortColumn == null) return this.filtered;
    const col = this.sortColumn;
    const dir = this.sortDir === "desc" ? -1 : 1;
    return [...this.filtered].sort((a, b) => {
      const av = a[col];
      const bv = b[col];
      if (av == null && bv == null) return 0;
      if (av == null) return -dir;
      if (bv == null) return dir;
      if (typeof av === "number" && typeof bv === "number") return (av - bv) * dir;
      return String(av).localeCompare(String(bv)) * dir;
    });
  },

  get total() {
    return this.filtered.length;
  },

  get pageCount() {
    return Math.max(1, Math.ceil(this.total / this.perPage));
  },

  get visible() {
    const start = (this.page - 1) * this.perPage;
    return this.sorted.slice(start, start + this.perPage);
  },

  setQuery(q) {
    this.query = q;
    this.page = 1;
  },

  toggleSort(colKey) {
    if (this.sortColumn === colKey) {
      this.sortDir = this.sortDir === "asc" ? "desc" : "asc";
    } else {
      this.sortColumn = colKey;
      this.sortDir = "asc";
    }
  },

  setPage(p) {
    const max = this.pageCount;
    if (p < 1) p = 1;
    if (p > max) p = max;
    this.page = p;
  },

  nextPage() {
    this.setPage(this.page + 1);
  },

  prevPage() {
    this.setPage(this.page - 1);
  },

  setPerPage(n) {
    this.perPage = n;
    this.page = 1;
  },

  sortAria(colKey) {
    if (this.sortColumn !== colKey) return "none";
    return this.sortDir === "asc" ? "ascending" : "descending";
  },

  // --- row selection (optional; used by the selectable table example) ------
  isSelected(key) {
    return this.selected.includes(key);
  },

  toggleRow(key) {
    const i = this.selected.indexOf(key);
    if (i === -1) this.selected.push(key);
    else this.selected.splice(i, 1);
  },

  // select-all acts on the rows currently visible on the page
  get allVisibleSelected() {
    const vis = this.visible;
    return vis.length > 0 && vis.every((r) => this.selected.includes(r.name));
  },

  toggleAll() {
    const vis = this.visible.map((r) => r.name);
    if (this.allVisibleSelected) {
      this.selected = this.selected.filter((k) => !vis.includes(k));
    } else {
      vis.forEach((k) => {
        if (!this.selected.includes(k)) this.selected.push(k);
      });
    }
  },

  get selectedCount() {
    return this.selected.length;
  },

  // --- row expansion (optional; used by the expandable table example) -------
  isExpanded(key) {
    return this.expanded.includes(key);
  },

  toggleExpand(key) {
    const i = this.expanded.indexOf(key);
    if (i === -1) this.expanded.push(key);
    else this.expanded.splice(i, 1);
  },
}));
