import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-info-alert").size("md").variant("info").alertPrefix()
        .trigger("Open info alert modal").title("info alert modal").headingLevel("h2")
        .body("<p>The status modifier colors the title bar icon and adds a screen-reader-only alert prefix to the title.</p>")
        .footerButton("Confirm", "pf-m-primary")
        .footerButton("Cancel", "pf-m-link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
