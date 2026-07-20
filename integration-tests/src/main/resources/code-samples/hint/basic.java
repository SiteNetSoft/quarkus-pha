import org.sitenetsoft.quarkus.pha.model.*;

Hint hint = Hint.of("ht-basic").title("Pro tip")
        .body("You can drag and drop files onto the upload zone — they'll auto-upload"
                + " in batches of five.").build();

// Template side, with the data in scope:
// {#include components/feedback/hint hint=hint /}
