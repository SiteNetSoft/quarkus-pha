import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-success-alert").size("md").variant("success").alertPrefix()
        .trigger("Open success alert modal").title("success alert modal").headingLevel("h2")
        .body("<p>The status modifier colors the title bar icon and adds a screen-reader-only alert prefix to the title.</p>")
        .footerButton("Confirm", "primary")
        .footerButton("Cancel", "link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
