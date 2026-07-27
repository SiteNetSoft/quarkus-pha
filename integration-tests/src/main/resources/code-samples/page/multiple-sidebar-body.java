import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-multiple-sidebar-body").style("min-height: 400px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo").toggle("Multiple sidebar body demo").toolbarText("header-tools")
        .sidebarContextSelector("First sidebar body (for a context selector/perspective switcher)")
        .sidebarNavFill()
        .sidebar(Nav.builder().ariaLabel("Multiple sidebar body demo secondary")
                .item("Nav item 1", "#", true).item("Nav item 2", "#").item("Nav item 3", "#").build())
        .section(Section.of("The sidebar stacks two <code class=\"ws-code\">pf-v6-c-page__sidebar-body</code>"
                + " elements — the first with <code class=\"ws-code\">pf-m-context-selector</code>, the second"
                + " with <code class=\"ws-code\">pf-m-fill</code> to take the remaining height."))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
