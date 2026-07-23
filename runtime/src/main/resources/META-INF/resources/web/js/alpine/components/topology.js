/*
 * Topology — Alpine.js component wrapping Cytoscape.js
 *
 * Hosts a Cytoscape graph instance inside the .pha-c-topology__canvas
 * element with PatternFly-aligned default styling.
 *
 * Usage:
 *   <div class="pha-c-topology" id="cy-basic"
 *        x-data="phaTopology({
 *          elements: [
 *            { data: { id: 'a' } },
 *            { data: { id: 'b' } },
 *            { data: { source: 'a', target: 'b' } }
 *          ],
 *          layout: { name: 'cose' }
 *        })">
 *     <div class="pha-c-topology__canvas"></div>
 *   </div>
 *
 * Config keys (all optional unless noted):
 *   elements           — Cytoscape elements array (required for anything to render)
 *   layout             — Cytoscape layout config. Default { name: 'cose' }.
 *   style              — Cytoscape style array. Default = PF-aligned (see DEFAULT_STYLE).
 *   userZoomingEnabled — defaults to true
 *   userPanningEnabled — defaults to true
 *   boxSelectionEnabled — defaults to true
 *   onTap              — function called with the Cytoscape event for taps (node, edge or background)
 *
 * Events dispatched:
 *   topology-ready  — { cy: Cytoscape instance } after initialisation
 *   topology-tap    — { target: 'node' | 'edge' | 'core', id, event } on tap
 *
 * Public API (callable via Alpine $refs / $data):
 *   getCy() / fit() / center() / addElements(els) / removeElements(sel)
 *
 * License: Apache 2.0
 */

const PF_TOPOLOGY_COLORS = {
  nodeBg: "#06c" /* blue-400 */,
  nodeText: "#fff",
  nodeBorder: "#003366" /* blue-700 */,
  nodeSelectedBg: "#004d99",
  edge: "#8a8d90" /* grey-500 */,
  edgeText: "#151515",
  background: "transparent",
};

const DEFAULT_STYLE = [
  {
    selector: "node",
    style: {
      "background-color": PF_TOPOLOGY_COLORS.nodeBg,
      "border-color": PF_TOPOLOGY_COLORS.nodeBorder,
      "border-width": 2,
      color: PF_TOPOLOGY_COLORS.nodeText,
      label: "data(label)",
      "text-valign": "center",
      "text-halign": "center",
      "font-size": 12,
      "font-family": "RedHatText, Helvetica, Arial, sans-serif",
      width: 44,
      height: 44,
    },
  },
  {
    selector: "node:selected",
    style: {
      "background-color": PF_TOPOLOGY_COLORS.nodeSelectedBg,
      "border-width": 3,
    },
  },
  {
    selector: "edge",
    style: {
      width: 2,
      "line-color": PF_TOPOLOGY_COLORS.edge,
      "target-arrow-color": PF_TOPOLOGY_COLORS.edge,
      "target-arrow-shape": "triangle",
      "curve-style": "bezier",
      label: "data(label)",
      color: PF_TOPOLOGY_COLORS.edgeText,
      "font-size": 10,
      "font-family": "RedHatText, Helvetica, Arial, sans-serif",
      "text-background-color": "#fff",
      "text-background-opacity": 0.9,
      "text-background-padding": 2,
    },
  },
  {
    selector: "edge:selected",
    style: { width: 3, "line-color": "#004d99", "target-arrow-color": "#004d99" },
  },
];

phaAlpine("phaTopology", (config = {}) => ({
  _cy: null,

  init() {
    const container = this.$el.querySelector(".pha-c-topology__canvas") || this.$el;
    const self = this;

    this._cy = cytoscape({
      container: container,
      elements: config.elements || [],
      style: config.style || DEFAULT_STYLE,
      layout: config.layout || { name: "cose", animate: false },
      userZoomingEnabled: config.userZoomingEnabled !== false,
      userPanningEnabled: config.userPanningEnabled !== false,
      boxSelectionEnabled: config.boxSelectionEnabled !== false,
      wheelSensitivity: 0.2,
    });

    this._cy.on("tap", function (evt) {
      const target = evt.target;
      const kind = target === self._cy ? "core" : target.isNode() ? "node" : "edge";
      const id = kind === "core" ? null : target.id();
      if (typeof config.onTap === "function") {
        config.onTap({ target: kind, id: id, event: evt });
      }
      self.$dispatch("topology-tap", { target: kind, id: id, event: evt });
    });

    this.$dispatch("topology-ready", { cy: this._cy });
  },

  destroy() {
    if (this._cy && !this._cy.destroyed()) {
      this._cy.destroy();
    }
  },

  /* ---- Public API ---- */

  getCy() {
    return this._cy;
  },

  fit(padding) {
    if (this._cy) {
      this._cy.fit(undefined, padding || 30);
    }
  },

  center() {
    if (this._cy) {
      this._cy.center();
    }
  },

  addElements(els) {
    if (this._cy) {
      this._cy.add(els);
    }
  },

  removeElements(selector) {
    if (this._cy) {
      this._cy.remove(selector);
    }
  },
}));
