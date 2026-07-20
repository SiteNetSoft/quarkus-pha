import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-dynamic-sticky-section").style("height: 360px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo").scrollableMain()
        .section(Section.of("Sticky section (<code class=\"ws-code\">pf-m-sticky-top</code>) — scroll"
                + " the area below; this stays pinned. Height-based variants like"
                + " <code class=\"ws-code\">pf-m-sticky-top-on-lg-height</code> make it conditional.")
                .modifiers("pf-m-sticky-top pf-m-secondary"))
        .section(Section.of("""
                <p>Scrollable content 1</p>
                <p style="margin-top: 4rem">Scrollable content 2</p>
                <p style="margin-top: 4rem">Scrollable content 3</p>
                <p style="margin-top: 4rem">Scrollable content 4</p>
                <p style="margin-top: 4rem">Scrollable content 5</p>
                <p style="margin-top: 4rem">Scrollable content 6</p>"""))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
