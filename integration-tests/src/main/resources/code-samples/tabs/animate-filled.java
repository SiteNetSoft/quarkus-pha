import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-animate-filled").animated().filled()
        .ariaLabel("Animated filled tabs")
        .item(TabItem.of("af1", "Users").panel("Users panel content"))
        .item(TabItem.of("af2", "Containers").panel("Containers panel content"))
        .item(TabItem.of("af3", "Database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
