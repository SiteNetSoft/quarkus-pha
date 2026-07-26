/**
 * Focus Trap — vanilla JS utility for trapping focus within a container.
 *
 * Usage with Alpine.js:
 *   <div x-data="{ open: false }"
 *        x-effect="if (open) PHA.trapFocus($el); else PHA.releaseFocus($el);">
 *
 * Or imperatively:
 *   PHA.trapFocus(containerEl);
 *   PHA.releaseFocus(containerEl);
 */
(function () {
  "use strict";

  var FOCUSABLE = [
    "a[href]",
    "button:not([disabled])",
    'input:not([disabled]):not([type="hidden"])',
    "select:not([disabled])",
    "textarea:not([disabled])",
    '[tabindex]:not([tabindex="-1"])',
    '[contenteditable="true"]',
  ].join(",");

  var traps = new WeakMap();

  function getFocusable(container) {
    var elements = Array.from(container.querySelectorAll(FOCUSABLE));
    return elements.filter(function (el) {
      return el.offsetParent !== null; // visible
    });
  }

  function grabInitialFocus(container) {
    // Never steal focus a caller has already placed inside the trap
    // (e.g. a modal that focuses its primary action on open).
    if (container.contains(document.activeElement)) return;
    var focusable = getFocusable(container);
    if (focusable.length > 0) {
      focusable[0].focus();
    }
  }

  function trapFocus(container) {
    if (traps.has(container)) return; // already trapped

    var previousFocus = document.activeElement;

    function handler(e) {
      if (e.key !== "Tab") return;

      var focusable = getFocusable(container);
      if (focusable.length === 0) {
        e.preventDefault();
        return;
      }

      var first = focusable[0];
      var last = focusable[focusable.length - 1];

      if (e.shiftKey) {
        if (document.activeElement === first) {
          e.preventDefault();
          last.focus();
        }
      } else {
        if (document.activeElement === last) {
          e.preventDefault();
          first.focus();
        }
      }
    }

    container.addEventListener("keydown", handler);
    traps.set(container, { handler: handler, previousFocus: previousFocus });

    if (container.getClientRects().length === 0) {
      // Not rendered yet — an Alpine x-effect can trap in the same reactive
      // flush that flips x-show, before the display change lands. Grab after
      // the frame resolves, unless the trap was released in the meantime.
      requestAnimationFrame(function () {
        if (traps.has(container)) grabInitialFocus(container);
      });
    } else {
      grabInitialFocus(container);
    }
  }

  function releaseFocus(container) {
    var data = traps.get(container);
    if (!data) return;

    container.removeEventListener("keydown", data.handler);

    // Return focus to the element that had focus before the trap
    if (data.previousFocus && data.previousFocus.focus) {
      data.previousFocus.focus();
    }

    traps.delete(container);
  }

  // Expose globally
  window.PHA = window.PHA || {};
  window.PHA.trapFocus = trapFocus;
  window.PHA.releaseFocus = releaseFocus;
})();
