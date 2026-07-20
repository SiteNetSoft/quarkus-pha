import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Open").id("mt-expanded").asExpanded().build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
