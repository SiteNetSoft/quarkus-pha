import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-expandable").expandable()
        .header(Card.Header.create()
                .withExpandToggle()
                .kebab("Card actions")
                .checkbox("cd-expandable-check", "cd-expandable-title")
                .title("Expandable card", "cd-expandable-title"))
        .expandableContent("The expandable content — hidden until the toggle is expanded.", "Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
