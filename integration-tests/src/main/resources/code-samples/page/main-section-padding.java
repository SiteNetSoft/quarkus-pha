import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-main-section-padding").style("min-height: 360px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo")
        .section(Section.of("Section with default padding."))
        .section(Section.of("Section with <code class=\"ws-code\">pf-m-no-padding</code> —"
                + " content runs edge to edge.").modifiers("pf-m-no-padding"))
        .section(Section.of("Section with <code class=\"ws-code\">pf-m-no-padding pf-m-padding-on-md</code>"
                + " — padding returns at the md breakpoint.").modifiers("pf-m-no-padding pf-m-padding-on-md"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
