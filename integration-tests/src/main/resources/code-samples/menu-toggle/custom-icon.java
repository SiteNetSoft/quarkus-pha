import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.of("CPU usage").id("mt-custom-icon").icon("fa:server").build(),
        MenuToggle.of("Schedule").id("mt-custom-icon-2").icon("fa:clock").build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
