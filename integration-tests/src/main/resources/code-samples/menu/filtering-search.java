import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-filtering-search").style("max-width: 260px")
        .searchFilter("Search", "Search menu items")
        .item(MenuItem.of("Action 1"))
        .item(MenuItem.of("Action 2"))
        .item(MenuItem.of("Action 3"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
