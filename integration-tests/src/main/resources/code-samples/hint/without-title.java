import org.sitenetsoft.quarkus.pha.model.*;

Hint hint = Hint.of("ht-without-title")
        .body("A hint without a title — just the body text drawing attention to something useful.")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/hint hint=hint /}
