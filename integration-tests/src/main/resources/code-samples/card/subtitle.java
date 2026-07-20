import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-subtitle")
        .header(Card.Header.create()
                .mainTitle("Card title", null)
                .mainSubtitle("Card subtitle"))
        .body("The subtitle sits in its own <code>pf-v6-c-card__subtitle</code> element below the title.")
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
