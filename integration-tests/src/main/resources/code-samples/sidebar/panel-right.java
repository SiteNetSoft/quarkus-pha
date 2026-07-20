import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-right")
        .panelRight().gutter().contentFirst()
        .contentStyle("padding: 1rem").content("Main content on the left.")
        .panelStyle("background: var(--pf-t--global--background--color--secondary--default); padding: 1rem")
        .panel("Panel on the right — useful for activity, related items, or contextual help.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
