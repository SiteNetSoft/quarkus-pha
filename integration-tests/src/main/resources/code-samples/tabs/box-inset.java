import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-box-inset").box()
        .modifiers("pf-m-inset-sm-on-md pf-m-inset-lg-on-lg pf-m-inset-2xl-on-xl")
        .ariaLabel("Box tabs with adjusted insets")
        .item(TabItem.of("bi1", "Users").panel("Users panel content"))
        .item(TabItem.of("bi2", "Containers").panel("Containers panel content"))
        .item(TabItem.of("bi3", "Database").panel("Database panel content"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
