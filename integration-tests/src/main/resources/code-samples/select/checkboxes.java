import org.sitenetsoft.quarkus.pha.model.*;

// Multi-select with checkbox menu items — checkboxes(idPrefix) renders each
// item as a labelled checkbox row.
MenuToggle toggle = MenuToggle.of("Pick fruit").build();

Menu menu = Menu.builder()
        .id("sl-checkboxes-menu")
        .checkboxes("sl-check")
        .items(MenuItem.of("Apple"), MenuItem.of("Banana"),
                MenuItem.of("Cherry"), MenuItem.of("Date"))
        .build();

// Template side — same select composition wrapper; this demo counts the checked
// boxes in Alpine and shows "N selected" in the toggle label:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
