import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Spinner> items = List.of(
        Spinner.of("Loading (2rem)").diameter("2rem").build(),
        Spinner.of("Loading (80px)").diameter("80px").build(),
        Spinner.of("Loading (6rem)").diameter("6rem").build());

// Template side, with the data in scope:
// {#for x in items}{#include components/feedback/spinner spinner=x /}{/for}
