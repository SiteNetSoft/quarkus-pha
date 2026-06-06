/*
 * Tooltip — Alpine.js component for PatternFly v6 tooltips.
 *
 * Shows the tip when the trigger is hovered or focused and hides it on
 * mouseleave / blur, with PatternFly's default 300ms entry/exit delay.
 * Positioning (top/bottom/left/right, 15px offset) and stacking (z-index)
 * live in /web/css/components/tooltip.css; this just owns the open state.
 *
 * Usage (rendered by components/feedback/tooltip):
 *   <span class="pha-tooltip" x-data="phaTooltip()">
 *     <span x-ref="trigger" @mouseenter="show()" @mouseleave="hide()"
 *           @focusin="show()" @focusout="hide()"> ...trigger... </span>
 *     <div x-ref="tip" x-show="open" role="tooltip" class="pf-v6-c-tooltip pf-m-top"> ... </div>
 *   </span>
 *
 * License: Apache 2.0
 */
phaAlpine("phaTooltip", () => ({
  open: false,
  _showTimer: null,
  _hideTimer: null,

  show() {
    clearTimeout(this._hideTimer);
    this._showTimer = setTimeout(() => {
      this.open = true;
    }, 300);
  },

  hide() {
    clearTimeout(this._showTimer);
    this._hideTimer = setTimeout(() => {
      this.open = false;
    }, 300);
  },
}));
