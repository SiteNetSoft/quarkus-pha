/*
 * Tree view — Alpine.js component for PatternFly v6 tree-view.
 *
 * Provides:
 *   - Roving-focus arrow-key navigation across [role=treeitem] (Up/Down/Home/End).
 *   - Per-node expand/collapse via click. Two PF v6 markup patterns are
 *     supported:
 *       1) Whole-node button — `<button class="pf-v6-c-tree-view__node">` —
 *          clicking anywhere on it toggles the containing list-item.
 *       2) Separate toggle button (PF's hasSelectableNodes pattern) — the
 *          node wrapper is a `<div>` with two child buttons; only
 *          `<button class="pf-v6-c-tree-view__node-toggle">` toggles.
 *          The `__node-text` button selects without toggling (no JS here).
 *   - WAI-ARIA tree expand/collapse via ArrowRight/ArrowLeft.
 *   - Bulk expandAll() / collapseAll() / toggleAll() with reactive `allExpanded`
 *     for binding to a header button (e.g. "Expand all" ↔ "Collapse all").
 *
 * Expansion state per <li class="pf-v6-c-tree-view__list-item">:
 *   expanded   → adds `pf-m-expanded`, sets `aria-expanded="true"`,
 *                removes `inert` from immediate child <ul>.
 *   collapsed  → removes `pf-m-expanded`, sets `aria-expanded="false"`,
 *                adds `inert=""` to immediate child <ul>.
 *
 * Usage (basic, just a tree):
 *   <div class="pf-v6-c-tree-view" x-data="phaTreeView()">
 *     <ul class="pf-v6-c-tree-view__list" role="tree"
 *         @keydown="handleKeyNav($event)"
 *         @click="handleToggle($event)">
 *       ...
 *     </ul>
 *   </div>
 *
 * Usage (with an "Expand all / Collapse all" button outside the tree):
 *   <div x-data="phaTreeView()">
 *     <button type="button" @click="toggleAll()">
 *       <span x-text="allExpanded ? 'Collapse all' : 'Expand all'">Expand all</span>
 *     </button>
 *     <div class="pf-v6-c-tree-view">
 *       <ul class="pf-v6-c-tree-view__list" role="tree"
 *           @keydown="handleKeyNav($event)"
 *           @click="handleToggle($event)">...</ul>
 *     </div>
 *   </div>
 *
 * Options:
 *   initial  — 'collapsed' (default), 'expanded', or 'preserve'.
 *              On init(), normalizes every list-item to the chosen state.
 *              'preserve' leaves the static markup untouched and just syncs
 *              the `allExpanded` flag from current DOM state.
 *
 * License: Apache 2.0
 */
phaAlpine("phaTreeView", (config = {}) => ({
  allExpanded: false,

  init() {
    let initial = config.initial || "collapsed";
    if (initial === "expanded") {
      this.expandAll();
    } else if (initial === "preserve") {
      this._syncAllExpanded();
    } else {
      this.collapseAll();
    }
  },

  expandAll() {
    this._items().forEach((li) => this._setExpanded(li, true));
    this.allExpanded = true;
  },

  collapseAll() {
    this._items().forEach((li) => this._setExpanded(li, false));
    this.allExpanded = false;
  },

  toggleAll() {
    if (this.allExpanded) this.collapseAll();
    else this.expandAll();
  },

  handleToggle(e) {
    /*
     * Toggle fires for clicks anywhere on:
     *   - <button class="pf-v6-c-tree-view__node">         (whole-node button)
     *   - <button class="pf-v6-c-tree-view__node-toggle">  (separate toggle)
     * NOT for clicks on:
     *   - <button class="pf-v6-c-tree-view__node-text">    (selection only)
     *   - anything outside the tree (e.g. an external "Expand all" button)
     */
    let btn = e.target.closest("button");
    if (!btn || !this.$root.contains(btn)) return;
    let triggersToggle =
      btn.classList.contains("pf-v6-c-tree-view__node") ||
      btn.classList.contains("pf-v6-c-tree-view__node-toggle");
    if (!triggersToggle) return;
    let li = btn.closest("li.pf-v6-c-tree-view__list-item");
    if (!li || !this._childList(li)) return;
    e.preventDefault();
    e.stopPropagation();
    this._setExpanded(li, !this._isExpanded(li));
    this._syncAllExpanded();
  },

  handleKeyNav(e) {
    let currentLi = e.target.closest("li[role=treeitem]");
    if (!currentLi) return;

    if (e.key === "ArrowRight") {
      if (this._childList(currentLi)) {
        if (this._isExpanded(currentLi)) {
          let firstChild = currentLi.querySelector(
            ":scope > ul.pf-v6-c-tree-view__list > li[role=treeitem]"
          );
          if (firstChild) {
            e.preventDefault();
            this._focus(firstChild);
          }
        } else {
          e.preventDefault();
          this._setExpanded(currentLi, true);
          this._syncAllExpanded();
        }
      }
      return;
    }

    if (e.key === "ArrowLeft") {
      if (this._childList(currentLi) && this._isExpanded(currentLi)) {
        e.preventDefault();
        this._setExpanded(currentLi, false);
        this._syncAllExpanded();
      } else {
        let parent =
          currentLi.parentElement &&
          currentLi.parentElement.closest("li[role=treeitem]");
        if (parent) {
          e.preventDefault();
          this._focus(parent);
        }
      }
      return;
    }

    let visible = this._items().filter((li) => !li.closest("ul[inert]"));
    let idx = visible.indexOf(currentLi);
    if (idx < 0) return;
    let next = -1;
    if (e.key === "ArrowDown") next = Math.min(idx + 1, visible.length - 1);
    else if (e.key === "ArrowUp") next = Math.max(idx - 1, 0);
    else if (e.key === "Home") next = 0;
    else if (e.key === "End") next = visible.length - 1;
    if (next >= 0 && next !== idx) {
      e.preventDefault();
      this._focus(visible[next]);
    }
  },

  _items() {
    return Array.from(
      this.$root.querySelectorAll("li.pf-v6-c-tree-view__list-item")
    );
  },

  _childList(li) {
    return li.querySelector(":scope > ul.pf-v6-c-tree-view__list");
  },

  _isExpanded(li) {
    return li.getAttribute("aria-expanded") === "true";
  },

  _setExpanded(li, expanded) {
    li.classList.toggle("pf-m-expanded", expanded);
    li.setAttribute("aria-expanded", expanded ? "true" : "false");
    let child = this._childList(li);
    if (child) {
      if (expanded) child.removeAttribute("inert");
      else child.setAttribute("inert", "");
    }
  },

  _syncAllExpanded() {
    let expandable = this._items().filter((li) => this._childList(li));
    this.allExpanded =
      expandable.length > 0 && expandable.every((li) => this._isExpanded(li));
  },

  _focus(li) {
    let btn = li.querySelector(
      ":scope > .pf-v6-c-tree-view__content > .pf-v6-c-tree-view__node"
    );
    if (btn) btn.focus();
  },
}));
