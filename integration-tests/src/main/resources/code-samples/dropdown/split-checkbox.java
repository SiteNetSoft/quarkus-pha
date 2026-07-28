import org.sitenetsoft.quarkus.pha.model.*;

// Split toggle: the checkbox selects, the caret opens the action menu.
MenuToggle toggle = MenuToggle.split("dd-split-checkbox-check")
        .checkbox("Select all")
        .build();

Menu menu = Menu.builder()
        .id("dd-split-menu")
        .item(MenuItem.of("Select all"))
        .item(MenuItem.of("Select none"))
        .build();

// Template side — same composition wrapper as the basic dropdown; this demo
// additionally x-models the checkbox so the menu actions drive its state:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
