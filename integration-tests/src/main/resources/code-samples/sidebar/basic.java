import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-basic")
        .panelStyle("background: var(--pf-t--global--background--color--secondary--default); padding: 1rem")
        .panel("Panel — filters, secondary nav, or context info.")
        .contentStyle("padding: 1rem")
        .content("Main content sits next to the panel. The sidebar component itself doesn't impose"
                + " a width; control via CSS variables or container size.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
