import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Placeholder text").id("mt-placeholder")
        .asPlaceholder().build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
