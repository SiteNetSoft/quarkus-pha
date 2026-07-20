import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Saving…").id("btn-loading-primary").variant("primary").loading("Saving").build(),
        Button.of("Loading…").id("btn-loading-secondary").variant("secondary").loading("Loading").build(),
        Button.of("Fetching").id("btn-loading-link").variant("link").loading("Fetching").build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
