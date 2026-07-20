import org.sitenetsoft.quarkus.pha.model.*;

Tabs mainTabs = Tabs.of("tabs-animate-subtabs-main")
        .modifiers("pf-m-animate-current").ariaLabel("Animated main tabs").externalState()
        .item(TabItem.of("t1", "Users"))
        .item(TabItem.of("t2", "Containers"))
        .build();

Tabs subTabs = Tabs.of("tabs-animate-subtabs-secondary")
        .subtab().modifiers("pf-m-animate-current").ariaLabel("Animated secondary tabs")
        .externalState().stateVar("sub")
        .item(TabItem.of("s1", "Active"))
        .item(TabItem.of("s2", "Disabled"))
        .item(TabItem.of("s3", "Pending"))
        .build();

// Template side, with the data in scope:
// Chrome owns the shared state: <div x-data="{ active: '…', sub: '…' }">
// {#include components/navigation/tabs tabs=mainTabs /}
// {#include components/navigation/tabs tabs=subTabs /}
