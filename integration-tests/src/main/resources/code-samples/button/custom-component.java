import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Span as a button").id("btn-custom-span").variant("secondary").asSpan().build(),
        Button.of("Div as a button").id("btn-custom-div").variant("tertiary").asDiv().build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
