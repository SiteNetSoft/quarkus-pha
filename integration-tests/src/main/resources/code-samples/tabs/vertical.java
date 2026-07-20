import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-vertical").vertical()
        .item(TabItem.of("v1", "General").panel("General settings."))
        .item(TabItem.of("v2", "Security").panel("Security settings."))
        .item(TabItem.of("v3", "Notifications").panel("Notification preferences."))
        .panelPadding()
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
