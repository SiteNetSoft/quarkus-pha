import org.sitenetsoft.quarkus.pha.model.*;

Timestamp timestamp = Timestamp.of("May 20, 2026 at 2:30 PM")
        .id("ts-basic").datetime("2026-05-20T14:30:00Z").build();

// Template side, with the data in scope:
// {#include components/data-display/timestamp timestamp=timestamp /}
