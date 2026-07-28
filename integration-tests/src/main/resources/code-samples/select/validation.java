import org.sitenetsoft.quarkus.pha.model.*;

// Validated select: the toggle carries a status treatment until a valid option
// is chosen. status("danger") renders the invalid state server-side.
MenuToggle toggle = MenuToggle.of("Select a state").asPlaceholder().status("danger").build();

Menu menu = Menu.builder()
        .id("sl-validation-menu")
        .selectSingle()
        .items(MenuItem.of("Alabama"), MenuItem.of("Florida"), MenuItem.of("New Jersey"),
                MenuItem.of("New Mexico"), MenuItem.of("New York"), MenuItem.of("North Carolina"))
        .build();

// Template side — same select composition wrapper; this demo flips the status
// classes and the helper text ("You must select a state.") client-side once a
// choice is made:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
