import org.sitenetsoft.quarkus.pha.model.*;

ExpandableSection section = ExpandableSection
        .of("es-expanded", "Section open by default").asExpanded()
        .content("\n<p>Pass <code>expanded=true</code> to start the section in the open state.</p>\n")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/expandable-section section=section /}
