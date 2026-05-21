/*
 * Editable label — Alpine.js component for PatternFly v6 label edit-in-place.
 *
 * Wraps a <span class="pf-v6-c-label pf-m-editable"> so the label text
 * becomes contenteditable on click. Enter commits, Esc cancels, blur
 * (click outside or Tab away) also commits.
 *
 * Usage:
 *   <span class="pf-v6-c-label pf-m-editable" x-data="phaLabelEditable()">
 *     <span class="pf-v6-c-label__content" tabindex="0">
 *       <span class="pf-v6-c-label__text" x-ref="text">Editable label</span>
 *     </span>
 *   </span>
 *
 * The component template (components/data-display/label) emits the
 * tabindex="0" and pf-m-editable class when editable=true; consumers add
 * the x-data binding and the x-ref="text" on the __text span.
 *
 * License: Apache 2.0
 */
phaAlpine("phaLabelEditable", () => ({
  active: false,
  previousText: "",

  start() {
    if (this.active) return;
    this.previousText = this.$refs.text.textContent;
    this.active = true;
    this.$el.classList.add("pf-m-editable-active");
    const text = this.$refs.text;
    text.setAttribute("contenteditable", "true");
    // Defer focus so the next tick can move the caret into the editable region.
    requestAnimationFrame(() => {
      text.focus();
      const range = document.createRange();
      range.selectNodeContents(text);
      const sel = window.getSelection();
      sel.removeAllRanges();
      sel.addRange(range);
    });
  },

  commit() {
    if (!this.active) return;
    this.active = false;
    this.$el.classList.remove("pf-m-editable-active");
    this.$refs.text.removeAttribute("contenteditable");
    this.$el.dispatchEvent(
      new CustomEvent("pha:label:edit-complete", {
        detail: { value: this.$refs.text.textContent },
        bubbles: true,
      }),
    );
  },

  cancel() {
    if (!this.active) return;
    this.$refs.text.textContent = this.previousText;
    this.active = false;
    this.$el.classList.remove("pf-m-editable-active");
    this.$refs.text.removeAttribute("contenteditable");
    this.$el.dispatchEvent(
      new CustomEvent("pha:label:edit-cancel", {
        detail: { value: this.previousText },
        bubbles: true,
      }),
    );
  },

  onKey(event) {
    if (!this.active) {
      if (event.key === "Enter" || event.key === " ") {
        event.preventDefault();
        this.start();
      }
      return;
    }
    if (event.key === "Enter") {
      event.preventDefault();
      this.commit();
    } else if (event.key === "Escape") {
      event.preventDefault();
      this.cancel();
    }
  },
}));
