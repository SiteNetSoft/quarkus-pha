import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.icon("fa:pen-to-square", "Edit (padded)").id("btn-plain-padded").variant("plain").build(),
        Button.icon("fa:pen-to-square", "Edit (no padding)").id("btn-plain-no-pad")
                .variant("plain").asNoPadding().build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
