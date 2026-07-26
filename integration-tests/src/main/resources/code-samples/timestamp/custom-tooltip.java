import org.sitenetsoft.quarkus.pha.model.*;

Timestamp timestamp = Timestamp.of("8/9/2022, 2:57 PM")
        .id("ts-custom-tooltip").datetime("2022-08-09T14:57:00Z")
        .tooltip("Last updated on August 9th, 2022 - 2:57 PM UTC").build();

Timestamp withContent = Timestamp.of("Halloween")
        .id("ts-custom-tooltip-content").datetime("2022-10-31T00:00:00Z")
        .tooltip("31st of October, 2022").build();

// Template side, with the data in scope:
// {#include components/data-display/timestamp timestamp=timestamp /}
// {#include components/data-display/timestamp timestamp=withContent /}
