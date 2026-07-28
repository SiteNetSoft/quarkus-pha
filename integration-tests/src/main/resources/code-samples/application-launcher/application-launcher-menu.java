import org.sitenetsoft.quarkus.pha.model.*;

// The application launcher is a composition around the menu component: a plain
// icon toggle, a search filter, favorites and grouped app links. These builders
// produce the same server-rendered anatomy.
Menu menu = Menu.builder()
        .id("al-menu")
        .searchFilter("", "Filter menu items")
        .group(Menu.group("Group 1",
                MenuItem.of("Application 1").asFavoriteAction(),
                MenuItem.of("Application 2").href("#default-link2").asFavoriteAction()))
        .group(Menu.group("Group 2",
                MenuItem.of("Custom component (such as @reach/router Link)")
                        .href("#router-link").asFavoriteAction(),
                MenuItem.of("Custom component with icon")
                        .href("#router-link2").asExternal().asFavoriteAction()))
        .group(Menu.group(null,
                MenuItem.of("Application 3 with tooltip").asFavoriteAction(),
                MenuItem.of("Unavailable Application").asDisabled()))
        .build();

// Template side — a plain menu-toggle (the waffle icon lives in its icon slot)
// anchors the menu, with the wrapper owning the Alpine open state:
// {#include components/navigation/menu-toggle plain=true ariaLabel='Application launcher'
//     expandedExpr='open' /}
// ... {#include components/navigation/menu menu=menu /}
//
// The live behaviors in this demo — filtering as you type, the favorites group
// cloning favorited items, the item tooltip — are Alpine on top of this anatomy;
// see the Qute source for that wiring.
