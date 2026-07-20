import org.sitenetsoft.quarkus.pha.model.*;

Timestamp timestamp = Timestamp.of("2 hours ago")
        .id("ts-tooltip").datetime("2026-05-20T14:30:00Z")
        .tooltip("2026-05-20 14:30:00 UTC").build();

// Template side, with the data in scope:
// {#include components/data-display/timestamp timestamp=timestamp /}
