/*
 * Map — Alpine.js component wrapping MapLibre GL JS
 *
 * Provides an interactive map component using MapLibre GL JS with
 * PatternFly design token integration and Alpine.js reactivity.
 *
 * Usage:
 *   <div x-data="phaMap({ center: [-75.7, 45.4], zoom: 10 })">
 *     <div class="pha-c-map__container"></div>
 *   </div>
 *
 * Options:
 *   center             — [lng, lat] array (default: [-98.5, 39.8])
 *   zoom               — initial zoom level 0–22 (default: 3)
 *   style              — preset name: 'osm', 'light', 'dark', or a MapLibre style object/URL (default: 'osm')
 *   navigationControl  — show zoom/rotation controls (default: true)
 *   scaleControl       — show scale bar (default: false)
 *   fullscreenControl  — show fullscreen toggle (default: false)
 *   markers            — array of { lngLat: [lng, lat], popup: 'HTML string', color: '#hex' }
 *   geoJson            — GeoJSON object to add as a fill+outline layer
 *
 * Events dispatched:
 *   map-loaded — {} after map style is fully loaded
 *   map-click  — { lngLat: {lng, lat}, point: {x, y} } on map click
 *
 * License: Apache 2.0
 */
/* Preset raster tile styles (no API key required) */
var STYLES = {
  osm: {
    version: 8,
    sources: {
      osm: {
        type: "raster",
        tiles: ["https://tile.openstreetmap.org/{z}/{x}/{y}.png"],
        tileSize: 256,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      },
    },
    layers: [{ id: "osm", type: "raster", source: "osm" }],
  },
  light: {
    version: 8,
    sources: {
      carto: {
        type: "raster",
        tiles: ["https://basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png"],
        tileSize: 256,
        attribution:
          '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/">CARTO</a>',
      },
    },
    layers: [{ id: "carto-light", type: "raster", source: "carto" }],
  },
  dark: {
    version: 8,
    sources: {
      carto: {
        type: "raster",
        tiles: ["https://basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png"],
        tileSize: 256,
        attribution:
          '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors &copy; <a href="https://carto.com/">CARTO</a>',
      },
    },
    layers: [{ id: "carto-dark", type: "raster", source: "carto" }],
  },
};

phaAlpine("phaMap", (config = {}) => ({
  map: null,
  _markers: [],
  _loaded: false,

  init() {
    // MapLibre v6 is ESM-only; the page loads it via a module shim that sets
    // window.maplibregl and fires maplibre-ready. Wait for it if it hasn't run yet.
    if (!window.maplibregl) {
      var self = this;
      window.addEventListener(
        "maplibre-ready",
        function () {
          self.init();
        },
        { once: true },
      );
      return;
    }
    var container = this.$el.querySelector(".pha-c-map__container") || this.$el;

    var style = config.style || "osm";
    if (typeof style === "string" && STYLES[style]) {
      style = STYLES[style];
    }

    this.map = new maplibregl.Map({
      container: container,
      style: style,
      center: config.center || [-98.5, 39.8],
      zoom: config.zoom != null ? config.zoom : 3,
    });

    if (config.navigationControl !== false) {
      this.map.addControl(new maplibregl.NavigationControl(), "top-right");
    }
    if (config.scaleControl) {
      this.map.addControl(new maplibregl.ScaleControl(), "bottom-left");
    }
    if (config.fullscreenControl) {
      this.map.addControl(new maplibregl.FullscreenControl());
    }

    if (config.markers && Array.isArray(config.markers)) {
      this._addMarkers(config.markers);
    }

    var self = this;
    this.map.on("load", function () {
      self._loaded = true;

      if (config.geoJson) {
        self._addGeoJson(config.geoJson);
      }

      self.$dispatch("map-loaded");
    });

    this.map.on("click", function (e) {
      self.$dispatch("map-click", {
        lngLat: e.lngLat,
        point: e.point,
      });
    });
  },

  destroy() {
    this._markers.forEach(function (m) {
      m.remove();
    });
    if (this.map) this.map.remove();
  },

  _addMarkers(markers) {
    var self = this;
    markers.forEach(function (m) {
      var opts = {};
      if (m.color) opts.color = m.color;

      var marker = new maplibregl.Marker(opts).setLngLat(m.lngLat);

      if (m.popup) {
        marker.setPopup(new maplibregl.Popup({ offset: 25 }).setHTML(m.popup));
      }

      marker.addTo(self.map);
      self._markers.push(marker);
    });
  },

  _addGeoJson(geojson) {
    this.map.addSource("geojson-data", {
      type: "geojson",
      data: geojson,
    });
    this.map.addLayer({
      id: "geojson-fill",
      type: "fill",
      source: "geojson-data",
      paint: {
        "fill-color": "#06c",
        "fill-opacity": 0.3,
      },
    });
    this.map.addLayer({
      id: "geojson-outline",
      type: "line",
      source: "geojson-data",
      paint: {
        "line-color": "#06c",
        "line-width": 2,
      },
    });
  },
}));
