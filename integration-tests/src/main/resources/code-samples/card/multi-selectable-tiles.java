import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Card> cards = List.of(
        Card.of("cd-mtile-1").selectable().selectedExpr("t1")
                .header(Card.Header.create()
                        .selectCheckbox("cd-mtile-1-input", "cd-mtile-1-title", "t1")
                        .mainIcon("fa:server")
                        .title("Tile one", "cd-mtile-1-title"))
                .body("Pick any number of tiles.")
                .build(),
        Card.of("cd-mtile-2").selectable().selectedExpr("t2")
                .header(Card.Header.create()
                        .selectCheckbox("cd-mtile-2-input", "cd-mtile-2-title", "t2")
                        .mainIcon("fa:server")
                        .title("Tile two", "cd-mtile-2-title"))
                .body("Checkbox-backed multi selection.")
                .build(),
        Card.of("cd-mtile-3").selectable().selectedExpr("t3")
                .header(Card.Header.create()
                        .selectCheckbox("cd-mtile-3-input", "cd-mtile-3-title", "t3")
                        .mainIcon("fa:server")
                        .title("Tile three", "cd-mtile-3-title"))
                .body("Toggle freely.")
                .build());

// Template side, with the data in scope:
// {#for c in cards}{#include components/data-display/card card=c /}{/for}
