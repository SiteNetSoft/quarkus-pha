import org.sitenetsoft.quarkus.pha.model.*;

// Multi typeahead with checkboxes: the typeahead toggle plus a checkbox menu.
MenuToggle toggle = MenuToggle.typeahead("sl-mt-checkbox", "Select states").build();

Menu menu = Menu.builder()
        .id("sl-mt-checkbox-menu")
        .checkboxes("sl-mt-check")
        .items(MenuItem.of("Alabama"), MenuItem.of("Florida"), MenuItem.of("New Jersey"),
                MenuItem.of("New Mexico"), MenuItem.of("New York"), MenuItem.of("North Carolina"))
        .build();

// Template side — same select composition wrapper; filtering and the
// selected-count toggle text are Alpine:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
