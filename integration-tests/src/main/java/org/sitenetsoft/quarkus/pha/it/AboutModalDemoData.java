package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.AboutModal;

/**
 * Demo data for the about-modal examples — the examples on
 * /components/about-modal are populated from these models. Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/about-modal/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class AboutModalDemoData {

    public static AboutModal demoAmBasic = AboutModal
            .of("basic", "/web/images/quarkus-pha-logo-dark.svg", "quarkus-pha logo")
            .productName("quarkus-pha").headingLevel("h2")
            .backgroundImage("/web/images/pf-background.svg")
            .trigger("About")
            .content("""
                    <div class="pf-v6-c-content">
                      <dl>
                        <dt>Version</dt>
                        <dd>1.0.0-SNAPSHOT</dd>
                        <dt>Quarkus</dt>
                        <dd>3.37.3</dd>
                        <dt>PatternFly</dt>
                        <dd>6.6.0</dd>
                        <dt>HTMX</dt>
                        <dd>2.0.10</dd>
                        <dt>Alpine.js</dt>
                        <dd>3.15.12</dd>
                        <dt>Java</dt>
                        <dd>25</dd>
                        <dt>License</dt>
                        <dd>Apache-2.0</dd>
                      </dl>
                    </div>""")
            .strapline("Copyright &copy; 2026 SiteNetSoft")
            .build();

    public static AboutModal demoAmComplex = AboutModal
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
                              <span>Quarkus 3.37 + Qute</span>
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

    public static AboutModal demoAmNoName = AboutModal
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
}
