import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Dropdown").build();

// A title-less group followed by a titled group renders PF's grouped-dropdown
// anatomy (sections split by dividers).
Menu menu = Menu.builder()
        .id("dd-groups-menu")
        .group(Menu.group(null,
                MenuItem.of("Action 1")))
        .group(Menu.group("Group 1",
                MenuItem.of("Grouped action"),
                MenuItem.of("Another grouped action")))
        .build();

// Template side — same composition wrapper as the basic dropdown:
// {#include components/navigation/menu-toggle toggleText='Dropdown' expandedExpr='open' /}
// ... {#include components/navigation/menu menu=menu /}
