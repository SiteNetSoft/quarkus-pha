import org.sitenetsoft.quarkus.pha.model.*;

ExpandableSection section = ExpandableSection
        .of("es-expanded", "Section open by default").asExpanded()
        .content("\n<p>Call <code>asExpanded()</code> on the builder to start the section in the open state.</p>\n")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/expandable-section section=section /}
