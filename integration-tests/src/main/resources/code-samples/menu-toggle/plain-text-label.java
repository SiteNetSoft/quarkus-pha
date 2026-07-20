import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.of("Text").id("mt-plain-text").asPlain().asText()
                .ariaLabel("Plain menu toggle").build(),
        MenuToggle.of("Text").id("mt-plain-text-expanded").asPlain().asText().asExpanded()
                .ariaLabel("Expanded plain menu toggle").build(),
        MenuToggle.of("Text").id("mt-plain-text-disabled").asPlain().asText().asDisabled()
                .ariaLabel("Disabled plain menu toggle").build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
