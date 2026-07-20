import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-nav").nav()
        .ariaLabel("Tabs linked to nav elements")
        .item(TabItem.of("n1", "Cluster overview").panel("Cluster overview panel"))
        .item(TabItem.of("n2", "Nodes").panel("Nodes panel"))
        .item(TabItem.of("n3", "Workloads").panel("Workloads panel"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
