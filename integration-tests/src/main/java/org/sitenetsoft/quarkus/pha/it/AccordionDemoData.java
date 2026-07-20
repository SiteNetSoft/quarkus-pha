package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Accordion;
import org.sitenetsoft.quarkus.pha.model.AccordionItem;

/**
 * Demo data for the accordion examples — every example on
 * /components/accordion is populated from one of these Accordion models.
 * Globals so the standalone example route (which renders templates without
 * data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/accordion/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class AccordionDemoData {

    public static Accordion demoAccBordered = Accordion.of("acc-bordered").bordered()
            .item(AccordionItem.of("acc-bordered-item-1", "Item one")
                    .body("This is the expandable content for item one. It provides details and additional information."))
            .item(AccordionItem.of("acc-bordered-item-2", "Item two")
                    .body("This is the expandable content for item two. It provides details and additional information."))
            .item(AccordionItem.of("acc-bordered-item-3", "Item three")
                    .body("This is the expandable content for item three. It provides details and additional information."))
            .item(AccordionItem.of("acc-bordered-item-4", "Item four")
                    .body("This is the expandable content for item four. It provides details and additional information."))
            .build();

    public static Accordion demoAccDefinitionList = Accordion.of("acc-dl").definitionList()
            .item(AccordionItem.of("acc-dl-item-1", "Item one")
                    .body("This is the expandable content for item one. It provides details and additional information."))
            .item(AccordionItem.of("acc-dl-item-2", "Item two")
                    .body("This is the expandable content for item two. It provides details and additional information."))
            .item(AccordionItem.of("acc-dl-item-3", "Item three")
                    .body("This is the expandable content for item three. It provides details and additional information."))
            .item(AccordionItem.of("acc-dl-item-4", "Item four")
                    .body("This is the expandable content for item four. It provides details and additional information."))
            .build();

    public static Accordion demoAccFixed = Accordion.of("acc-fixed")
            .item(AccordionItem.of("acc-fixed-item-1", "Item one").asFixed()
                    .body("<p>This is the expandable content for item one. The fixed modifier caps the expanded body at 9.375rem, so once the content runs taller than that the body scrolls instead of growing.</p><p>Use a fixed accordion for changelogs, license text, and long help answers — the reader keeps their place on the page while the details scroll independently inside the panel.</p><p>Nothing outside the accordion moves when this inner region scrolls; the scrollbar lives inside the item body.</p><p>These stacked paragraphs guarantee the body exceeds the cap at any viewport width.</p><p>Scroll inside the body to reach this final paragraph.</p>"))
            .item(AccordionItem.of("acc-fixed-item-2", "Item two").asFixed().body("This is the expandable content for item two. It provides details and additional information."))
            .item(AccordionItem.of("acc-fixed-item-3", "Item three").asFixed().body("This is the expandable content for item three. It provides details and additional information."))
            .item(AccordionItem.of("acc-fixed-item-4", "Item four").asFixed().body("This is the expandable content for item four. It provides details and additional information."))
            .build();

    public static Accordion demoAccLargeBordered = Accordion.of("acc-large-bordered").bordered().displayLg()
            .item(AccordionItem.of("acc-lg-item-1", "Item one").body("This is the expandable content for item one. It provides details and additional information."))
            .item(AccordionItem.of("acc-lg-item-2", "Item two").body("This is the expandable content for item two. It provides details and additional information."))
            .item(AccordionItem.of("acc-lg-item-3", "Item three").body(
                    "\n<p>This is the expandable content for item three, with a follow-up action for the reader.</p>\n"
                            + "<a href=\"#\" class=\"pf-v6-c-button pf-m-link pf-m-inline\"><span class=\"pf-v6-c-button__text\">Call to action</span></a>\n"))
            .build();

    public static Accordion demoAccSingleExpand = Accordion.of("acc-single").singleExpand()
            .item(AccordionItem.of("acc-single-item-1", "Item one").key("one")
                    .body("Body content for item one. Opening another item collapses this one."))
            .item(AccordionItem.of("acc-single-item-2", "Item two").key("two")
                    .body("Body content for item two."))
            .item(AccordionItem.of("acc-single-item-3", "Item three").key("three")
                    .body("Body content for item three."))
            .build();

    public static Accordion demoAccToggleStart = Accordion.of("acc-toggle-start").toggleStart()
            .item(AccordionItem.of("acc-toggle-start-item-1", "Item one")
                    .body("This is the expandable content for item one. It provides details and additional information."))
            .item(AccordionItem.of("acc-toggle-start-item-2", "Item two")
                    .body("This is the expandable content for item two. It provides details and additional information."))
            .item(AccordionItem.of("acc-toggle-start-item-3", "Item three")
                    .body("This is the expandable content for item three. It provides details and additional information."))
            .item(AccordionItem.of("acc-toggle-start-item-4", "Item four")
                    .body("This is the expandable content for item four. It provides details and additional information."))
            .build();
}
