import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-content-body-padding")
        .ariaLabel("Tabs with padded content body")
        .item(TabItem.of("cb1", "Users")
                .panel("This tab content body carries <code class=\"ws-code\">pf-m-padding</code>."))
        .item(TabItem.of("cb2", "Containers").panel("Containers panel — also padded."))
        .panelPadding()
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
