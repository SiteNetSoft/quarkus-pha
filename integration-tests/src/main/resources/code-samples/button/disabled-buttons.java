import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Primary disabled").id("btn-disabled-primary").variant("primary").asDisabled().build(),
        Button.of("Secondary disabled").id("btn-disabled-secondary").variant("secondary").asDisabled().build(),
        Button.of("Tertiary disabled").id("btn-disabled-tertiary").variant("tertiary").asDisabled().build(),
        Button.of("Danger disabled").id("btn-disabled-danger").variant("danger").asDisabled().build(),
        Button.of("Link disabled").id("btn-disabled-link").variant("link").asDisabled().build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
