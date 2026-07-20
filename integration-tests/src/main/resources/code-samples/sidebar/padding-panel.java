import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-padding-panel")
        .panelSecondary().panelPadding()
        .panel("Only the panel opts into padding (pf-m-padding).")
        .content("Content without the padding modifier.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
