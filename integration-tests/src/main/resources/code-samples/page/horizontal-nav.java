import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-horizontal-nav").style("min-height: 320px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo").mastheadInline()
        .horizontalNav("Global",
                NavItem.of("Horizontal nav item 1").current(),
                NavItem.of("Horizontal nav item 2"),
                NavItem.of("Horizontal nav item 3"))
        .section(Section.of("The navigation lives in the masthead content area as a horizontal"
                + " <code class=\"ws-code\">pf-v6-c-nav pf-m-horizontal</code> — no sidebar."))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
