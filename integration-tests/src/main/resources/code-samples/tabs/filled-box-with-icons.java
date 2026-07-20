import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-filled-box-with-icons").filled().box()
        .ariaLabel("Filled box tabs with icons")
        .item(TabItem.of("fbi1", "Users").icon("fa:users").panel("Users panel content"))
        .item(TabItem.of("fbi2", "Containers").icon("fa:cube").panel("Containers panel content"))
        .item(TabItem.of("fbi3", "Database").icon("fa:database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
