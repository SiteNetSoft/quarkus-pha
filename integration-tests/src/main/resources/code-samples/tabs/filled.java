import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-filled").filled()
        .ariaLabel("Filled tabs")
        .item(TabItem.of("fl1", "Users").panel("Users panel content"))
        .item(TabItem.of("fl2", "Containers").panel("Containers panel content"))
        .item(TabItem.of("fl3", "Database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
