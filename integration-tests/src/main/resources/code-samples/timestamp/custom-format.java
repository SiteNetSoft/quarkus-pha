import org.sitenetsoft.quarkus.pha.model.*;

Timestamp timestamp = Timestamp.of("2026-05-20 · 14:30 UTC")
        .id("ts-custom-format").datetime("2026-05-20T14:30:00Z").build();

// Template side, with the data in scope:
// {#include components/data-display/timestamp timestamp=timestamp /}
