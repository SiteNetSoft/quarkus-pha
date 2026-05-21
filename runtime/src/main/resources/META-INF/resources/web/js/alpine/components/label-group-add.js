/*
 * Label group add — Alpine.js component for the "editable label-group with add
 * button" PF v6 example. Manages a free-form text input that appends new
 * labels to a label-group list when the user presses Enter or clicks Add.
 *
 * Usage:
 *   <div x-data="phaLabelGroupAdd()">
 *     <template x-ref="template">
 *       <li class="pf-v6-c-label-group__list-item">...</li>
 *     </template>
 *     <ul class="pf-v6-c-label-group__list" x-ref="list">
 *       <li ...>existing label</li>
 *       <li x-ref="overflowSlot">
 *         <form @submit.prevent="addLabel()">
 *           <input x-model="draft">
 *           <button :disabled="!draft.trim()">Add</button>
 *         </form>
 *       </li>
 *     </ul>
 *   </div>
 *
 * License: Apache 2.0
 */
phaAlpine("phaLabelGroupAdd", () => ({
  next: 1,
  draft: "",

  init() {
    // Seed `next` based on existing labels so generated ids don't collide.
    const existing = this.$refs.list?.querySelectorAll(".pf-v6-c-label[id]") ?? [];
    this.next = existing.length + 1;
  },

  addLabel() {
    const text = this.draft.trim();
    if (!text) return;
    const tpl = this.$refs.template.content.cloneNode(true);
    const li = tpl.firstElementChild;
    const span = li.querySelector(".pf-v6-c-label");
    if (span) span.id = `lbg-add-${this.next}`;
    const textNode = li.querySelector(".pf-v6-c-label__text");
    if (textNode) textNode.textContent = text;
    this.$refs.list.insertBefore(li, this.$refs.overflowSlot);
    this.draft = "";
    this.next++;
  },
}));
