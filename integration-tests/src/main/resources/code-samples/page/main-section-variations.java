import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-main-section-variations").style("min-height: 420px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo")
        .region("subnav", "With subnav type",
                "Element with <code class=\"ws-code\">pf-v6-c-page__main-subnav</code> for horizontal subnav navigation")
        .region("tabs", "With tabs type",
                "Element with <code class=\"ws-code\">pf-v6-c-page__main-tabs</code> for tabs")
        .region("breadcrumb", "With breadcrumb type",
                "Element with <code class=\"ws-code\">pf-v6-c-page__main-breadcrumb</code> for breadcrumbs")
        .section(Section.of("Element with <code class=\"ws-code\">pf-v6-c-page__main-section</code>"
                + " for main content sections").ariaLabel("With default type"))
        .region("wizard", "With wizard type",
                "Element with <code class=\"ws-code\">pf-v6-c-page__main-wizard</code> for wizards")
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
