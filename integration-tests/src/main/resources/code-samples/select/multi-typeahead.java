import org.sitenetsoft.quarkus.pha.model.*;

// Multi typeahead: the typeahead toggle hosts the picked values as label chips;
// the menu stays a multi-select list.
MenuToggle toggle = MenuToggle.typeahead("sl-multi-typeahead", "Select states").build();

Menu menu = Menu.builder()
        .id("sl-multi-typeahead-menu")
        .selectMulti()
        .items(MenuItem.of("Alabama"), MenuItem.of("Florida"), MenuItem.of("New Jersey"),
                MenuItem.of("New Mexico"), MenuItem.of("New York"), MenuItem.of("North Carolina"))
        .build();

// Template side — same select composition wrapper; the chip group inside the
// toggle (one removable Label per picked state) and the filtering are Alpine:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
