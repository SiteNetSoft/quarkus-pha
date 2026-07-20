import org.sitenetsoft.quarkus.pha.model.*;

ExpandableSection section = ExpandableSection
        .of("es-indented", "Show indented content").asIndented()
        .content("<p>The content aligns under the toggle text instead of the icon (pf-m-indented).</p>")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/expandable-section section=section /}
