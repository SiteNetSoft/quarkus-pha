import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Locked").id("mt-disabled").asDisabled().build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
