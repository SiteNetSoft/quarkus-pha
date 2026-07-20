import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-advanced")
        .trigger("Show advanced popover")
        .title("Advanced popover").titleIcon("fa:circle-info").dismissable()
        .body("Header with an icon, a dismissable close button, and footer actions together.")
        .footerButton("Dismiss", "pf-m-link pf-m-inline", true).build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
