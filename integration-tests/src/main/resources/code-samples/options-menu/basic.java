import org.sitenetsoft.quarkus.pha.model.*;

// Options menu: a single-select menu behind a text toggle. selectSingle() gives
// items the option role and the selected-check treatment.
MenuToggle toggle = MenuToggle.of("Options menu").build();

Menu menu = Menu.builder()
        .id("om-basic-menu")
        .selectSingle()
        .group(Menu.group(null,
                MenuItem.of("Option 1").asSelected(),
                MenuItem.of("Disabled Option").asDisabled()))
        .group(Menu.group("Group 1",
                MenuItem.of("Option 1"),
                MenuItem.of("Option 2")))
        .group(Menu.group("Group 2",
                MenuItem.of("Option 1"),
                MenuItem.of("Option 2")))
        .build();

// Template side — the composition wrapper owns the open state; this demo also
// tracks the chosen option in Alpine so clicks re-render the check client-side:
// {#include components/navigation/menu-toggle toggleText='Options menu' expandedExpr='open' /}
// ... {#include components/navigation/menu menu=menu /}
