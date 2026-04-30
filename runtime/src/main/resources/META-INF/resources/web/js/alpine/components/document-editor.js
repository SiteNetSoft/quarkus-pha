/*
 * Document Editor — Alpine.js component wrapping Collabora Online
 *
 * Embeds the Collabora Online (LibreOffice in the browser) document editor
 * via iframe and communicates with it using the PostMessage API and WOPI protocol.
 *
 * Usage:
 *   <div x-data="phaDocumentEditor({
 *     collaboraUrl: 'https://collabora.example.com',
 *     wopiSrc: 'https://app.example.com/wopi/files/abc123',
 *     accessToken: 'token',
 *     fileName: 'Report.odt'
 *   })">
 *     <div class="pha-c-document-editor__body">
 *       <iframe class="pha-c-document-editor__frame"></iframe>
 *     </div>
 *   </div>
 *
 * Options:
 *   collaboraUrl  — base URL of the Collabora Online server
 *   wopiSrc       — WOPI source URL for the document
 *   accessToken   — WOPI access token for authentication
 *   fileName      — display name for the document (default: 'Untitled')
 *   readOnly      — open in read-only/view mode (default: false)
 *   lang          — editor UI language, e.g. 'en', 'fr' (default: browser language)
 *   closeButton   — show the close button in the Collabora UI (default: true)
 *
 * Events dispatched:
 *   document-loaded — {} when the document has fully loaded in the editor
 *   document-saved  — { success: Boolean } after a save operation completes
 *   document-close  — {} when the user clicks close in the editor
 *   document-error  — { message: String } on editor errors
 *
 * License: Apache 2.0
 */
phaAlpine("phaDocumentEditor", (config = {}) => ({
  loaded: false,
  saving: false,
  fileName: config.fileName || "Untitled",
  readOnly: config.readOnly || false,
  _iframe: null,
  _messageHandler: null,

  get connected() {
    return !!(config.collaboraUrl && config.wopiSrc);
  },

  init() {
    this._iframe = this.$el.querySelector(".pha-c-document-editor__frame");
    this._messageHandler = this._onMessage.bind(this);
    window.addEventListener("message", this._messageHandler);

    if (this.connected && this._iframe) {
      this._loadEditor();
    }
  },

  destroy() {
    window.removeEventListener("message", this._messageHandler);
  },

  _loadEditor() {
    /*
     * Build the Collabora Online editor URL.
     * Modern Collabora uses /browser/dist/cool.html with WOPISrc param.
     */
    var url =
      config.collaboraUrl.replace(/\/+$/, "") + "/browser/dist/cool.html?WOPISrc=" + encodeURIComponent(config.wopiSrc);

    if (config.accessToken) {
      url += "&access_token=" + encodeURIComponent(config.accessToken);
    }
    if (config.lang) {
      url += "&lang=" + encodeURIComponent(config.lang);
    }
    if (config.closeButton === false) {
      url += "&closebutton=0";
    }
    if (this.readOnly) {
      url += "&permission=readonly";
    }

    this._iframe.src = url;
  },

  _onMessage(e) {
    /* Only accept messages from the Collabora iframe origin */
    if (config.collaboraUrl && e.origin !== new URL(config.collaboraUrl).origin) {
      return;
    }

    var data;
    try {
      data = JSON.parse(e.data);
    } catch (ex) {
      return;
    }

    switch (data.MessageId) {
      case "App_LoadingStatus":
        if (data.Values && data.Values.Status === "Document_Loaded") {
          this.loaded = true;
          this.$dispatch("document-loaded", { fileName: this.fileName });
        }
        break;

      case "Action_Save_Resp":
        this.saving = false;
        this.$dispatch("document-saved", {
          success: !!(data.Values && data.Values.success),
        });
        break;

      case "UI_Close":
      case "Close_Button":
        this.$dispatch("document-close");
        break;

      case "View_Added":
        /* A new user joined the editing session */
        break;

      case "View_Removed":
        /* A user left the editing session */
        break;
    }
  },

  _postMessage(msg) {
    if (this._iframe && this._iframe.contentWindow) {
      this._iframe.contentWindow.postMessage(JSON.stringify(msg), "*");
    }
  },

  /* ---- Public API ---- */

  save() {
    if (!this.connected || !this.loaded) return;
    this.saving = true;
    this._postMessage({
      MessageId: "Action_Save",
      Values: {
        DontTerminateEdit: true,
        DontSaveIfUnmodified: true,
        Notify: true,
      },
    });
  },

  print() {
    if (!this.connected || !this.loaded) return;
    this._postMessage({ MessageId: "Action_Print" });
  },

  download(format) {
    if (!this.connected || !this.loaded) return;
    this._postMessage({
      MessageId: "Action_Export",
      Values: { Format: format || "" },
    });
  },

  fullscreen() {
    var el = this.$el;
    if (el.requestFullscreen) el.requestFullscreen();
    else if (el.webkitRequestFullscreen) el.webkitRequestFullscreen();
  },

  insertText(text) {
    if (!this.connected || !this.loaded || this.readOnly) return;
    this._postMessage({
      MessageId: "Action_Paste",
      Values: { Mimetype: "text/plain;charset=utf-8", Data: text },
    });
  },
}));
