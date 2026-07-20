import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-border").split()
        .panelSecondary().panelPadding()
        .panel("Split border between panel and content (pf-m-split).")
        .contentPadding().content("A divider line separates the two regions.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
