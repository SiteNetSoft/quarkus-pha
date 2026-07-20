import org.sitenetsoft.quarkus.pha.model.*;

ExpandableSection section = ExpandableSection
        .of("es-heading-semantics", "Section wrapped in an h2").headingLevel("h2")
        .content("\n<p>The toggle sits inside a real heading element so the section lands in the page outline.</p>\n")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/expandable-section section=section /}
