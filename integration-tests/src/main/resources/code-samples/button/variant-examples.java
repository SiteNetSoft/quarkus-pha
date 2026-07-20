import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Primary").id("btn-primary").variant("primary").build(),
        Button.of("Secondary").id("btn-secondary").variant("secondary").build(),
        Button.of("Tertiary").id("btn-tertiary").variant("tertiary").build(),
        Button.of("Danger").id("btn-danger").variant("danger").build(),
        Button.of("Warning").id("btn-warning").variant("warning").build(),
        Button.of("Link").id("btn-link").variant("link").build(),
        Button.icon("fa:xmark", "Close").id("btn-plain").variant("plain").build(),
        Button.of("Control").id("btn-control").variant("control").build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
