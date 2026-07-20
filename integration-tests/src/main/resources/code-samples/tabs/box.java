import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-box").box()
        .item(TabItem.of("b1", "Tab 1").panel("Box variant — content one."))
        .item(TabItem.of("b2", "Tab 2").panel("Box variant — content two."))
        .panelPadding()
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
