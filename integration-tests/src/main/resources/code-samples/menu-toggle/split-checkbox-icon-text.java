import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.split("mt-split-check-icon-text").checkbox("Select all")
                .toggleIcon("fa:gear").toggleText("Icon").build(),
        MenuToggle.split("mt-split-check-icon-text-primary").checkbox("Select all")
                .toggleIcon("fa:gear").toggleText("Icon").asPrimary().build(),
        MenuToggle.split("mt-split-check-icon-text-secondary").checkbox("Select all")
                .toggleIcon("fa:gear").toggleText("Icon").asSecondary().build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
