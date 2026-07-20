import org.sitenetsoft.quarkus.pha.model.*;

Alert alert = Alert.of("Inline alert").id("al-inline").variant("warning")
        .asInline()
        .description("Inline variant — sits inside content blocks without the boxed treatment.")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/alert alert=alert /}
