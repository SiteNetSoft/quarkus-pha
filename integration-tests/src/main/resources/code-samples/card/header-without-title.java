import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-header-without-title")
        .header(Card.Header.create()
                .kebab("Card actions")
                .checkbox("cd-header-without-title-check", "cd-header-without-title-title")
                .checkboxAriaLabel("Select card"))
        .body("This is the card body. The header above carries only actions — no title element at all.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
