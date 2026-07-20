import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-box-vertical").box().vertical()
        .item(TabItem.of("bv1", "General").panel("General settings."))
        .item(TabItem.of("bv2", "Security").panel("Security settings."))
        .item(TabItem.of("bv3", "Notifications").panel("Notification preferences."))
        .panelPadding()
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
