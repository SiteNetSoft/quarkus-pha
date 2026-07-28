import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.typeahead("sl-typeahead-creatable", "Select a state").build();

Menu menu = Menu.builder()
        .id("sl-typeahead-creatable-menu")
        .selectSingle()
        .items(MenuItem.of("Alabama"), MenuItem.of("Florida"), MenuItem.of("New Jersey"),
                MenuItem.of("New Mexico"), MenuItem.of("New York"), MenuItem.of("North Carolina"))
        .build();

// Template side — same as the typeahead select; additionally, when the input
// matches nothing, Alpine appends a `Create "<input>"` option that adds the
// typed value to the option list:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
