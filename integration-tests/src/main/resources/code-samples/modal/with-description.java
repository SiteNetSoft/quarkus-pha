import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-with-description").size("md")
        .trigger("Open modal with description")
        .title("With a static description").headingLevel("h2")
        .description("A static description appears here, below the modal title.")
        .body("<p>The description sits in the header below the title and does not scroll with the body.</p>")
        .footerButton("Confirm", "pf-m-primary")
        .footerButton("Cancel", "pf-m-link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
