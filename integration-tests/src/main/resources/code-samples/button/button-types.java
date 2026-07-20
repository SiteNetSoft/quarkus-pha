import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Submit").id("btn-type-submit").type("submit").build(),
        Button.of("Reset").id("btn-type-reset").type("reset").build(),
        Button.of("Default").id("btn-type-default").build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
