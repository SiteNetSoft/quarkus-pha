import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Title> items = List.of(
        Title.of("h1 — default").build(),
        Title.of("h2 — default").headingLevel("h2").build(),
        Title.of("h3 — default").headingLevel("h3").build(),
        Title.of("h4 — default").headingLevel("h4").build(),
        Title.of("h5 — default").headingLevel("h5").build(),
        Title.of("h6 — default").headingLevel("h6").build());

// Template side, with the data in scope:
// {#for x in items}{#include components/data-display/title titleModel=x /}{/for}
