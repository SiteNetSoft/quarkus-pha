import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Primary aria-disabled").id("btn-aria-disabled-primary").variant("primary")
                .asAriaDisabled().build(),
        Button.of("Secondary aria-disabled").id("btn-aria-disabled-secondary").variant("secondary")
                .asAriaDisabled().build(),
        Button.of("Link aria-disabled").id("btn-aria-disabled-link").variant("link")
                .asAriaDisabled().build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
