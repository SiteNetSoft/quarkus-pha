import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-animate-default").animated()
        .ariaLabel("Animated tabs")
        .item(TabItem.of("ad1", "Users").panel("Users panel content"))
        .item(TabItem.of("ad2", "Containers").panel("Containers panel content"))
        .item(TabItem.of("ad3", "Database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
