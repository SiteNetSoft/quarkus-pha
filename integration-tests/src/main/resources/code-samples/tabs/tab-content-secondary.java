import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-panels-secondary").panelsOnly().panelSecondary()
        .item(TabItem.of("tcs1", "Panel 1").panel("Panel 1"))
        .item(TabItem.of("tcs2", "Panel 2").panel("Panel 2"))
        .item(TabItem.of("tcs3", "Panel 3").panel("Panel 3"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
