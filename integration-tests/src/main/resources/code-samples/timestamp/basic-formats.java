import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Timestamp> timestamps = List.of(
        Timestamp.of("Wednesday, May 20, 2026 at 2:30:00 PM UTC")
                .id("ts-format-full").datetime("2026-05-20T14:30:00Z").build(),
        Timestamp.of("May 20, 2026 at 2:30:00 PM UTC")
                .id("ts-format-long").datetime("2026-05-20T14:30:00Z").build(),
        Timestamp.of("May 20, 2026, 2:30 PM")
                .id("ts-format-medium").datetime("2026-05-20T14:30:00Z").build(),
        Timestamp.of("5/20/26, 2:30 PM")
                .id("ts-format-short").datetime("2026-05-20T14:30:00Z").build());

// Template side, with the data in scope:
// {#for t in timestamps}{#include components/data-display/timestamp timestamp=t /}{/for}
