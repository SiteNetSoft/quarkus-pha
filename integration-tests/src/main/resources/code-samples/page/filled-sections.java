import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-filled-sections").style("min-height: 480px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo")
        .section(Section.of("Default fill behavior — the section sizes to its content."))
        .section(Section.of("<code class=\"ws-code\">pf-m-fill</code> — this section grows to fill"
                + " the available vertical space.").modifiers("pf-m-fill"))
        .section(Section.of("<code class=\"ws-code\">pf-m-no-fill</code> — this section never grows"
                + " beyond its content.").modifiers("pf-m-no-fill"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
