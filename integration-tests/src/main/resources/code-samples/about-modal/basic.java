import org.sitenetsoft.quarkus.pha.model.*;

AboutModal aboutModal = AboutModal
        .of("basic", "/web/images/quarkus-pha-logo-dark.svg", "quarkus-pha logo")
        .productName("quarkus-pha").headingLevel("h2")
        .backgroundImage("/web/images/pf-background.svg")
        .trigger("About")
        .content("""
                <dl class="pf-v6-c-description-list pf-m-horizontal">
                  <div class="pf-v6-c-description-list__group">
                    <dt class="pf-v6-c-description-list__term"><span class="pf-v6-c-description-list__text">Version</span></dt>
                    <dd class="pf-v6-c-description-list__description">
                      <div class="pf-v6-c-description-list__text">1.0.0-SNAPSHOT</div>
                    </dd>
                  </div>
                  <div class="pf-v6-c-description-list__group">
                    <dt class="pf-v6-c-description-list__term"><span class="pf-v6-c-description-list__text">Quarkus</span></dt>
                    <dd class="pf-v6-c-description-list__description"><div class="pf-v6-c-description-list__text">3.35.3</div></dd>
                  </div>
                </dl>""")
        .trademarkHtml("<p class=\"pf-v6-c-about-modal-box__strapline\">Trademark and legal notices</p>")
        .strapline("Copyright &copy; 2026 SiteNetSoft")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/about-modal aboutModal=aboutModal /}
