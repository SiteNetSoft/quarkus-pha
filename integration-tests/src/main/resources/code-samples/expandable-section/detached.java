import org.sitenetsoft.quarkus.pha.model.*;

ExpandableSection section = ExpandableSection
        .of("es-detached", "Show build details").asDetached()
        .content("<p>Build log details appear above the toggle that controls them.</p>")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/expandable-section section=section /}
