import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Select a state").asPlaceholder().build();

Menu menu = Menu.builder()
        .id("sl-footer-menu")
        .selectSingle()
        .items(MenuItem.of("Alabama"), MenuItem.of("Florida"), MenuItem.of("New Jersey"))
        .footerButton("View all states")
        .build();

// Template side — same select composition wrapper as the single-select example:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
