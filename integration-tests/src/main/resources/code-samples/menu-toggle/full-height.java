import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Full height").id("mt-full-height")
        .asFullHeight().build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
