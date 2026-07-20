import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-group-section").style("min-height: 400px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo")
        .group(false,
                Breadcrumb.of("pg-group-breadcrumb")
                        .item(BreadcrumbItem.of("Section home").href("#"))
                        .item(BreadcrumbItem.of("Section title").href("#").asCurrent()).build(),
                Section.of("The breadcrumb and this section are wrapped in a single"
                        + " <code class=\"ws-code\">pf-v6-c-page__main-group</code>, so they read"
                        + " as one visual unit."))
        .section(Section.of("A separate section outside the group.").modifiers("pf-m-secondary"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
