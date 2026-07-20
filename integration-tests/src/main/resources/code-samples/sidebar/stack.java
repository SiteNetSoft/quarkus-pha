import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-stack").stack()
        .panelSecondary().panelPadding()
        .panel("Stacked panel — sits above the content instead of beside it.")
        .contentPadding().content("Content below the stacked panel (pf-m-stack).")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
