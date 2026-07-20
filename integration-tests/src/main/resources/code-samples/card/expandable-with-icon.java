import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-expandable-with-icon").expandable()
        .header(Card.Header.create()
                .withExpandToggle()
                .kebab("Card actions")
                .checkbox("cd-expandable-with-icon-check", "cd-expandable-with-icon-title")
                .checkboxAriaLabel("Select card")
                .brandMain("/web/images/quarkus-pha-icon.svg", "Quarkus PHA", "32px"))
        .expandableContent("A brand image takes the title&#39;s place in the header.", "Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
