import org.sitenetsoft.quarkus.pha.model.*;

Accordion accordion = Accordion.of("acc-toggle-start").toggleStart()
        .item(AccordionItem.of("acc-toggle-start-item-1", "Item one")
                .body("This is the expandable content for item one. It provides details and additional information."))
        .item(AccordionItem.of("acc-toggle-start-item-2", "Item two")
                .body("This is the expandable content for item two. It provides details and additional information."))
        .item(AccordionItem.of("acc-toggle-start-item-3", "Item three")
                .body("This is the expandable content for item three. It provides details and additional information."))
        .item(AccordionItem.of("acc-toggle-start-item-4", "Item four")
                .body("This is the expandable content for item four. It provides details and additional information."))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/accordion accordion=accordion /}
