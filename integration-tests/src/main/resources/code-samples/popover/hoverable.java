import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-hoverable").hoverable()
        .trigger("Hover or focus me")
        .title("Hoverable popover")
        .body("This popover opens on hover or keyboard focus and closes when the pointer leaves.").build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
