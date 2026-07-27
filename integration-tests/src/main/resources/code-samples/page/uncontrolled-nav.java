import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-uncontrolled-nav").style("min-height: 360px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo").toggle("Uncontrolled nav demo")
        .sidebar(Nav.builder().ariaLabel("Uncontrolled nav demo secondary")
                .item("Nav item 1", "#", true).item("Nav item 2", "#").item("Nav item 3", "#").build())
        .section(Section.of("The sidebar state is owned entirely by the page shell (local Alpine"
                + " state) — no outside component controls it. Click the burger to toggle."))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
