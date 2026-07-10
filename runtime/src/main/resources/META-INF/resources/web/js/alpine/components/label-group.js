/*
 * Label group — Alpine.js component for PatternFly v6 label-group.
 *
 * Manages overflow (collapse extra labels behind an "N more" toggle) and
 * exposes hooks for the editable label-group examples.
 *
 * Usage:
 *   <div class="pf-v6-c-label-group" x-data="phaLabelGroup(3)" ...>
 *     <ul class="pf-v6-c-label-group__list" x-ref="list">
 *       <li class="pf-v6-c-label-group__list-item">...</li>
 *       ...
 *       <li class="pf-v6-c-label-group__list-item pha-label-group__overflow"
 *           x-show="overflowVisible" x-cloak>
 *         <button @click="toggle()" :aria-expanded="expanded.toString()">
 *           <span x-text="expanded ? expandedText : `${hiddenCount} more`"></span>
 *         </button>
 *       </li>
 *     </ul>
 *   </div>
 *
 * `numLabels` controls how many chips stay visible when collapsed.
 *
 * License: Apache 2.0
 */
phaAlpine("phaLabelGroup", (numLabels = 3, startExpanded = false) => ({
  numLabels: numLabels,
  expanded: startExpanded,
  hiddenCount: 0,
  collapsedTextTemplate: "REMAINING_PLACEHOLDER more",
  expandedText: "Show Less",

  init() {
    this.collapsedTextTemplate = this.$el.dataset.collapsedText || this.collapsedTextTemplate;
    this.expandedText = this.$el.dataset.expandedText || this.expandedText;
    this.applyOverflow();
  },

  get items() {
    if (!this.$refs.list) return [];
    return Array.from(this.$refs.list.querySelectorAll(":scope > li:not(.pha-label-group__overflow)"));
  },

  get overflowVisible() {
    return this.items.length > this.numLabels;
  },

  applyOverflow() {
    // PF sets display:inline-flex on __list-item, which outranks the UA [hidden] rule —
    // an inline display:none is required for the collapse to actually hide items.
    const hide = (li, hidden) => {
      li.hidden = hidden;
      li.style.display = hidden ? "none" : "";
    };
    const items = this.items;
    if (items.length <= this.numLabels || this.expanded) {
      this.hiddenCount = 0;
      items.forEach((li) => hide(li, false));
      return;
    }
    this.hiddenCount = items.length - this.numLabels;
    items.forEach((li, i) => hide(li, i >= this.numLabels));
  },

  toggle() {
    this.expanded = !this.expanded;
    this.applyOverflow();
  },

  remove(labelId) {
    const item = this.$refs.list.querySelector(`#${CSS.escape(labelId)}`);
    if (!item) return;
    const li = item.closest("li.pf-v6-c-label-group__list-item");
    if (li) li.remove();
    this.applyOverflow();
  },
}));
