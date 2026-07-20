import org.sitenetsoft.quarkus.pha.model.*;

Sidebar sidebar = Sidebar.of("sb-responsive-panel")
        .panelSecondary().panelPadding()
        .panelWidths("pf-m-width-25 pf-m-width-33-on-lg pf-m-width-50-on-xl")
        .panel("Responsive width — 25% by default, 33% on lg, 50% on xl.")
        .contentPadding().content("The panel width modifiers take breakpoint suffixes.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/sidebar sidebar=sidebar /}
