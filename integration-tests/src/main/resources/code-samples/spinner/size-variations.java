import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Spinner> items = List.of(
        Spinner.of("Loading (xs)").size("xs").build(),
        Spinner.of("Loading (sm)").size("sm").build(),
        Spinner.of("Loading (md)").size("md").build(),
        Spinner.of("Loading (lg)").size("lg").build(),
        Spinner.of("Loading (xl)").size("xl").build());

// Template side, with the data in scope:
// {#for x in items}{#include components/feedback/spinner spinner=x /}{/for}
