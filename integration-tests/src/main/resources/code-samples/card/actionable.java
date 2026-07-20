import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Card> cards = List.of(
        Card.of("cd-actionable-1").clickable()
                .header(Card.Header.create()
                        .clickButton("cd-actionable-1-title", "clicks++")
                        .title("First card", "cd-actionable-1-title"))
                .body("Performs an action on click — clicked <span x-text=\"clicks\">0</span> times.")
                .build(),
        Card.of("cd-actionable-2").clickable()
                .header(Card.Header.create()
                        .clickAnchor("#", "cd-actionable-2-title")
                        .title("Second card", "cd-actionable-2-title"))
                .body("This whole card is a link.")
                .build(),
        Card.of("cd-actionable-3").clickable().asDisabled()
                .header(Card.Header.create()
                        .clickButton("cd-actionable-3-title", null)
                        .asSelectDisabled()
                        .title("Third card", "cd-actionable-3-title"))
                .body("This clickable card is disabled.")
                .build());

// Template side, with the data in scope:
// {#for c in cards}{#include components/data-display/card card=c /}{/for}
