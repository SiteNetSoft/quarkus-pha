import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-centered-section").style("min-height: 320px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo")
        .section(Section.of("""
                <div class="pf-v6-c-card">
                  <div class="pf-v6-c-card__body">
                    When a width-limited page section is wider than the value of
                    <code class="ws-code">--pf-v6-c-page--section--m-limit-width--MaxWidth</code>, the section is centered in
                    the main area (<code class="ws-code">pf-m-limit-width pf-m-align-center</code>).
                  </div>
                </div>""")
                .modifiers("pf-m-limit-width pf-m-align-center"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
