import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-filled-box").filled().box()
        .ariaLabel("Filled box tabs")
        .item(TabItem.of("fb1", "Users").panel("Users panel content"))
        .item(TabItem.of("fb2", "Containers").panel("Containers panel content"))
        .item(TabItem.of("fb3", "Database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
