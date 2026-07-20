import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Card> cards = List.of(
        Card.of("cd-tile-1").selectable().selectedExpr("chosen === 'one'")
                .header(Card.Header.create()
                        .selectRadio("cd-tile-radio", "cd-tile-1-input", "one", "cd-tile-1-title", "chosen")
                        .mainIcon("fa:server")
                        .title("Tile one", "cd-tile-1-title"))
                .body("Pick exactly one tile.")
                .build(),
        Card.of("cd-tile-2").selectable().selectedExpr("chosen === 'two'")
                .header(Card.Header.create()
                        .selectRadio("cd-tile-radio", "cd-tile-2-input", "two", "cd-tile-2-title", "chosen")
                        .mainIcon("fa:server")
                        .title("Tile two", "cd-tile-2-title"))
                .body("Radio-backed selectable card.")
                .build(),
        Card.of("cd-tile-3").selectable().selectedExpr("chosen === 'three'")
                .header(Card.Header.create()
                        .selectRadio("cd-tile-radio", "cd-tile-3-input", "three", "cd-tile-3-title", "chosen")
                        .mainIcon("fa:server")
                        .title("Tile three", "cd-tile-3-title"))
                .body("Tiles are just selectable cards.")
                .build());

// Template side, with the data in scope:
// {#for c in cards}{#include components/data-display/card card=c /}{/for}
