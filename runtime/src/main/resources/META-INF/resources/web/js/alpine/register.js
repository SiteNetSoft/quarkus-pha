/*
 * phaAlpine — registration helper for Alpine.data() factories.
 *
 * Defers Alpine.data() until the alpine:init event fires, so component
 * files can be loaded before alpine.min.js without referencing Alpine
 * at script-evaluation time.
 *
 * Usage:
 *   phaAlpine('phaTreeView', () => ({ ... }));
 *
 * License: Apache 2.0
 */
window.phaAlpine = function (name, factory) {
  document.addEventListener("alpine:init", function () {
    Alpine.data(name, factory);
  });
};
