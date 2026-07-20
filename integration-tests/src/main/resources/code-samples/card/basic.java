import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-basic")
        .title("Project Apollo")
        .body("Ship the new dashboard by end of quarter. Owner: Alice. Status: on track.")
        .footer("Last updated 2 hours ago")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
