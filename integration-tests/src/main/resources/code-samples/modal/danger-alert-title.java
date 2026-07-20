import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-danger-status-title").size("md")
        .variant("danger").statusTitle().alertPrefix()
        .trigger("Open danger alert modal (title modifier)")
        .title("danger alert modal").headingLevel("h2")
        .body("<p>\n    Here <code class=\"ws-code\">pf-m-danger</code> sits on the title element"
                + " instead of the modal box — the box carries\n    no status class.\n  </p>")
        .footerButton("Confirm", "pf-m-primary")
        .footerButton("Cancel", "pf-m-link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
