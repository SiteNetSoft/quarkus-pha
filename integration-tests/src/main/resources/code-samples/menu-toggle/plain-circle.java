import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.of("").id("mt-circle").asPlain().asCircle().icon("fa:ellipsis-vertical")
                .ariaLabel("Plain circle kebab").build(),
        MenuToggle.of("").id("mt-circle-expanded").asPlain().asCircle().asExpanded()
                .icon("fa:ellipsis-vertical").ariaLabel("Expanded plain circle kebab").build(),
        MenuToggle.of("").id("mt-circle-disabled").asPlain().asCircle().asDisabled()
                .icon("fa:ellipsis-vertical").ariaLabel("Disabled plain circle kebab").build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
