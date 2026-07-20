import org.sitenetsoft.quarkus.pha.model.*;

Accordion accordion = Accordion.of("acc-fixed")
        .item(AccordionItem.of("acc-fixed-item-1", "Item one").asFixed()
                .body("<p>This is the expandable content for item one. The fixed modifier caps the expanded body at 9.375rem, so once the content runs taller than that the body scrolls instead of growing.</p><p>Use a fixed accordion for changelogs, license text, and long help answers — the reader keeps their place on the page while the details scroll independently inside the panel.</p><p>Nothing outside the accordion moves when this inner region scrolls; the scrollbar lives inside the item body.</p><p>These stacked paragraphs guarantee the body exceeds the cap at any viewport width.</p><p>Scroll inside the body to reach this final paragraph.</p>"))
        .item(AccordionItem.of("acc-fixed-item-2", "Item two").asFixed().body("This is the expandable content for item two. It provides details and additional information."))
        .item(AccordionItem.of("acc-fixed-item-3", "Item three").asFixed().body("This is the expandable content for item three. It provides details and additional information."))
        .item(AccordionItem.of("acc-fixed-item-4", "Item four").asFixed().body("This is the expandable content for item four. It provides details and additional information."))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/accordion accordion=accordion /}
