/*
 * HTMX toast queue + confirm-modal bridge — PatternFly v6.
 *
 * Toasts: the server signals a toast with an HX-Trigger response header, e.g.
 *   HX-Trigger: {"pha:toast": {"variant": "success", "title": "Saved"}}
 * HTMX dispatches a `pha:toast` event; this renders a PF alert into a fixed
 * toast alert-group (created on demand) and auto-dismisses it after 6s.
 *
 * Confirm: any element with both `hx-confirm` and `data-pha-confirm` opens the
 * PF modal `#pha-confirm-modal` (modal family, eventOpen) instead of window.confirm;
 * the modal's confirm button issues the held HTMX request.
 *
 * License: Apache 2.0
 */
(function () {
  function toastRegion() {
    var r = document.getElementById("pha-toast-region");
    if (!r) {
      r = document.createElement("ul");
      r.id = "pha-toast-region";
      r.className = "pf-v6-c-alert-group pf-m-toast";
      r.setAttribute("role", "list");
      r.style.cssText =
        "position: fixed; top: 1rem; right: 1rem; z-index: 9999; max-width: 28rem;";
      document.body.appendChild(r);
    }
    return r;
  }

  var ICONS = {
    success: "fas fa-circle-check",
    danger: "fas fa-circle-exclamation",
    warning: "fas fa-triangle-exclamation",
    info: "fas fa-circle-info",
  };

  function showToast(detail) {
    var variant = (detail && detail.variant) || "info";
    var title = (detail && detail.title) || "";
    var li = document.createElement("li");
    li.className = "pf-v6-c-alert-group__item";
    li.innerHTML =
      '<div class="pf-v6-c-alert pf-m-' + variant + '" role="alert">' +
      '<div class="pf-v6-c-alert__icon"><i class="' + (ICONS[variant] || ICONS.info) + '" aria-hidden="true"></i></div>' +
      '<p class="pf-v6-c-alert__title"><span class="pf-v6-c-alert__screen-reader">' + variant + " alert:</span> " +
      title + "</p>" +
      '<div class="pf-v6-c-alert__action">' +
      '<button class="pf-v6-c-button pf-m-plain" type="button" aria-label="Close alert">' +
      '<span class="pf-v6-c-button__icon"><i class="fas fa-xmark" aria-hidden="true"></i></span></button></div></div>';
    var dismiss = function () {
      if (li.parentNode) li.parentNode.removeChild(li);
    };
    li.querySelector("button").addEventListener("click", dismiss);
    toastRegion().appendChild(li);
    setTimeout(dismiss, 6000);
  }

  document.addEventListener("pha:toast", function (e) {
    showToast(e.detail);
  });

  // Replace window.confirm with the PF modal for opted-in elements.
  var pending = null;
  document.addEventListener("htmx:confirm", function (e) {
    var el = e.detail.elt;
    if (!el || !el.hasAttribute("data-pha-confirm")) return; // default behaviour
    e.preventDefault();
    pending = e.detail;
    var body = document.getElementById("pha-confirm-text");
    if (body) body.textContent = e.detail.question || "Are you sure?";
    window.dispatchEvent(new CustomEvent("open-modal", { detail: "pha-confirm-modal" }));
  });

  document.addEventListener("DOMContentLoaded", function () {
    var yes = document.getElementById("pha-confirm-yes");
    if (yes) {
      yes.addEventListener("click", function () {
        if (pending) {
          pending.issueRequest(true);
          pending = null;
        }
      });
    }
  });
})();
