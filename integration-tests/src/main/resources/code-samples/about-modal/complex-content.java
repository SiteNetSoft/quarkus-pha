import org.sitenetsoft.quarkus.pha.model.*;

AboutModal aboutModal = AboutModal
        .of("complex", "/web/images/quarkus-pha-logo-dark.svg", "quarkus-pha logo")
        .productName("quarkus-pha").headingLevel("h2")
        .backgroundImage("/web/images/pf-background.svg")
        .noContentContainer()
        .trigger("About (complex content)")
        .content("""
                <div class="pf-v6-c-about-modal-box__content">
                  <div class="pf-v6-l-stack pf-m-gutter">
                    <div class="pf-v6-l-stack__item">
                      <h2 class="pf-v6-c-title pf-m-xl">Hand-crafted UI components</h2>
                      <p>
                        A Quarkus extension that delivers ready-to-use HTML components to any Quarkus/Qute application —
                        server-rendered, framework-free, HTMX-driven.
                      </p>
                    </div>
                    <div class="pf-v6-l-stack__item">
                      <div class="pf-v6-l-flex pf-m-space-items-md pf-m-justify-content-space-between">
                        <div>
                          <strong>Server</strong><br />
                          <span>Quarkus 3.35 + Qute</span>
                        </div>
                        <div>
                          <strong>UI</strong><br />
                          <span>PatternFly v6 + Alpine + HTMX</span>
                        </div>
                        <div>
                          <strong>License</strong><br />
                          <span>Apache 2.0</span>
                        </div>
                      </div>
                    </div>
                    <p class="pf-v6-c-about-modal-box__strapline">Copyright &copy; 2026 SiteNetSoft</p>
                  </div>
                </div>""")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/about-modal aboutModal=aboutModal /}
