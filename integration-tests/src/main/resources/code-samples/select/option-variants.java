import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Basic option").build();

Menu menu = Menu.builder()
        .id("sl-variants-menu")
        .selectSingle()
        .item(MenuItem.of("Basic option").asSelected())
        .item(MenuItem.of("With description").description("A second line of descriptive text."))
        .item(MenuItem.of("With icon").icon("fa:bell"))
        .item(MenuItem.of("Disabled option").asDisabled())
        .build();

// Template side — same select composition wrapper as the single-select example:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
