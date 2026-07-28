import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Dropdown").build();

Menu menu = Menu.builder()
        .id("dd-desc-menu")
        .item(MenuItem.of("Edit").description("Change the resource name or labels"))
        .item(MenuItem.of("Delete").asDanger().description("Permanently remove the resource"))
        .build();

// Template side — same composition wrapper as the basic dropdown:
// {#include components/navigation/menu-toggle toggleText='Dropdown' expandedExpr='open' /}
// ... {#include components/navigation/menu menu=menu /}
