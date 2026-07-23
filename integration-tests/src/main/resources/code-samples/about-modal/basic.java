import org.sitenetsoft.quarkus.pha.model.*;

AboutModal aboutModal = AboutModal
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

// Template side, with the data in scope:
// {#include components/feedback/about-modal aboutModal=aboutModal /}
