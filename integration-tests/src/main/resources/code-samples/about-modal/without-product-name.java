import org.sitenetsoft.quarkus.pha.model.*;

AboutModal aboutModal = AboutModal
        .of("noname", "/web/images/quarkus-pha-logo-dark.svg", "quarkus-pha logo")
        .ariaLabel("About this product")
        .backgroundImage("/web/images/pf-background.svg")
        .trigger("About (no product name)")
        .content("""
                <p>
                  This dialog has no product-name heading. Screen readers announce the modal via the <code>aria-label</code> attribute
                  instead.
                </p>
                <dl class="pf-v6-c-description-list pf-m-horizontal">
                  <div class="pf-v6-c-description-list__group">
                    <dt class="pf-v6-c-description-list__term"><span class="pf-v6-c-description-list__text">Version</span></dt>
                    <dd class="pf-v6-c-description-list__description">
                      <div class="pf-v6-c-description-list__text">1.0.0-SNAPSHOT</div>
                    </dd>
                  </div>
                </dl>""")
        .strapline("Copyright &copy; 2026 SiteNetSoft")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/about-modal aboutModal=aboutModal /}
