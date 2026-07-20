import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Icon> icons = List.of(
        Icon.of("fa:circle-info").size("xl").status("info").label("Info").build(),
        Icon.of("fa:circle-check").size("xl").status("success").label("Success").build(),
        Icon.of("fa:triangle-exclamation").size("xl").status("warning").label("Warning").build(),
        Icon.of("fa:circle-exclamation").size("xl").status("danger").label("Danger").build(),
        Icon.of("fa:bell").size("xl").status("custom").label("Custom").build());

// Template side, with the data in scope:
// {#for i in icons}{#include components/icon iconModel=i /}{/for}
