import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-header-images-actions")
        .header(Card.Header.create()
                .kebab("Card actions")
                .checkbox("cd-header-images-actions-check", "cd-header-images-actions-title")
                .brand("/web/images/quarkus-pha-icon.svg", "Quarkus PHA", "40px"))
        .title("Card title", "cd-header-images-actions-title", null)
        .body("A brand image in the header with actions aligned to the end.")
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
