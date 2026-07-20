import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-danger").staticOpen()
        .trigger("Delete").triggerVariant("danger").variant("danger")
        .chromeStyle("position: relative; display: inline-block; padding: 4rem 0")
        .wrapperStyle("position: absolute; bottom: 100%; left: 50%; transform: translateX(-50%);"
                + " width: 320px; visibility: visible")
        .titlePlain("Delete this project?")
        .body("This action cannot be undone. All associated data will be removed.")
        .footerButton("Delete", "pf-m-danger", false)
        .footerButton("Cancel", "pf-m-link", false).build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
