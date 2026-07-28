import org.sitenetsoft.quarkus.pha.model.*;

// Typeahead select: the toggle is a text input with caret + clear controls —
// MenuToggle.typeahead renders that anatomy.
MenuToggle toggle = MenuToggle.typeahead("sl-typeahead", "Select a state").build();

Menu menu = Menu.builder()
        .id("sl-typeahead-menu")
        .selectSingle()
        .items(MenuItem.of("Alabama"), MenuItem.of("Florida"), MenuItem.of("New Jersey"),
                MenuItem.of("New Mexico"), MenuItem.of("New York"), MenuItem.of("North Carolina"))
        .build();

// Template side — same select composition wrapper; the as-you-type filtering
// (and the "No results found" row) is Alpine over this anatomy — the menu shows
// only the options matching the input:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
