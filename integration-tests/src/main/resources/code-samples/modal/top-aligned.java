import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-top-aligned").size("md").alignTop()
        .trigger("Open top-aligned modal")
        .title("Top aligned").headingLevel("h2")
        .body("<p><code class=\"ws-code\">pf-m-align-top</code> pins the dialog to the top of the viewport.</p>")
        .footerButton("Confirm", "pf-m-primary")
        .footerButton("Cancel", "pf-m-link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
