import org.sitenetsoft.quarkus.pha.model.*;

Card card = Card.of("cd-heading-element")
        .title("This title is an &lt;h4&gt;", null, "h4")
        .body("Pick the heading level that fits the page outline — the styling comes from the class, not the tag.")
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/card card=card /}
