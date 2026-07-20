import org.sitenetsoft.quarkus.pha.model.*;

Timestamp timestamp = Timestamp.of("3 weeks ago")
        .id("ts-custom-content").datetime("2026-05-20T14:30:00Z").build();

// Template side, with the data in scope:
// {#include components/data-display/timestamp timestamp=timestamp /}
