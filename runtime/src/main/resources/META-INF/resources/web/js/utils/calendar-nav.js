/**
 * Calendar month arrow-key navigation — roving focus across the date grid,
 * mirroring PF React CalendarMonth's onKeyDown (left/right = ±1 day,
 * up/down = ±7 days; blocked at the grid edges and on disabled dates).
 *
 * Usage (Alpine): <tbody @keydown="PHA.calendarArrowNav($event)">
 */
(function () {
  "use strict";

  var DELTAS = {
    ArrowLeft: -1,
    ArrowRight: 1,
    ArrowUp: -7,
    ArrowDown: 7,
  };

  function calendarArrowNav(event) {
    var delta = DELTAS[event.key];
    if (!delta) return;

    var buttons = Array.from(event.currentTarget.querySelectorAll(".pf-v6-c-calendar-month__date"));
    var idx = buttons.indexOf(document.activeElement);
    if (idx === -1) return;

    var target = idx + delta;
    if (target < 0 || target >= buttons.length) return;
    if (buttons[target].disabled) return;

    event.preventDefault();
    buttons[idx].setAttribute("tabindex", "-1");
    buttons[target].setAttribute("tabindex", "0");
    buttons[target].focus();
  }

  window.PHA = window.PHA || {};
  window.PHA.calendarArrowNav = calendarArrowNav;
})();
