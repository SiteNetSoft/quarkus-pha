import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-plain-sections").style("min-height: 480px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo")
        .section(Section.of("Plain section — <code class=\"ws-code\">pf-m-plain</code> removes"
                + " the background color.").modifiers("pf-m-plain"))
        .section(Section.of("Default section with the standard background."))
        .group(true, null,
                Section.of("Section inside a <code class=\"ws-code\">pf-v6-c-page__main-group pf-m-plain</code>."),
                Section.of("Another section in the same plain group."))
        .section(Section.of("Secondary variant section (<code class=\"ws-code\">pf-m-secondary</code>)"
                + " for contrast.").modifiers("pf-m-secondary"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
