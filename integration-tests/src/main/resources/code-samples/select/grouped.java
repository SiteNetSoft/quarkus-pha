import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Select a resource").asPlaceholder().build();

Menu menu = Menu.builder()
        .id("sl-grouped-menu")
        .selectSingle()
        .group(Menu.group("Compute",
                MenuItem.of("Pods"),
                MenuItem.of("Deployments")))
        .group(Menu.group("Network",
                MenuItem.of("Services"),
                MenuItem.of("Routes")))
        .build();

// Template side — same select composition wrapper as the single-select example:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
