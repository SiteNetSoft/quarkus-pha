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
 *   chart-ready          — { chart: ECharts instance } after initialization
 *   chart-click          — { params: ECharts event params } on chart element click
 *   chart-legend-select  — { name, selected } when a legend item is toggled
 *
 * License: Apache 2.0
 */
/*
 * PatternFly v6 chart palettes, resolved from the @patternfly/patternfly
 * charts tokens (--pf-t--chart--theme--colorscales--*, light theme).
 *
 * PF_COLORS = multi-colored (ordered): cycles blue/green/teal/yellow/orange
 * through 300 → 100/500 → 200/400 shades. Default for every chart via the
 * registered "patternfly" theme.
 */
let PF_COLORS = [
  "#0066cc" /* blue-300   */,
  "#63993d" /* green-300  */,
  "#37a3a3" /* teal-300   */,
  "#dca614" /* yellow-300 */,
  "#ca6c0f" /* orange-300 */,
  "#92c5f9" /* blue-100   */,
  "#204d00" /* green-500  */,
  "#9ad8d8" /* teal-100   */,
  "#96640f" /* yellow-500 */,
  "#f8ae54" /* orange-100 */,
  "#003366" /* blue-500   */,
  "#afdc8f" /* green-100  */,
  "#004d4d" /* teal-500   */,
  "#ffe072" /* yellow-100 */,
  "#732e00" /* orange-500 */,
  "#4394e5" /* blue-200   */,
  "#3d7317" /* green-400  */,
  "#63bdbd" /* teal-200   */,
  "#b98412" /* yellow-400 */,
  "#f5921b" /* orange-200 */,
  "#004d99" /* blue-400   */,
  "#87bb62" /* green-200  */,
  "#147878" /* teal-400   */,
  "#ffcc17" /* yellow-200 */,
  "#9e4a06" /* orange-400 */,
];

/*
 * PF_COLORS_UNORDERED = multi-colored (unordered): adds purple + gray and
 * interleaves hue families so ADJACENT series contrast strongly — PF
 * recommends it when series have no inherent order (area & line charts).
 * Opt in per chart: x-data="phaChart({ option: { color: PF_COLORS_UNORDERED, ... } })".
 */
let PF_COLORS_UNORDERED = [
  "#0066cc" /* blue-300   */,
  "#dca614" /* yellow-300 */,
  "#63993d" /* green-300  */,
  "#5e40be" /* purple-300 */,
  "#ca6c0f" /* orange-300 */,
  "#37a3a3" /* teal-300   */,
  "#c7c7c7" /* black-300  */,
  "#92c5f9" /* blue-100   */,
  "#96640f" /* yellow-500 */,
  "#afdc8f" /* green-100  */,
  "#21134d" /* purple-500 */,
  "#f8ae54" /* orange-100 */,
  "#004d4d" /* teal-500   */,
  "#f2f2f2" /* black-100  */,
  "#003366" /* blue-500   */,
  "#ffe072" /* yellow-100 */,
  "#204d00" /* green-500  */,
  "#b6a6e9" /* purple-100 */,
  "#732e00" /* orange-500 */,
  "#9ad8d8" /* teal-100   */,
  "#707070" /* black-500  */,
  "#4394e5" /* blue-200   */,
  "#b98412" /* yellow-400 */,
  "#87bb62" /* green-200  */,
  "#3d2785" /* purple-400 */,
  "#f5921b" /* orange-200 */,
  "#147878" /* teal-400   */,
  "#e0e0e0" /* black-200  */,
  "#004d99" /* blue-400   */,
  "#ffcc17" /* yellow-200 */,
  "#3d7317" /* green-400  */,
  "#876fd4" /* purple-200 */,
  "#9e4a06" /* orange-400 */,
  "#63bdbd" /* teal-200   */,
  "#a3a3a3" /* black-400  */,
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

    /* Forward legend toggles so consumers can react (filter, navigate, count) */
    this._chart.on("legendselectchanged", function (params) {
      self.$dispatch("chart-legend-select", {
        name: params.name,
        selected: params.selected,
      });
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
