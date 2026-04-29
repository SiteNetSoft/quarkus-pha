/*
 * RectangleSelection — Alpine.js component
 *
 * Translated from the MooTools RectangleSelection by Jean-Nicolas Boulay.
 * Allows users to select multiple elements on a page by dragging a selection
 * rectangle over them, similar to desktop OS file selection.
 *
 * Features:
 *   - Drag to select multiple items
 *   - Auto-scrolls the window when the mouse reaches viewport edges
 *   - AABB collision detection (ported from Element.collide.js)
 *   - Fade-out animation on release
 *
 * Usage:
 *   <div x-data="rectangleSelection({ selector: '[data-selectable]' })">
 *     <div data-selectable>Item 1</div>
 *     <div data-selectable>Item 2</div>
 *   </div>
 *
 * Options:
 *   selector  — CSS selector for selectable elements (default: '[data-selectable]')
 *   fadeOut   — animate rectangle disappearance (default: true)
 *
 * Events dispatched:
 *   rectangle-selected   — { selected: Element[] } after mouseup
 *   rectangle-deselected — {} when selection is cleared
 *
 * License: MIT
 * Original author: Jean-Nicolas Boulay (http://jean-nicolas.com/)
 */
document.addEventListener('alpine:init', () => {
  Alpine.data('rectangleSelection', (config = {}) => ({
    mousedown: false,
    rectEl: null,
    selected: [],

    /* start position in document coordinates relative to container */
    _pox: 0,
    _poy: 0,
    /* container's document-level offset (set once at mousedown) */
    _containerLeft: 0,
    _containerTop: 0,

    _selector: config.selector || '[data-selectable]',
    _fadeOut: config.fadeOut !== false,
    _scrollInterval: null,
    _scrollToX: 0,
    _scrollToY: 0,
    _lastEvent: null,
    _boundDrag: null,
    _boundStop: null,

    init() {
      this.rectEl = document.createElement('div');
      this.rectEl.className = 'pha-c-rectangle-selection__rect';
      this.$el.appendChild(this.rectEl);

      this._boundDrag = this._onDrag.bind(this);
      this._boundStop = this._onStop.bind(this);

      this.$el.addEventListener('mousedown', this._onStart.bind(this));
      document.addEventListener('mousemove', this._boundDrag);
      document.addEventListener('mouseup', this._boundStop);
    },

    destroy() {
      document.removeEventListener('mousemove', this._boundDrag);
      document.removeEventListener('mouseup', this._boundStop);
      clearInterval(this._scrollInterval);
      if (this.rectEl && this.rectEl.parentNode) {
        this.rectEl.parentNode.removeChild(this.rectEl);
      }
    },

    /* ---- mouse handlers (translated from MooTools start/drag/stop) ---- */

    _onStart(e) {
      if (e.target.closest(this._selector)) return;
      if (e.button !== 0) return;
      e.preventDefault();

      this._deselect();

      /* compute container's position relative to the document origin */
      var cr = this.$el.getBoundingClientRect();
      this._containerLeft = cr.left + window.pageXOffset;
      this._containerTop = cr.top + window.pageYOffset;

      /* start point in container-local coordinates (using page coords) */
      this._pox = e.pageX - this._containerLeft;
      this._poy = e.pageY - this._containerTop;
      this.mousedown = true;

      Object.assign(this.rectEl.style, {
        display: 'block',
        left: this._pox + 'px',
        top: this._poy + 'px',
        width: '0',
        height: '0',
        opacity: '',
        transition: ''
      });
    },

    _onDrag(e) {
      if (!this.mousedown) return;
      e.preventDefault();
      clearInterval(this._scrollInterval);
      this._lastEvent = e;

      /*
       * Auto-scroll when mouse is near viewport edges.
       * Ported from the original MooTools drag() — uses the same
       * 5 / 10 / 20 px edge zones with 30 / 20 / 10 px scroll speeds.
       */
      var winH = window.innerHeight;
      var winW = window.innerWidth;
      this._scrollToX = 0;
      this._scrollToY = 0;

      /* bottom edge */
      if (e.clientY > winH - 5) this._scrollToY = 30;
      else if (e.clientY > winH - 10) this._scrollToY = 20;
      else if (e.clientY > winH - 20) this._scrollToY = 10;
      /* top edge */
      else if (e.clientY < 5) this._scrollToY = -30;
      else if (e.clientY < 10) this._scrollToY = -20;
      else if (e.clientY < 20) this._scrollToY = -10;

      /* right edge */
      if (e.clientX > winW - 5) this._scrollToX = 30;
      else if (e.clientX > winW - 10) this._scrollToX = 20;
      else if (e.clientX > winW - 20) this._scrollToX = 10;
      /* left edge */
      else if (e.clientX < 5) this._scrollToX = -30;
      else if (e.clientX < 10) this._scrollToX = -20;
      else if (e.clientX < 20) this._scrollToX = -10;

      if (this._scrollToX || this._scrollToY) {
        this._scrollInterval = setInterval(() => {
          this._autoScroll();
        }, 10);
      }

      this._resizeRect(e);
    },

    /*
     * Auto-scroll the window and keep resizing the rectangle.
     * Ported from the MooTools resizeRectangle() scroll logic.
     */
    _autoScroll() {
      var scrollY = window.pageYOffset;
      var scrollX = window.pageXOffset;
      var maxY = document.documentElement.scrollHeight - window.innerHeight;
      var maxX = document.documentElement.scrollWidth - window.innerWidth;

      /* stop at document boundaries (matches original MooTools guard) */
      if ((scrollY <= 0 && this._scrollToY < 0) ||
          (scrollY >= maxY && this._scrollToY > 0)) {
        this._scrollToY = 0;
      }
      if ((scrollX <= 0 && this._scrollToX < 0) ||
          (scrollX >= maxX && this._scrollToX > 0)) {
        this._scrollToX = 0;
      }
      if (!this._scrollToX && !this._scrollToY) {
        clearInterval(this._scrollInterval);
        return;
      }

      window.scrollBy(this._scrollToX, this._scrollToY);

      /* re-resize rect with updated scroll — the mouse hasn't moved,
         but the page has scrolled, so the effective page coordinates change */
      if (this._lastEvent) {
        this._resizeRect(this._lastEvent);
      }
    },

    _resizeRect(e) {
      /*
       * Convert mouse position to container-local coordinates.
       * e.clientX/Y is viewport-relative (frozen during auto-scroll),
       * so we add the CURRENT scroll offset to get true page coords,
       * then subtract the container's document offset.
       */
      var mx = e.clientX + window.pageXOffset - this._containerLeft;
      var my = e.clientY + window.pageYOffset - this._containerTop;

      /* clamp to container bounds */
      mx = Math.max(0, mx);
      my = Math.max(0, my);

      var left, top, width, height;
      if (this._pox < mx) {
        width = mx - this._pox;
        left = this._pox;
      } else {
        width = this._pox - mx;
        left = mx;
      }
      if (this._poy < my) {
        height = my - this._poy;
        top = this._poy;
      } else {
        height = this._poy - my;
        top = my;
      }

      Object.assign(this.rectEl.style, {
        left: left + 'px',
        top: top + 'px',
        width: width + 'px',
        height: height + 'px'
      });

      this._checkCollisions();
    },

    _onStop() {
      if (!this.mousedown) return;
      this.mousedown = false;
      clearInterval(this._scrollInterval);

      this.selected = Array.from(
        this.$el.querySelectorAll(this._selector + '.pha-m-selected')
      );
      this.$dispatch('rectangle-selected', { selected: this.selected });

      if (this._fadeOut) {
        this.rectEl.style.transition = 'opacity 0.3s ease';
        this.rectEl.style.opacity = '0';
        setTimeout(() => {
          this.rectEl.style.display = 'none';
          this.rectEl.style.transition = '';
        }, 300);
      } else {
        this.rectEl.style.display = 'none';
      }
    },

    /* ---- collision detection (ported from Element.collide.js) ---- */

    _checkCollisions() {
      var rectBounds = this.rectEl.getBoundingClientRect();
      this.$el.querySelectorAll(this._selector).forEach(function(el) {
        var b = el.getBoundingClientRect();
        var hit = !(b.bottom < rectBounds.top || b.top > rectBounds.bottom ||
                    b.right < rectBounds.left || b.left > rectBounds.right);
        el.classList.toggle('pha-m-selected', hit);
      });
    },

    _deselect() {
      this.$el.querySelectorAll(this._selector + '.pha-m-selected')
        .forEach(function(el) { el.classList.remove('pha-m-selected'); });
      this.selected = [];
      this.$dispatch('rectangle-deselected');
    }
  }));
});
