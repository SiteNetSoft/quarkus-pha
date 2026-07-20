import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-box-secondary").box().secondary()
        .ariaLabel("Box secondary tabs")
        .item(TabItem.of("s1", "Users").panel("Users panel content"))
        .item(TabItem.of("s2", "Containers").panel("Containers panel content"))
        .item(TabItem.of("s3", "Database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
