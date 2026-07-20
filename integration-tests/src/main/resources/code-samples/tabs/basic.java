import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-basic")
        .item(TabItem.of("tab-1", "Overview").panel("Overview panel — high-level summary."))
        .item(TabItem.of("tab-2", "Details").panel("Details panel — per-field values."))
        .item(TabItem.of("tab-3", "Logs").panel("Logs panel — recent activity."))
        .panelPadding()
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
