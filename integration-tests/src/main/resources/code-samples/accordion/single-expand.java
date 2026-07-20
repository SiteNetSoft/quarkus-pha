import org.sitenetsoft.quarkus.pha.model.*;

Accordion accordion = Accordion.of("acc-single").singleExpand()
        .item(AccordionItem.of("acc-single-item-1", "Item one").key("one")
                .body("Body content for item one. Opening another item collapses this one."))
        .item(AccordionItem.of("acc-single-item-2", "Item two").key("two")
                .body("Body content for item two."))
        .item(AccordionItem.of("acc-single-item-3", "Item three").key("three")
                .body("Body content for item three."))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/accordion accordion=accordion /}
