import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-header-wraps")
        .header(Card.Header.create()
                .asWrapped()
                .kebab("Card actions")
                .checkbox("cd-header-wraps-check", "cd-header-wraps-title")
                .mainTitle("This is a really really really really really really really really really really long header",
                        "cd-header-wraps-title"))
        .body("<code>pf-m-wrap</code> on the header lets a long title wrap under the actions.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
