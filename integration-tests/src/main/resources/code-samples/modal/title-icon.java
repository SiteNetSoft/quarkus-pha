import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-title-icon").size("md").variant("warning")
        .trigger("Open warning modal")
        .title("Warning modal").headingLevel("h2")
        .body("<p>The <code class=\"ws-code\">pf-m-warning</code> variant colors the title icon.</p>")
        .footerButton("Confirm", "pf-m-primary")
        .footerButton("Cancel", "pf-m-link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
