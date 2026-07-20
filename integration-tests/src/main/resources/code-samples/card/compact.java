import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-compact").compact()
        .title("Compact card")
        .body("Tighter padding — useful in dense card grids.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
