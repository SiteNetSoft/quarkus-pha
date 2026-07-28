import org.sitenetsoft.quarkus.pha.model.*;

// Menu with an inline search filter above the list — searchFilter(placeholder,
// ariaLabel) renders the pf-v6-c-menu__search text-input-group.
Menu menu = Menu.builder()
        .id("cm-search-menu")
        .searchFilter("", "Filter menu items")
        .items(MenuItem.of("Action 1"), MenuItem.of("Action 2"), MenuItem.of("Action 3"),
                MenuItem.of("AWS"), MenuItem.of("AWS 2"),
                MenuItem.of("Azure"), MenuItem.of("Azure 2"),
                MenuItem.of("My project"), MenuItem.of("My project 2"),
                MenuItem.of("OpenShift cluster"), MenuItem.of("OpenShift cluster 2"))
        .build();

// Template side — same composition wrapper; the live filtering (and the
// "No results found" row) is Alpine over this anatomy:
// {#include components/navigation/menu-toggle expandedExpr='open' /}
// ... {#include components/navigation/menu menu=menu /}
