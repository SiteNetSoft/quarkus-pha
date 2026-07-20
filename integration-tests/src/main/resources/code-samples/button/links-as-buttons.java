import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Primary link").id("btn-link-primary").variant("primary")
                .href("#links-as-buttons").build(),
        Button.of("Secondary link").id("btn-link-secondary").variant("secondary")
                .href("#links-as-buttons").build(),
        Button.of("Open external").id("btn-link-link").variant("link").href("#links-as-buttons")
                .icon("fa:arrow-up-right-from-square").iconAtEnd().ariaLabel("Open external").build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
