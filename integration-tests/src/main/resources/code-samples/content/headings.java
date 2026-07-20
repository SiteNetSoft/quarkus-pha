import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Content> items = List.of(
        Content.element("h1").text("First level heading").build(),
        Content.element("h2").text("Second level heading").build(),
        Content.element("h3").text("Third level heading").build(),
        Content.element("h4").text("Fourth level heading").build(),
        Content.element("h5").text("Fifth level heading").build(),
        Content.element("h6").text("Sixth level heading").build());

// Template side, with the data in scope:
// {#for c in items}{#include components/data-display/content contentModel=c /}{/for}
