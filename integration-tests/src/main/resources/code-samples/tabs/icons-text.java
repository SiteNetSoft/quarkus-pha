import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-icons-text")
        .ariaLabel("Tabs with icons and text")
        .item(TabItem.of("it1", "Users").icon("fa:users").panel("Users panel content"))
        .item(TabItem.of("it2", "Containers").icon("fa:cube").panel("Containers panel content"))
        .item(TabItem.of("it3", "Database").icon("fa:database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
