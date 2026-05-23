/*
 * Code example — Alpine.js component that wraps a demo example with a
 * collapsible Monaco editor showing the underlying Qute source.
 *
 * Lazy-loads Monaco (AMD bundle from /web/vendor/monaco/vs) the first time the
 * user expands the panel. The source URL is read from data-source-href on the
 * x-data element. The editor is created read-only and follows the page's
 * light/dark/contrast theme via a MutationObserver on <html> class.
 *
 * Usage:
 *   <div data-source-href="/components/x/source/y" x-data="phaCodeExample()">
 *     <button type="button" :aria-expanded="open" @click="toggle()">Qute</button>
 *     <div x-show="open" x-ref="host" style="height: 400px"></div>
 *   </div>
 *
 * License: Apache 2.0
 */
(function () {
  let monacoReady = null;
  let themeObserverInstalled = false;

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

  function currentMonacoTheme() {
    let cl = document.documentElement.classList;
    let dark = cl.contains("pf-v6-theme-dark");
    let hc = cl.contains("pf-v6-theme-high-contrast");
    if (dark && hc) return "hc-black";
    if (dark) return "vs-dark";
    if (hc) return "hc-light";
    return "vs";
  }

  function installThemeObserver() {
    if (themeObserverInstalled) return;
    themeObserverInstalled = true;
    let obs = new MutationObserver(function () {
      if (window.monaco && window.monaco.editor) {
        window.monaco.editor.setTheme(currentMonacoTheme());
      }
    });
    obs.observe(document.documentElement, {
      attributes: true,
      attributeFilter: ["class"],
    });
  }

  phaAlpine("phaCodeExample", () => ({
    open: false,
    loaded: false,
    loading: false,
    error: null,
    sourceHref: null,
    _editor: null,
    _pendingSource: null,

    init() {
      this.sourceHref = this.$root.dataset.sourceHref;
    },

    destroy() {
      if (this._editor) {
        this._editor.dispose();
        this._editor = null;
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
      let r = await fetch(this.sourceHref, {
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
          loadMonaco("/web/vendor/monaco/vs"),
          this._fetchSource(),
        ]);
        installThemeObserver();
        await this.$nextTick();
        this._editor = monaco.editor.create(this.$refs.host, {
          value: source,
          language: "html",
          readOnly: true,
          automaticLayout: true,
          minimap: { enabled: false },
          scrollBeyondLastLine: false,
          fontSize: 13,
          lineNumbers: "on",
          renderLineHighlight: "none",
          theme: currentMonacoTheme(),
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
