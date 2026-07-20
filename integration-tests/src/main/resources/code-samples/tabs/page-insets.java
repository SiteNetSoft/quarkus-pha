import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-page-insets").pageInsets()
        .ariaLabel("Tabs with page insets")
        .item(TabItem.of("p1", "Users").panel("Users panel content"))
        .item(TabItem.of("p2", "Containers").panel("Containers panel content"))
        .item(TabItem.of("p3", "Database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
