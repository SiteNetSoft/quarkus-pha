import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-uncontrolled-nav").style("min-height: 360px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo").toggle("Uncontrolled nav demo")
        .sidebarNav("Uncontrolled nav demo secondary",
                NavItem.of("Nav item 1").current(), NavItem.of("Nav item 2"), NavItem.of("Nav item 3"))
        .section(Section.of("The sidebar state is owned entirely by the page shell (local Alpine"
                + " state) — no outside component controls it. Click the burger to toggle."))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
