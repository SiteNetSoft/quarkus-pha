import org.sitenetsoft.quarkus.pha.model.*;

ExpandableSection section = ExpandableSection
        .of("es-collapsed", "Show advanced settings")
        .content("\n<p>Advanced settings only experienced users should touch — caching, retry policies, telemetry verbosity.</p>\n"
                + "<p>Default values work for most use cases.</p>\n")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/expandable-section section=section /}
