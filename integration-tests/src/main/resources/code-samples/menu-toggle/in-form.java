import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Select an owner").id("mt-in-form")
        .asFullWidth().build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
