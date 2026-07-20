import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.of("Default").id("mt-variant-default").build(),
        MenuToggle.of("Primary").id("mt-variant-primary").asPrimary().build(),
        MenuToggle.of("Secondary").id("mt-variant-secondary").asSecondary().build(),
        MenuToggle.of("Danger").id("mt-variant-danger").asDanger().build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
