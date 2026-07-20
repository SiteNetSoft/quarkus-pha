import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-without-title").size("md")
        .trigger("Open modal without a title")
        .ariaLabel("Example of a modal without a title")
        .body("<p>\n    When a modal has no title element, the modal box itself must carry an\n"
                + "    <code class=\"ws-code\">aria-label</code> naming the dialog for assistive"
                + " technologies.\n  </p>")
        .footerButton("Close", "pf-m-primary").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
