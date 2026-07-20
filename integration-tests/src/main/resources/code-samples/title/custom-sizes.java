import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Title> items = List.of(
        Title.of("4xl title").headingLevel("h1").size("4xl").build(),
        Title.of("3xl title").headingLevel("h2").size("3xl").build(),
        Title.of("2xl title").headingLevel("h3").size("2xl").build(),
        Title.of("xl title").headingLevel("h4").size("xl").build(),
        Title.of("lg title").headingLevel("h5").size("lg").build(),
        Title.of("md title").headingLevel("h6").size("md").build());

// Template side, with the data in scope:
// {#for x in items}{#include components/data-display/title titleModel=x /}{/for}
