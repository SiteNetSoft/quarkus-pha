import org.sitenetsoft.quarkus.pha.model.*;

ExpandableSection section = ExpandableSection
        .of("es-disclosure", "Disclosure variation").asDisclosure()
        .content("<p>The disclosure variation uses the large display size with a width limit.</p>")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/expandable-section section=section /}
