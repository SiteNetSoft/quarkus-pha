/*
 * Rich text editor — Alpine.js component wrapping Quill
 *
 * Mounts a Quill editor on a surface div, exposes a small Alpine API for
 * reading / writing the HTML content, and keeps an optional hidden input
 * in sync so the editor can be submitted with a standard form (or via HTMX).
 *
 * Usage:
 *   <div x-data="phaRichTextEditor({
 *     theme: 'snow',
 *     placeholder: 'Write something…',
 *     toolbar: 'full',
 *     name: 'body'
 *   })">
 *     <div class="pha-c-rich-text-editor__surface"><p>Initial HTML…</p></div>
 *     <input type="hidden" name="body" x-ref="hidden">
 *   </div>
 *
 * Options:
 *   theme       — 'snow' (default) or 'bubble'
 *   placeholder — placeholder text shown when empty
 *   readOnly    — open in read-only mode (default: false)
 *   toolbar     — 'full' | 'basic' | 'minimal' (default: 'full')
 *                 'full'    : headings, formatting, lists, links, blocks, clean
 *                 'basic'   : bold/italic/underline + bullet/ordered lists + link
 *                 'minimal' : bold/italic/underline only
 *   name        — when set, mirrors HTML to a hidden <input name="..."> via x-ref="hidden"
 *
 * Events dispatched (Alpine $dispatch):
 *   editor-ready  — { editor }
 *   editor-change — { html, length, source } on user changes
 *
 * License: Apache 2.0
 */
const PHA_QUILL_TOOLBARS = {
  full: [
    [{ header: [1, 2, 3, false] }],
    ["bold", "italic", "underline", "strike"],
    [{ color: [] }, { background: [] }],
    [{ list: "ordered" }, { list: "bullet" }, { indent: "-1" }, { indent: "+1" }],
    ["blockquote", "code-block"],
    ["link", "image"],
    ["clean"],
  ],
  basic: [["bold", "italic", "underline"], [{ list: "ordered" }, { list: "bullet" }], ["link"]],
  minimal: [["bold", "italic", "underline"]],
};

phaAlpine("phaRichTextEditor", (config = {}) => ({
  ready: false,
  length: 0,
  _editor: null,
  _changeHandler: null,
  _settings: null,

  init() {
    if (typeof Quill === "undefined") {
      console.warn("phaRichTextEditor: Quill is not loaded");
      return;
    }

    const surface = this.$el.querySelector(".pha-c-rich-text-editor__surface");
    if (!surface) return;

    // The Qute template supplies config as data-* on the root; an explicit config
    // argument still wins so direct JS callers keep working.
    const ds = this.$root.dataset;
    const settings = {
      theme: config.theme ?? ds.theme,
      placeholder: config.placeholder ?? ds.placeholder,
      readOnly: config.readOnly ?? ds.readOnly === "true",
      toolbar: config.toolbar ?? ds.toolbar,
      name: config.name ?? ds.name,
    };
    this._settings = settings;

    const toolbarKey = settings.toolbar || "full";
    const toolbar = PHA_QUILL_TOOLBARS[toolbarKey] || PHA_QUILL_TOOLBARS.full;

    const modules = { toolbar: toolbar };

    this._editor = new Quill(surface, {
      theme: settings.theme === "bubble" ? "bubble" : "snow",
      placeholder: settings.placeholder || "",
      readOnly: !!settings.readOnly,
      modules: modules,
    });

    this.length = Math.max(0, this._editor.getLength() - 1);
    this._syncHidden();

    this._changeHandler = (_delta, _old, source) => {
      this.length = Math.max(0, this._editor.getLength() - 1);
      this._syncHidden();
      this.$dispatch("editor-change", {
        html: this.getHTML(),
        length: this.length,
        source: source,
      });
    };
    this._editor.on("text-change", this._changeHandler);

    this.ready = true;
    this.$dispatch("editor-ready", { editor: this._editor });
  },

  destroy() {
    if (this._editor && this._changeHandler) {
      this._editor.off("text-change", this._changeHandler);
    }
    this._editor = null;
  },

  _syncHidden() {
    if (this._settings && this._settings.name && this.$refs && this.$refs.hidden) {
      this.$refs.hidden.value = this.getHTML();
    }
  },

  /* ---- Public API ---- */

  getHTML() {
    if (!this._editor) return "";
    return this._editor.root.innerHTML;
  },

  setHTML(html) {
    if (!this._editor) return;
    this._editor.clipboard.dangerouslyPasteHTML(html || "");
    this._syncHidden();
  },

  getText() {
    return this._editor ? this._editor.getText() : "";
  },

  clear() {
    if (!this._editor) return;
    this._editor.setText("");
    this._syncHidden();
  },

  focus() {
    if (this._editor) this._editor.focus();
  },

  setReadOnly(flag) {
    if (this._editor) this._editor.enable(!flag);
  },
}));
