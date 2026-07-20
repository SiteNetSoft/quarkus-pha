import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-sticky-panel")
        .panelSticky().panelSecondary().panelPadding()
        .panel("Sticky panel — stays pinned while the content scrolls.")
        .contentPadding().content("<p>Scrollable content row 1</p><p>Scrollable content row 2</p>… (12 rows)")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
