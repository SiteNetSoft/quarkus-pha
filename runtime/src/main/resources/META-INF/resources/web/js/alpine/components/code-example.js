/*
 * Code example — Alpine.js component that wraps a demo example with a
 * collapsible Monaco editor showing the underlying Qute source.
 *
 * Lazy-loads Monaco (AMD bundle from /web/vendor/monaco/vs) the first time the
 * user expands the panel. The source is fetched as plain text from the URL
 * provided by `sourceHref`. The editor is created read-only.
 *
 * Usage:
 *   <div x-data="phaCodeExample({sourceHref:'/components/tree-view/source/single-selectable'})">
 *     <button type="button" :aria-expanded="open" @click="toggle()">Qute</button>
 *     <div x-show="open" x-ref="host" style="height: 400px"></div>
 *   </div>
 *
 * Options:
 *   sourceHref  — URL returning the raw template text
 *   language    — Monaco language id (default 'html')
 *   monacoBase  — path to the Monaco vs/ dir (default '/web/vendor/monaco/vs')
 *
 * License: Apache 2.0
 */
(function () {
  let monacoReady = null;

  function loadMonaco(base) {
    if (monacoReady) return monacoReady;
    monacoReady = new Promise(function (resolve, reject) {
      let s = document.createElement("script");
      s.src = base + "/loader.js";
      s.onload = function () {
        window.require.config({ paths: { vs: base } });
        window.require(["vs/editor/editor.main"], function () {
          resolve(window.monaco);
        });
      };
      s.onerror = function () {
        monacoReady = null;
        reject(new Error("Failed to load Monaco loader from " + base));
      };
      document.head.appendChild(s);
    });
    return monacoReady;
  }

  phaAlpine("phaCodeExample", (config = {}) => ({
    open: false,
    loaded: false,
    loading: false,
    error: null,
    _editor: null,

    init() {
      if (!config.sourceHref && this.$root.dataset.sourceHref) {
        config.sourceHref = this.$root.dataset.sourceHref;
      }
    },

    async toggle() {
      this.open = !this.open;
      if (this.open && !this.loaded && !this.loading) {
        await this._mount();
      }
    },

    async copy() {
      if (!this._editor) {
        try {
          await this._fetchSource();
        } catch (e) {
          this.error = String(e);
          return;
        }
      }
      let value = this._editor ? this._editor.getValue() : this._pendingSource || "";
      try {
        await navigator.clipboard.writeText(value);
      } catch (_) {
        /* ignore — clipboard may be unavailable in non-secure contexts */
      }
    },

    async _fetchSource() {
      if (this._pendingSource != null) return this._pendingSource;
      let r = await fetch(config.sourceHref, {
        headers: { Accept: "text/plain" },
      });
      if (!r.ok) throw new Error("Source fetch failed: " + r.status);
      this._pendingSource = await r.text();
      return this._pendingSource;
    },

    async _mount() {
      this.loading = true;
      this.error = null;
      try {
        let [monaco, source] = await Promise.all([
          loadMonaco(config.monacoBase || "/web/vendor/monaco/vs"),
          this._fetchSource(),
        ]);
        await this.$nextTick();
        this._editor = monaco.editor.create(this.$refs.host, {
          value: source,
          language: config.language || "html",
          readOnly: true,
          automaticLayout: true,
          minimap: { enabled: false },
          scrollBeyondLastLine: false,
          fontSize: 13,
          lineNumbers: "on",
          renderLineHighlight: "none",
        });
        this.loaded = true;
      } catch (e) {
        this.error = e && e.message ? e.message : String(e);
      } finally {
        this.loading = false;
      }
    },
  }));
})();
