import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-custom-alert").size("md").variant("custom").alertPrefix()
        .trigger("Open custom alert modal").title("custom alert modal").headingLevel("h2")
        .body("<p>The status modifier colors the title bar icon and adds a screen-reader-only alert prefix to the title.</p>")
        .footerButton("Confirm", "pf-m-primary")
        .footerButton("Cancel", "pf-m-link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
