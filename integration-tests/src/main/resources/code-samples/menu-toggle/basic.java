import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Actions").id("mt-basic").build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
