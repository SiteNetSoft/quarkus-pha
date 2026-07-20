import org.sitenetsoft.quarkus.pha.model.*;

Alert alert = Alert.of("Static live region alert").id("al-live-static").variant("info")
        .asInline()
        .description("Screen readers announce updates to this region without moving focus.")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/alert alert=alert /}
