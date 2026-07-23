/**
 * phaUserFeedback — Alpine component for the PatternFly user-feedback extension.
 *
 * Owns the multi-page modal state machine (home → share / bug / research →
 * success / error) and the form state for each page.
 *
 * Per-instance config is read from data-* attributes on the root in init()
 * to dodge the factory-closure leak documented in
 * [[feedback_alpine_factory_closure_leak]]:
 *   data-basic="true"   — skip the home menu, open straight to the share form
 *   data-email="..."    — pre-fill the email field
 *
 * Submission is wired to the document via two CustomEvents that the host
 * page can listen for:
 *   pha:user-feedback-submit  — { detail: { type, payload } }
 *   pha:user-feedback-cancel  — fires when the user closes without submitting
 *
 * The factory itself doesn't make any HTTP calls — the demo wires submit to
 * a /api/htmx/user-feedback endpoint that simulates success/failure based on
 * the payload. Real consumers wire onShareFeedback / onReportBug callbacks
 * via the events.
 *
 * License: Apache 2.0
 */
phaAlpine("phaUserFeedback", () => ({
  isOpen: false,
  page: "home",
  basic: false,

  feedback: "",
  bug: "",
  email: "",
  optInResearch: false,
  emailError: "",

  successTitle: "Feedback sent",
  successMessage: "Thank you, we appreciate your feedback.",

  init() {
    this.basic = this.$root.dataset.basic === "true";
    if (this.$root.dataset.email) this.email = this.$root.dataset.email;
  },

  open() {
    this.page = this.basic ? "share" : "home";
    this.isOpen = true;
  },

  close() {
    if (this.isOpen) {
      this.$dispatch("pha:user-feedback-cancel");
    }
    this.isOpen = false;
    this._reset();
  },

  setPage(page) {
    this.page = page;
  },

  back() {
    if (this.basic) {
      this.close();
    } else {
      this.page = "home";
    }
  },

  validateEmail() {
    const value = (this.email || "").trim();
    if (!value) {
      this.emailError = "";
      return false;
    }
    // RFC 5322 lite — good enough for client-side validation, server should
    // re-validate before persisting.
    const ok = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
    this.emailError = ok ? "" : "Email address is invalid.";
    return ok;
  },

  submit(type) {
    if ((type === "share" && this.optInResearch) || type === "research") {
      if (!this.validateEmail()) return;
    }
    const payload =
      type === "share"
        ? { feedback: this.feedback, email: this.optInResearch ? this.email : null }
        : type === "bug"
          ? { bug: this.bug }
          : { email: this.email };

    // Dispatch the event so the host page can intercept (e.g. POST to a server).
    this.$dispatch("pha:user-feedback-submit", { type, payload });

    // Default behaviour: assume success after a short delay. Hosts that want a
    // server round-trip can preventDefault, do their own request, and call
    // markSuccess()/markError() on the component via $refs.
    const self = this;
    setTimeout(function () {
      self._markSuccessFor(type);
    }, 250);
  },

  markSuccess(title, message) {
    if (title) this.successTitle = title;
    if (message) this.successMessage = message;
    this.page = "success";
  },

  markError() {
    this.page = "error";
  },

  _markSuccessFor(type) {
    if (type === "bug") {
      this.markSuccess("Bug reported", "Thank you, our team will review your report.");
    } else if (type === "research") {
      this.markSuccess("Thank you for your interest", "You will hear from our user research team soon.");
    } else {
      this.markSuccess("Feedback sent", "Thank you, we appreciate your feedback.");
    }
  },

  _reset() {
    this.feedback = "";
    this.bug = "";
    this.optInResearch = false;
    this.emailError = "";
    // Email is intentionally not cleared — it's typically pre-populated.
    this.page = this.basic ? "share" : "home";
  },
}));
