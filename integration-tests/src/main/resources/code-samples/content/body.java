import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Content> items = List.of(
        Content.element("p").text("A standard paragraph rendered through the Content component."
                + " It uses PF v6's body font size, line height, and text color tokens.").build(),
        Content.element("small").text("Small text — typically used for captions, footnotes,"
                + " or secondary metadata.").build(),
        Content.element("blockquote").text("A blockquote with PF v6's editorial border accent"
                + " on the inline-start edge.").build(),
        Content.element("pre").text("Preformatted text. It preserves whitespace and line breaks.").build());

// Template side, with the data in scope:
// {#for c in items}{#include components/data-display/content contentModel=c /}{/for}
