/*
 * Tree view — Alpine.js component for PatternFly v6 tree-view.
 *
 * Implements the WAI-ARIA tree-view interaction pattern:
 *   - Roving focus: exactly one <li role="treeitem"> has tabindex="0" at any
 *     time; arrow keys move both focus and the tabindex. Tab enters the tree
 *     once, then arrows navigate within it.
 *   - Expand / collapse (per node):
 *       1. Whole-node-button pattern — clicking anywhere on the
 *          <button class="pf-v6-c-tree-view__node"> toggles its row.
 *       2. Separate toggle/text pattern — only the dedicated
 *          <button class="pf-v6-c-tree-view__node-toggle"> toggles; the
 *          <button class="pf-v6-c-tree-view__node-text"> selects without
 *          toggling (PF's hasSelectableNodes equivalent).
 *       3. Checkbox pattern — <label class="pf-v6-c-tree-view__node">
 *          wraps an <input type="checkbox">; the toggle button still expands
 *          and clicking the label toggles the checkbox.
 *     ArrowRight / ArrowLeft also expand / collapse per WAI-ARIA.
 *   - Selection: single-select by default; multi-select when the root
 *     <ul role="tree"> has aria-multiselectable="true". Selected <li> gets
 *     aria-selected="true" and the __node wrapper gets pf-m-current. Space
 *     and Enter on a focused item also toggle selection.
 *   - Checkbox cascade: checking a parent checks all descendants; unchecking
 *     all descendants unchecks the parent; partial state sets the parent's
 *     checkbox to indeterminate=true.
 *   - Search filter: handleSearchInput hides non-matching items and
 *     auto-expands matching subtrees so the matches are visible.
 *   - Bulk expandAll / collapseAll / toggleAll with reactive `allExpanded`.
 *
 * Expansion state per <li class="pf-v6-c-tree-view__list-item">:
 *   expanded   → adds `pf-m-expanded`, sets `aria-expanded="true"`,
 *                removes `inert` from the immediate child <ul>.
 *   collapsed  → removes `pf-m-expanded`, sets `aria-expanded="false"`,
 *                adds `inert=""` to the immediate child <ul>.
 *
 * Usage:
 *   <div x-data="phaTreeView()">
 *     <input @input="handleSearchInput($event)" type="search" ...>   <!-- optional -->
 *     <div class="pf-v6-c-tree-view">
 *       <ul class="pf-v6-c-tree-view__list" role="tree"
 *           aria-multiselectable="false"
 *           @keydown="handleKeyNav($event)"
 *           @click="handleClick($event)">
 *         ... items ...
 *       </ul>
 *     </div>
 *   </div>
 *
 * Options:
 *   initial  — 'collapsed' (default), 'expanded', or 'preserve'.
 *              Normalizes every list-item to the chosen state on init().
 *              'preserve' keeps the static markup and just syncs runtime flags.
 *
 * License: Apache 2.0
 */
phaAlpine("phaTreeView", (config = {}) => ({
  allExpanded: false,

  init() {
    const initial = config.initial || "collapsed";
    if (initial === "expanded") {
      this.expandAll();
    } else if (initial === "preserve") {
      this._syncAllExpanded();
    } else {
      this.collapseAll();
    }
    this._initRovingFocus();
    this._refreshAllCheckboxStates();
  },

  /* ---- bulk expand/collapse ---- */

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

  /* ---- click dispatcher ---- */

  handleClick(e) {
    if (!this.$root.contains(e.target)) return;

    const li = e.target.closest("li.pf-v6-c-tree-view__list-item");
    if (!li) return;

    /* Skip clicks on action-area buttons (e.g. row actions menu). */
    if (e.target.closest(".pf-v6-c-tree-view__action")) return;

    const toggleBtn = e.target.closest("button.pf-v6-c-tree-view__node-toggle");
    const textBtn = e.target.closest("button.pf-v6-c-tree-view__node-text");
    const nodeBtn = e.target.closest("button.pf-v6-c-tree-view__node");
    const checkbox = e.target.closest("input[type='checkbox']");

    this._setFocusedItem(li);

    /* Checkbox toggled directly — cascade after the native state change. */
    if (checkbox) {
      const self = this;
      requestAnimationFrame(function () {
        self._cascadeCheckbox(li, !!checkbox.checked);
      });
      return;
    }

    /* Toggle button (separate pattern, or sitting next to a label/checkbox). */
    if (toggleBtn) {
      e.preventDefault();
      e.stopPropagation();
      if (this._childList(li)) {
        this._setExpanded(li, !this._isExpanded(li));
        this._syncAllExpanded();
      }
      return;
    }

    /* Text button — select only (the separate-selection pattern). */
    if (textBtn) {
      e.preventDefault();
      this._toggleSelected(li);
      return;
    }

    /* Whole-node button — expand AND select in one action. */
    if (nodeBtn) {
      e.preventDefault();
      if (this._childList(li)) {
        this._setExpanded(li, !this._isExpanded(li));
        this._syncAllExpanded();
      }
      this._toggleSelected(li);
      return;
    }

    /*
     * Checkbox-pattern node wrapper: clicking anywhere inside a
     * .pf-v6-c-tree-view__node that owns a checkbox should toggle that
     * checkbox. Replaces the native <label for="..."> click forwarding,
     * which was dropped because <label> wrapping both a toggle button
     * and the checkbox input is HTML-invalid (multiple form descendants).
     */
    const nodeWrapper = e.target.closest(".pf-v6-c-tree-view__node");
    if (nodeWrapper) {
      const localCheck = nodeWrapper.querySelector("input[type='checkbox']");
      if (localCheck) {
        e.preventDefault();
        localCheck.checked = !localCheck.checked;
        const self = this;
        requestAnimationFrame(function () {
          self._cascadeCheckbox(li, !!localCheck.checked);
        });
      }
    }
  },

  /* ---- keyboard navigation ---- */

  handleKeyNav(e) {
    const currentLi = e.target.closest("li[role=treeitem]");
    if (!currentLi) return;

    /* Space / Enter — toggle selection (and expand if branch). */
    if (e.key === " " || e.key === "Spacebar" || e.key === "Enter") {
      e.preventDefault();
      this._toggleSelected(currentLi);
      if (e.key === "Enter" && this._childList(currentLi)) {
        this._setExpanded(currentLi, !this._isExpanded(currentLi));
        this._syncAllExpanded();
      }
      return;
    }

    if (e.key === "ArrowRight") {
      if (this._childList(currentLi)) {
        if (this._isExpanded(currentLi)) {
          const firstChild = currentLi.querySelector(":scope > ul.pf-v6-c-tree-view__list > li[role=treeitem]");
          if (firstChild) {
            e.preventDefault();
            this._setFocusedItem(firstChild);
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
        const parent = currentLi.parentElement && currentLi.parentElement.closest("li[role=treeitem]");
        if (parent) {
          e.preventDefault();
          this._setFocusedItem(parent);
        }
      }
      return;
    }

    const visible = this._visibleItems();
    const idx = visible.indexOf(currentLi);
    if (idx < 0) return;
    let next = -1;
    if (e.key === "ArrowDown") next = Math.min(idx + 1, visible.length - 1);
    else if (e.key === "ArrowUp") next = Math.max(idx - 1, 0);
    else if (e.key === "Home") next = 0;
    else if (e.key === "End") next = visible.length - 1;
    if (next >= 0 && next !== idx) {
      e.preventDefault();
      this._setFocusedItem(visible[next]);
    }
  },

  /* ---- search filter ---- */

  handleSearchInput(e) {
    const query = (e.target.value || "").trim().toLowerCase();
    const items = this._items();
    if (!query) {
      items.forEach(function (li) {
        li.style.display = "";
        delete li.dataset.phaTreeMatch;
      });
      return;
    }
    /* Mark items whose own label matches. */
    items.forEach((li) => {
      const textEl = li.querySelector(":scope > .pf-v6-c-tree-view__content .pf-v6-c-tree-view__node-text");
      const text = (textEl ? textEl.textContent : "").toLowerCase();
      li.dataset.phaTreeMatch = text.indexOf(query) >= 0 ? "self" : "none";
    });
    /* Propagate up: ancestor matches if any descendant matches. */
    items
      .slice()
      .reverse()
      .forEach((li) => {
        if (li.dataset.phaTreeMatch === "self") return;
        const kids = this._childList(li);
        if (!kids) return;
        const anyMatch = Array.from(kids.querySelectorAll(":scope > li.pf-v6-c-tree-view__list-item")).some(
          (c) => c.dataset.phaTreeMatch !== "none",
        );
        if (anyMatch) li.dataset.phaTreeMatch = "ancestor";
      });
    /* Apply visibility + auto-expand matches with kids. */
    items.forEach((li) => {
      const state = li.dataset.phaTreeMatch;
      li.style.display = state === "none" ? "none" : "";
      if ((state === "self" || state === "ancestor") && this._childList(li)) {
        this._setExpanded(li, true);
      }
    });
    this._syncAllExpanded();
  },

  /* ---- internals ---- */

  _items() {
    return Array.from(this.$root.querySelectorAll("li.pf-v6-c-tree-view__list-item"));
  },

  _visibleItems() {
    return this._items().filter(function (li) {
      return !li.closest("ul[inert]") && li.style.display !== "none";
    });
  },

  _childList(li) {
    return li.querySelector(":scope > ul.pf-v6-c-tree-view__list");
  },

  _nodeWrapper(li) {
    return li.querySelector(":scope > .pf-v6-c-tree-view__content > .pf-v6-c-tree-view__node");
  },

  _isExpanded(li) {
    return li.getAttribute("aria-expanded") === "true";
  },

  _setExpanded(li, expanded) {
    li.classList.toggle("pf-m-expanded", expanded);
    li.setAttribute("aria-expanded", expanded ? "true" : "false");
    const child = this._childList(li);
    if (child) {
      if (expanded) child.removeAttribute("inert");
      else child.setAttribute("inert", "");
    }
  },

  _syncAllExpanded() {
    const expandable = this._items().filter((li) => this._childList(li));
    this.allExpanded = expandable.length > 0 && expandable.every((li) => this._isExpanded(li));
  },

  /* Roving focus — exactly one item is tabindex=0; arrow keys move it. */

  _initRovingFocus() {
    const items = this._items();
    if (!items.length) return;
    items.forEach(function (li) {
      li.setAttribute("tabindex", "-1");
    });
    /* Pick the currently selected item if any, otherwise the first. */
    const initial =
      items.find(function (li) {
        return li.getAttribute("aria-selected") === "true";
      }) || items[0];
    initial.setAttribute("tabindex", "0");
  },

  _setFocusedItem(li) {
    this._items().forEach(function (other) {
      other.setAttribute("tabindex", other === li ? "0" : "-1");
    });
    /*
     * Focus the wrapper element actually responsible for receiving focus:
     * either the <button> / <label> / <div> __node, or the <li> itself.
     */
    const nodeEl = this._nodeWrapper(li);
    if (nodeEl && (nodeEl.tagName === "BUTTON" || nodeEl.tagName === "A")) {
      nodeEl.focus();
    } else {
      li.focus();
    }
  },

  /* Selection — aria-selected on <li>, pf-m-current on the __node wrapper. */

  _isMultiSelectable() {
    const tree = this.$root.querySelector("ul.pf-v6-c-tree-view__list[role='tree']");
    return !!(tree && tree.getAttribute("aria-multiselectable") === "true");
  },

  _toggleSelected(li) {
    const multi = this._isMultiSelectable();
    const wasSelected = li.getAttribute("aria-selected") === "true";
    if (!multi) {
      /* Clear other selections. */
      this._items().forEach((other) => {
        if (other === li) return;
        if (other.getAttribute("aria-selected") === "true") {
          other.setAttribute("aria-selected", "false");
          const n = this._nodeWrapper(other);
          if (n) n.classList.remove("pf-m-current");
        }
      });
    }
    const next = !wasSelected;
    li.setAttribute("aria-selected", next ? "true" : "false");
    const nodeEl = this._nodeWrapper(li);
    if (nodeEl) nodeEl.classList.toggle("pf-m-current", next);
  },

  /* Checkbox cascade — parent ↔ children, indeterminate for partial. */

  _checkboxOf(li) {
    return li.querySelector(":scope > .pf-v6-c-tree-view__content input[type='checkbox']");
  },

  _directChildItems(li) {
    const kids = this._childList(li);
    if (!kids) return [];
    return Array.from(kids.querySelectorAll(":scope > li.pf-v6-c-tree-view__list-item"));
  },

  _cascadeCheckbox(li, checked) {
    /* Push state down to every descendant checkbox. */
    const descendants = li.querySelectorAll("li.pf-v6-c-tree-view__list-item input[type='checkbox']");
    descendants.forEach(function (cb) {
      cb.checked = checked;
      cb.indeterminate = false;
    });
    /* Walk up; ancestors reflect their children's aggregate state. */
    let parent = li.parentElement && li.parentElement.closest("li.pf-v6-c-tree-view__list-item");
    while (parent) {
      this._updateParentCheckbox(parent);
      parent = parent.parentElement && parent.parentElement.closest("li.pf-v6-c-tree-view__list-item");
    }
  },

  _updateParentCheckbox(li) {
    const cb = this._checkboxOf(li);
    if (!cb) return;
    const kids = this._directChildItems(li);
    if (!kids.length) return;
    const childCbs = kids
      .map((c) => this._checkboxOf(c))
      .filter(function (c) {
        return !!c;
      });
    if (!childCbs.length) return;
    const checked = childCbs.filter(function (c) {
      return c.checked;
    }).length;
    const indeterminate = childCbs.filter(function (c) {
      return c.indeterminate;
    }).length;
    if (checked === childCbs.length && indeterminate === 0) {
      cb.checked = true;
      cb.indeterminate = false;
    } else if (checked === 0 && indeterminate === 0) {
      cb.checked = false;
      cb.indeterminate = false;
    } else {
      cb.checked = false;
      cb.indeterminate = true;
    }
  },

  _refreshAllCheckboxStates() {
    /*
     * On init, walk leaves → root once so any initial checkbox state in the
     * markup (e.g. some children pre-checked) propagates to parent
     * indeterminate / checked correctly.
     */
    this._items()
      .slice()
      .reverse()
      .forEach((li) => {
        if (this._directChildItems(li).length) {
          this._updateParentCheckbox(li);
        }
      });
  },
}));
