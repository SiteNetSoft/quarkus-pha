import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.typeahead("sl-mt-creatable", "Select states").build();

Menu menu = Menu.builder()
        .id("sl-mt-creatable-menu")
        .selectMulti()
        .items(MenuItem.of("Alabama"), MenuItem.of("Florida"), MenuItem.of("New Jersey"),
                MenuItem.of("New Mexico"), MenuItem.of("New York"), MenuItem.of("North Carolina"))
        .build();

// Template side — same as the multi typeahead; Alpine additionally appends a
// `Create "<input>"` option when the input matches nothing, adding the typed
// value as a new chip + option:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
