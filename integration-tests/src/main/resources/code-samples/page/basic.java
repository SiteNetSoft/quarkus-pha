import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-basic").style("min-height: 360px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("App name")
        .section(Section.of("""
                <h2 class="pf-v6-c-title pf-m-2xl">Welcome</h2>
                <p style="margin-top: 1rem">Page content lives inside <code>pf-v6-c-page__main</code>. Combine with the Layouts components (Sidebar, Stack, Flex) to structure the body.</p>""")
                .bodyStyle("padding: 1rem"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
