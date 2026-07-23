import org.sitenetsoft.quarkus.pha.model.*;

Timestamp timestamp = Timestamp.of("2 hours ago")
        .id("ts-inline").datetime("2026-05-20T14:30:00Z").build();

// Template side, with the data in scope:
// {#include components/data-display/timestamp timestamp=timestamp /}
