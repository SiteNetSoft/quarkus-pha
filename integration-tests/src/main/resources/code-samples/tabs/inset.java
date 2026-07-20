import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-inset")
        .modifiers("pf-m-inset-sm-on-md pf-m-inset-lg-on-lg pf-m-inset-2xl-on-xl")
        .ariaLabel("Tabs with adjusted inset")
        .item(TabItem.of("i1", "Users").panel("Users panel content"))
        .item(TabItem.of("i2", "Containers").panel("Containers panel content"))
        .item(TabItem.of("i3", "Database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
