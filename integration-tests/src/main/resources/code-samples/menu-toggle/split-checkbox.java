import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.split("mt-split-check").checkbox("Select all").build(),
        MenuToggle.split("mt-split-check-primary").checkbox("Select all").asPrimary().build(),
        MenuToggle.split("mt-split-check-secondary").checkbox("Select all").asSecondary().build(),
        MenuToggle.split("mt-split-check-disabled").checkbox("Select all").asDisabled().build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
