import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-multiple-body-sections")
        .title("Card title")
        .body("First body section")
        .body("Second body section")
        .body("Third body section")
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
