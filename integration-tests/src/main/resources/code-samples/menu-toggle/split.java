import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.split("mt-split").actionButton("Action").build(),
        MenuToggle.split("mt-split-primary").actionButton("Action").asPrimary().build(),
        MenuToggle.split("mt-split-secondary").actionButton("Action").asSecondary().build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
