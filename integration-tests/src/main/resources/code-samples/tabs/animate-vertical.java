import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-animate-vertical").animated().vertical()
        .ariaLabel("Animated vertical tabs")
        .item(TabItem.of("av1", "General").panel("General settings."))
        .item(TabItem.of("av2", "Security").panel("Security settings."))
        .item(TabItem.of("av3", "Notifications").panel("Notification preferences."))
        .panelPadding()
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
