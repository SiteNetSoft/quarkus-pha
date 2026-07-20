import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-with-dividers")
        .title("Card title")
        .divider()
        .body("First body section")
        .divider()
        .body("Second body section")
        .divider()
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
