import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Content> items = List.of(
        Content.element("a").href("#default").text("Default link styling").build(),
        Content.element("a").href("#visited").visited().text("Visited link styling").build());

// Template side, with the data in scope:
// {#for c in items}{#include components/data-display/content contentModel=c /}{/for}
