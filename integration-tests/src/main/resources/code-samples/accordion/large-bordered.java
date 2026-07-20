import org.sitenetsoft.quarkus.pha.model.*;

Accordion accordion = Accordion.of("acc-large-bordered").bordered().displayLg()
        .item(AccordionItem.of("acc-lg-item-1", "Item one").body("This is the expandable content for item one. It provides details and additional information."))
        .item(AccordionItem.of("acc-lg-item-2", "Item two").body("This is the expandable content for item two. It provides details and additional information."))
        .item(AccordionItem.of("acc-lg-item-3", "Item three").body(
                "\n<p>This is the expandable content for item three, with a follow-up action for the reader.</p>\n"
                        + "<a href=\"#\" class=\"pf-v6-c-button pf-m-link pf-m-inline\"><span class=\"pf-v6-c-button__text\">Call to action</span></a>\n"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/accordion accordion=accordion /}
