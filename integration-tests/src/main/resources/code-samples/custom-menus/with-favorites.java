import org.sitenetsoft.quarkus.pha.model.*;

// Favorites menu: star actions on every item; favorited items are cloned into a
// leading "Favorites" group. asFavoriteAction() renders the star action.
MenuToggle toggle = MenuToggle.of("Favorites").build();

Menu menu = Menu.builder()
        .id("cm-favorites-menu")
        .group(Menu.group("Group 1",
                MenuItem.of("Item 1").asFavoriteAction(),
                MenuItem.of("Item 2").asFavoriteAction(),
                MenuItem.of("Item 3").asFavoriteAction(),
                MenuItem.of("Item 4").asFavoriteAction()))
        .group(Menu.group("Group 2",
                MenuItem.of("Item 5").asFavoriteAction(),
                MenuItem.of("Item 6").asFavoriteAction(),
                MenuItem.of("Item 7").asFavoriteAction(),
                MenuItem.of("Item 8").asFavoriteAction()))
        .build();

// Template side — same composition wrapper; the live "Favorites" group at the
// top (cloning starred items as you toggle them) is Alpine over this anatomy:
// {#include components/navigation/menu-toggle toggleText='Favorites' expandedExpr='open' /}
// ... {#include components/navigation/menu menu=menu /}
