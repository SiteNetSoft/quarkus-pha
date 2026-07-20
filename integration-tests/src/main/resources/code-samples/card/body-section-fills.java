import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-body-section-fills").fullHeight()
        .title("Card title")
        .bodyNoFill("Body section with <code>pf-m-no-fill</code>")
        .bodyNoFill("Another no-fill body section")
        .body("This default body section fills the remaining card height.")
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
