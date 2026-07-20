import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-close-from-content")
        .trigger("Show popover")
        .title("Close from content").dismissable()
        .body("An action inside the popover body or footer can dismiss it — PF documents this"
                + " as the controlled and uncontrolled close patterns.")
        .footerButton("Got it", "pf-m-primary", true).build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
