import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.of("Settings").id("mt-settings").asSettings().icon("fa:gear").build(),
        MenuToggle.of("").id("mt-settings-plain").asPlain().asSettings().icon("fa:gear")
                .ariaLabel("Settings").build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
