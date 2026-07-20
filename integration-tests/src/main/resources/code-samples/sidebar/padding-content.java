import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-padding-content")
        .panelSecondary().panel("Panel without padding.")
        .contentPadding().content("Only the content opts into padding (pf-m-padding).")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
