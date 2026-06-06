/*
 * Theme selector — Alpine.js component for the PatternFly v6 theme switcher
 * (matches patternfly.org's current three-section toggle menu).
 *
 * Three independent choices, each toggling a pf-v6-theme-* class on <html>:
 *   theme        default | felt           -> pf-v6-theme-felt
 *   colorScheme  system  | light | dark   -> pf-v6-theme-dark
 *   contrast     system  | default | high | glass
 *                  high  -> pf-v6-theme-high-contrast
 *                  glass -> pf-v6-theme-glass
 *                  system -> follow the OS (prefers-contrast: more)
 *
 * Persisted in localStorage (pha-theme-variant / pha-color-scheme / pha-contrast);
 * "system" choices follow the OS via matchMedia and live-update on change. The early
 * inline script in layouts/_head.html applies the stored classes before first paint.
 *
 * Usage (rendered by components/navigation/theme-selector):
 *   <div x-data="phaThemeSelector()" @click.outside="open = false"> … </div>
 *
 * License: Apache 2.0
 */
phaAlpine("phaThemeSelector", () => ({
  open: false,
  theme: "default", // default | felt
  colorScheme: "system", // system | light | dark
  contrast: "system", // system | default | high | glass

  init() {
    this.theme = localStorage.getItem("pha-theme-variant") || "default";
    this.colorScheme = localStorage.getItem("pha-color-scheme") || "system";
    this.contrast = localStorage.getItem("pha-contrast") || "system";
    this.applyTheme();
    this.applyColorScheme();
    this.applyContrast();
    this._darkMq = window.matchMedia("(prefers-color-scheme: dark)");
    this._contrastMq = window.matchMedia("(prefers-contrast: more)");
    this._onDarkChange = () => {
      if (this.colorScheme === "system") this.applyColorScheme();
    };
    this._onContrastChange = () => {
      if (this.contrast === "system") this.applyContrast();
    };
    this._darkMq.addEventListener("change", this._onDarkChange);
    this._contrastMq.addEventListener("change", this._onContrastChange);
  },

  destroy() {
    if (this._darkMq) this._darkMq.removeEventListener("change", this._onDarkChange);
    if (this._contrastMq) this._contrastMq.removeEventListener("change", this._onContrastChange);
  },

  setTheme(t) {
    this.theme = t;
    localStorage.setItem("pha-theme-variant", t);
    this.applyTheme();
  },

  setColorScheme(c) {
    this.colorScheme = c;
    localStorage.setItem("pha-color-scheme", c);
    this.applyColorScheme();
  },

  setContrast(c) {
    this.contrast = c;
    localStorage.setItem("pha-contrast", c);
    this.applyContrast();
  },

  applyTheme() {
    document.documentElement.classList.toggle("pf-v6-theme-felt", this.theme === "felt");
  },

  applyColorScheme() {
    const dark =
      this.colorScheme === "dark" ||
      (this.colorScheme === "system" && window.matchMedia("(prefers-color-scheme: dark)").matches);
    document.documentElement.classList.toggle("pf-v6-theme-dark", dark);
  },

  applyContrast() {
    const high =
      this.contrast === "high" || (this.contrast === "system" && window.matchMedia("(prefers-contrast: more)").matches);
    document.documentElement.classList.toggle("pf-v6-theme-high-contrast", high);
    document.documentElement.classList.toggle("pf-v6-theme-glass", this.contrast === "glass");
  },
}));
