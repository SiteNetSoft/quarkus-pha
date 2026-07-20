import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Call to action").id("btn-cta-primary").variant("primary").size("lg").build(),
        Button.of("Call to action").id("btn-cta-secondary").variant("secondary").size("lg").build(),
        Button.of("Call to action").id("btn-cta-tertiary").variant("tertiary").size("lg").build(),
        Button.of("Call to action").id("btn-cta-link").variant("link").size("lg").build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
