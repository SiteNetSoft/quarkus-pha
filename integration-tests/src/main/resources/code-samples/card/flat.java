import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-flat")
        .title("Flat card")
        .body("No shadow — sits flush against the background.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
