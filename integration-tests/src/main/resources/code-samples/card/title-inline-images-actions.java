import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-title-inline-images-actions")
        .header(Card.Header.create()
                .kebab("Card actions")
                .checkbox("cd-title-inline-images-actions-check", "cd-title-inline-images-actions-title")
                .brandMain("/web/images/quarkus-pha-icon.svg", "Quarkus PHA", "32px")
                .mainTitle("This title sits in the card head", "cd-title-inline-images-actions-title"))
        .body("Image and title share the header main area, inline with the actions.")
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
