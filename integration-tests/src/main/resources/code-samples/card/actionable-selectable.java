import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-actionable-selectable-1")
        .clickable().selectable().selectedExpr("sel1")
        .header(Card.Header.create()
                .selectCheckbox("cd-actionable-selectable-1-check", "cd-actionable-selectable-1-title", "sel1")
                .title("<button class=\"pf-v6-c-card__clickable-action\" type=\"button\" @click=\"clicks++\" style=\"all: unset; cursor: pointer\">Clickable title</button>",
                        "cd-actionable-selectable-1-title"))
        .body("Selectable via the checkbox AND actionable via the title — clicked <span x-text=\"clicks\">0</span> times.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
