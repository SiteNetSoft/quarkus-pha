import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-custom-title-icon").size("md")
        .trigger("Open custom-icon modal")
        .title("Custom icon modal").headingLevel("h2").titleIcon("fa:rocket")
        .body("<p>Any icon can sit in the title icon slot.</p>")
        .footerButton("Confirm", "pf-m-primary")
        .footerButton("Cancel", "pf-m-link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
