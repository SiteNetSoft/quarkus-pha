import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Card> cards = List.of(
        Card.of("cd-single-selectable-1").selectable().selectedExpr("chosen === 'first'")
                .header(Card.Header.create()
                        .selectRadio("cd-single-selectable-radio", "cd-single-selectable-1-radio",
                                "first", "cd-single-selectable-1-title", "chosen")
                        .title("First card", "cd-single-selectable-1-title"))
                .body("Only one card in this set can be selected.")
                .build(),
        Card.of("cd-single-selectable-2").selectable().selectedExpr("chosen === 'second'")
                .header(Card.Header.create()
                        .selectRadio("cd-single-selectable-radio", "cd-single-selectable-2-radio",
                                "second", "cd-single-selectable-2-title", "chosen")
                        .title("Second card", "cd-single-selectable-2-title"))
                .body("Radio-backed single selection.")
                .build(),
        Card.of("cd-single-selectable-3").selectable().selectedExpr("chosen === 'third'")
                .header(Card.Header.create()
                        .selectRadio("cd-single-selectable-radio", "cd-single-selectable-3-radio",
                                "third", "cd-single-selectable-3-title", "chosen")
                        .title("Third card", "cd-single-selectable-3-title"))
                .body("Pick me instead.")
                .build());

// Template side, with the data in scope:
// {#for c in cards}{#include components/data-display/card card=c /}{/for}
