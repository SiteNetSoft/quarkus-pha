import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-filled-with-icons").filled()
        .ariaLabel("Filled tabs with icons")
        .item(TabItem.of("f1", "Users").icon("fa:users").panel("Users panel content"))
        .item(TabItem.of("f2", "Containers").icon("fa:cube").panel("Containers panel content"))
        .item(TabItem.of("f3", "Database").icon("fa:database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
