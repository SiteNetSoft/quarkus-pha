/*
 * Code example — Alpine.js component that wraps a demo example with a
 * collapsible Monaco editor showing either the underlying Qute source or the
 * rendered HTML output (after Qute processing).
 *
 * Lazy-loads Monaco (AMD bundle from /web/vendor/monaco/vs) the first time the
 * user opens a view. URLs are read from data-source-href (raw Qute),
 * data-rendered-href (the standalone HTML page; we extract the <main> body)
 * and optionally data-java-href (the Java code that builds the example's
 * model) on the x-data element. The editor is created read-only and follows
 * the page's light/dark/contrast theme via a MutationObserver on <html> class.
 *
 * Usage:
 *   <div
 *     data-source-href="/components/x/source/y"
 *     data-rendered-href="/components/x/y"
 *     x-data="phaCodeExample()"
 *   >
 *     <button :aria-expanded="mode === 'qute'" @click="toggleMode('qute')">Qute</button>
 *     <button :aria-expanded="mode === 'html'" @click="toggleMode('html')">HTML</button>
 *     <div x-show="mode" x-ref="host" style="height: 400px"></div>
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
      const s = document.createElement("script");
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
    const cl = document.documentElement.classList;
    const dark = cl.contains("pf-v6-theme-dark");
    const hc = cl.contains("pf-v6-theme-high-contrast");
    if (dark && hc) return "hc-black";
    if (dark) return "vs-dark";
    if (hc) return "hc-light";
    return "vs";
  }

  function installThemeObserver() {
    if (themeObserverInstalled) return;
    themeObserverInstalled = true;
    const obs = new MutationObserver(function () {
      if (window.monaco && window.monaco.editor) {
        window.monaco.editor.setTheme(currentMonacoTheme());
      }
    });
    obs.observe(document.documentElement, {
      attributes: true,
      attributeFilter: ["class"],
    });
  }

  phaAlpine("phaCodeExample", () => {
    // Non-reactive per-instance state. Alpine's reactivity wraps the returned
    // object in a Proxy; storing Monaco's editor or cached source strings on
    // `this` would route every internal Monaco property access through that
    // Proxy, which freezes the main thread on `setValue`. Keep these in the
    // factory closure so they stay raw.
    let editor = null;
    let quteSource = null;
    let htmlSource = null;
    let javaSource = null;

    async function fetchSource(self, target) {
      if (target === "java") {
        if (javaSource != null) return javaSource;
        const r = await fetch(self.javaHref, {
          headers: { Accept: "text/plain" },
        });
        if (!r.ok) throw new Error("Java source fetch failed: " + r.status);
        javaSource = await r.text();
        return javaSource;
      }
      if (target === "qute") {
        if (quteSource != null) return quteSource;
        const r = await fetch(self.sourceHref, {
          headers: { Accept: "text/plain" },
        });
        if (!r.ok) throw new Error("Source fetch failed: " + r.status);
        quteSource = await r.text();
        return quteSource;
      }
      if (htmlSource != null) return htmlSource;
      const r = await fetch(self.renderedHref, {
        headers: { Accept: "text/html" },
      });
      if (!r.ok) throw new Error("Rendered fetch failed: " + r.status);
      const doc = new DOMParser().parseFromString(await r.text(), "text/html");
      const main = doc.querySelector("main");
      if (!main) throw new Error("No <main> in rendered page");
      htmlSource = main.innerHTML.trim();
      return htmlSource;
    }

    return {
      mode: null, // null | 'java' | 'qute' | 'html'
      loading: false,
      error: null,
      sourceHref: null,
      renderedHref: null,
      javaHref: null,

      init() {
        this.sourceHref = this.$root.dataset.sourceHref;
        this.renderedHref = this.$root.dataset.renderedHref;
        this.javaHref = this.$root.dataset.javaHref || null;
      },

      destroy() {
        if (editor) {
          editor.dispose();
          editor = null;
        }
      },

      async toggleMode(target) {
        if (this.mode === target) {
          this.mode = null;
          return;
        }
        this.mode = target;
        await this._show(target);
      },

      async copy() {
        const target = this.mode || "qute";
        let value;
        try {
          value = await fetchSource(this, target);
        } catch (e) {
          this.error = String(e);
          return;
        }
        try {
          await navigator.clipboard.writeText(value);
        } catch {
          /* ignore — clipboard may be unavailable in non-secure contexts */
        }
      },

      async _show(target) {
        this.loading = true;
        this.error = null;
        try {
          const [monaco, source] = await Promise.all([loadMonaco("/web/vendor/monaco/vs"), fetchSource(this, target)]);
          installThemeObserver();
          await this.$nextTick();
          const language = target === "java" ? "java" : "html";
          if (!editor) {
            editor = monaco.editor.create(this.$refs.host, {
              value: source,
              language: language,
              readOnly: true,
              automaticLayout: true,
              minimap: { enabled: false },
              scrollBeyondLastLine: false,
              fontSize: 13,
              lineNumbers: "on",
              renderLineHighlight: "none",
              theme: currentMonacoTheme(),
            });
          } else {
            monaco.editor.setModelLanguage(editor.getModel(), language);
            editor.setValue(source);
          }
        } catch (e) {
          this.error = e && e.message ? e.message : String(e);
        } finally {
          this.loading = false;
        }
      },
    };
  });
})();
