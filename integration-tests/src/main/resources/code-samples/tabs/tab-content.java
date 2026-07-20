import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-panels").panelsOnly()
        .item(TabItem.of("tc1", "Panel 1").panel("Panel 1"))
        .item(TabItem.of("tc2", "Panel 2").panel("Panel 2"))
        .item(TabItem.of("tc3", "Panel 3").panel("Panel 3"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
