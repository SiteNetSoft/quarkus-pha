import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Card> cards = List.of(
        Card.of("cd-secondary-1").secondary()
                .title("First card")
                .body("<code>pf-m-secondary</code> uses the secondary background color variant.")
                .build(),
        Card.of("cd-secondary-2").secondary()
                .title("Second card")
                .body("Secondary cards pair well on a primary surface.")
                .build(),
        Card.of("cd-secondary-3").secondary()
                .title("Third card")
                .body("All three share the secondary treatment.")
                .build());

// Template side, with the data in scope:
// {#for c in cards}{#include components/data-display/card card=c /}{/for}
