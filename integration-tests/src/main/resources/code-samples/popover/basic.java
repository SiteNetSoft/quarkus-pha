import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-basic").trigger("Show popover")
        .title("Popover header").dismissable()
        .body("Popovers are triggered by click rather than hover. Click the trigger again,"
                + " the X, or outside to dismiss.")
        .footerText("Popover footer").build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
