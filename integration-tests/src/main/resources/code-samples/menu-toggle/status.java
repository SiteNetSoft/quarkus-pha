import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.of("Success").id("mt-status-success").status("success").build(),
        MenuToggle.of("Warning").id("mt-status-warning").status("warning").build(),
        MenuToggle.of("Danger").id("mt-status-danger").status("danger").build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
