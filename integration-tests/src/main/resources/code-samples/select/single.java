import org.sitenetsoft.quarkus.pha.model.*;

// Select is a composition: a menu-toggle whose label tracks the chosen option,
// plus a single-select menu. These builders produce the same anatomy.
MenuToggle toggle = MenuToggle.of("Pick one").id("sl-single-toggle").asPlaceholder().build();

Menu menu = Menu.builder()
        .id("sl-single-menu")
        .selectSingle()
        .items(MenuItem.of("Mr"), MenuItem.of("Mrs"), MenuItem.of("Ms"),
                MenuItem.of("Dr"), MenuItem.of("Other"))
        .build();

// Template side — the wrapper owns the Alpine open state; this demo also swaps
// the toggle label and the selected check client-side when an option is picked:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
