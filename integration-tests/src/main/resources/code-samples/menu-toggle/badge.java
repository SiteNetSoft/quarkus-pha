import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.of("Count").id("mt-badge").badge("4 selected").build(),
        MenuToggle.of("").id("mt-badge-plain").asPlain().asText().badge("4")
                .ariaLabel("Menu toggle with badge").build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
