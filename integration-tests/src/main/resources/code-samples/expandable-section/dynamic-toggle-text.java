import org.sitenetsoft.quarkus.pha.model.*;

ExpandableSection section = ExpandableSection
        .of("es-dynamic-toggle-text", null)
        .dynamicToggle("Show more", "Show less")
        .content("<p>This content is revealed by the toggle, whose label flips between Show more and Show less.</p>")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/expandable-section section=section /}
