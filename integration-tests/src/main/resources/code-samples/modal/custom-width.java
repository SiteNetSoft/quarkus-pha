import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-custom-width").width("50%")
        .trigger("Open 50%-width modal")
        .title("Custom width").headingLevel("h2")
        .body("<p>An inline width (here 50%) replaces the size modifier.</p>")
        .footerButton("Confirm", "pf-m-primary").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
