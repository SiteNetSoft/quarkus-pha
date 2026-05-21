/*
 * Breadcrumb dropdown — Alpine.js component for PatternFly v6 breadcrumb
 * items with isDropdown=true.
 *
 * Manages the open/close state of the inline menu that hangs off a
 * breadcrumb crumb. Click the trigger to toggle, click outside or press
 * Esc to close.
 *
 * Usage:
 *   <div class="pf-v6-c-breadcrumb__dropdown" x-data="phaBreadcrumbDropdown()">
 *     <button @click="toggle()" :aria-expanded="open.toString()">…</button>
 *     <div class="pf-v6-c-menu" x-show="open" x-cloak
 *          @click.outside="close()">…</div>
 *   </div>
 *
 * The runtime breadcrumb-item template (components/navigation/breadcrumb-item)
 * emits this markup automatically when isDropdown=true.
 *
 * License: Apache 2.0
 */
phaAlpine("phaBreadcrumbDropdown", () => ({
  open: false,

  toggle() {
    this.open = !this.open;
  },

  close() {
    this.open = false;
  },
}));
