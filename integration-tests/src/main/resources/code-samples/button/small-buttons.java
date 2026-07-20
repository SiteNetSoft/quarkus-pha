import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Small primary").id("btn-sm-primary").variant("primary").size("sm").build(),
        Button.of("Small secondary").id("btn-sm-secondary").variant("secondary").size("sm").build(),
        Button.of("Small tertiary").id("btn-sm-tertiary").variant("tertiary").size("sm").build(),
        Button.of("Small link").id("btn-sm-link").variant("link").size("sm").build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
