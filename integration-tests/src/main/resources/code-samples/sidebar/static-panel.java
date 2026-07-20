import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-static-panel")
        .panelStatic().panelSecondary().panelPadding()
        .panel("Static panel — scrolls away with the content (pf-m-static).")
        .contentPadding().content("<p>Scrollable content row 1</p><p>Scrollable content row 2</p>… (12 rows)")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
