import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-subtitle-actions")
        .header(Card.Header.create()
                .kebab("Card actions")
                .checkbox("cd-subtitle-actions-check", "cd-subtitle-actions-title")
                .mainTitle("Card title", "cd-subtitle-actions-title")
                .mainSubtitle("Card subtitle"))
        .body("Header actions (kebab + checkbox) pair with a title + subtitle in the header main area.")
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
