import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-panel-right-gutter")
        .panelRight().gutter().panelSecondary().panelPadding()
        .panel("Right panel with a gutter between panel and content.")
        .contentPadding().content("pf-m-panel-right pf-m-gutter combine placement and spacing.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
