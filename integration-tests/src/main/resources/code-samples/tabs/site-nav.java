import org.sitenetsoft.quarkus.pha.model.*;

Tabs tabs = Tabs.of("tabs-site-nav").nav().pageInsets().noBorderBottom()
        .ariaLabel("Site navigation tabs")
        .item(TabItem.of("sn1", "Home"))
        .item(TabItem.of("sn2", "Documentation"))
        .item(TabItem.of("sn3", "Community"))
        .item(TabItem.of("sn4", "Support"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/tabs tabs=tabs /}
