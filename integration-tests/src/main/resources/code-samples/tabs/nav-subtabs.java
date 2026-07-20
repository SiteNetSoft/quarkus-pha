import org.sitenetsoft.quarkus.pha.model.*;

Tabs mainTabs = Tabs.of("tabs-nav-main").nav()
        .ariaLabel("Main navigation tabs").externalState()
        .item(TabItem.of("m1", "Cluster"))
        .item(TabItem.of("m2", "Nodes"))
        .build();

Tabs subTabs = Tabs.of("tabs-nav-subtab").nav().subtab()
        .ariaLabel("Secondary navigation tabs").externalState().stateVar("sub")
        .item(TabItem.of("sb1", "Details"))
        .item(TabItem.of("sb2", "Metrics"))
        .item(TabItem.of("sb3", "Events"))
        .build();

// Template side, with the data in scope:
// Chrome owns the shared state: <div x-data="{ active: '…', sub: '…' }">
// {#include components/navigation/tabs tabs=mainTabs /}
// {#include components/navigation/tabs tabs=subTabs /}
