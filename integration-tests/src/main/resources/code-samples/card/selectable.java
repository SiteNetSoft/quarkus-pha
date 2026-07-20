import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Card> cards = List.of(
        Card.of("cd-selectable-1").selectable().selectedExpr("sel1")
                .header(Card.Header.create()
                        .selectCheckbox("cd-selectable-1-check", "cd-selectable-1-title", "sel1")
                        .title("First card", "cd-selectable-1-title"))
                .body("This card is selectable.")
                .build(),
        Card.of("cd-selectable-2").selectable().selectedExpr("sel2")
                .header(Card.Header.create()
                        .selectCheckbox("cd-selectable-2-check", "cd-selectable-2-title", "sel2")
                        .title("Second card", "cd-selectable-2-title"))
                .body("This card is selectable.")
                .build(),
        Card.of("cd-selectable-3").selectable().asDisabled()
                .header(Card.Header.create()
                        .selectCheckbox("cd-selectable-3-check", "cd-selectable-3-title", null)
                        .asSelectDisabled()
                        .title("Third card", "cd-selectable-3-title"))
                .body("This card is disabled.")
                .build());

// Template side, with the data in scope:
// {#for c in cards}{#include components/data-display/card card=c /}{/for}
