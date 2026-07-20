import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-no-header-footer")
        .trigger("Show plain popover").ariaLabel("Plain popover")
        .body("Body only — no header, footer, close button, or padding.").build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
