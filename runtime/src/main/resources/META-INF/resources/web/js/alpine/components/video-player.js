/*
 * Video player — Alpine.js component wrapping Video.js
 *
 * Embeds an HTML5 video element managed by Video.js, exposing a small
 * Alpine API for play / pause / mute / seek and dispatching playback
 * lifecycle events.
 *
 * Usage:
 *   <div x-data="phaVideoPlayer({
 *     src: '/media/sample.mp4',
 *     type: 'video/mp4',
 *     autoplay: false,
 *     muted: true,
 *     fluid: true
 *   })">
 *     <video-js class="video-js" playsinline></video-js>
 *   </div>
 *
 * Options:
 *   src           — media URL (string)
 *   type          — MIME type, e.g. 'video/mp4' or 'application/x-mpegURL'
 *   poster        — poster image URL (handled via template attribute)
 *   autoplay      — start playback automatically (default: false)
 *   loop          — loop playback (default: false)
 *   muted         — start muted (default: false)
 *   controls      — show native controls (default: true)
 *   preload       — 'auto' | 'metadata' | 'none' (default: 'auto')
 *   fluid         — responsive 16:9 sizing (default: true)
 *   playbackRates — array of selectable rates, e.g. [0.5, 1, 1.5, 2]
 *
 * Events dispatched (Alpine $dispatch):
 *   video-ready  — { player }
 *   video-play   — { currentTime }
 *   video-pause  — { currentTime }
 *   video-ended  — {}
 *   video-error  — { code, message }
 *
 * License: Apache 2.0
 */
phaAlpine("phaVideoPlayer", (config = {}) => ({
  playing: false,
  ready: false,
  _player: null,

  init() {
    if (typeof videojs === "undefined") {
      console.warn("phaVideoPlayer: videojs is not loaded");
      return;
    }

    const el = this.$el.querySelector(".pha-c-video-player__video");
    if (!el) return;

    const opts = {
      autoplay: !!config.autoplay,
      loop: !!config.loop,
      muted: !!config.muted,
      controls: config.controls !== false,
      preload: config.preload || "auto",
      fluid: config.fluid !== false,
    };

    if (Array.isArray(config.playbackRates) && config.playbackRates.length) {
      opts.playbackRates = config.playbackRates;
    }

    if (config.src) {
      opts.sources = [{ src: config.src, type: config.type || undefined }];
    }

    this._player = videojs(el, opts);

    this._player.ready(() => {
      this.ready = true;
      this.$dispatch("video-ready", { player: this._player });
    });

    this._player.on("play", () => {
      this.playing = true;
      this.$dispatch("video-play", { currentTime: this._player.currentTime() });
    });
    this._player.on("pause", () => {
      this.playing = false;
      this.$dispatch("video-pause", { currentTime: this._player.currentTime() });
    });
    this._player.on("ended", () => {
      this.playing = false;
      this.$dispatch("video-ended");
    });
    this._player.on("error", () => {
      const err = this._player.error();
      this.$dispatch("video-error", {
        code: err ? err.code : 0,
        message: err ? err.message : "unknown error",
      });
    });
  },

  destroy() {
    if (this._player) {
      this._player.dispose();
      this._player = null;
    }
  },

  /* ---- Public API ---- */

  play() {
    if (this._player) this._player.play();
  },

  pause() {
    if (this._player) this._player.pause();
  },

  toggle() {
    if (!this._player) return;
    if (this._player.paused()) this._player.play();
    else this._player.pause();
  },

  mute() {
    if (this._player) this._player.muted(true);
  },

  unmute() {
    if (this._player) this._player.muted(false);
  },

  seek(seconds) {
    if (this._player) this._player.currentTime(seconds);
  },

  load(src, type) {
    if (!this._player) return;
    this._player.src({ src: src, type: type });
  },
}));
