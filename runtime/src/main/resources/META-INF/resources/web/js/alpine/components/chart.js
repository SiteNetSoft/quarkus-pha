/*
 * Chart — Alpine.js component wrapping Apache ECharts
 *
 * Provides reactive chart rendering using ECharts with automatic resizing,
 * theme support, and PatternFly design token integration.
 *
 * Usage:
 *   <div x-data="phaChart({ type: 'bar', option: { ... } })">
 *     <div class="pha-c-chart__canvas"></div>
 *   </div>
 *
 * Options:
 *   option    — ECharts option object (required)
 *   theme     — ECharts theme name or object (default: null, uses built-in PF theme)
 *   renderer  — 'canvas' or 'svg' (default: 'canvas')
 *   autoResize — automatically resize on window resize (default: true)
 *   loading   — show loading animation initially (default: false)
 *
 * Events dispatched:
 *   chart-ready  — { chart: ECharts instance } after initialization
 *   chart-click  — { params: ECharts event params } on chart element click
 *
 * License: Apache 2.0
 */
/*
 * PatternFly-aligned color palette.
 * Uses PF v6 chart color sequence for consistent look.
 */
let PF_COLORS = [
  "#06c" /* blue-400    */,
  "#4cb140" /* green-400   */,
  "#009596" /* cyan-400    */,
  "#f4c145" /* gold-400    */,
  "#ec7a08" /* orange-400  */,
  "#7d1007" /* red-700     */,
  "#6753ac" /* purple-400  */,
  "#c9190b" /* red-400     */,
  "#8bc1f7" /* blue-200    */,
  "#bde2b9" /* green-200   */,
];

let PF_THEME = {
  color: PF_COLORS,
  backgroundColor: "transparent",
  textStyle: { fontFamily: "RedHatText, Helvetica, Arial, sans-serif" },
  title: {
    textStyle: { color: "#151515", fontWeight: 700, fontSize: 16 },
    subtextStyle: { color: "#6a6e73" },
  },
  legend: { textStyle: { color: "#151515" } },
  tooltip: {
    backgroundColor: "#fff",
    borderColor: "#d2d2d2",
    textStyle: { color: "#151515" },
  },
  categoryAxis: {
    axisLine: { lineStyle: { color: "#d2d2d2" } },
    axisTick: { lineStyle: { color: "#d2d2d2" } },
    axisLabel: { color: "#6a6e73" },
    splitLine: { lineStyle: { color: "#f0f0f0" } },
  },
  valueAxis: {
    axisLine: { lineStyle: { color: "#d2d2d2" } },
    axisTick: { lineStyle: { color: "#d2d2d2" } },
    axisLabel: { color: "#6a6e73" },
    splitLine: { lineStyle: { color: "#f0f0f0" } },
  },
};

/* Register theme globally so all charts can use it */
if (typeof echarts !== "undefined") {
  echarts.registerTheme("patternfly", PF_THEME);
}

phaAlpine("phaChart", (config = {}) => ({
  _chart: null,
  _resizeHandler: null,

  init() {
    let container = this.$el.querySelector(".pha-c-chart__canvas") || this.$el;
    let theme = config.theme || "patternfly";
    let renderer = config.renderer || "canvas";

    this._chart = echarts.init(container, theme, { renderer: renderer });

    if (config.loading) {
      this._chart.showLoading("default", {
        text: "",
        color: "#06c",
        maskColor: "rgba(255,255,255,0.8)",
      });
    }

    if (config.option) {
      this._chart.setOption(config.option);
      if (config.loading) {
        this._chart.hideLoading();
      }
    }

    let self = this;

    /* Auto-resize on window resize */
    if (config.autoResize !== false) {
      this._resizeHandler = function () {
        if (self._chart && !self._chart.isDisposed()) {
          self._chart.resize();
        }
      };
      window.addEventListener("resize", this._resizeHandler);
    }

    /* Forward chart click events */
    this._chart.on("click", function (params) {
      self.$dispatch("chart-click", { params: params });
    });

    this.$dispatch("chart-ready", { chart: this._chart });
  },

  destroy() {
    if (this._resizeHandler) {
      window.removeEventListener("resize", this._resizeHandler);
    }
    if (this._chart && !this._chart.isDisposed()) {
      this._chart.dispose();
    }
  },

  /* ---- Public API ---- */

  setOption(option, notMerge) {
    if (this._chart && !this._chart.isDisposed()) {
      this._chart.setOption(option, notMerge);
    }
  },

  resize() {
    if (this._chart && !this._chart.isDisposed()) {
      this._chart.resize();
    }
  },

  getChart() {
    return this._chart;
  },
}));
